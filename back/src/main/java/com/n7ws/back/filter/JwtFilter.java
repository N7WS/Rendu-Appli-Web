package com.n7ws.back.filter;

import java.io.IOException;
import java.util.List;

import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


import com.n7ws.back.config.JwtUtils;
import com.n7ws.back.service.CustomUserDetailsService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor 
public class JwtFilter extends OncePerRequestFilter{

    private final CustomUserDetailsService customUserDetailsService;
    private final JwtUtils jwtUtils;
    

    @Override
    /** Filter ayant pour objectif de filter les requetes qui ont besoin d'authentication (routes privées) et les autres (routes publiques)
     *  Objectif de ce filtre est de vérifier si le token JWT est valide et d'authentifier l'utilisateur dans le cadre d'une requête sur une route privée
     *  */
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");

        //TODO: Mettre le token de bypass dans un fichier de configuration (.env ?)
        final String expectedBypassToken = "66ecf4a0b8085d4f93c4a392184548c6771bb6471e196bc26cf68636bfd450d5";

        // Vérification si dans le paquet HTTP il y a le token de bypass
        if(authHeader != null && authHeader.equals(expectedBypassToken)) {
            // On autorise l'accès sans authentification

            // On crée un token d'authentification pour passer la sécurité après filtre
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
    "bypassUser", null, List.of(new SimpleGrantedAuthority("ROLE_BYPASS"))
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

        } else {

            String username = null;
            String jwt = null;

            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                // Récupération du token JWT dans l'en-tête Authorization
                //System.out.println("Authentication header found: " + authHeader);

                jwt = authHeader.substring(7); // Récupération du token après "Bearer "
                username = jwtUtils.extractUid(jwt);
            }

            // Vérification si l'utilisateur est déjà authentifié
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = this.customUserDetailsService.loadUserByUsername(username);

                //System.out.println("Creating authentication token for user: " + username);
                //System.out.println("Using token: " + jwt);

                if (jwtUtils.validateToken(jwt, username)) {
                    // Le token est valide, on peut authentifier l'utilisateur

                    //System.out.println("Token is valid for user: " + username);

                    // Créer un token d'authentification
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                    );

                    // On ajoute dans les details du token la source d'authentification du token, sert à du debug et des logs
                    authenticationToken.setDetails(new WebAuthenticationDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                    //System.out.println("Authentication successful for user: " + username);
                }
            }
        }
        filterChain.doFilter(request, response); // On continue la chaîne de filtres si il y en a (pas notre cas ?)
    }
}
