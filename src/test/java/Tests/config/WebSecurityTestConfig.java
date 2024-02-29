package Tests.config;

import com.liquibase.services.security.CustomUserDetailsService;
import com.liquibase.services.security.JwtAuthFilter;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@TestConfiguration
public class WebSecurityTestConfig {

    @Bean
//    @Primary
    public SecurityFilterChain testSecurityFilterChain(HttpSecurity http,
                                                       CustomUserDetailsService customUserDetailsService,
                                                       JwtAuthFilter jwtAuthFilter) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
                .build();
    }


}
