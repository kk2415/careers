package com.levelup.job.domain.vo;

import java.util.List;

public record PagingJob(
        List<JobVO> jobs,
        Long totalCount
) {
    public static PagingJob of(List<JobVO> jobs, Long totalCount) {
        return new PagingJob(jobs, totalCount);
    }
}
