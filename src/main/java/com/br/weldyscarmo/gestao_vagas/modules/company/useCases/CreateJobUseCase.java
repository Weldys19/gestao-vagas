package com.br.weldyscarmo.gestao_vagas.modules.company.useCases;

import com.br.weldyscarmo.gestao_vagas.modules.company.entities.JobEntity;
import com.br.weldyscarmo.gestao_vagas.modules.company.repositories.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CreateJobUseCase {

    @Autowired
    private JobRepository jobRepository;

    public JobEntity execute(JobEntity jobEntity){
        return this.jobRepository.save(jobEntity);
    }
}
