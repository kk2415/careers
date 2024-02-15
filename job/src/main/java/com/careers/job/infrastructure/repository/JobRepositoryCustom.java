package com.careers.job.infrastructure.repository;

import com.careers.job.domain.model.JobSearchCondition;
import com.careers.job.infrastructure.jpaentity.JobEntity;
import org.springframework.data.domain.PageImpl;

public interface JobRepositoryCustom {

    PageImpl<JobEntity> search(JobSearchCondition filterCondition, Integer page, Integer size);
}
