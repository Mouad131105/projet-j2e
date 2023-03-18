package fr.uge.jee.ugeoverflow.config;

import fr.uge.jee.ugeoverflow.security.CustomAuthenticationProvider;
import fr.uge.jee.ugeoverflow.user.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
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
                .authorizeRequests().anyRequest().authenticated()
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
    }



    @Bean
    public PasswordEncoder passwordEncoder() {
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
