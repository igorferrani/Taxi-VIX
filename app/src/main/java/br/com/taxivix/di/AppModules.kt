package br.com.taxivix.di

import br.com.taxivix.data.TaxiRemoteRepositoryImpl
import br.com.taxivix.domain.repository.TaxiRemoteRepository
import br.com.taxivix.domain.usecase.ConfirmUserAddressUseCase
import br.com.taxivix.domain.usecase.DetailTaxiStandUseCase
import br.com.taxivix.domain.usecase.ListTaxiStandsUseCase
import br.com.taxivix.ui.confirmaddress.presentation.ConfirmAddressViewModel
import br.com.taxivix.ui.detailtaxistand.presentation.DetailTaxiStandViewModel
import br.com.taxivix.ui.listtaxistands.presentation.ListTaxiStandsViewModel
import br.com.taxivix.ui.splashscreen.presentation.SplashScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val appModules: Module = module {
    factory { TaxiRemoteRepositoryImpl() as TaxiRemoteRepository }
    factory { ListTaxiStandsUseCase(get()) }
    factory { ConfirmUserAddressUseCase(get()) }
    factory { DetailTaxiStandUseCase(get()) }

    viewModel { ListTaxiStandsViewModel(get()) }
    viewModel { ConfirmAddressViewModel(get()) }
    viewModel { SplashScreenViewModel(get()) }
    viewModel { DetailTaxiStandViewModel(get()) }
}