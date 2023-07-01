package com.levelup.job.crawler;

import com.levelup.job.domain.model.Job;
import com.levelup.job.infrastructure.enumeration.Company;

import java.util.List;

public interface Crawler {

    Company getCompany();
    List<Job> crawling();
}
