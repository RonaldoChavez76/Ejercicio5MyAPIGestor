package com.mx.cbtis.gestor.modelo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ModelosCoverageTest {

	@Test
	@DisplayName("Test modelo Asignaciones completo")
	void testAsignaciones() {
		Asignaciones aVacio = new Asignaciones();
		assertNull(aVacio.getId_asignacion());
		assertNull(aVacio.getId_usuario());
		assertNull(aVacio.getId_tarea());
		assertNull(aVacio.getFecha_asignacion());

		Asignaciones a = new Asignaciones(1, 10, 20, "2026-10-27");
		assertEquals(Integer.valueOf(1), a.getId_asignacion());
		assertEquals(Integer.valueOf(10), a.getId_usuario());
		assertEquals(Integer.valueOf(20), a.getId_tarea());
		assertEquals("2026-10-27", a.getFecha_asignacion());

		a.setId_asignacion(2);
		a.setId_usuario(11);
		a.setId_tarea(21);
		a.setFecha_asignacion("2026-10-28");

		assertEquals(Integer.valueOf(2), a.getId_asignacion());
		assertEquals(Integer.valueOf(11), a.getId_usuario());
		assertEquals(Integer.valueOf(21), a.getId_tarea());
		assertEquals("2026-10-28", a.getFecha_asignacion());
	}

	@Test
	@DisplayName("Test modelo Comentarios completo")
	void testComentarios() {
		Comentarios cVacio = new Comentarios();
		assertNull(cVacio.getId_comentario());
		assertNull(cVacio.getId_usuario());
		assertNull(cVacio.getId_tarea());
		assertNull(cVacio.getTexto_comentario());
		assertNull(cVacio.getFecha_hora());

		Comentarios c = new Comentarios(100, "1", 200, "Comentario de prueba", "2026-10-27 12:00:00");
		assertEquals(Integer.valueOf(100), c.getId_comentario());
		assertEquals("1", c.getId_usuario());
		assertEquals(Integer.valueOf(200), c.getId_tarea());
		assertEquals("Comentario de prueba", c.getTexto_comentario());
		assertEquals("2026-10-27 12:00:00", c.getFecha_hora());

		c.setId_comentario(101);
		c.setId_usuario("2");
		c.setId_tarea(201);
		c.setTexto_comentario("Comentario editado");
		c.setFecha_hora("2026-10-27 13:00:00");

		assertEquals(Integer.valueOf(101), c.getId_comentario());
		assertEquals("2", c.getId_usuario());
		assertEquals(Integer.valueOf(201), c.getId_tarea());
		assertEquals("Comentario editado", c.getTexto_comentario());
		assertEquals("2026-10-27 13:00:00", c.getFecha_hora());
	}

	@Test
	@DisplayName("Test modelo Proyectos completo")
	void testProyectos() {
		Proyectos pVacio = new Proyectos();
		assertNull(pVacio.getId_proyecto());
		assertNull(pVacio.getNombre());
		assertNull(pVacio.getDescripcion());
		assertNull(pVacio.getFecha_inicio());
		assertNull(pVacio.getFecha_finalizacion());
		assertNull(pVacio.getEstado_proyecto());
		assertNull(pVacio.getFondo());

		Proyectos p = new Proyectos(1, "Proyecto 1", "Desc 1", "2026-01-01", "2026-12-31", "Activo", "fondo.jpg");
		assertEquals(Integer.valueOf(1), p.getId_proyecto());
		assertEquals("Proyecto 1", p.getNombre());
		assertEquals("Desc 1", p.getDescripcion());
		assertEquals("2026-01-01", p.getFecha_inicio());
		assertEquals("2026-12-31", p.getFecha_finalizacion());
		assertEquals("Activo", p.getEstado_proyecto());
		assertEquals("fondo.jpg", p.getFondo());

		p.setId_proyecto(2);
		p.setNombre("Proyecto 2");
		p.setDescripcion("Desc 2");
		p.setFecha_inicio("2026-02-01");
		p.setFecha_finalizacion("2026-11-30");
		p.setEstado_proyecto("Completado");
		p.setFondo("fondo2.jpg");

		assertEquals(Integer.valueOf(2), p.getId_proyecto());
		assertEquals("Proyecto 2", p.getNombre());
		assertEquals("Desc 2", p.getDescripcion());
		assertEquals("2026-02-01", p.getFecha_inicio());
		assertEquals("2026-11-30", p.getFecha_finalizacion());
		assertEquals("Completado", p.getEstado_proyecto());
		assertEquals("fondo2.jpg", p.getFondo());
	}

	@Test
	@DisplayName("Test modelo Tareas completo")
	void testTareas() {
		Tareas tVacia = new Tareas();
		assertNull(tVacia.getId_tarea());
		assertNull(tVacia.getNombre());
		assertNull(tVacia.getDescripcion());
		assertNull(tVacia.getFecha_vencimiento());
		assertNull(tVacia.getLista());
		assertNull(tVacia.getEstado());
		assertNull(tVacia.getId_proyecto());

		Tareas t = new Tareas(10, "Tarea 1", "Desc tarea", "2026-10-31", "pendiente", "activo", 50);
		assertEquals(Integer.valueOf(10), t.getId_tarea());
		assertEquals("Tarea 1", t.getNombre());
		assertEquals("Desc tarea", t.getDescripcion());
		assertEquals("2026-10-31", t.getFecha_vencimiento());
		assertEquals("pendiente", t.getLista());
		assertEquals("activo", t.getEstado());
		assertEquals(Integer.valueOf(50), t.getId_proyecto());

		t.setId_tarea(11);
		t.setNombre("Tarea 2");
		t.setDescripcion("Desc tarea 2");
		t.setFecha_vencimiento("2026-11-15");
		t.setLista("en_proceso");
		t.setEstado("inactivo");
		t.setId_proyecto(51);

		assertEquals(Integer.valueOf(11), t.getId_tarea());
		assertEquals("Tarea 2", t.getNombre());
		assertEquals("Desc tarea 2", t.getDescripcion());
		assertEquals("2026-11-15", t.getFecha_vencimiento());
		assertEquals("en_proceso", t.getLista());
		assertEquals("inactivo", t.getEstado());
		assertEquals(Integer.valueOf(51), t.getId_proyecto());
	}

	@Test
	@DisplayName("Test modelo Usuario completo")
	void testUsuario() {
		Usuario uVacio = new Usuario();
		assertNull(uVacio.getId_usuario());
		assertNull(uVacio.getNombre());
		assertNull(uVacio.getCorreo_electronico());
		assertNull(uVacio.getContrasena());
		assertNull(uVacio.getRol());
		assertNull(uVacio.getImagen_usuario());

		Usuario u = new Usuario(1, "Santiago", "santiago@test.com", "pass123", "admin", "avatar.png");
		assertEquals(Integer.valueOf(1), u.getId_usuario());
		assertEquals("Santiago", u.getNombre());
		assertEquals("santiago@test.com", u.getCorreo_electronico());
		assertEquals("pass123", u.getContrasena());
		assertEquals("admin", u.getRol());
		assertEquals("avatar.png", u.getImagen_usuario());

		u.setId_usuario(2);
		u.setNombre("Ronaldo");
		u.setCorreo_electronico("ronaldo@test.com");
		u.setContrasena("newpass");
		u.setRol("user");
		u.setImagen_usuario("avatar2.png");

		assertEquals(Integer.valueOf(2), u.getId_usuario());
		assertEquals("Ronaldo", u.getNombre());
		assertEquals("ronaldo@test.com", u.getCorreo_electronico());
		assertEquals("newpass", u.getContrasena());
		assertEquals("user", u.getRol());
		assertEquals("avatar2.png", u.getImagen_usuario());
	}
}
