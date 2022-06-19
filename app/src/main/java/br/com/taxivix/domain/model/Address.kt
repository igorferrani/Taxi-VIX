package br.com.taxivix.domain.model

interface Address {
    val fullNameOfAddress: String
    val latitude: Double
    val longitude: Double
}