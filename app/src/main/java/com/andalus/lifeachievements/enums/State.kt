package com.andalus.lifeachievements.enums

import android.os.Bundle

sealed class State {

    object NormalState : State()
    object LoadingState : State()
    object SuccessState : State()
    object ErrorState : State()

    val extras: Bundle = Bundle()

}
