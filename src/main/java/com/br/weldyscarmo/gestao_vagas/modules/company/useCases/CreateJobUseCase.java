package com.br.weldyscarmo.gestao_vagas.modules.company.useCases;

import com.br.weldyscarmo.gestao_vagas.exceptions.CompanyNotFoundException;
import com.br.weldyscarmo.gestao_vagas.modules.company.entities.JobEntity;
import com.br.weldyscarmo.gestao_vagas.modules.company.repositories.CompanyRepository;
import com.br.weldyscarmo.gestao_vagas.modules.company.repositories.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CreateJobUseCase {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private CompanyRepository companyRepository;

    public JobEntity execute(JobEntity jobEntity){
        this.companyRepository.findById(jobEntity.getCompanyId()).orElseThrow(() -> {
            throw new CompanyNotFoundException();
        });
        return this.jobRepository.save(jobEntity);
    }
}
