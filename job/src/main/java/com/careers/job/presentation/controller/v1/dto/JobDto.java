package com.careers.job.presentation.controller.v1.dto;

import com.careers.job.domain.model.CreateJob;
import com.careers.job.domain.model.Job;
import com.careers.job.domain.model.PagingJob;
import com.careers.job.infrastructure.enumeration.Company;

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
            String jobGroup,
            LocalDateTime createdAt
    ) {
        public static Response from(Job job) {
            return new Response(
                    job.id(),
                    job.title(),
                    job.company(),
                    job.url(),
                    job.noticeEndDate(),
                    job.jobGroup(),
                    job.createdAt()
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
