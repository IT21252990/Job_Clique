package com.example.jobclique

import com.google.firebase.firestore.FieldValue

class JobApplicationModel(
    var Name: String? = null,
    var Email: String? = null,
    var PhoneNo: String? = null,
    var Optional: String? = null,
    var Status: String? = null,
    var AppliedDate: FieldValue ?= null,
    var UserID: String? = null,
//    var JobPostID: String? = null,





    )