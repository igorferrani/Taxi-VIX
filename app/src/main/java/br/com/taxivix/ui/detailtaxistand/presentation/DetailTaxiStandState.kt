package br.com.taxivix.ui.detailtaxistand.presentation

import br.com.taxivix.domain.model.TaxiStand

data class DetailTaxiStandState(
    val isSuccessful: Boolean = false,
    val taxiStand: TaxiStand? = null
)
