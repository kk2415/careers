package com.careers.job.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan("com.careers")
@EnableJpaRepositories("com.careers")
@EnableJpaAuditing
@Configuration(value = "JobJpaConfig")
public class JpaConfig {
}
