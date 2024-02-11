package com.levelup.job.infrastructure.repository;

import com.levelup.job.domain.model.Paging;
import com.levelup.job.infrastructure.jpaentity.JobEntity;
import com.levelup.job.infrastructure.enumeration.Company;
import com.levelup.job.domain.model.JobSearchCondition;
import com.levelup.job.infrastructure.jpaentity.QJobEntity;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Objects;

public class JobRepositoryCustomImpl implements JobRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public JobRepositoryCustomImpl(@Autowired EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);;
    }

    @Override
    public PageImpl<JobEntity> search(
            JobSearchCondition filterCondition,
            Integer page,
            Integer size
    ) {
        if (Paging.pageable(size)) {
            final Pageable pageable = Paging.createPageable(page, size);

            final Long total = queryFactory
                    .select(Wildcard.count)
                    .from(QJobEntity.jobEntity)
                    .where(filterKeyword(filterCondition.keyword()))
                    .where(filterCompany(filterCondition.company()))
                    .fetchOne();
            final List<JobEntity> contents = queryFactory
                    .select(QJobEntity.jobEntity)
                    .from(QJobEntity.jobEntity)
                    .where(filterKeyword(filterCondition.keyword()))
                    .where(filterCompany(filterCondition.company()))
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize())
                    .orderBy(QJobEntity.jobEntity.createdAt.desc(), QJobEntity.jobEntity.id.desc())
                    .fetch();

            return new PageImpl<JobEntity>(contents, pageable, total);
        } else {
            final List<JobEntity> contents = queryFactory
                    .select(QJobEntity.jobEntity)
                    .from(QJobEntity.jobEntity)
                    .where(filterKeyword(filterCondition.keyword()))
                    .where(filterCompany(filterCondition.company()))
                    .orderBy(QJobEntity.jobEntity.createdAt.desc(), QJobEntity.jobEntity.id.desc())
                    .fetch();

            return new PageImpl<JobEntity>(contents);
        }
    }

    private BooleanExpression filterKeyword(String keyword) {
        return Objects.isNull(keyword) || keyword.isEmpty() || keyword.isBlank() ?
                null : QJobEntity.jobEntity.title.containsIgnoreCase(keyword);
    }

    private BooleanExpression filterCompany(Company company) {
        return Objects.isNull(company) ? null : QJobEntity.jobEntity.company.eq(company);
    }
}
