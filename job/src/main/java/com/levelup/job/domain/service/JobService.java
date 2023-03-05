package com.levelup.job.domain.service;

import com.levelup.job.domain.vo.JobFilterCondition;
import com.levelup.job.domain.vo.JobVO;
import com.levelup.job.domain.entity.Job;
import com.levelup.job.domain.enumeration.Company;
import com.levelup.job.domain.enumeration.OrderBy;
import com.levelup.job.domain.repository.JobRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class JobService {

    private final JobRepository jobRepository;

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
    public List<JobVO> filtering(JobFilterCondition filterCondition, OrderBy orderBy, Pageable pageable) {
        return jobRepository.findByFilterCondition(filterCondition, orderBy, pageable)
                .map(JobVO::from)
                .stream()
                .toList();
    }

    @Transactional(readOnly = true)
    public List<JobVO> getCreatedTodayByCompany(Company company, int page, int size) {
        LocalDateTime startOfDay = LocalDateTime.now().with(LocalTime.MIN);
        LocalDateTime endOfDay = LocalDateTime.now().with(LocalTime.MAX);
        Pageable pageable = PageRequest.of(page, size);

        if (company == null) {
            return jobRepository.findByCreatedAt(startOfDay, endOfDay, pageable).stream()
                    .map(JobVO::from)
                    .toList();
        }

        return jobRepository.findByCompanyAndCreatedAt(company, startOfDay, endOfDay, pageable).stream()
                .map(JobVO::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<JobVO> getNotMatched(List<JobVO> jobs, Company company) {
        List<JobVO> findJobs = jobRepository.findByCompany(company).stream()
                .map(JobVO::from)
                .toList();

        return findJobs.stream()
                .filter(findJob -> !jobs.contains(findJob)).toList();
    }

    @Transactional
    public void update(Long findJobId, JobVO updateJob) {
        Job findJob = jobRepository.findById(findJobId)
                .orElseThrow(() -> new EntityNotFoundException("해당 ID의 채용 공고를 찾을 수 없습니다."));

        findJob.update(updateJob.getTitle(), updateJob.getUrl(), updateJob.getCompany(), updateJob.getNoticeEndDate());
    }

    @Transactional
    public void deleteAll(List<JobVO> jobs) {
        jobRepository.deleteAll(
                jobs.stream()
                        .map(JobVO::toEntity)
                        .toList()
        );
    }
}
