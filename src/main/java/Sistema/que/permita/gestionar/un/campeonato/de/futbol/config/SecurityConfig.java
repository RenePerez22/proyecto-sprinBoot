package Sistema.que.permita.gestionar.un.campeonato.de.futbol.config;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Component;

import Sistema.que.permita.gestionar.un.campeonato.de.futbol.model.Usuario;
import Sistema.que.permita.gestionar.un.campeonato.de.futbol.repository.UsuarioRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private final UserDetailsService userDetailsService;
	  public SecurityConfig(UserDetailsService userDetailsService) {
	        this.userDetailsService = userDetailsService;
	    }
	 

	  @Bean
	    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	        http
	            .csrf(csrf -> csrf.disable())
	            .authorizeHttpRequests(auth -> auth
	                .requestMatchers("/login", "/registro","/api/chatbot").permitAll()
	                .anyRequest().authenticated()
	            )
	            .formLogin(form -> form
	            	    .loginPage("/login")
	            	    .successHandler((request, response, authentication) -> {
	            	        // Obtener los roles del usuario autenticado
	            	        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
	            	        
	            	        // Redirigir según el rol
	            	        if (authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
	            	            response.sendRedirect("/admin/menu");
	            	        } else if (authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_JUGADOR"))) {
	            	            response.sendRedirect("/jugadores/menu");
	            	        } else {
	            	            response.sendRedirect("/default");
	            	        }
	            	    })
	            	    .permitAll()
	            	)
	            .logout(logout -> logout
	                .permitAll()
	            )
	            .userDetailsService(userDetailsService);
	        
	        return http.build();
	    }
	  @Bean
	    public BCryptPasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    }
	  @Component
	  public class PasswordUpdater implements CommandLineRunner {

	      @Autowired
	      private UsuarioRepository usuarioRepository;

	      @Autowired
	      private BCryptPasswordEncoder passwordEncoder;

	      @Override
	      public void run(String... args) throws Exception {
	          List<Usuario> usuarios = usuarioRepository.findAll();
	          for (Usuario usuario : usuarios) {
	              String pw = usuario.getPassword();
	              // Si no empieza con el prefijo típico de BCrypt, lo ciframos
	              if (!pw.startsWith("$2a$")) {
	                  String cifrada = passwordEncoder.encode(pw);
	                  usuario.setPassword(cifrada);
	                  usuarioRepository.save(usuario);
	                  System.out.println("Contraseña actualizada para usuario: " + usuario.getCorreo());
	              }
	          }
	      }
	  }
	  
}
