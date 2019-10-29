package com.andalus.lifeachievements.enums

sealed class State {

    object NormalState : State()
    object LoadingState : State()
    object SuccessState : State()
    object ErrorState : State()

}
