package tech.bosta.bostatask.domain.entities

import java.io.Serializable

class UsersResponse : ArrayList<UsersResponseItem>()

data class UsersResponseItem(
    val address: Address,
    val company: Company,
    val email: String,
    val id: Int,
    val name: String,
    val phone: String,
    val username: String,
    val website: String
): Serializable {
    fun toStringAddress():String = "${address.street},${address.suite},${address.city},\n${address.zipcode}"
}

data class Address(
    val city: String,
    val geo: Geo,
    val street: String,
    val suite: String,
    val zipcode: String
): Serializable

data class Company(
    val bs: String,
    val catchPhrase: String,
    val name: String
): Serializable

data class Geo(
    val lat: String,
    val lng: String
): Serializable