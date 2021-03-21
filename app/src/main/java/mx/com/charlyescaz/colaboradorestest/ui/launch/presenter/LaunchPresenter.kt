package mx.com.charlyescaz.colaboradorestest.ui.launch.presenter

import mx.com.charlyescaz.colaboradorestest.ui.launch.data.LaunchRepository
import mx.com.charlyescaz.colaboradorestest.ui.launch.view.interfaces.LaunchView

class LaunchPresenter(
    private val view: LaunchView,
    private val repository: LaunchRepository
) {

    fun checkSession(){
        repository.checkUser { user ->
            view.onSessionCheck(user)
        }
    }
}