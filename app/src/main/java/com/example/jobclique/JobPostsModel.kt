package com.example.jobclique

import com.google.firebase.firestore.FieldValue
import com.google.type.Date

data class JobPostsModel(

//    var JobId : String? = null,
    var JobName: String? = null,
    var JobDescrip: String? = null,
    var JobDate: String? = null,
    var JobCNumber: String? = null,
    var JobSalary: String? = null,

//    val timestamp: Long,
//    var id:String? = null,

    var EmployerID:String? = null
)

{
    // Empty constructor required for Firestore deserialization
    constructor() : this(null, null, null, null, null, null )
}


