package com.mx.cbtis.gestor.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.mx.cbtis.gestor.modelo.Comentarios;
import com.mx.cbtis.gestor.modelo.Proyectos;
import com.mx.cbtis.gestor.modelo.Tareas;
import com.mx.cbtis.gestor.repository.ComentariosRepository;
import com.mx.cbtis.gestor.repository.ProtectosRepository;
import com.mx.cbtis.gestor.repository.TareasRepository;
import com.mx.cbtis.gestor.repository.UsuarioRepository;

/**
 * Pruebas automáticas (Práctica 2, Actividad 2).
 * Tres casos de éxito (AUT-E01 a AUT-E03) y tres escenarios negativos
 * (AUT-F01 a AUT-F03). Los casos AUT-F comprueban que la API maneje el error
 * esperado; por ello, deben pasar cuando se ejecuta la suite.
 * Los repositorios se simulan con Mockito, por lo que no se requiere la base de datos.
 * Los datos de prueba corresponden a los registros de la base dbgestor:
 * proyecto 197, tarea 199 y comentarios 201 y 202.
 */
@WebMvcTest({ ProyectosController.class, TareasController.class,
		ComentariosController.class, UsuarioController.class })
class GestorApiAutomaticTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ProtectosRepository proyectosRepository;

	@MockBean
	private TareasRepository tareasRepository;

	@MockBean
	private ComentariosRepository comentariosRepository;

	@MockBean
	private UsuarioRepository usuarioRepository;

	// ---------------------------- CASOS DE ÉXITO ----------------------------

	@Test
	@DisplayName("AUT-E01 (API-22): ProyectosController devuelve la lista de proyectos")
	void AUT_E01_consultaProyectosExito() throws Exception {
		Proyectos proyecto = new Proyectos(197, "My API Rest", "Proyecto de la API REST",
				"2024-05-31", "2024-06-29", "En proceso", "/Images/animacion1.gif");
		when(proyectosRepository.findAll()).thenReturn(Arrays.asList(proyecto));

		mockMvc.perform(post("/buscarProyectos"))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.length()").value(1))
				.andExpect(jsonPath("$[0].id_proyecto").value(197))
				.andExpect(jsonPath("$[0].nombre").value("My API Rest"));
		verify(proyectosRepository).findAll();
	}

	@Test
	@DisplayName("AUT-E02 (API-08): TareasController guarda una tarea con datos válidos")
	void AUT_E02_registroTareaExito() throws Exception {
		String json = "{\"nombre\":\"Tarea 12\",\"descripcion\":\"Tarea de prueba automática\","
				+ "\"fecha_vencimiento\":\"2026-10-15\",\"lista\":\"pendiente\",\"estado\":\"activo\","
				+ "\"id_proyecto\":197}";

		mockMvc.perform(post("/addTareas").contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(status().isOk())
				.andExpect(content().string("1"));

		ArgumentCaptor<Tareas> captor = ArgumentCaptor.forClass(Tareas.class);
		verify(tareasRepository).save(captor.capture());
		Tareas tareaGuardada = captor.getValue();
		assertEquals("Tarea 12", tareaGuardada.getNombre());
		assertEquals("Tarea de prueba automática", tareaGuardada.getDescripcion());
		assertEquals("2026-10-15", tareaGuardada.getFecha_vencimiento());
		assertEquals("pendiente", tareaGuardada.getLista());
		assertEquals("activo", tareaGuardada.getEstado());
		assertEquals(Integer.valueOf(197), tareaGuardada.getId_proyecto());
	}

	@Test
	@DisplayName("AUT-E03 (API-24): ComentariosController devuelve los comentarios de la tarea 199")
	void AUT_E03_consultaComentariosExito() throws Exception {
		Comentarios c1 = new Comentarios(201, "Perla Joceline Martínez Meza", 199, "Subirlo YA!!!", "2024-06-26 13:48:00");
		Comentarios c2 = new Comentarios(202, "Liliana Gómez Martínez", 199, "hola", "2024-06-26 13:49:00");
		when(comentariosRepository.findByTarea(199)).thenReturn(Arrays.asList(c1, c2));

		mockMvc.perform(post("/getComentario").contentType(MediaType.APPLICATION_JSON)
				.content("{\"id_tarea\":199}"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.length()").value(2))
				.andExpect(jsonPath("$[0].id_comentario").value(201))
				.andExpect(jsonPath("$[0].id_tarea").value(199))
				.andExpect(jsonPath("$[0].texto_comentario").value("Subirlo YA!!!"))
				.andExpect(jsonPath("$[1].id_comentario").value(202))
				.andExpect(jsonPath("$[1].id_tarea").value(199))
				.andExpect(jsonPath("$[1].texto_comentario").value("hola"));
	}

	// --------------------------- CASOS DE FRACASO ---------------------------

	@Test
	@DisplayName("AUT-F01 (API-13-N1): escenario negativo; login inválido responde nombre 000 sin sesión")
	void AUT_F01_loginCredencialesIncorrectas() throws Exception {
		when(usuarioRepository.findByCorreoPass(any(), any())).thenReturn(Optional.empty());

		mockMvc.perform(post("/getUsuarioLogin").contentType(MediaType.APPLICATION_JSON)
				.content("{\"correo_electronico\":\"usuario131@prueba.local\",\"contrasena\":\"incorrecta\"}"))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.nombre").value("000"))
				.andExpect(jsonPath("$.id_usuario").value(nullValue()))
				.andExpect(jsonPath("$.correo_electronico").value(nullValue()))
				.andExpect(jsonPath("$.contrasena").value(nullValue()))
				.andExpect(jsonPath("$.rol").value(nullValue()))
				.andExpect(jsonPath("$.imagen_usuario").value(nullValue()));
		verify(usuarioRepository).findByCorreoPass("usuario131@prueba.local", "incorrecta");
	}

	@Test
	@DisplayName("AUT-F02 (API-06-N1): escenario negativo; tarea inexistente responde un objeto vacío")
	void AUT_F02_consultaTareaInexistente() throws Exception {
		when(tareasRepository.findById(999999)).thenReturn(Optional.empty());

		mockMvc.perform(get("/buscarTarea").param("id_tarea", "999999"))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id_tarea").value(nullValue()))
				.andExpect(jsonPath("$.nombre").value(nullValue()))
				.andExpect(jsonPath("$.descripcion").value(nullValue()))
				.andExpect(jsonPath("$.fecha_vencimiento").value(nullValue()))
				.andExpect(jsonPath("$.lista").value(nullValue()))
				.andExpect(jsonPath("$.estado").value(nullValue()))
				.andExpect(jsonPath("$.id_proyecto").value(nullValue()));
		verify(tareasRepository).findById(999999);
	}

	@Test
	@DisplayName("AUT-F03 (API-08-N1): escenario negativo; JSON mal formado responde 400 y no guarda")
	void AUT_F03_registroTareaJsonInvalido() throws Exception {
		mockMvc.perform(post("/addTareas").contentType(MediaType.APPLICATION_JSON)
				.content("{\"nombre\": "))
				.andExpect(status().isBadRequest());

		verify(tareasRepository, never()).save(any(Tareas.class));
	}
}
