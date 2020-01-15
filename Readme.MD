#### **Job Management Service**

###### **Modules**
- **job-api** - common API classes
- **job-impls** - 2 mock job implementations (long-operation and summ)  
- **job-manager-core** - engine of job management service
- **job-manager-web** - REST API for job management service 

###### **Starting**
 - run in project root **mvn install**
 
###### **REST API**

 - Add new Job:  
    - POST http://localhost:8080/job/
    - REQUEST BODY FOR **SUMM_MOCK_JOB**
    ---
    ```json
    {
    	"jobName": "SUMM_MOCK_JOB",
    	"additionalParams": {
    		"param1" : "1",
    		"param2" : "4"
    	},
         "startDate": "2020-01-15 16:02:00"
    }
    ```
     - REQUEST BODY FOR **LONG_OPERATION_MOCK_JOB**, without **startDate** will start immediately
      ---
      ```json
      {
        "jobName": "LONG_OPERATION_MOCK_JOB",
        "additionalParams": {
            "waitInSeconds" : "10",
            "repeatCount" : "5"
        }
      }
      ```
  
 - Get all added Jobs: GET 
    http://localhost:8080/job/
 - Get Job by Id: GET
    http://localhost:8080/job/<**JOB_ID**>,
    **JOB_ID** from Response after adding Job    
    
    