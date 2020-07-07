package br.ce.wcaquino.tasks.apitest;

import org.hamcrest.CoreMatchers;
import org.junit.BeforeClass;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

/**
 * Classe que realiza testes a uma API.
 * @author dantiii
 *
 */
public class APITest {
	
	// @BeforeClass eh executado uam vez antes de todos os testes
	@BeforeClass
	public static void setup() {
		// Config global para o Rest Assured
		RestAssured.baseURI = "http://localhost:8001/tasks-backend";
	}
	
	// Cenários para testar
	
	// (1) Status code do GET
	
	@Test
	public void deveRetornarTarefas() {
		// Estilo a linguagem utilizada no Cucumber: gien/when/then => dado/when/entao
		RestAssured.given()
			//.log().all() 	// log da req
		.when()
			.get("/todo")
		.then()
			//.log().all()	// log da res
			.statusCode(200)
		;
	}
	
	// (2) Adicionar uma tarefa com sucesso
	
	@Test
	public void deveAdicionarTarefaComSucesso() {
		RestAssured.given()
			.body("{\"task\":\"Teste via API\",\"dueDate\":\"2030-12-30\"}")
			.contentType(ContentType.JSON)
		.when()
			.post("/todo")
		.then()
			.statusCode(201)
		;
	}
	
	// (3) Validacao da data da tarefa
	
	@Test
	public void naoDeveAdicionarTarefaInvalida() {
		RestAssured.given()
			.body("{\"task\":\"Teste via API\",\"dueDate\":\"2010-12-30\"}")
			.contentType(ContentType.JSON)
		.when()
			.post("/todo")
		.then().log().all()
			.statusCode(400)
			.body("message", CoreMatchers.is("Due date must not be in past"))
		;
	}
}
