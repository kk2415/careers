package com.levelup.job.infrastructure.repository;

import com.levelup.job.infrastructure.jpaentity.JobEntity;
import com.levelup.job.infrastructure.enumeration.Company;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface JobRepository extends JpaRepository<JobEntity, Long>, JobRepositoryCustom {

    List<JobEntity> findByCompany(Company company);
    List<JobEntity> findByIsPushSent(Boolean isPushSent);

    @Query("SELECT j FROM JobEntity j WHERE j.company = :company AND j.createdAt >= :startOfDay AND j.createdAt <= :endOfDay")
    List<JobEntity> findByCompanyAndCreatedAt(
            @Param("company") Company company,
            @Param("startOfDay") LocalDateTime startOfDay,
            @Param("endOfDay") LocalDateTime endOfDay,
            Pageable pageable);

    @Query("SELECT j FROM JobEntity j WHERE j.createdAt >= :startOfDay AND j.createdAt <= :endOfDay")
    List<JobEntity> findByCreatedAt(
            @Param("startOfDay") LocalDateTime startOfDay,
            @Param("endOfDay") LocalDateTime endOfDay,
            Pageable pageable);

    @Query("SELECT j FROM JobEntity j WHERE (j.company = :company AND j.title = :title) OR (j.company = :company AND j.url = :url)")
    Optional<JobEntity> findByCompanyAndTitleOrCompanyAndUrl(@Param("company") Company company, @Param("title") String title, @Param("url") String url);
}
