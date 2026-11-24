#!/usr/bin/env bash
set -e

if [ "${USE_INTERNAL_DB}" = "true" ]; then
    echo "=== [Render Cloud] Inicializando MariaDB embebida en contenedor ==="
    mkdir -p /var/run/mysqld
    chown -R mysql:mysql /var/run/mysqld /var/lib/mysql

    if [ ! -d "/var/lib/mysql/mysql" ]; then
        mariadb-install-db --user=mysql --datadir=/var/lib/mysql >/dev/null
    fi

    mariadbd --user=mysql --datadir=/var/lib/mysql --bind-address=127.0.0.1 --port=3306 --innodb-buffer-pool-size=32M --performance-schema=OFF --max-connections=30 &

    for i in $(seq 1 30); do
        if mariadb-admin ping --silent 2>/dev/null || mariadb-admin ping -h 127.0.0.1 --silent 2>/dev/null; then
            break
        fi
        sleep 1
    done

    DB_PASS="${SPRING_DATASOURCE_PASSWORD:-supersecreto}"
    mariadb -u root -e "ALTER USER IF EXISTS 'root'@'localhost' IDENTIFIED VIA mysql_native_password USING PASSWORD('${DB_PASS}'); CREATE USER IF NOT EXISTS 'root'@'127.0.0.1' IDENTIFIED BY '${DB_PASS}'; ALTER USER 'root'@'127.0.0.1' IDENTIFIED BY '${DB_PASS}'; GRANT ALL PRIVILEGES ON *.* TO 'root'@'127.0.0.1' WITH GRANT OPTION; GRANT ALL PRIVILEGES ON *.* TO 'root'@'localhost' WITH GRANT OPTION; FLUSH PRIVILEGES;"
    mariadb -h 127.0.0.1 -u root -p"${DB_PASS}" -e "CREATE DATABASE IF NOT EXISTS dbgestor;"

    TABLE_COUNT=$(mariadb -h 127.0.0.1 -u root -p"${DB_PASS}" -sN -e "SELECT COUNT(*) FROM information_schema.tables WHERE table_schema='dbgestor' AND table_name='usuario';")
    if [ "${TABLE_COUNT}" = "0" ] && [ -f "/app/dbgestor_init.sql" ]; then
        echo "=== Importando esquema y datos iniciales de dbgestor ==="
        mariadb -h 127.0.0.1 -u root -p"${DB_PASS}" dbgestor < /app/dbgestor_init.sql
    fi
fi

echo "=== Iniciando Ejercicio5MyAPIGestor (Java 21) ==="
exec java ${JAVA_OPTS:--Xms96m -Xmx224m -XX:+UseSerialGC} -jar /app/app.jar
