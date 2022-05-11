package br.com.taxivix.ui.listtaxistands.presentation

import br.com.taxivix.domain.model.TaxiStand

sealed class ListTaxiStandsState {
    data class ShowList(val items: List<TaxiStand>) : ListTaxiStandsState()
    object HideList : ListTaxiStandsState()
}
