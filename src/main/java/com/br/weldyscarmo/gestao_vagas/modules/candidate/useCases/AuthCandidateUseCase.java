package com.br.weldyscarmo.gestao_vagas.modules.candidate.useCases;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.br.weldyscarmo.gestao_vagas.modules.candidate.CandidateRepository;
import com.br.weldyscarmo.gestao_vagas.modules.candidate.dto.AuthCandidateDTO;
import com.br.weldyscarmo.gestao_vagas.modules.candidate.dto.AuthCandidateResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

@Service
public class AuthCandidateUseCase {

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${security.token.secret.candidate}")
    private String secret;

    public AuthCandidateResponseDTO execute(AuthCandidateDTO authCandidateDTO){
        var candidate = this.candidateRepository.findByUsername(authCandidateDTO.username())
                .orElseThrow(() -> {
                    throw new UsernameNotFoundException("Usuário/Senha incorreta");
        });

        var passwordMatches = passwordEncoder.matches(authCandidateDTO.password(), candidate.getPassword());

        if (!passwordMatches){
            throw new BadCredentialsException("Usuário/Senha incorreta");
        }

        Algorithm algorithm = Algorithm.HMAC256(secret);
        var expiresAt = Instant.now().plus(Duration.ofHours(2));
        var token = JWT.create().withIssuer("javagas")
                .withSubject(candidate.getId().toString())
                .withClaim("roles", Arrays.asList("CANDIDATE"))
                .withExpiresAt(expiresAt)
                .sign(algorithm);

        var authCandidateResponse = AuthCandidateResponseDTO
                .builder()
                .accessToken(token)
                .expiresAt(expiresAt.toEpochMilli())
                .build();

        return authCandidateResponse;
    }
}
