package br.com.taxivix.ui.listtaxistands.presentation

import android.app.Application
import android.location.Location
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

    private val _locationState = MutableStateFlow(UserLocationState())
    val locationState: StateFlow<UserLocationState> = _locationState.asStateFlow()
        get() = field.apply {
            value.isUsingLocation = SharedPreferencesManager.getInstance(getApplication())
                .getBoolean("isUsingLocation", false)
        }

    fun onEvent(event: ListTaxiStandsEvent) {
        when (event) {
            is ListTaxiStandsEvent.GetListTaxiStands -> getListTaxiStands(event.location)
            ListTaxiStandsEvent.GetCurrentCity -> onGetCurrentCity()
            is ListTaxiStandsEvent.ChangePrefUserLocation -> onChangeStateLocation(event.isUsingLocation)
        }
    }

    private fun getListTaxiStands(location: Location?) {
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isLoading = true) }
                val cityId = SharedPreferencesManager.getInstance(getApplication())
                    .getString("cityId", "")
                val result = listTaxiStandsUseCase(cityId ?: "", location)
                _uiState.update { it.copy(items = result) }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e) }
            } finally {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    private fun onChangeStateLocation(isUsingLocation: Boolean) {
        SharedPreferencesManager.getInstance(getApplication())
            .edit { putBoolean("isUsingLocation", isUsingLocation).commit() }
        _locationState.update { it.copy(
            isUsingLocation = isUsingLocation,
            valueChanged = true
        ) }
    }

    private fun onGetCurrentCity() {
        val shared = SharedPreferencesManager.getInstance(getApplication())
        val cityName = shared.getString("cityName", "")
        _uiState.update { it.copy(currentCityName = cityName ?: "") }
    }
}