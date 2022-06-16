package br.com.taxivix.ui.listtaxistands.presentation

import br.com.taxivix.domain.model.TaxiStand

data class ListTaxiStandsState(
    val isSuccessful: Boolean = false,
    val items: List<TaxiStand> = emptyList()
)
