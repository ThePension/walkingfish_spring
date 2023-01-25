package ch.walkingfish.walkingfish;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
// @Profile(value = "secure")
public class SecurityConfiguration {
    @Bean
    public UserDetailsService userDetailsService() {

        UserDetails user = User.withDefaultPasswordEncoder()
                .username("user")
                .password("password")
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(user);
    }

    @Bean
    // @Profile(value = "secure")
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.logout()
                .logoutSuccessUrl("/catalogue");

        http.authorizeRequests()
                .antMatchers(
                        "/",
                        "/accueil",
                        "/catalogue/**",
                        "/articlesImages/**",
                        "/css/**",
                        "/img/**",
                        "/js/**",
                        "/h2/**",
                        "/fonts/**")
                .permitAll()
                .anyRequest().authenticated()
                .and().formLogin().permitAll();

        return http.build();
    }
}