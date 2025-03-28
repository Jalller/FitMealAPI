package app.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class AppUserDetailsService {

    @Bean
    public UserDetailsService userDetailsService() {
        // Directly use bcrypt-encoded password
        String encodedPassword = new BCryptPasswordEncoder().encode("password");

        UserDetails user = User.builder()
                .username("testuser")
                .password(encodedPassword) // "password" bcrypt encoded
                .roles("ADMIN")             // Must match hasRole("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user);
    }
}
