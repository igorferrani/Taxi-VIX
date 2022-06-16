package br.com.taxivix.domain.model

interface TaxiStand : Address {
    val pointName: String
    val pointPhoto: String
    val pointPhone: String
}