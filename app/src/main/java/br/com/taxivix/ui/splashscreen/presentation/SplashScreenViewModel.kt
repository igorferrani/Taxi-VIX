package br.com.taxivix.ui.splashscreen.presentation

import android.app.Application
import androidx.core.content.edit
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.taxivix.domain.usecase.ListTaxiStandsUseCase
import br.com.taxivix.util.SharedPreferencesManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SplashScreenViewModel(
    app: Application,
) : AndroidViewModel(app) {
    private val _uiState = MutableStateFlow(SplashScreenState(Pages.SPLASH))
    val uiState: StateFlow<SplashScreenState> = _uiState.asStateFlow()

    fun checkAddressConfirmed() {
        viewModelScope.launch {
            try {
                val shared = SharedPreferencesManager.getInstance(getApplication())
                val uf = shared.getString("uf", "")
                val city = shared.getString("city", "")
                onResultCheckAddressConfirmed(uf?.isNotEmpty() ?: false && city?.isNotEmpty() ?: false)
            } catch (e: Exception) {
                _uiState.update { it.copy( redirectTo = Pages.SPLASH ) }
            }
        }
    }

    private fun onResultCheckAddressConfirmed(confirmed: Boolean) {
        _uiState.update { it.copy( redirectTo = if (confirmed) Pages.LIST_TAXI_STANDS else Pages.CONFIRM_ADDRESS) }
    }
}