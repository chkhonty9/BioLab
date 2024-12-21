package org.cn.userservice.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.cn.userservice.entity.Person;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;


public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtEncoder jwtEncoder;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, JwtEncoder jwtEncoder) {
        super();
        this.authenticationManager = authenticationManager;
        this.jwtEncoder = jwtEncoder;
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        try {
            Person customer = new ObjectMapper().readValue(request.getInputStream(), Person.class);
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            customer.getEmail(),
                            customer.getPassword()
                    )
            );
        } catch (Exception e) {
            //e.printStackTrace();
            throw new RuntimeException("Pb in request content " + e.getLocalizedMessage());
        }

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        User springUser = (User) authResult.getPrincipal();
        List<String> role = new ArrayList<>();
        authResult.getAuthorities().forEach(a ->{
            role.add(a.getAuthority());
        });

        JwtClaimsSet jwtClaimSet_Access_Token = JwtClaimsSet.builder()
                .issuer(request.getRequestURI())
                .subject(springUser.getUsername())
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plus(4, ChronoUnit.MINUTES))
                .claim("email", springUser.getUsername())
                .claim("scope", role)
                .build();
        String Access_Token = jwtEncoder.encode(JwtEncoderParameters.from(jwtClaimSet_Access_Token)).getTokenValue();

        JwtClaimsSet jwtClaimsSet_Refresh_Token = JwtClaimsSet.builder()
                .issuer(request.getRequestURI())
                .subject(springUser.getUsername())
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plus(15, ChronoUnit.MINUTES))
                .claim("email", springUser.getUsername())
                .claim("scope", role)
                .build();
        String Refresh_Token = jwtEncoder.encode(JwtEncoderParameters.from(jwtClaimsSet_Refresh_Token)).getTokenValue();
        response.addHeader("Authorization", "Bearer " + Access_Token);
        response.addHeader("Refresh-Token", "Bearer " + Refresh_Token );

    }

}
