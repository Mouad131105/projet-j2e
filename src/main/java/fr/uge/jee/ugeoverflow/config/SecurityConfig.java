package fr.uge.jee.ugeoverflow.config;

import fr.uge.jee.ugeoverflow.security.CustomAuthenticationProvider;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private CustomAuthenticationProvider customAuthenticationProvider;

    public SecurityConfig(CustomAuthenticationProvider customAuthenticationProvider) {
        this.customAuthenticationProvider = customAuthenticationProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        auth.authenticationProvider(this.customAuthenticationProvider);
                //.inMemoryAuthentication()
                ///.withUser("amyr")
                //.password("{noop}amyr")
                //.authorities(Role.ADMIN.name());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //.loginProcessingUrl("/authentication/login")
                //.defaultSuccessUrl("/authentication/profile", true)
                .csrf().and().cors().disable()
                .authorizeRequests()
                .mvcMatchers("/authentication/**").permitAll()
                .and()
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/authentication/login")
                .successForwardUrl("/users/homepage/questions")
                .failureForwardUrl("/authentication/login")
                //.defaultSuccessUrl("/authentication/login")
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/authentication/logout")
                //.invalidateHttpSession(true)
                .deleteCookies()
                .logoutSuccessUrl("/authentication/login");
        //
    }

    /**
     * Override this method to configure {@link WebSecurity}. For example, if you wish to
     * ignore certain requests.
     *
     * @param web
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        // For boostrap layout
        web.ignoring().mvcMatchers("/webjars/**");
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /*
    @Bean
    protected InMemoryUserDetailsManager userDetailsManager(PasswordEncoder passwordEncoder) {
        UserDetails admin = User.withUsername("admin")
                .password(passwordEncoder.encode("admin"))
                .roles(Role.ADMIN.name())
                .build();
        return new InMemoryUserDetailsManager(admin);
    }*/


}
