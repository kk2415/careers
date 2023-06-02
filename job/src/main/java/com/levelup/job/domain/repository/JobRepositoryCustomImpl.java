package com.levelup.job.domain.repository;

import com.levelup.job.domain.vo.JobFilterCondition;
import com.levelup.job.domain.entity.Job;
import com.levelup.job.domain.enumeration.Company;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;

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
        JPAQuery<Job> query = findByFilterCondition(filterCondition);

        if (page != null && size != null) {
            query = query
                    .offset(page)
                    .limit(size);
        }

        return query.fetch();
    }

    private JPAQuery<Job> findByFilterCondition(JobFilterCondition filterCondition) {
        return queryFactory
                .select(job)
                .from(job)
                .where(filterCompany(filterCondition.getCompany()))
                .orderBy(job.createdAt.desc());
    }

    private BooleanExpression filterCompany(Company company) {
        return company == null ? null : job.company.eq(company);
    }
}
