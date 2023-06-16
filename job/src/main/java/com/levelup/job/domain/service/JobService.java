package com.levelup.job.domain.service;

import com.levelup.common.exception.EntityNotFoundException;
import com.levelup.common.exception.ExceptionCode;
import com.levelup.job.domain.entity.Job;
import com.levelup.job.domain.enumeration.Company;
import com.levelup.job.domain.enumeration.OrderBy;
import com.levelup.job.domain.repository.JobRepository;
import com.levelup.job.domain.vo.JobFilterCondition;
import com.levelup.job.domain.vo.JobVO;
import com.levelup.job.domain.vo.PagingJob;
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
    public JobVO save(JobVO jobVO) {
        Job saveJob = jobRepository.save(jobVO.toEntity());

        return JobVO.from(saveJob);
    }

    @Transactional
    public List<JobVO> saveIfAbsent(List<JobVO> jobs, Company company) {
        List<JobVO> findJobs = jobRepository.findByCompany(company).stream()
                .map(JobVO::from).toList();

        List<Job> saveJobs = jobs.stream()
                .filter(job -> !findJobs.contains(job))
                .map(JobVO::toEntity)
                .toList();

        jobRepository.saveAll(saveJobs);

        return saveJobs.stream()
                .map(JobVO::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public PagingJob search(JobFilterCondition filterCondition, OrderBy orderBy, Long size, Long page) {
        List<JobVO> jobs = jobRepository.findByFilterCondition(filterCondition, size, page).stream()
                .map(JobVO::from)
                .filter(JobVO::getActive)
                .toList();
        Long totalCount = jobRepository.countByFilterCondition(filterCondition);

        return PagingJob.of(jobs, totalCount);
    }

    @Transactional(readOnly = true)
    public List<JobVO> getCreatedTodayByCompany(Company company, int page, int size) {
        LocalDateTime startOfDay = LocalDateTime.now().with(LocalTime.MIN);
        LocalDateTime endOfDay = LocalDateTime.now().with(LocalTime.MAX);
        Pageable pageable = PageRequest.of(page, size);

        if (company == null) {
            return jobRepository.findByCreatedAt(startOfDay, endOfDay, pageable).stream()
                    .map(JobVO::from)
                    .filter(JobVO::getActive)
                    .toList();
        }

        return jobRepository.findByCompanyAndCreatedAt(company, startOfDay, endOfDay, pageable).stream()
                .map(JobVO::from)
                .filter(JobVO::getActive)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<JobVO> getNotMatched(List<JobVO> jobs, Company company) {
        List<JobVO> findJobs = jobRepository.findByCompany(company).stream()
                .map(JobVO::from)
                .toList();

        return findJobs.stream()
                .filter(findJob -> !jobs.contains(findJob))
                .toList();
    }

    @Transactional
    public void update(Long findJobId, JobVO updateJob) {
        Job findJob = jobRepository.findById(findJobId)
                .orElseThrow(() -> new EntityNotFoundException(ExceptionCode.JOB_NOT_FOUND));

        findJob.update(
                updateJob.getTitle(),
                updateJob.getUrl(),
                updateJob.getCompany(),
                updateJob.getNoticeEndDate(),
                updateJob.getJobGroup(),
                updateJob.getActive()
        );
    }

    @Transactional
    public void deleteAll(List<JobVO> jobs) {
        jobRepository.deleteAllById(jobs.stream()
                .map(JobVO::getId)
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
