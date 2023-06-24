package com.levelup.job.infrastructure.repository;

import com.levelup.job.infrastructure.jpaentity.JobEntity;
import com.levelup.job.infrastructure.enumeration.Company;
import com.levelup.job.domain.model.JobFilterCondition;
import com.levelup.job.infrastructure.jpaentity.QJobEntity;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import javax.persistence.EntityManager;
import java.util.List;

public class JobRepositoryCustomImpl implements JobRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public JobRepositoryCustomImpl(@Autowired EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);;
    }

    @Override
    public List<JobEntity> findByFilterCondition(JobFilterCondition filterCondition, Long size, Long page) {
        if (page != null && size != null) {
            PageRequest pageRequest = PageRequest.of(Math.toIntExact(page), Math.toIntExact(size));

            return queryFactory
                    .select(QJobEntity.jobEntity)
                    .from(QJobEntity.jobEntity)
                    .where(filterCompany(filterCondition.getCompany()))
                    .offset(pageRequest.getOffset())
                    .limit(pageRequest.getPageSize())
                    .orderBy(QJobEntity.jobEntity.createdAt.desc())
                    .fetch();
        } else {
            return queryFactory
                    .select(QJobEntity.jobEntity)
                    .from(QJobEntity.jobEntity)
                    .where(filterCompany(filterCondition.getCompany()))
                    .orderBy(QJobEntity.jobEntity.createdAt.desc())
                    .fetch();
        }
    }

    @Override
    public Long countByFilterCondition(JobFilterCondition filterCondition) {
        return queryFactory
                .select(QJobEntity.jobEntity)
                .from(QJobEntity.jobEntity)
                .where(filterCompany(filterCondition.getCompany()))
                .fetchCount();
    }

    private BooleanExpression filterCompany(Company company) {
        return company == null ? null : QJobEntity.jobEntity.company.eq(company);
    }
}
