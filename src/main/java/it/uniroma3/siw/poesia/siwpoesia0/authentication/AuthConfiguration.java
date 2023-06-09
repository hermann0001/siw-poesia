package it.uniroma3.siw.poesia.siwpoesia0.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static it.uniroma3.siw.poesia.siwpoesia0.model.Credentials.POETA_RUOLO;
import static it.uniroma3.siw.poesia.siwpoesia0.model.Credentials.AUTORE_RUOLO;


import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
//public  class WebSecurityConfig {
	public class AuthConfiguration {

    @Autowired
    private DataSource dataSource;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .authoritiesByUsernameQuery("SELECT username, role from credentials WHERE username=?")
                .usersByUsernameQuery("SELECT username, password, 1 as enabled FROM credentials WHERE username=?");
    }
    
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    protected SecurityFilterChain configure(final HttpSecurity httpSecurity) throws Exception{
        httpSecurity
                .csrf().and().cors().disable()
                .authorizeHttpRequests()
//                .requestMatchers("/**").permitAll()
                // chiunque (autenticato o no) può accedere alle pagine index, login, register, ai css e alle immagini
                .requestMatchers(HttpMethod.GET,"/","/index","/register","/css/**", "/images/**", "favicon.ico", "/bootstrap/**", "/fragments/**", "/error", "/files/**").permitAll()
                //chiunque(autenticato o no) può accedere alle pagine della navbar
                .requestMatchers(HttpMethod.GET,"/libro", "/chi-siamo", "/street-poetry", "/contattaci", "/login").permitAll()
                //chiunque (autenticato o no) può accedere alle pagine degli autori
                .requestMatchers(HttpMethod.GET, "/autori", "/autori/**").permitAll()
                //chiunque (autenticato o no) può leggere le poesie
                .requestMatchers(HttpMethod.GET, "/poesia", "/poesia/**").permitAll()
        		// chiunque (autenticato o no) può mandare richieste POST al punto di accesso per login, register e cerca poesia
                .requestMatchers(HttpMethod.POST,"/register", "/login", "/find").permitAll()
                .requestMatchers(HttpMethod.GET,"/poeta/**").hasAnyAuthority(POETA_RUOLO)
                .requestMatchers(HttpMethod.POST,"/poeta/**").hasAnyAuthority(POETA_RUOLO)
        		
                .requestMatchers(HttpMethod.GET,"/autore/**").hasAnyAuthority(AUTORE_RUOLO,POETA_RUOLO)
                .requestMatchers(HttpMethod.POST,"/autore/**").hasAnyAuthority(AUTORE_RUOLO,POETA_RUOLO)
                
                // tutti gli utenti autenticati possono accere alle pagine rimanenti 
                .anyRequest().authenticated()
                // LOGIN: qui definiamo il login
                .and().formLogin()
                .loginPage("/login")
                .permitAll()
                .defaultSuccessUrl("/profilo", true)
                .failureUrl("/login?error=true")
                // LOGOUT: qui definiamo il logout
                .and()
                .logout()
                // il logout è attivato con una richiesta GET a "/logout"
                .logoutUrl("/logout")
                // in caso di successo, si viene reindirizzati alla home
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .clearAuthentication(true).permitAll();
        return httpSecurity.build();
    }
}