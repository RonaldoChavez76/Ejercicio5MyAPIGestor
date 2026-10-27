package com.mx.cbtis.gestor;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class Ejercicio5MyApiGestorApplicationCoverageTest {

	@Test
	@DisplayName("Test constructor de Ejercicio5MyApiGestorApplication")
	void testAppConstructor() {
		Ejercicio5MyApiGestorApplication app = new Ejercicio5MyApiGestorApplication();
		assertNotNull(app);
	}
}
