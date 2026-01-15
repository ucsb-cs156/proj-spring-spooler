package edu.ucsb.cs156.spooler.controllers;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MvcResult;

import edu.ucsb.cs156.spooler.ControllerTestCase;
import edu.ucsb.cs156.spooler.repositories.UserRepository;
import edu.ucsb.cs156.spooler.testconfig.TestConfig;

@ActiveProfiles("development")
@WebMvcTest(controllers = CSRFController.class)
public class CSRFControllerTests extends ControllerTestCase {

  @MockitoBean UserRepository userRepository;

  @Test
  public void csrf_returns_ok() throws Exception {
    MvcResult response = mockMvc.perform(get("/csrf")).andExpect(status().isOk()).andReturn();

    String responseString = response.getResponse().getContentAsString();
    assertTrue(responseString.contains("parameterName"));
    assertTrue(responseString.contains("_csrf"));
    assertTrue(responseString.contains("headerName"));
    assertTrue(responseString.contains("X-XSRF-TOKEN"));
  }
}
