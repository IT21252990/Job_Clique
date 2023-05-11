package com.example.jobclique

import com.google.firebase.Timestamp

class JobApplicationData(
    var Id: String,
    var name: String? = null,
    var email: String? = null,
    var phoneNo: String? = null,
    var optional: String? = null,
    var status: String? = null,
    var appliedDate: Timestamp?= null,
    var userID: String? = null,
    var employerID:String ? = null,
)