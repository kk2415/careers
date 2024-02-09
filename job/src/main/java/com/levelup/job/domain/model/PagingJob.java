package com.levelup.job.domain.model;

import java.util.List;

public record PagingJob(
        List<Job> jobs,
        Long totalElements,
        Integer totalPages
) {
    public static PagingJob of(
            List<Job> jobs,
            Long totalElements,
            Integer totalPages
    ) {
        return new PagingJob(
                jobs,
                totalElements,
                totalPages
        );
    }
}
