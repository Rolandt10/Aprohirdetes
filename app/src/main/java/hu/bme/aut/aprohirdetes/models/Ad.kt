package hu.bme.aut.aprohirdetes.models

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Ad(val uid: String? = null,
              val title: String? = null,
              val description: String? = null,
              val price: String? = null,
              val city: String? = null,
              val createdAt: String? = null,
              val email: String? = null,
              val phoneNumber: String? = null,
              val category: String? = null)