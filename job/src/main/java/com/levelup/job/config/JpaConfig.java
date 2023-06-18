package com.levelup.job.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan("com.levelup.job")
@EnableJpaRepositories("com.levelup.job")
@EnableJpaAuditing
@Configuration(value = "JobJpaConfig")
public class JpaConfig {
}
