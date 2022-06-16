package br.com.taxivix.ui.confirmaddress.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.taxivix.domain.usecase.ConfirmUserAddressUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ConfirmAddressViewModel(
    private val confirmUserAddress: ConfirmUserAddressUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ConfirmAddressState(false))
    val uiState: StateFlow<ConfirmAddressState> = _uiState.asStateFlow()

    fun onEvent(event: ConfirmAddressEvent) {
        when (event) {
            ConfirmAddressEvent.ConfirmAddress -> onConfirmAddress()
        }
    }

    private fun onConfirmAddress() {
        viewModelScope.launch {
            try {
                confirmUserAddress()
                _uiState.update {
                    it.copy(
                        isSuccessful = true
                    )
                }
            } catch (e: Exception) {
                onErrorListTaxiStands(e)
            }
        }
    }

    private fun onSuccessListTaxiStands() {

    }

    private fun onErrorListTaxiStands(e: Exception) {

    }
}