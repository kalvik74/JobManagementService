package com.java993.jobmanagementservice.web.rest;

import com.java993.jobmanagementservice.api.JobInfo;
import com.java993.jobmanagementservice.core.JobManager;
import com.java993.jobmanagementservice.web.rest.dto.JobConfig;
import com.java993.jobmanagementservice.web.rest.dto.ResponseStatus;
import com.java993.jobmanagementservice.web.rest.dto.StdResponse;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/job")
public class JobResource {

    @Resource
    private JobManager jobManager;

    @GetMapping("/{id}")
    public StdResponse<JobInfo> status(@PathVariable String id) {
        try {
            JobInfo jobInfo = jobManager.getJobInfo(id);
            return new StdResponse<JobInfo>(
                    ResponseStatus.SUCCESS,
                    jobManager.getJobInfo(id),
                    null
            );
        }catch (Exception e) {
            return new StdResponse<>(ResponseStatus.ERROR, null, e.toString());
        }
    }

    @GetMapping("/")
    public StdResponse<List<JobInfo>> status() {
        return new StdResponse<List<JobInfo>>(
                ResponseStatus.SUCCESS,
                jobManager.getJobInfoList(),
                null
        );
    }

    @PostMapping("/")
    public StdResponse<String> add(@RequestBody JobConfig request) {
        Date executeDate;
        try {
            if (request.getStartDate() != null) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                executeDate = simpleDateFormat.parse(request.getStartDate());
                if (executeDate.getTime() < new Date().getTime()) {
                    throw new IllegalArgumentException("date " + request.getStartDate() + " is in the past");
                }
            } else {
                executeDate = new Date();
            }
            String id = jobManager.addJob(request.getJobName(), request.getAdditionalParams(), executeDate);
            return new StdResponse<>(ResponseStatus.SUCCESS, id, null);
        } catch (Exception e) {
            return new StdResponse<>(ResponseStatus.ERROR, null, e.toString());
        }

    }

}
