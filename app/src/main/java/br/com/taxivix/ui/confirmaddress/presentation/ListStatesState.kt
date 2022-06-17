package br.com.taxivix.ui.confirmaddress.presentation

import br.com.taxivix.data.dto.StateResponse
import java.lang.Exception

sealed class ListStatesState {
    data class Success(val list: List<StateResponse>) : ListStatesState()
    data class Error(val exception: Exception) : ListStatesState()

    override fun equals(other: Any?): Boolean {
        return false
    }

    override fun hashCode(): Int {
        return javaClass.hashCode()
    }
}
