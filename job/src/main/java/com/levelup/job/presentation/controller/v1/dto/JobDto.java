package com.levelup.job.presentation.controller.v1.dto;

import com.levelup.job.domain.model.CreateJob;
import com.levelup.job.domain.model.Job;
import com.levelup.job.domain.model.PagingJob;
import com.levelup.job.infrastructure.enumeration.Company;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

public class JobDto {

    public record Requests(
            List<Request> jobs
    ) {
        public List<CreateJob> toDomain() {
            return jobs.stream()
                    .map(jobRequest -> CreateJob.of(
                            jobRequest.title(),
                            jobRequest.company(),
                            jobRequest.url(),
                            jobRequest.noticeEndDate(),
                            jobRequest.jobGroup()
                    ))
                    .toList();
        }
    }

    public record Request(
            @NotNull @NotBlank
            String title,

            @NotNull @NotBlank
            Company company,

            @NotNull
            String url,

            @NotNull
            String noticeEndDate,

            @NotNull
            String jobGroup
    ) {
    }

    public record Response(
            Long id,
            String title,
            Company company,
            String url,
            String noticeEndDate,
            LocalDateTime createdAt
    ) {
        public static Response from(Job job) {
            return new Response(
                    job.getId(),
                    job.getTitle(),
                    job.getCompany(),
                    job.getUrl(),
                    job.getNoticeEndDate(),
                    job.getCreatedAt()
            );
        }
    }

    public record PagingResponse(
            List<Response> jobs,
            Long totalElements,
            Integer totalPages
    ) {
        public static PagingResponse from(PagingJob pagingJob) {
            return new PagingResponse(pagingJob.jobs().stream()
                    .map(Response::from)
                    .toList(),
                    pagingJob.totalElements(),
                    pagingJob.totalPages()
            );
        }
    }
}
