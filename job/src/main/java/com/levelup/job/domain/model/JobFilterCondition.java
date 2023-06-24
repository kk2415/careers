package com.levelup.job.domain.model;

import com.levelup.job.infrastructure.enumeration.Company;
import com.levelup.job.infrastructure.enumeration.OpenStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class JobFilterCondition {

    private Company company;
    private OpenStatus openStatus;

    public static JobFilterCondition of(Company company, OpenStatus openStatus) {
        return new JobFilterCondition(company, openStatus);
    }
}
