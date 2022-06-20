package br.com.taxivix.ui.confirmaddress.presentation

import android.app.Application
import androidx.core.content.edit
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import br.com.taxivix.domain.usecase.ListCitiesUseCase
import br.com.taxivix.util.SharedPreferencesManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ConfirmAddressViewModel(
    app: Application,
    private val listCitiesUseCase: ListCitiesUseCase
) : AndroidViewModel(app) {

    private val _uiState = MutableStateFlow(ConfirmAddressState(false))
    val uiState: StateFlow<ConfirmAddressState> = _uiState.asStateFlow()

    private val _uiCityState = MutableStateFlow(CityState(false))
    val uiCityState: StateFlow<CityState> = _uiCityState.asStateFlow()

    private val _uiListCitiesState = MutableStateFlow(ListCitiesState())
    val uiListCitiesState: StateFlow<ListCitiesState> = _uiListCitiesState.asStateFlow()

    fun onEvent(event: ConfirmAddressEvent) {
        when (event) {
            is ConfirmAddressEvent.ConfirmAddress -> onConfirmAddress(event.cityId, event.cityName)
            is ConfirmAddressEvent.GetListCities -> onGetListCities(event.uf)
            is ConfirmAddressEvent.ChangeCity -> onChangeCity(event.cityId, event.cityName)
            ConfirmAddressEvent.GetCurrentCity -> onGetCurrentCity()
        }
    }

    private fun onConfirmAddress(cityId: String, cityName: String) {
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isLoading = true) }
                SharedPreferencesManager.getInstance(getApplication())
                    .edit {
                        putString("cityId", cityId).commit()
                        putString("cityName", cityName).commit()
                    }
                _uiState.update { it.copy(isSuccessful = true) }
            } catch (e: Exception) {
                _uiState.update { it.copy(isSuccessful = false) }
            } finally {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    private fun onGetListCities(uf: String) {
        viewModelScope.launch {
            try {
                _uiListCitiesState.update { it.copy(isLoading = true) }
                val result = listCitiesUseCase(uf)
                _uiListCitiesState.update { it.copy(list = result) }
            } catch (e: Exception) {
                _uiListCitiesState.update { it.copy(error = e) }
            } finally {
                _uiListCitiesState.update { it.copy(isLoading = false) }
            }
        }
    }

    private fun onChangeCity(cityId: String, cityName: String) {
        _uiCityState.update {
            it.copy(
                citySelected = true,
                cityId = cityId,
                cityName = cityName
            )
        }
    }

    private fun onGetCurrentCity() {
        val shared = SharedPreferencesManager.getInstance(getApplication())
        val cityId = shared.getString("cityId", "") ?: ""
        val cityName = shared.getString("cityName", "") ?: ""

        _uiCityState.update {
            it.copy(
                citySelected = cityId.isNotEmpty() && cityName.isNotEmpty(),
                cityId = cityId,
                cityName = cityName
            )
        }
    }
}