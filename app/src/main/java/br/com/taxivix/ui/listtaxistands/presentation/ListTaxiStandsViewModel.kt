package br.com.taxivix.ui.listtaxistands.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.taxivix.domain.usecase.ListTaxiStandsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ListTaxiStandsViewModel(
    private val listTaxiStandsUseCase: ListTaxiStandsUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(ListTaxiStandsState(false))
    val uiState: StateFlow<ListTaxiStandsState> = _uiState.asStateFlow()

    fun getListTaxiStands() {
        viewModelScope.launch {
            try {
                val result = listTaxiStandsUseCase()
                _uiState.update {
                    it.copy(
                        isSuccessful = true,
                        items = result
                    )
                }
            } catch (e: Exception) {
                onErrorListTaxiStands(e)
            }
        }
    }

    private fun onErrorListTaxiStands(e: Exception) {

    }
}