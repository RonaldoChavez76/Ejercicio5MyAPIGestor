package com.mx.cbtis.gestor;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mockStatic;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.boot.SpringApplication;

class Ejercicio5MyApiGestorApplicationCoverageTest {

	@Test
	@DisplayName("Test constructor de Ejercicio5MyApiGestorApplication")
	void testAppConstructor() {
		Ejercicio5MyApiGestorApplication app = new Ejercicio5MyApiGestorApplication();
		assertNotNull(app);
	}

	@Test
	@DisplayName("Test main method de Ejercicio5MyApiGestorApplication")
	void testMainMethod() {
		try (MockedStatic<SpringApplication> mockedSpring = mockStatic(SpringApplication.class)) {
			mockedSpring.when(() -> SpringApplication.run(Ejercicio5MyApiGestorApplication.class, new String[]{}))
					.thenReturn(null);
			Ejercicio5MyApiGestorApplication.main(new String[]{});
			mockedSpring.verify(() -> SpringApplication.run(Ejercicio5MyApiGestorApplication.class, new String[]{}));
		}
	}
}
