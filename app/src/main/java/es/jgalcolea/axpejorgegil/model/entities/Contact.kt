package es.jgalcolea.axpejorgegil.model.entities

data class Contact(
    val name: String = "-",
    val email: String = "-",
    val gender: String = "-",
    val registerDate: String = "-",
    val phone: String = "-",
    val city: String = "Madrid",
    val photoUrl: String = ""
)
