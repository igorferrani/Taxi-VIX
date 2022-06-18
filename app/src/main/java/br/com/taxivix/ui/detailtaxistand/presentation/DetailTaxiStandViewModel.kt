package br.com.taxivix.ui.detailtaxistand.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.taxivix.domain.usecase.DetailTaxiStandUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DetailTaxiStandViewModel(
    private val detailTaxiStandUseCase: DetailTaxiStandUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(DetailTaxiStandState(false))
    val uiState: StateFlow<DetailTaxiStandState> = _uiState.asStateFlow()
    var phoneNumber: String? = null

    fun getTaxiStand(id: String) {
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isLoading = true) }
                val result = detailTaxiStandUseCase(id)
                _uiState.update { it.copy(taxiStand = result) }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e) }
            } finally {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }
}