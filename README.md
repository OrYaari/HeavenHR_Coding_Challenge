# HeavenHR_Coding_Challenge

Task for HeavenHR made by Or Yaari

## Task description

Build a backend service that handles a (very simple) recruiting process. 
The process requires two types of objects: job offers and applications from candidates

## User cases

* user has to be able to create a job offer and read a single and list all offers.
* candidate has to be able to apply for an offer.
* user has to be able to read one and list all applications per offer.
* user has to be able to progress the status of an application.
* user has to be able to track the number of applications.
* status change triggers a notification

## Solution explanation

Using Spring and h2 (embedded DB)  I created the backend for this challenge.

There are 6 API calls that we can make:

1. Create a job offer - a POST call to the URI ``/api/v1/heavenhr/offers`` with the body:
``{
    "jobTitle": "dev",
    "startDate": "07/05/2020"
  }``
  
2. Get all job offers - a GET call to the URI ``/api/v1/heavenhr/offers``
Should return a list of all job offers.

3. Get a single job - a GET call to the URI ``/api/v1/heavenhr/offers/{job_title}`` with `{job_title}` being the offer job title.

4. Apply for an application - a POST call to the URI ``/api/v1/heavenhr/applications`` with body like 
``{
    "offer": "dev",
    "candidateEmail": "mymail@gmai.com",
    "resume":"dda"
  }``
  
  5. Get applications - a GET call to URI ``/api/v1/heavenhr/applications`` to get all application.
  You can filter the applications by ``offer``/ ``email`` or ``applicationStatus`` the response is a list of all applications answering to the filters chosen.
  
  6. Progress application - a POST call to the URI ```/api/v1/heavenhr/applications/progress``` with body:
  ``{
       "offer": "dev",
       "candidateEmail": "myMail@gmai.com",
       "applicationStatus":"INVITED"
     }``
     The response is a message if the application progress was successful based on the new application status. 
 