package br.com.taxivix.ui.confirmaddress.presentation

import android.app.Application
import androidx.core.content.edit
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import br.com.taxivix.domain.usecase.ListCitiesUseCase
import br.com.taxivix.domain.usecase.ListStatesUseCase
import br.com.taxivix.util.SharedPreferencesManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ConfirmAddressViewModel(
    app: Application,
    private val listStatesUseCase: ListStatesUseCase,
    private val listCitiesUseCase: ListCitiesUseCase
    ) : AndroidViewModel(app) {

    private val _uiState = MutableStateFlow(ConfirmAddressState(false))
    val uiState: StateFlow<ConfirmAddressState> = _uiState.asStateFlow()

    private val _uiListStatesState = MutableStateFlow(ListStatesState.Success(emptyList()))
    val uiListStatesState: StateFlow<ListStatesState> = _uiListStatesState.asStateFlow()

    private val _uiListCitiesState = MutableStateFlow(ListCitiesState.Success(emptyList()))
    val uiListCitiesState: StateFlow<ListCitiesState> = _uiListCitiesState.asStateFlow()

    fun onEvent(event: ConfirmAddressEvent) {
        when (event) {
            is ConfirmAddressEvent.ConfirmAddress -> onConfirmAddress(event.uf, event.city)
            is ConfirmAddressEvent.GetListCities -> onGetListCities(event.uf)
            ConfirmAddressEvent.GetListStates -> onGetListStates()
        }
    }

    private fun onConfirmAddress(uf: String, city: String) {
        viewModelScope.launch {
            try {
                SharedPreferencesManager.getInstance(getApplication())
                    .edit {
                        putString("uf", uf)
                            .commit()
                        putString("city", city)
                            .commit()
                    }
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

    private fun onGetListCities(uf: String) {
        viewModelScope.launch {
            try {
                val result = listCitiesUseCase(uf)
                _uiListCitiesState.update {
                    it.copy(
                        list = result
                    )
                }
            } catch (e: Exception) {
                onErrorListTaxiStands(e)
            }
        }
    }

    private fun onGetListStates() {
        viewModelScope.launch {
            try {
                val result = listStatesUseCase()
                _uiListStatesState.update {
                    it.copy(
                        list = result
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