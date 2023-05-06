package com.example.jobclique

import com.google.firebase.firestore.FieldValue

class JobApplicationModel(
    var Name: String? = null,
    var Email: String? = null,
    var PhoneNo: String? = null,
    var Optional: String,
    var Status: String? = null,
    var ID :String,
    var AppliedDate: FieldValue ?= null,
    var UserID: String? = null,


//    var JobPostID: String? = null,





    )