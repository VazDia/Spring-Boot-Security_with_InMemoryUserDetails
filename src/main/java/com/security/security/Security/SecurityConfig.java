package com.security.security.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
/* On puvait aussi etendre la classe avec WebSecurit... mais il est deprecier
* donc voici une nouvelle manière */
public class SecurityConfig {
    // Propriété appélée depuis la classe principale de l'application
    @Autowired
    private PasswordEncoder passwordEncoder;

    // Nous allons créer des utilisateurs personnalisés avec InMemoryUserDetailManager
    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager(){
        // {noop} devant le mot de passe pour ne pas le crypter ex: "{noop}password"
        // passwordEncoder.encode("password") pour un encodage personnaliser
       return new InMemoryUserDetailsManager(
               User.withUsername("User1").password(passwordEncoder.encode("1234")).roles("USER").build(),
               User.withUsername("User2").password(passwordEncoder.encode("5678")).roles("USER").build(),
               User.withUsername("User3").password(passwordEncoder.encode("1234")).roles("USER","ADMIN").build()
       ) ;
    }
    // Cette methode permet de créer un securityFilterChain
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{


        httpSecurity.authorizeHttpRequests().antMatchers("/Users/**").hasRole("USER");
        httpSecurity.authorizeHttpRequests().antMatchers("/Admin/**").hasRole("ADMIN");
        // NB: La demande de toutes les autres authentification vient après les spécification de roles
        // C'est-à dire après les "antMachers" Sinon cela ne va pas fonctionner.
        httpSecurity.authorizeHttpRequests().anyRequest().authenticated();
        // Changer l'option d'affichage des erreurs d'accès
        httpSecurity.exceptionHandling().accessDeniedPage("/notAuthorized");
        // vu que le filtre de security sera appleler par defaut lors du demarrage de l'appli,
        // Nous allons demander d'afficher un formulaire
        httpSecurity.formLogin();
        // on pouvait utiliser formLogin().loginPage("/login").permitAll() pour spécifier specifier la page de connexion
        // cela sera utile pour l'utilsation d'un autre serveur pour l'autentification
        return httpSecurity.build();
    }
}
