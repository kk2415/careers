package com.levelup.job.domain.repository;

import com.levelup.job.domain.VO.JobFilterCondition;
import com.levelup.job.domain.entity.Job;
import com.levelup.job.domain.enumeration.OrderBy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface JobRepositoryCustom {

    Page<Job> findByFilterCondition(JobFilterCondition filterCondition, OrderBy orderBy, Pageable pageable);
}
