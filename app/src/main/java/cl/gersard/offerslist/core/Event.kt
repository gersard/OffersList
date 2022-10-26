package cl.gersard.offerslist.core

sealed class Event<out T> {
    data class Loading<out T>(val isLoading: Boolean) : Event<T>()
    data class Success<out T>(val items: T) : Event<T>()
    class Failure<out T> : Event<T>()
}
