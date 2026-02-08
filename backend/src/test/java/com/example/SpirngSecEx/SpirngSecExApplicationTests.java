package com.example.SpirngSecEx;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


@SpringBootTest
@AutoConfigureMockMvc
class SpirngSecExApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void contextLoads() {
	}
	@Test
	public void testCreateUser() throws Exception {
		String loginJson = "{\"username\":\"apiuser\", \"password\":\"pass123\", \"email\":\"xxx@example.com\", \"role\":\"admin\"}";
		// Act
		ResultActions result = mockMvc.perform(post("/api/auth/signup")
				.contentType(MediaType.APPLICATION_JSON)
				.content(loginJson));

		// Assert
		result.andExpect(status().isOk())
			.andDo(print());
	}

	@Test
	public void testSignUser() throws Exception {
		// Arrange
		String userJson = "{\"username\":\"apiuser\",\"password\":\"pass123\"}";

		// Act
		ResultActions result = mockMvc.perform(post("/api/auth/signin")
				.contentType(MediaType.APPLICATION_JSON)
				.content(userJson));
		// Assert
		result.andExpect(status().isOk())
				.andExpect(jsonPath("$.username").value("apiuser"))
				.andDo(print());
	}

	@Test
	public void testAccessSecuredEndpointWithJWT() throws Exception {
		// First, sign in to get the JWT token
		String loginJson = "{\"username\":\"apiuser\",\"password\":\"pass123\"}";

		ResultActions loginResult = mockMvc.perform(post("/api/auth/signin")
						.contentType(MediaType.APPLICATION_JSON)
						.content(loginJson))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.accessToken").exists());

		// Extract the token from the response
		String responseBody = loginResult.andReturn().getResponse().getContentAsString();
		// Use a JSON parser to extract the token
		String token = com.jayway.jsonpath.JsonPath.read(responseBody, "$.accessToken");

		// Use the token to call a secured endpoint
		ResultActions result = mockMvc.perform(
				get("/api/project") // change to your actual secured endpoint
						.header("Authorization", "Bearer " + token)
						.contentType(MediaType.APPLICATION_JSON));

		// Assert access is successful
		result.andExpect(status().isOk())
			.andDo(print());
	}
// Test δικα μου 24-12-25
	@Test
	public void testAccessSecuredEndpointWithoutJWT_ShouldReturn401() throws Exception {
		mockMvc.perform(get("/api/project")
				.contentType(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isForbidden()); 
	}

	@Test
	public void testSigninWithWrongPassword_ShouldFail() throws Exception {
		String badLoginJson = "{\"username\":\"apiuser\",\"password\":\"WRONG\"}";

		mockMvc.perform(post("/api/auth/signin")
				.contentType(MediaType.APPLICATION_JSON)
				.content(badLoginJson))
			.andDo(print())                 
			.andExpect(status().isForbidden()); 
	}
}