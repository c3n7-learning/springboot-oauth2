package tech.c3n7.clients.sociallogin.SocialWebClient.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.oidc.web.logout.OidcClientInitiatedLogoutSuccessHandler;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;

@SuppressWarnings("deprecation")
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

    @Autowired
    ClientRegistrationRepository clientRegistrationRepository;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/").permitAll()
                .and()
                    .authorizeRequests()
                    .anyRequest().authenticated()
                .and()
                    .oauth2Login()
                .and()
                    .logout()
//                    .logoutSuccessUrl("/")
                    .logoutSuccessHandler(oidcClientInitiatedLogoutSuccessHandler())
                    .invalidateHttpSession(true)
                    .clearAuthentication(true)
                    .deleteCookies("JSESSIONID");
    }

    private OidcClientInitiatedLogoutSuccessHandler oidcClientInitiatedLogoutSuccessHandler() {
        // clientRegistrationRepository will have the open-id configuration and thus the logout url for the openID oauth service
        // doing the heavy lifting for us
        OidcClientInitiatedLogoutSuccessHandler successHandler =
                new OidcClientInitiatedLogoutSuccessHandler(clientRegistrationRepository);
        successHandler.setPostLogoutRedirectUri("http://localhost:8080/");
        return successHandler;
    }
}
