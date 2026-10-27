package com.mx.cbtis.gestor.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mx.cbtis.gestor.modelo.Asignaciones;
import com.mx.cbtis.gestor.modelo.Comentarios;
import com.mx.cbtis.gestor.modelo.Proyectos;
import com.mx.cbtis.gestor.modelo.Tareas;
import com.mx.cbtis.gestor.modelo.Usuario;
import com.mx.cbtis.gestor.repository.AsignacionesRepository;
import com.mx.cbtis.gestor.repository.ComentariosRepository;
import com.mx.cbtis.gestor.repository.ProtectosRepository;
import com.mx.cbtis.gestor.repository.TareasRepository;
import com.mx.cbtis.gestor.repository.UsuarioRepository;

@WebMvcTest({
		AsignacionesController.class,
		ComentariosController.class,
		ProyectosController.class,
		TareasController.class,
		UsuarioController.class
})
class BackendControllersCoverageTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private AsignacionesController asignacionesController;

	@Autowired
	private ComentariosController comentariosController;

	@Autowired
	private ProyectosController proyectosController;

	@Autowired
	private TareasController tareasController;

	@Autowired
	private UsuarioController usuarioController;

	@MockBean
	private AsignacionesRepository asignacionesRepository;

	@MockBean
	private ComentariosRepository comentariosRepository;

	@MockBean
	private ProtectosRepository proyectosRepository;

	@MockBean
	private TareasRepository tareasRepository;

	@MockBean
	private UsuarioRepository usuarioRepository;

	// ==================== ASIGNACIONES CONTROLLER ====================

	@Test
	@DisplayName("Asignaciones: buscarAsignacion encontrada")
	void testBuscarAsignacionExistente() throws Exception {
		Asignaciones a = new Asignaciones(1, 10, 20, "2026-10-27");
		when(asignacionesRepository.findById(1)).thenReturn(Optional.of(a));

		mockMvc.perform(get("/buscarAsignacion").param("id", "1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id_asignacion", is(1)))
				.andExpect(jsonPath("$.id_usuario", is(10)))
				.andExpect(jsonPath("$.id_tarea", is(20)))
				.andExpect(jsonPath("$.fecha_asignacion", is("2026-10-27")));
	}

	@Test
	@DisplayName("Asignaciones: buscarAsignacion inexistente")
	void testBuscarAsignacionInexistente() throws Exception {
		when(asignacionesRepository.findById(999)).thenReturn(Optional.empty());

		mockMvc.perform(get("/buscarAsignacion").param("id", "999"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id_asignacion", nullValue()));
	}

	@Test
	@DisplayName("Asignaciones: addAsignacion")
	void testAddAsignacion() throws Exception {
		Asignaciones a = new Asignaciones(1, 10, 20, "2026-10-27");

		mockMvc.perform(post("/addAsignacion")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(a)))
				.andExpect(status().isOk())
				.andExpect(content().string("1"));

		verify(asignacionesRepository).save(any(Asignaciones.class));
	}

	@Test
	@DisplayName("Asignaciones: updateAsignacion")
	void testUpdateAsignacion() throws Exception {
		Asignaciones a = new Asignaciones(1, 10, 20, "2026-10-28");

		mockMvc.perform(post("/updateAsignacion")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(a)))
				.andExpect(status().isOk())
				.andExpect(content().string("1"));

		verify(asignacionesRepository).save(any(Asignaciones.class));
	}

	@Test
	@DisplayName("Asignaciones: deleteAsignacion")
	void testDeleteAsignacion() throws Exception {
		Asignaciones a = new Asignaciones(1, 10, 20, "2026-10-28");

		mockMvc.perform(delete("/deleteAsignacion")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(a)))
				.andExpect(status().isOk())
				.andExpect(content().string("1"));

		verify(asignacionesRepository).delete(any(Asignaciones.class));
	}

	@Test
	@DisplayName("Asignaciones: buscarAsignaciones (lista)")
	void testBuscarAsignaciones() throws Exception {
		Asignaciones a1 = new Asignaciones(1, 10, 20, "2026-10-27");
		Asignaciones a2 = new Asignaciones(2, 11, 21, "2026-10-28");
		when(asignacionesRepository.findAll()).thenReturn(Arrays.asList(a1, a2));

		mockMvc.perform(post("/buscarAsignaciones"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].id_asignacion", is(1)))
				.andExpect(jsonPath("$[1].id_asignacion", is(2)));
	}

	// ==================== COMENTARIOS CONTROLLER ====================

	@Test
	@DisplayName("Comentarios: buscarComentario existente")
	void testBuscarComentarioExistente() throws Exception {
		Comentarios c = new Comentarios(1, "10", 20, "Buen trabajo", "2026-10-27 10:00");
		when(comentariosRepository.findById(1)).thenReturn(Optional.of(c));

		mockMvc.perform(get("/buscarComentario").param("id", "1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id_comentario", is(1)))
				.andExpect(jsonPath("$.texto_comentario", is("Buen trabajo")));
	}

	@Test
	@DisplayName("Comentarios: buscarComentario inexistente")
	void testBuscarComentarioInexistente() throws Exception {
		when(comentariosRepository.findById(999)).thenReturn(Optional.empty());

		mockMvc.perform(get("/buscarComentario").param("id", "999"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id_comentario", nullValue()));
	}

	@Test
	@DisplayName("Comentarios: addComentario")
	void testAddComentario() throws Exception {
		Comentarios c = new Comentarios(1, "10", 20, "Nuevo comentario", "2026-10-27 10:00");

		mockMvc.perform(post("/addComentario")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(c)))
				.andExpect(status().isOk())
				.andExpect(content().string("1"));

		verify(comentariosRepository).save(any(Comentarios.class));
	}

	@Test
	@DisplayName("Comentarios: updateComentario")
	void testUpdateComentario() throws Exception {
		Comentarios c = new Comentarios(1, "10", 20, "Comentario modificado", "2026-10-27 10:30");

		mockMvc.perform(post("/updateComentario")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(c)))
				.andExpect(status().isOk())
				.andExpect(content().string("1"));

		verify(comentariosRepository).save(any(Comentarios.class));
	}

	@Test
	@DisplayName("Comentarios: deleteComentario")
	void testDeleteComentario() throws Exception {
		Comentarios c = new Comentarios(1, "10", 20, "Comentario a borrar", "2026-10-27 10:30");

		mockMvc.perform(delete("/deleteComentario")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(c)))
				.andExpect(status().isOk())
				.andExpect(content().string("1"));

		verify(comentariosRepository).delete(any(Comentarios.class));
	}

	@Test
	@DisplayName("Comentarios: buscarComentarios (lista)")
	void testBuscarComentarios() throws Exception {
		Comentarios c1 = new Comentarios(1, "10", 20, "C1", "2026-10-27 10:00");
		Comentarios c2 = new Comentarios(2, "11", 20, "C2", "2026-10-27 11:00");
		when(comentariosRepository.findAll()).thenReturn(Arrays.asList(c1, c2));

		mockMvc.perform(post("/buscarComentarios"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].texto_comentario", is("C1")))
				.andExpect(jsonPath("$[1].texto_comentario", is("C2")));
	}

	// ==================== PROYECTOS CONTROLLER ====================

	@Test
	@DisplayName("Proyectos: buscarProyecto existente")
	void testBuscarProyectoExistente() throws Exception {
		Proyectos p = new Proyectos(1, "Gestor Proyectos", "Sistema de gestión", "2026-01-01", "2026-12-31", "Activo", "fondo.png");
		when(proyectosRepository.findById(1)).thenReturn(Optional.of(p));

		mockMvc.perform(get("/buscarProyecto").param("id_proyecto", "1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id_proyecto", is(1)))
				.andExpect(jsonPath("$.nombre", is("Gestor Proyectos")));
	}

	@Test
	@DisplayName("Proyectos: buscarProyecto inexistente")
	void testBuscarProyectoInexistente() throws Exception {
		when(proyectosRepository.findById(999)).thenReturn(Optional.empty());

		mockMvc.perform(get("/buscarProyecto").param("id_proyecto", "999"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id_proyecto", nullValue()));
	}

	@Test
	@DisplayName("Proyectos: addProyecto")
	void testAddProyecto() throws Exception {
		Proyectos p = new Proyectos(1, "Nuevo Proyecto", "Desc", "2026-01-01", "2026-12-31", "Activo", "fondo.png");

		mockMvc.perform(post("/addProyecto")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(p)))
				.andExpect(status().isOk())
				.andExpect(content().string("1"));

		verify(proyectosRepository).save(any(Proyectos.class));
	}

	@Test
	@DisplayName("Proyectos: updateProyecto")
	void testUpdateProyecto() throws Exception {
		Proyectos p = new Proyectos(1, "Proyecto Actualizado", "Desc", "2026-01-01", "2026-12-31", "En proceso", "fondo.png");

		mockMvc.perform(post("/updateProyecto")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(p)))
				.andExpect(status().isOk())
				.andExpect(content().string("1"));

		verify(proyectosRepository).save(any(Proyectos.class));
	}

	@Test
	@DisplayName("Proyectos: deleteProyecto")
	void testDeleteProyecto() throws Exception {
		Proyectos p = new Proyectos(1, "Proyecto a borrar", "Desc", "2026-01-01", "2026-12-31", "Cerrado", "fondo.png");

		mockMvc.perform(delete("/deleteProyecto")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(p)))
				.andExpect(status().isOk())
				.andExpect(content().string("1"));

		verify(proyectosRepository).delete(any(Proyectos.class));
	}

	// ==================== TAREAS CONTROLLER ====================

	@Test
	@DisplayName("Tareas: getTarea por id_proyecto")
	void testGetTareaPorProyecto() throws Exception {
		Tareas t1 = new Tareas(1, "T1", "Desc1", "2026-10-30", "pendiente", "activo", 50);
		Tareas t2 = new Tareas(2, "T2", "Desc2", "2026-10-31", "en_proceso", "activo", 50);
		when(tareasRepository.findByIdProyecto(50)).thenReturn(Arrays.asList(t1, t2));

		Tareas req = new Tareas();
		req.setId_proyecto(50);

		mockMvc.perform(post("/getTarea")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(req)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].nombre", is("T1")))
				.andExpect(jsonPath("$[1].nombre", is("T2")));
	}

	@Test
	@DisplayName("Tareas: buscarTarea existente")
	void testBuscarTareaExistente() throws Exception {
		Tareas t = new Tareas(1, "Tarea A", "Desc A", "2026-10-30", "pendiente", "activo", 50);
		when(tareasRepository.findById(1)).thenReturn(Optional.of(t));

		mockMvc.perform(get("/buscarTarea").param("id_tarea", "1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id_tarea", is(1)))
				.andExpect(jsonPath("$.nombre", is("Tarea A")));
	}

	@Test
	@DisplayName("Tareas: updateTareas")
	void testUpdateTareas() throws Exception {
		Tareas t = new Tareas(1, "Tarea A Actualizada", "Desc A", "2026-10-30", "completada", "activo", 50);

		mockMvc.perform(post("/updateTareas")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(t)))
				.andExpect(status().isOk())
				.andExpect(content().string("1"));

		verify(tareasRepository).save(any(Tareas.class));
	}

	@Test
	@DisplayName("Tareas: deleteTareas")
	void testDeleteTareas() throws Exception {
		Tareas t = new Tareas(1, "Tarea A", "Desc A", "2026-10-30", "pendiente", "activo", 50);

		mockMvc.perform(delete("/deleteTareas")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(t)))
				.andExpect(status().isOk())
				.andExpect(content().string("1"));

		verify(tareasRepository).delete(any(Tareas.class));
	}

	@Test
	@DisplayName("Tareas: buscarTareas (lista)")
	void testBuscarTareas() throws Exception {
		Tareas t1 = new Tareas(1, "T1", "Desc1", "2026-10-30", "pendiente", "activo", 50);
		when(tareasRepository.findAll()).thenReturn(Arrays.asList(t1));

		mockMvc.perform(post("/buscarTareas"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$[0].nombre", is("T1")));
	}

	// ==================== USUARIO CONTROLLER ====================

	@Test
	@DisplayName("Usuario: getUsuarioLogin exitoso")
	void testGetUsuarioLoginExitoso() throws Exception {
		Usuario u = new Usuario(1, "Admin", "admin@cbtis.com", "secret", "administrador", "user.jpg");
		when(usuarioRepository.findByCorreoPass("admin@cbtis.com", "secret")).thenReturn(Optional.of(u));

		Usuario loginParam = new Usuario();
		loginParam.setCorreo_electronico("admin@cbtis.com");
		loginParam.setContrasena("secret");

		mockMvc.perform(post("/getUsuarioLogin")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(loginParam)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id_usuario", is(1)))
				.andExpect(jsonPath("$.nombre", is("Admin")));
	}

	@Test
	@DisplayName("Usuario: buscarUsuario existente")
	void testBuscarUsuarioExistente() throws Exception {
		Usuario u = new Usuario(1, "Admin", "admin@cbtis.com", "secret", "administrador", "user.jpg");
		when(usuarioRepository.findById(1)).thenReturn(Optional.of(u));

		mockMvc.perform(get("/buscarUsuario").param("id", "1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id_usuario", is(1)))
				.andExpect(jsonPath("$.nombre", is("Admin")));
	}

	@Test
	@DisplayName("Usuario: buscarUsuario inexistente")
	void testBuscarUsuarioInexistente() throws Exception {
		when(usuarioRepository.findById(999)).thenReturn(Optional.empty());

		mockMvc.perform(get("/buscarUsuario").param("id", "999"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id_usuario", nullValue()));
	}

	@Test
	@DisplayName("Usuario: addUsuario")
	void testAddUsuario() throws Exception {
		Usuario u = new Usuario(1, "Santiago", "santiago@test.com", "1234", "estudiante", "santiago.jpg");

		mockMvc.perform(post("/addUsuario")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(u)))
				.andExpect(status().isOk())
				.andExpect(content().string("1"));

		verify(usuarioRepository).save(any(Usuario.class));
	}

	@Test
	@DisplayName("Usuario: updateUsuario")
	void testUpdateUsuario() throws Exception {
		Usuario u = new Usuario(1, "Santiago Chavez", "santiago@test.com", "newpass", "estudiante", "santiago.jpg");

		mockMvc.perform(post("/updateUsuario")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(u)))
				.andExpect(status().isOk())
				.andExpect(content().string("1"));

		verify(usuarioRepository).save(any(Usuario.class));
	}

	@Test
	@DisplayName("Usuario: deleteUsuario")
	void testDeleteUsuario() throws Exception {
		Usuario u = new Usuario(1, "Santiago", "santiago@test.com", "1234", "estudiante", "santiago.jpg");

		mockMvc.perform(delete("/deleteUsuario")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(u)))
				.andExpect(status().isOk())
				.andExpect(content().string("1"));

		verify(usuarioRepository).delete(any(Usuario.class));
	}

	@Test
	@DisplayName("Usuario: buscarUsuarios (lista)")
	void testBuscarUsuarios() throws Exception {
		Usuario u1 = new Usuario(1, "Santiago", "santiago@test.com", "1234", "estudiante", "s.jpg");
		Usuario u2 = new Usuario(2, "Ronaldo", "ronaldo@test.com", "5678", "docente", "r.jpg");
		when(usuarioRepository.findAll()).thenReturn(Arrays.asList(u1, u2));

		mockMvc.perform(post("/buscarUsuarios"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].nombre", is("Santiago")))
				.andExpect(jsonPath("$[1].nombre", is("Ronaldo")));
	}

	// ==================== RAMAS FALSAS (!obj.equals(null)) ====================

	static class FakeAsignaciones extends Asignaciones {
		@Override
		public boolean equals(Object obj) {
			return obj == null;
		}
	}

	static class FakeComentarios extends Comentarios {
		@Override
		public boolean equals(Object obj) {
			return obj == null;
		}
	}

	static class FakeProyectos extends Proyectos {
		@Override
		public boolean equals(Object obj) {
			return obj == null;
		}
	}

	static class FakeTareas extends Tareas {
		@Override
		public boolean equals(Object obj) {
			return obj == null;
		}
	}

	static class FakeUsuario extends Usuario {
		@Override
		public boolean equals(Object obj) {
			return obj == null;
		}
	}

	@Test
	@DisplayName("Asignaciones: ramas falsas de add, update y delete")
	void testAsignacionesRamasFalsas() {
		FakeAsignaciones fake = new FakeAsignaciones();
		assertEquals(0, asignacionesController.addAsignacion(fake));
		assertEquals(0, asignacionesController.updateAsignacion(fake));
		assertEquals(0, asignacionesController.deleteAsignacion(fake));
	}

	@Test
	@DisplayName("Comentarios: ramas falsas de add, update y delete")
	void testComentariosRamasFalsas() {
		FakeComentarios fake = new FakeComentarios();
		assertEquals(0, comentariosController.addComentario(fake));
		assertEquals(0, comentariosController.updateComentario(fake));
		assertEquals(0, comentariosController.deleteComentario(fake));
	}

	@Test
	@DisplayName("Proyectos: ramas falsas de add, update y delete")
	void testProyectosRamasFalsas() {
		FakeProyectos fake = new FakeProyectos();
		assertEquals(0, proyectosController.addProyecto(fake));
		assertEquals(0, proyectosController.updateProyecto(fake));
		assertEquals(0, proyectosController.deleteProyecto(fake));
	}

	@Test
	@DisplayName("Tareas: ramas falsas de add, update y delete")
	void testTareasRamasFalsas() {
		FakeTareas fake = new FakeTareas();
		assertEquals(0, tareasController.addTareas(fake));
		assertEquals(0, tareasController.updateTareas(fake));
		assertEquals(0, tareasController.deleteTareas(fake));
	}

	@Test
	@DisplayName("Usuario: ramas falsas de add, update y delete")
	void testUsuarioRamasFalsas() {
		FakeUsuario fake = new FakeUsuario();
		assertEquals(0, usuarioController.addUsuario(fake));
		assertEquals(0, usuarioController.updateUsuario(fake));
		assertEquals(0, usuarioController.deleteUsuario(fake));
	}
}
