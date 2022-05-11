package br.com.taxivix.di

import br.com.taxivix.domain.repository.TaxiRemoteRepository
import br.com.taxivix.domain.repository.TaxiRemoteRepositoryImpl
import br.com.taxivix.domain.usecase.ListTaxiStandsUseCase
import br.com.taxivix.ui.listtaxistands.presentation.ListTaxiStandsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val appModules: Module = module {
    factory { TaxiRemoteRepositoryImpl() as TaxiRemoteRepository }
    factory { ListTaxiStandsUseCase(get()) }
    viewModel { ListTaxiStandsViewModel(get()) }
}