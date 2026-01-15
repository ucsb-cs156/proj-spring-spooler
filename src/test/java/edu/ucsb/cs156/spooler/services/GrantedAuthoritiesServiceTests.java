package edu.ucsb.cs156.spooler.services;

import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.ucsb.cs156.spooler.repositories.UserRepository;
import edu.ucsb.cs156.spooler.testconfig.TestConfig;
import java.util.Collection;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@EnableConfigurationProperties(value = SystemInfoServiceImpl.class)
@Import(TestConfig.class)
@ContextConfiguration
class GrantedAuthoritiesServiceTests {

  @MockitoBean UserRepository userRepository;

  @MockitoBean ClientRegistrationRepository clientRegistrationRepository;

  @Autowired GrantedAuthoritiesService grantedAuthoritiesService;

  @WithMockUser(roles = {"USER"})
  @Test
  void test_getGrantedAuthorities() {
    // act
    Collection<? extends GrantedAuthority> grantedAuthorities =
        grantedAuthoritiesService.getGrantedAuthorities();

    // assert

    assertTrue(grantedAuthorities.size() > 0);
  }
}
