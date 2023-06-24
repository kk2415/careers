package com.levelup.api.controller.v1.dto;

import com.levelup.job.infrastructure.enumeration.Company;
import com.levelup.job.domain.model.Job;
import com.levelup.job.domain.model.PagingJob;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

public class JobDto {

    public record Request(
            @NotNull @NotBlank
            String title,

            @NotNull @NotBlank
            Company company,

            @NotNull
            String url,

            @NotNull
            String noticeEndDate
    ) {
        public static Request of(String title, Company company, String url, String noticeEndDate) {
            return new Request(title, company, url, noticeEndDate);
        }

        public Job toDomain() {
            return Job.of(title, company, url, noticeEndDate);
        }
    }

    public record Response(
            Long id,
            String title,
            Company company,
            String url,
            String noticeEndDate,
            LocalDateTime createdAt
    ) {
        public static Response from(Job jobVO) {
            return new Response(
                    jobVO.getId(),
                    jobVO.getTitle(),
                    jobVO.getCompany(),
                    jobVO.getUrl(),
                    jobVO.getNoticeEndDate(),
                    jobVO.getCreatedAt());
        }
    }

    public record PagingResponse(
            List<Response> jobs,
            Long totalCount
    ) {
        public static PagingResponse from(PagingJob pagingJob) {
            return new PagingResponse(pagingJob.jobs().stream().map(Response::from).toList(), pagingJob.totalCount());
        }
    }
}
