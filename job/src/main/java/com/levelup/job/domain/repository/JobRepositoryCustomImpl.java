package com.levelup.job.domain.repository;

import com.levelup.job.domain.VO.JobFilterCondition;
import com.levelup.job.domain.entity.Job;
import com.levelup.job.domain.entity.QJob;
import com.levelup.job.domain.enumeration.Company;
import com.levelup.job.domain.enumeration.OrderBy;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import java.util.List;

@RequiredArgsConstructor
public class JobRepositoryCustomImpl implements JobRepositoryCustom {

    private final EntityManager em;

    @Override
    public Page<Job> findByFilterCondition(JobFilterCondition filterCondition, OrderBy orderBy, Pageable pageable) {
        QJob job = QJob.job;

        final JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        List<Job> jobs = queryFactory
                .select(job)
                .from(job)
                .where(filterCompany(filterCondition.getCompany()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(job.createdAt.desc())
                .fetch();

        return new PageImpl<>(jobs, pageable, jobs.size());
    }

    private BooleanExpression filterCompany(Company company) {
        return company == null ? null : QJob.job.company.eq(company);
    }
}
