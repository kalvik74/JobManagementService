package com.java993.jobmanagementservice.web.rest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import com.java993.jobmanagementservice.api.JobInfo;
import com.java993.jobmanagementservice.api.JobStatus;
import com.java993.jobmanagementservice.web.Application;
import com.java993.jobmanagementservice.web.rest.dto.JobConfig;
import com.java993.jobmanagementservice.web.rest.dto.ResponseStatus;
import com.java993.jobmanagementservice.web.rest.dto.StdResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.springframework.test.web.servlet.MockMvc;

import java.text.DateFormat;
import java.text.SimpleDateFormat;


@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = Application.class)
@AutoConfigureMockMvc
public class JobResourceTest {

    private ObjectMapper objectMapper;

    @Before
    public void setUp() throws Exception {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(df);
    }

    @Autowired
    private MockMvc mvc;

    @Test
    public void addJobTest() throws Exception {

        //add job
        StdResponse<String> addJobResponse = objectMapper.readValue(
                mvc.perform(post("/job/")
                        .contentType("application/json")
                        .content(
                                objectMapper.writeValueAsString(
                                        new JobConfig(
                                                new ImmutableMap.Builder<String, String>()
                                                        .put("param1", "5")
                                                        .put("param2", "10")
                                                        .build(),
                                                "SUMM_MOCK_JOB",
                                                null

                                        )
                                )
                        )
                ).andReturn().getResponse().getContentAsString(),
                new TypeReference<StdResponse<String>> () {});

        assertEquals(ResponseStatus.SUCCESS, addJobResponse.getStatus());
        assertNotNull(addJobResponse.getBody());

        StdResponse<JobInfo> jobInfoResponse = objectMapper.readValue(
                mvc.perform(get("/job/" + addJobResponse.getBody())).andReturn().getResponse().getContentAsString(),
                new TypeReference<StdResponse<JobInfo>>() {});
        assertEquals(ResponseStatus.SUCCESS, jobInfoResponse.getStatus());
        assertEquals(addJobResponse.getBody(), jobInfoResponse.getBody().getId());
        assertEquals("SUMM_MOCK_JOB", jobInfoResponse.getBody().getType());
        assertEquals(JobStatus.SUCCESS, jobInfoResponse.getBody().getStatus());
    }

    @Test
    public void addJobTest_error_status() throws Exception {

        //add job
        StdResponse<String> addJobResponse = objectMapper.readValue(
                mvc.perform(post("/job/")
                        .contentType("application/json")
                        .content(
                                objectMapper.writeValueAsString(
                                        new JobConfig(
                                                new ImmutableMap.Builder<String, String>()
                                                        .put("param1", "5")
                                                        .put("param2", "ERROR!!!!!!!!!!!!!!!!!!!!!")
                                                        .build(),
                                                "SUMM_MOCK_JOB",
                                                null

                                        )
                                )
                        )
                ).andReturn().getResponse().getContentAsString(),
                new TypeReference<StdResponse<String>> () {});

        assertEquals(ResponseStatus.SUCCESS, addJobResponse.getStatus());
        assertNotNull(addJobResponse.getBody());

        StdResponse<JobInfo> jobInfoResponse = objectMapper.readValue(
                mvc.perform(get("/job/" + addJobResponse.getBody())).andReturn().getResponse().getContentAsString(),
                new TypeReference<StdResponse<JobInfo>>() {});
        assertEquals(ResponseStatus.SUCCESS, jobInfoResponse.getStatus());
        assertEquals(addJobResponse.getBody(), jobInfoResponse.getBody().getId());
        assertEquals("SUMM_MOCK_JOB", jobInfoResponse.getBody().getType());
        assertEquals(JobStatus.FAILED, jobInfoResponse.getBody().getStatus());
    }
}
