package com.example.jobclique

import com.google.firebase.firestore.FieldValue
import java.util.*

class JobApplicationModel(
    var Name: String? = null,
    var Email: String? = null,
    var PhoneNo: String? = null,
    var Optional: String,
    var Status: String? = null,
    var id : String? = null,
    var AppliedDate: Date?= null,
    var UserID: String? = null,
    var employerID: String? = null,


//    var JobPostID: String? = null,

    )

{
    // Empty constructor required for Firestore deserialization
    constructor() : this(null, null, null, "", null, null, null, null)
}

