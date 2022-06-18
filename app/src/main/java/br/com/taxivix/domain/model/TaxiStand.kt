package br.com.taxivix.domain.model

interface TaxiStand : Address {
    val id: String
    val cityId: String
    val pointName: String
    val pointPhoto: String
    val pointPhone: String
}