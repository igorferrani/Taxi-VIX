package br.com.taxivix.ui.detailtaxistand.presentation

import br.com.taxivix.domain.model.TaxiStand

data class DetailTaxiStandState(
    val isLoading: Boolean = false,
    val taxiStand: TaxiStand? = null,
    val error: Exception? = null
)
