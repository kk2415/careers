package com.levelup.job.infrastructure.repository;

import com.levelup.job.domain.model.JobFilterCondition;
import com.levelup.job.infrastructure.jpaentity.JobEntity;

import java.util.List;

public interface JobRepositoryCustom {

    List<JobEntity> findByFilterCondition(JobFilterCondition filterCondition, Long size, Long page);
    Long countByFilterCondition(JobFilterCondition filterCondition);
}
