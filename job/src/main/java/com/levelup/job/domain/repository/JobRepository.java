package com.levelup.job.domain.repository;

import com.levelup.job.domain.entity.Job;
import com.levelup.job.domain.enumeration.Company;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface JobRepository extends JpaRepository<Job, Long>, JobRepositoryCustom {

    List<Job> findByCompany(Company company);

    @Query("SELECT j FROM Job j WHERE j.company = :company AND j.createdAt >= :startOfDay AND j.createdAt <= :endOfDay")
    List<Job> findByCompanyAndCreatedAt(
            @Param("company") Company company,
            @Param("startOfDay") LocalDateTime startOfDay,
            @Param("endOfDay") LocalDateTime endOfDay,
            Pageable pageable);

    @Query("SELECT j FROM Job j WHERE j.createdAt >= :startOfDay AND j.createdAt <= :endOfDay")
    List<Job> findByCreatedAt(
            @Param("startOfDay") LocalDateTime startOfDay,
            @Param("endOfDay") LocalDateTime endOfDay,
            Pageable pageable);

    @Query("SELECT j FROM Job j WHERE (j.company = :company AND j.title = :title) OR (j.company = :company AND j.url = :url)")
    Optional<Job> findByCompanyAndTitleOrCompanyAndUrl(@Param("company") Company company, @Param("title") String title, @Param("url") String url);
}
