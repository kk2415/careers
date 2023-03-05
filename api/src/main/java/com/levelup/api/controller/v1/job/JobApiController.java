package com.levelup.api.controller.v1.job;

import com.levelup.api.controller.v1.dto.JobDto;
import com.levelup.job.domain.VO.JobFilterCondition;
import com.levelup.job.domain.VO.JobVO;
import com.levelup.job.domain.enumeration.Company;
import com.levelup.job.domain.enumeration.OpenStatus;
import com.levelup.job.domain.enumeration.OrderBy;
import com.levelup.job.domain.service.JobService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Tag(name = "채용 공고 API")
@RequiredArgsConstructor
@RequestMapping("/api/v1/jobs")
@RestController
public class JobApiController {

    private final JobService jobService;
//    private final NotificationService notificationService;

//    @GetMapping("/test")
//    public void test() {
//        NotificationTemplateEntity notificationTemplateEntity = NotificationTemplateEntity.of(1L, "test push alram title", "test push alram body");
//        NotificationTemplate notificationTemplate = NotificationTemplate.from(notificationTemplateEntity);
//
//        notificationService.push(Notification.of(
//                1L,
//                1L,
//                NotificationType.ANDROID,
//                NotificationTemplateType.NEW_JOB,
//                notificationTemplate));
//    }

    @Operation(summary = "채용 공고 생성")
    @PostMapping("")
    public ResponseEntity<JobDto.Response> create(@RequestBody @Valid JobDto.Request request) {
        JobVO saveJob = jobService.save(request.toDomain());

        return ResponseEntity.ok().body(JobDto.Response.from(saveJob));
    }

    @Operation(summary = "채용 공고 페이징 조회")
    @GetMapping("")
    public ResponseEntity<List<JobDto.Response>> gets(
            @RequestParam(required = false) Company company,
            @RequestParam(required = false) OpenStatus openStatus,
            @RequestParam(defaultValue = "CREATED_AT") OrderBy orderBy,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "0") int page
    ) {
        List<JobVO> jobs = jobService.filtering(JobFilterCondition.of(company, openStatus), orderBy, PageRequest.of(page, size));

        return ResponseEntity.ok().body(jobs.stream()
                .map(JobDto.Response::from).toList());
    }

    @GetMapping("/today")
    public ResponseEntity<List<JobDto.Response>> getCreatedToday(
            @RequestParam(required = false) Company company,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        List<JobVO> jobs = jobService.getCreatedTodayByCompany(company, page, size);

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
