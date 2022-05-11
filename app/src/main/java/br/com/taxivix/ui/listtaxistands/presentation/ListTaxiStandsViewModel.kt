package br.com.taxivix.ui.listtaxistands.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.taxivix.domain.model.TaxiStand
import br.com.taxivix.domain.usecase.ListTaxiStandsUseCase
import kotlinx.coroutines.launch

class ListTaxiStandsViewModel(
    private val listTaxiStandsUseCase: ListTaxiStandsUseCase
) : ViewModel() {
    var state by mutableStateOf<ListTaxiStandsState>(ListTaxiStandsState.HideList)

    fun getListTaxiStands() {
        viewModelScope.launch {
            try {
                val result = listTaxiStandsUseCase()
                onSuccessListTaxiStands(result)
            } catch (e: Exception) {
                onErrorListTaxiStands(e)
            }
        }
    }

    private fun onSuccessListTaxiStands(result: List<TaxiStand>) {
        state = ListTaxiStandsState.ShowList(result)
    }

    private fun onErrorListTaxiStands(e: Exception) {

    }
}