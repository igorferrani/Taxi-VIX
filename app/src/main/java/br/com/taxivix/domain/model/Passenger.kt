package br.com.taxivix.domain.model

interface Passenger : User {
    val currentAddress: Address
}