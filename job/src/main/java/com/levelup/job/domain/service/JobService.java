package com.levelup.job.domain.service;

import com.levelup.common.exception.EntityNotFoundException;
import com.levelup.common.exception.ExceptionCode;
import com.levelup.job.infrastructure.jpaentity.JobEntity;
import com.levelup.job.infrastructure.enumeration.Company;
import com.levelup.job.infrastructure.enumeration.OrderBy;
import com.levelup.job.infrastructure.repository.JobRepository;
import com.levelup.job.domain.model.JobFilterCondition;
import com.levelup.job.domain.model.Job;
import com.levelup.job.domain.model.PagingJob;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class JobService {

    private final JobRepository jobRepository;
    private final WebClient webClient;

    @Transactional
    public Job save(Job jobVO) {
        JobEntity saveJob = jobRepository.save(jobVO.toEntity());

        return Job.from(saveJob);
    }

    @Transactional
    public List<Job> saveIfAbsent(List<Job> creationJobs, Company company) {
        List<Job> findJobs = jobRepository.findByCompany(company).stream()
                .map(Job::from).toList();

        List<JobEntity> saveJobs = creationJobs.stream()
                .filter(job -> !findJobs.contains(job))
                .map(Job::toEntity)
                .toList();

        jobRepository.saveAll(saveJobs);

        return saveJobs.stream()
                .map(Job::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public PagingJob search(JobFilterCondition filterCondition, OrderBy orderBy, Long size, Long page) {
        List<Job> jobs = jobRepository.findByFilterCondition(filterCondition, size, page).stream()
                .filter(JobEntity::getActive)
                .map(Job::from)
                .distinct()
                .toList();
        Long totalCount = jobRepository.countByFilterCondition(filterCondition);

        return PagingJob.of(jobs, totalCount);
    }

    @Transactional(readOnly = true)
    public List<Job> getCreatedTodayByCompany(Company company, int page, int size) {
        LocalDateTime startOfDay = LocalDateTime.now().with(LocalTime.MIN);
        LocalDateTime endOfDay = LocalDateTime.now().with(LocalTime.MAX);
        Pageable pageable = PageRequest.of(page, size);

        if (company == null) {
            return jobRepository.findByCreatedAt(startOfDay, endOfDay, pageable).stream()
                    .map(Job::from)
                    .filter(Job::getActive)
                    .toList();
        }

        return jobRepository.findByCompanyAndCreatedAt(company, startOfDay, endOfDay, pageable).stream()
                .map(Job::from)
                .filter(Job::getActive)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<Job> getNotMatched(List<Job> jobs, Company company) {
        List<Job> findJobs = jobRepository.findByCompany(company).stream()
                .map(Job::from)
                .toList();

        return findJobs.stream()
                .filter(findJob -> !jobs.contains(findJob))
                .toList();
    }

    @Transactional(readOnly = true)
    public List<Job> getNotPushedJobs() {
        return jobRepository.findByIsPushSent(false).stream()
                .map(Job::from)
                .toList();
    }

    @Transactional
    public List<Job> push(List<Job> notPushedJobs) {
        List<Long> jobIds = notPushedJobs.stream().map(Job::getId).toList();

        List<JobEntity> jobs = jobRepository.findAllById(jobIds);
        jobs.forEach(JobEntity::push);

        return jobs.stream()
                .map(Job::from)
                .toList();
    }

    @Transactional
    public void update(Long findJobId, Job updateJob) {
        JobEntity findJob = jobRepository.findById(findJobId)
                .orElseThrow(() -> new EntityNotFoundException(ExceptionCode.JOB_NOT_FOUND));

        findJob.update(
                updateJob.getTitle(),
                updateJob.getUrl(),
                updateJob.getCompany(),
                updateJob.getNoticeEndDate(),
                updateJob.getJobGroup(),
                updateJob.getActive(),
                updateJob.getIsPushSent()
        );
    }

    @Transactional
    public void deleteAll(List<Job> jobs) {
        jobRepository.deleteAllById(jobs.stream()
                .map(Job::getId)
                .toList());
    }

    public void test() {
        Mono<String> mono = webClient.get()
                .uri("/api/v1/notifications/test")
                .retrieve()
                .bodyToMono(String.class);

        String response = mono.block();
    }
}
