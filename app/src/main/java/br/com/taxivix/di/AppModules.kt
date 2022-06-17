package br.com.taxivix.di

import br.com.taxivix.data.AddressRemoteRepositoryImpl
import br.com.taxivix.data.TaxiRemoteRepositoryImpl
import br.com.taxivix.domain.repository.AddressRepository
import br.com.taxivix.domain.repository.TaxiRemoteRepository
import br.com.taxivix.domain.usecase.*
import br.com.taxivix.ui.confirmaddress.presentation.ConfirmAddressViewModel
import br.com.taxivix.ui.detailtaxistand.presentation.DetailTaxiStandViewModel
import br.com.taxivix.ui.listtaxistands.presentation.ListTaxiStandsViewModel
import br.com.taxivix.ui.splashscreen.presentation.SplashScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val appModules: Module = module {
    factory { TaxiRemoteRepositoryImpl() as TaxiRemoteRepository }
    factory { AddressRemoteRepositoryImpl() as AddressRepository }

    factory { ListTaxiStandsUseCase(get()) }
    factory { DetailTaxiStandUseCase(get()) }
    factory { ListStatesUseCase(get()) }
    factory { ListCitiesUseCase(get()) }

    viewModel { ListTaxiStandsViewModel(get()) }
    viewModel { ConfirmAddressViewModel(get(), get(), get()) }
    viewModel { SplashScreenViewModel(get()) }
    viewModel { DetailTaxiStandViewModel(get()) }
}