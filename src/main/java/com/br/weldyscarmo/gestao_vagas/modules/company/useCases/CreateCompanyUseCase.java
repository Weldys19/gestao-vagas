package com.br.weldyscarmo.gestao_vagas.modules.company.useCases;

import com.br.weldyscarmo.gestao_vagas.exceptions.UserFoundException;
import com.br.weldyscarmo.gestao_vagas.modules.company.entities.CompanyEntity;
import com.br.weldyscarmo.gestao_vagas.modules.company.repositories.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateCompanyUseCase {

    @Autowired
    CompanyRepository companyRepository;

    public CompanyEntity execute(CompanyEntity companyEntity){
        this.companyRepository
                .findByUsernameOrEmail(companyEntity.getUsername(), companyEntity.getEmail())
                .ifPresent((user) ->{
                    throw new UserFoundException();
                });
        return this.companyRepository.save(companyEntity);
    }
}
