package cn.jin.todo.config;

import cn.jin.todo.security.JwtAuthenticationEntryPoint;
import cn.jin.todo.security.JwtAuthenticationFilter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
@AllArgsConstructor
public class SecurityConfig {

	private final UserDetailsService userDetailsService;

	private final JwtAuthenticationEntryPoint authenticationEntryPoint;

	private final JwtAuthenticationFilter authenticationFilter;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable()).authorizeHttpRequests(authorize -> {
			// authorize.requestMatchers(HttpMethod.POST, "/api/**").hasRole("ADMIN");
			// authorize.requestMatchers(HttpMethod.PUT, "/api/**").hasRole("ADMIN");
			// authorize.requestMatchers(HttpMethod.DELETE, "/api/**").hasRole("ADMIN");
			// authorize.requestMatchers(HttpMethod.GET, "/api/**").hasAnyRole("USER",
			// "ADMIN");
			// authorize.requestMatchers(HttpMethod.PATCH, "/api/**").hasAnyRole("USER",
			// "ADMIN");
			// authorize.requestMatchers(HttpMethod.GET, "/api/**").permitAll();
			authorize.requestMatchers("/api/auth/**").permitAll();
			authorize.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll();
			authorize.anyRequest().authenticated();
		}).httpBasic(Customizer.withDefaults());

		http.exceptionHandling(ex -> ex.authenticationEntryPoint(authenticationEntryPoint));

		http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}

	// @Bean
	// public UserDetailsService userDetailsService() {
	// UserDetails jin = User.builder()
	// .username("jin")
	// .password(passwordEncoder().encode("password"))
	// .roles("USER")
	// .build();
	//
	// UserDetails admin = User.builder()
	// .username("admin")
	// .password(passwordEncoder().encode("admin"))
	// .roles("ADMIN")
	// .build();
	//
	// return new InMemoryUserDetailsManager(jin, admin);
	// }

}
