package br.com.taxivix.ui.detailtaxistand.presentation

import androidx.lifecycle.LiveData
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
                val result = detailTaxiStandUseCase(id)
                _uiState.update {
                    it.copy(
                        isSuccessful = true,
                        taxiStand = result
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