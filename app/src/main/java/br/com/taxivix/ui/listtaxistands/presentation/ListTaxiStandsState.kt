package br.com.taxivix.ui.listtaxistands.presentation

import br.com.taxivix.domain.model.TaxiStand
import java.lang.Exception

data class ListTaxiStandsState(
    val items: List<TaxiStand> = emptyList(),
    val isLoading: Boolean = false,
    val error: Exception? = null,
    val currentCityName: String = ""
)
