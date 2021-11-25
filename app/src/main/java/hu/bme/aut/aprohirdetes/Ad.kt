package hu.bme.aut.aprohirdetes

import com.google.firebase.database.IgnoreExtraProperties
import java.time.LocalDate
import java.util.*

@IgnoreExtraProperties
data class Ad(val title: String? = null,
              val description: String? = null,
              val price: String? = null,
              val city: String? = null,
              val createdAt: String? = null,
              val email: String? = null,
              val category: String? = null)
{ }