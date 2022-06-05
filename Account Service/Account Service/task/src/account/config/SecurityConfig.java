package account.config;

import account.exceptionhandler.RestAccessDeniedHandler;
import account.util.Role;
import account.exceptionhandler.RestAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;
    @Autowired
    private RestAccessDeniedHandler restAccessDeniedHandler;

    public void configure(HttpSecurity http) throws Exception {
        http.httpBasic()
                .authenticationEntryPoint(restAuthenticationEntryPoint)// Handle auth error
                .and()
                .exceptionHandling().accessDeniedHandler(restAccessDeniedHandler)
                .and()
                .csrf().disable().headers().frameOptions().disable() // for Postman, the H2 console
                .and()
                .authorizeRequests()
                .antMatchers("/api/auth/singup").permitAll()
                .antMatchers("/api/auth/changepass").hasAnyAuthority(Role.ROLE_ADMINISTRATOR.name()
                        ,Role.ROLE_USER.name()
                        ,Role.ROLE_ACCOUNTANT.name())
                .antMatchers("/api/empl/payment").hasAnyAuthority(Role.ROLE_USER.name()
                        ,Role.ROLE_ACCOUNTANT.name())
                .antMatchers("/api/acct/payments").hasAuthority(Role.ROLE_ACCOUNTANT.name())
                .antMatchers("/api/security/events/*").hasAuthority(Role.ROLE_AUDITOR.name())
                .antMatchers("/api/admin/**").hasAuthority(Role.ROLE_ADMINISTRATOR.name())
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS); // no session
    }
}
