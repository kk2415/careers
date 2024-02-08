package com.levelup.crawler.domain.model;

import java.util.List;

public record PagingJob(
        List<Job> jobs,
        Long totalCount
) {
    public static PagingJob of(List<Job> jobs, Long totalCount) {
        return new PagingJob(jobs, totalCount);
    }
}
