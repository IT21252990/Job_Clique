package com.example.jobclique

import com.google.firebase.database.DatabaseReference

data class JobPosts(
    var jobName: String ?= null,
    var jobSalary: String ?= null,
    var employerID:  String ?= null)
