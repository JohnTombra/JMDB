package com.smartapps.jmdb.enumeration.registrationdata.model



data class LoginBody(
    val password: String,
    val username: String,
    val email: String = ""
)//{"response":{"responseCode":"01","responseDescription":{"email":["The email field is required."]}}}
//{"response":{"responseCode":"01","responseDescription":"The provided credentials are incorrect"}}
//{"access_token":"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwczpcL1wvYWtzdWJlYnBheS5jb21cL2FwaVwvdjFcL2F1dGhcL2xvZ2luIiwiaWF0IjoxNzIxNjE0MjIyLCJleHAiOjE3MjE2MTc4MjIsIm5iZiI6MTcyMTYxNDIyMiwianRpIjoiUG1YQkt6bFR3TlVTM3ZJQiIsInN1YiI6ODczNDMsInBydiI6Ijg3ZTBhZjFlZjlmZDE1ODEyZmRlYzk3MTUzYTE0ZTBiMDQ3NTQ2YWEifQ.64cikhsQque2uZnVt-LfNM2Jq0SqVjdB3NdPmQYWrmg","token_type":"bearer"}