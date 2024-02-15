package com.careers.job.domain.model;

import com.careers.job.infrastructure.enumeration.Company;
import com.careers.job.infrastructure.enumeration.OpenStatus;

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
