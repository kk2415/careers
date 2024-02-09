package com.levelup.job.infrastructure.repository;

import com.levelup.job.domain.model.JobSearchCondition;
import com.levelup.job.infrastructure.jpaentity.JobEntity;
import org.springframework.data.domain.PageImpl;

public interface JobRepositoryCustom {

    PageImpl<JobEntity> search(JobSearchCondition filterCondition, Integer page, Integer size);
}
