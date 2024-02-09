package com.levelup.job.presentation.controller.v1.job;

import com.levelup.job.presentation.controller.v1.dto.JobDto;
import com.levelup.job.domain.model.Job;
import com.levelup.job.domain.model.JobSearchCondition;
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

    @Operation(summary = "신규 채용 공고 생성")
    @PostMapping("")
    public ResponseEntity<List<JobDto.Response>> create(@RequestBody @Valid JobDto.Requests request) {
        final List<Job> savedJob = jobService.saveIfAbsent(request.toDomain());

        return ResponseEntity.ok().body(savedJob.stream()
                .map(JobDto.Response::from)
                .toList());
    }

    @Operation(summary = "신규 채용 공고 생성 및 내려간 공고 삭제")
    @PostMapping("/create-drop")
    public ResponseEntity<List<JobDto.Response>> createAndDrop(@RequestBody @Valid JobDto.Requests request) {
        final List<Job> savedJob = jobService.saveIfAbsentAndDrop(request.toDomain());

        return ResponseEntity.ok().body(savedJob.stream()
                .map(JobDto.Response::from)
                .toList());
    }

    @Operation(summary = "채용 공고 페이징 조회")
    @GetMapping("")
    public ResponseEntity<JobDto.PagingResponse> gets(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(defaultValue = "CREATED_AT") OrderBy orderBy,
            @RequestParam(required = false) Company company,
            @RequestParam(required = false) OpenStatus openStatus
    ) {
        PagingJob pagingJob = jobService.search(
                JobSearchCondition.of(company, openStatus),
                orderBy,
                page,
                size
        );

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
        jobService.update(jobId, Job.of(
                request.title(),
                request.company(),
                request.url(),
                request.noticeEndDate()
        ));

        return ResponseEntity.ok().build();
    }
}
