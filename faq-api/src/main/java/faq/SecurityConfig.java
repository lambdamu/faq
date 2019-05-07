package faq;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

/**
 * <ul>
 * <li>Specifies UI and API authorized requests based on in-memory user details for
 * the demo (anonymous/user/admin permissions).</li>
 * <li>Adds logout.</li>
 * <li>Include Angular-compatible CSRF support (cookie repository).</li>
 * </ul>
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	  
	@Bean
	public UserDetailsService userDetailsService() {
		PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		UserDetails user = User.withUsername("user")
				.password(encoder.encode("user"))
				.roles("USER").build();
		UserDetails admin = User.withUsername("admin")
				.password(encoder.encode("admin"))
				.roles("ADMIN", "USER").build();
		InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
		manager.createUser(user);
		manager.createUser(admin);
		return manager;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.httpBasic()
			
			.and()
			.authorizeRequests()
			.antMatchers("/", "/[a-z][a-z]/*").permitAll()
			.antMatchers(HttpMethod.GET, "/api/**").permitAll()
			.antMatchers(HttpMethod.POST, "/api/**").hasRole("ADMIN")
			.antMatchers(HttpMethod.PUT, "/api/**").hasRole("ADMIN")
			.antMatchers(HttpMethod.DELETE, "/api/**").hasRole("ADMIN")
			
			.and()
			.logout().deleteCookies("remove").invalidateHttpSession(false)
			.logoutUrl("/api/logout")
			
	        .and()
			.csrf()
			.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
	}

}