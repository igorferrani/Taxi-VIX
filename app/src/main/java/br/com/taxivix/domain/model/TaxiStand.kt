package br.com.taxivix.domain.model

interface TaxiStand : Address {
    val pointName: String
    val pointPhotos: List<String>
    val pointPhone: String
}