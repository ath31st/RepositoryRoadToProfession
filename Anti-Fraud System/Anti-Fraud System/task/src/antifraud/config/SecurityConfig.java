package antifraud.config;

import antifraud.exceptionhandler.RestAuthenticationEntryPoint;
import antifraud.util.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    public void configure(HttpSecurity http) throws Exception {
        http.httpBasic()
                .authenticationEntryPoint(restAuthenticationEntryPoint) // Handles auth error
                .and()
                .csrf().disable().headers().frameOptions().disable() // for Postman, the H2 console
                .and()
                .authorizeRequests() // manage access
                .antMatchers("/actuator/shutdown").permitAll() // needs to run test
                .antMatchers(HttpMethod.POST, "/api/auth/user").permitAll()
                .antMatchers(HttpMethod.DELETE, "/api/auth/user").hasAuthority(Role.ADMINISTRATOR.getName())
                .antMatchers(HttpMethod.PUT, "/api/auth/access").hasAuthority(Role.ADMINISTRATOR.getName())
                .antMatchers(HttpMethod.GET, "/api/auth/list").hasAnyAuthority(Role.ADMINISTRATOR.getName(), Role.SUPPORT.getName())
                .antMatchers(HttpMethod.POST, "/api/antifraud/transaction").hasAuthority(Role.MERCHANT.getName())
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS); // no session
    }

    @Bean
    public PasswordEncoder getEncoder() {
        return new BCryptPasswordEncoder();
    }
}
