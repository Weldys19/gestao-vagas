package com.br.weldyscarmo.gestao_vagas.modules.candidate.useCases;

import com.br.weldyscarmo.gestao_vagas.exceptions.JobNotFoundException;
import com.br.weldyscarmo.gestao_vagas.exceptions.UserNotFoundException;
import com.br.weldyscarmo.gestao_vagas.modules.candidate.CandidateRepository;
import com.br.weldyscarmo.gestao_vagas.modules.company.repositories.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ApplyJobCandidateUseCase {

    @Autowired
    CandidateRepository candidateRepository;

    @Autowired
    JobRepository jobRepository;

    public void execute(UUID candidateId, UUID jobId){
        this.candidateRepository.findById(candidateId)
                .orElseThrow(() -> {
                    throw new UserNotFoundException();
                });

        this.jobRepository.findById(jobId)
                .orElseThrow(() -> {
                    throw new JobNotFoundException();
                });
    }
}
