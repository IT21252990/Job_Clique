package com.example.jobclique

import com.google.firebase.firestore.FieldValue
import com.google.type.Date

data class JobPostsModel(

//    var jPostID: String? = null,
    var JobName: String? = null,
    var JobDescrip: String? = null,
    var JobCNumber: String? = null,
    var JobSalary: String? = null,
    var JobDate: String? = null,
//    val timestamp: Long,
    var id:String? = null,

    var EmployerID:EmployerRegistrationNextPart? =null
)


