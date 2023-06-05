package com.levelup.job.domain.repository;

import com.levelup.job.domain.vo.JobFilterCondition;
import com.levelup.job.domain.entity.Job;

import java.util.List;

public interface JobRepositoryCustom {

    List<Job> findByFilterCondition(JobFilterCondition filterCondition, Long size, Long page);
    Long countByFilterCondition(JobFilterCondition filterCondition);
}
