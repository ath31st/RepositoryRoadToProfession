package account.config;

import account.exceptionhandler.CustomAccessDeniedHandler;
import account.util.Role;
import account.exceptionhandler.RestAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    DaoAuthenticationProvider authenticationProvider;
    @Autowired
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    public void configure(HttpSecurity http) throws Exception {
        http.httpBasic()
                .authenticationEntryPoint(restAuthenticationEntryPoint)// Handle auth error
                .and()
                .exceptionHandling().accessDeniedHandler(accessDeniedHandler())
                .and()
                .csrf().disable().headers().frameOptions().disable() // for Postman, the H2 console
                .and()
                .authorizeRequests()
                .antMatchers("/api/auth/singup").permitAll()
                .antMatchers("/api/auth/changepass").hasAnyAuthority(Role.ROLE_ADMINISTRATOR.name(),Role.ROLE_USER.name(),Role.ROLE_ACCOUNTANT.name())
                .antMatchers("/api/empl/payment").hasAnyAuthority(Role.ROLE_USER.name(),Role.ROLE_ACCOUNTANT.name())
                .antMatchers("/api/acct/payments").hasAuthority(Role.ROLE_ACCOUNTANT.name())
                .antMatchers("/api/admin/**").hasAuthority(Role.ROLE_ADMINISTRATOR.name())
                .antMatchers("/api/security/events").hasAuthority(Role.ROLE_AUDITOR.name())
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS); // no session
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider);
    }
    @Bean
    public AccessDeniedHandler accessDeniedHandler(){
        return new CustomAccessDeniedHandler();
    }
}
