package edu.ucsb.cs156.spooler.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MvcResult;

import edu.ucsb.cs156.spooler.ControllerTestCase;
import edu.ucsb.cs156.spooler.models.CurrentUser;
import edu.ucsb.cs156.spooler.repositories.UserRepository;
import edu.ucsb.cs156.spooler.testconfig.TestConfig;

@WebMvcTest(controllers = UserInfoController.class)
@Import(TestConfig.class)
@AutoConfigureDataJpa
public class UserInfoControllerTests extends ControllerTestCase {

  @MockitoBean UserRepository userRepository;

  @Test
  public void currentUser__logged_out() throws Exception {
    mockMvc.perform(get("/api/currentUser")).andExpect(status().is(403));
  }

  @WithMockUser(roles = {"USER"})
  @Test
  public void currentUser__logged_in() throws Exception {

    // arrange

    CurrentUser currentUser = currentUserService.getCurrentUser();
    String expectedJson = mapper.writeValueAsString(currentUser);

    // act

    MvcResult response =
        mockMvc.perform(get("/api/currentUser")).andExpect(status().isOk()).andReturn();

    // assert
    String responseString = response.getResponse().getContentAsString();
    assertEquals(expectedJson, responseString);
  }
}
