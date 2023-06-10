package com.levelup.api.controller.v1.dto;

import com.levelup.job.domain.enumeration.Company;
import com.levelup.job.domain.vo.JobVO;
import com.levelup.job.domain.vo.PagingJob;

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

        public JobVO toDomain() {
            return JobVO.of(title, company, url, noticeEndDate);
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
        public static Response from(JobVO jobVO) {
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
