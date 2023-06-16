package com.levelup.job.domain.repository;

import com.levelup.job.domain.entity.Job;
import com.levelup.job.domain.enumeration.Company;
import com.levelup.job.domain.vo.JobFilterCondition;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import javax.persistence.EntityManager;
import java.util.List;

import static com.levelup.job.domain.entity.QJob.job;

public class JobRepositoryCustomImpl implements JobRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public JobRepositoryCustomImpl(@Autowired EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);;
    }

    @Override
    public List<Job> findByFilterCondition(JobFilterCondition filterCondition, Long size, Long page) {
        if (page != null && size != null) {
            PageRequest pageRequest = PageRequest.of(Math.toIntExact(page), Math.toIntExact(size));

            return queryFactory
                    .select(job)
                    .from(job)
                    .where(filterCompany(filterCondition.getCompany()))
                    .offset(pageRequest.getOffset())
                    .limit(pageRequest.getPageSize())
                    .orderBy(job.createdAt.desc())
                    .fetch();
        } else {
            return queryFactory
                    .select(job)
                    .from(job)
                    .where(filterCompany(filterCondition.getCompany()))
                    .orderBy(job.createdAt.desc())
                    .fetch();
        }
    }

    @Override
    public Long countByFilterCondition(JobFilterCondition filterCondition) {
        return queryFactory
                .select(job)
                .from(job)
                .where(filterCompany(filterCondition.getCompany()))
                .fetchCount();
    }

    private BooleanExpression filterCompany(Company company) {
        return company == null ? null : job.company.eq(company);
    }
}
