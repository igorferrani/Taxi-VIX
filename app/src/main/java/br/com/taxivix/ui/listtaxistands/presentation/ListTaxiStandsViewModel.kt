package br.com.taxivix.ui.listtaxistands.presentation

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

class ListTaxiStandsViewModel(
    app: Application,
    private val listTaxiStandsUseCase: ListTaxiStandsUseCase
) : AndroidViewModel(app) {
    private val _uiState = MutableStateFlow(ListTaxiStandsState())
    val uiState: StateFlow<ListTaxiStandsState> = _uiState.asStateFlow()

    fun onEvent(event: ListTaxiStandsEvent) {
        when (event) {
            ListTaxiStandsEvent.GetListTaxiStands -> getListTaxiStands()
            ListTaxiStandsEvent.GetCurrentCity -> onGetCurrentCity()
        }
    }

    private fun getListTaxiStands() {
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isLoading = true) }
                val cityId = SharedPreferencesManager.getInstance(getApplication())
                    .getString("cityId", "")
                val result = listTaxiStandsUseCase(cityId ?: "")
                _uiState.update { it.copy(items = result) }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e) }
            } finally {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    private fun onGetCurrentCity() {
        val shared = SharedPreferencesManager.getInstance(getApplication())
        val cityName = shared.getString("cityName", "")
        _uiState.update { it.copy(currentCityName = cityName ?: "") }
    }
}