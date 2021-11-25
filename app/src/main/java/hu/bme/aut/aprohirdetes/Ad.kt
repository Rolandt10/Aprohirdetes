package hu.bme.aut.aprohirdetes

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Ad(val title: String? = null,
                val description: String? = null,
                val price: String? = null,
                val city: String? = null)
{ }