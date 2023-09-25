Feature: Test processJson method of API Controller

    Scenario: Make a date request with a valid result
        Given a date "20180131"
        When the user makes a request to ""
        Then the user should receive a code 200 response

    Scenario: Make a date request with a non existent date
        Given a date "22220101"
        When the user makes a request to ""
        Then the user should receive a code 404 response

    Scenario: Make a date request with a invalid date
        Given a date "1234"
        When the user makes a request to ""
        Then the user should receive a code 400 response

    Scenario: Make a date metrics request with a valid result
        Given a date "20180131"
        When the user makes a request to "/metrics"
        Then the user should receive a code 200 response

    Scenario: Make a date metrics request with a non existent date
        Given a date "22220101"
        When the user makes a request to "/metrics"
        Then the user should receive a code 404 response

    Scenario: Make a date metrics request with a invalid date
        Given a date "1234"
        When the user makes a request to "/metrics"
        Then the user should receive a code 400 response
    
    Scenario: Make a date kpis request with a valid result
        Given a date "20180131"
        When the user makes a request to "/kpis"
        Then the user should receive a code 200 response

    Scenario: Make a date kpis request with a non existent date
        Given a date "22220101"
        When the user makes a request to "/kpis"
        Then the user should receive a code 404 response

    Scenario: Make a date kpis request with a invalid date
        Given a date "1234"
        When the user makes a request to "/kpis"
        Then the user should receive a code 400 response

    Scenario: make a general metrics request with files on memory
        When the user makes a general request to "/metrics"
        Then the user should receive a code 200 response

    Scenario: make a general kpis request with files on memory
        When the user makes a general request to "kpis"
        Then the user should receive a code 200 response

    Scenario: Make a general metrics request without process previous files
        Given reset values on memory
        When the user makes a general request to "/metrics"
        Then the user should receive a code 400 response
    
    Scenario: make a general kpis request without process previous files
        Given reset values on memory
        When the user makes a general request to "/kpis"
        Then the user should receive a code 400 response

    