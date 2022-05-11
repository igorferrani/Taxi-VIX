package br.com.taxivix.ui.confirmaddress.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.taxivix.domain.usecase.ConfirmUserAddressUseCase
import kotlinx.coroutines.launch

class ConfirmAddressViewModel(
    private val confirmUserAddress: ConfirmUserAddressUseCase
) : ViewModel() {
    fun confirmUserAddress() {
        viewModelScope.launch {
            try {
                confirmUserAddress()
                onSuccessListTaxiStands()
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