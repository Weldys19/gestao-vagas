package com.br.weldyscarmo.gestao_vagas.modules.company.useCases;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.br.weldyscarmo.gestao_vagas.modules.company.dto.AuthCompanyDTO;
import com.br.weldyscarmo.gestao_vagas.modules.company.dto.AuthCompanyResponseDTO;
import com.br.weldyscarmo.gestao_vagas.modules.company.repositories.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

@Service
public class AuthCompanyUseCase {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${security.token.secret}")
    private String secret;

    public AuthCompanyResponseDTO execute(AuthCompanyDTO authCompanyDTO) {
        var company = this.companyRepository.findByUsername(authCompanyDTO.getUsername()).orElseThrow(
                () -> {
                    throw new UsernameNotFoundException("Usuário/Senha incorreto");
                });

        //Verificar se as senhas são iguais
        var passwordMatches = this.passwordEncoder.matches(authCompanyDTO.getPassword(), company.getPassword());
        //Se não for -> erro
        if (!passwordMatches){
            throw new BadCredentialsException("Usuário/Senha incorreto");
        }
        //Se for igual -> Gerar token
        Algorithm algorithm = Algorithm.HMAC256(secret);
        var expiresAt = Instant.now().plus(Duration.ofHours(2));
        var token = JWT.create().withIssuer("javagas")
                .withSubject(company.getId().toString())
                .withClaim("roles", Arrays.asList("COMPANY"))
                .withExpiresAt(expiresAt)
                .sign(algorithm);

        var authCompanyResponse = AuthCompanyResponseDTO.builder()
                .accessToken(token)
                .expiresAt(expiresAt.toEpochMilli())
                .build();

        return authCompanyResponse;
    }
}
