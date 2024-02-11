package com.levelup.job.domain.model;

import com.levelup.job.infrastructure.enumeration.Company;
import com.levelup.job.infrastructure.enumeration.OpenStatus;

public record JobSearchCondition (
        String keyword,
        Company company,
        OpenStatus openStatus
) {
    public static JobSearchCondition of(
            String keyword,
            Company company,
            OpenStatus openStatus
    ) {
        return new JobSearchCondition(
                keyword,
                company,
                openStatus
        );
    }
}
