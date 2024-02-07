package com.levelup.job.controller.v1.job;

import com.levelup.job.controller.v1.dto.JobDto;
import com.levelup.job.domain.model.Job;
import com.levelup.job.domain.model.JobFilterCondition;
import com.levelup.job.domain.model.PagingJob;
import com.levelup.job.domain.service.JobService;
import com.levelup.job.infrastructure.enumeration.Company;
import com.levelup.job.infrastructure.enumeration.OpenStatus;
import com.levelup.job.infrastructure.enumeration.OrderBy;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Tag(name = "채용 공고 API")
@RequiredArgsConstructor
@RequestMapping("/api/v1/jobs")
@RestController
public class JobController {

    private final JobService jobService;

    @Operation(summary = "채용 공고 생성")
    @PostMapping("")
    public ResponseEntity<JobDto.Response> create(@RequestBody @Valid JobDto.Request request) {
        Job saveJob = jobService.save(request.toDomain());

        return ResponseEntity.ok().body(JobDto.Response.from(saveJob));
    }

    @Operation(summary = "채용 공고 페이징 조회")
    @GetMapping("")
    public ResponseEntity<JobDto.PagingResponse> gets(
            @RequestParam(required = false) Company company,
            @RequestParam(required = false) OpenStatus openStatus,
            @RequestParam(defaultValue = "CREATED_AT") OrderBy orderBy,
            @RequestParam(required = false) Long size,
            @RequestParam(required = false) Long page
    ) {
        PagingJob pagingJob = jobService.search(JobFilterCondition.of(company, openStatus), orderBy, size, page);

        return ResponseEntity.ok().body(JobDto.PagingResponse.from(pagingJob));
    }

    @GetMapping("/today")
    public ResponseEntity<List<JobDto.Response>> getCreatedToday(
            @RequestParam(required = false) Company company,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        List<Job> jobs = jobService.getCreatedTodayByCompany(company, page, size);

        return ResponseEntity.ok().body(jobs.stream()
                .map(JobDto.Response::from).toList());
    }

    @Operation(summary = "채용 공고 수정")
    @PatchMapping("")
    public ResponseEntity<Void> update(
            @RequestParam Long jobId,
            @RequestBody @Valid JobDto.Request request
    ) {
        jobService.update(jobId, request.toDomain());

        return ResponseEntity.ok().build();
    }
}
