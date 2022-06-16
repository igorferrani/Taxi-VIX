package br.com.taxivix.ui.splashscreen.presentation

data class SplashScreenState(
    var redirectTo: Pages
)

enum class Pages {
    LIST_TAXI_STANDS, CONFIRM_ADDRESS, SPLASH
}
