package com.shhatrat.loggerek.watch

import androidx.lifecycle.viewModelScope
import com.shhatrat.loggerek.base.BaseViewModel
import com.shhatrat.loggerek.base.ButtonAction
import com.shhatrat.loggerek.base.Error
import com.shhatrat.loggerek.base.Loader
import com.shhatrat.loggerek.manager.watch.GarminWatch
import com.shhatrat.loggerek.manager.watch.Watch
import com.shhatrat.loggerek.manager.watch.model.WatchCache
import com.shhatrat.loggerek.manager.watch.model.WatchData
import com.shhatrat.loggerek.manager.watch.model.WatchLog
import com.shhatrat.loggerek.manager.watch.model.WatchSendKeys
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

data class WatchUiState(
    val loader: Loader = Loader(),
    val error: Error? = null,
    val initButton: ButtonAction = {},
    val sendButton: ButtonAction = {},
    val devices: List<GarminWatch.GarminDevice> = listOf()
)

class WatchViewModel(
    private val watchManager: GarminWatch,
) : BaseViewModel<WatchUiState>(WatchUiState()) {


    override fun onStart() {
        super.onStart()

        updateUiState {
            copy(
                initButton = {
                    viewModelScope.launch {
                        watchManager.init()
                        updateUiState { copy(devices = watchManager.getListOfDevices()) }
                    }
                },
                sendButton = {

//                    viewModelScope.launch {
//                        watchManager.getListOfDevices().forEach {
//                            println("${it.name} --> ${watchManager.isAppInstalled(it)}")
//                        }
//                    }


                    viewModelScope.launch(Dispatchers.Default) {
                        watchManager.selectDevice(watchManager.getListOfDevices().first())
                        watchManager.loadApp()
                        watchManager.getData().collect {
                            println(it)
                            viewModelScope.launch(Dispatchers.Unconfined) {
                                watchManager.sendData(
                                    WatchSendKeys.GET_DATA(
                                        watchData = WatchData(
                                            items = listOf(
                                                WatchCache("[OH] #6 Biblioteka", "OPE4EA"),
                                            WatchCache("Staw Schrödingera", "OPE40A"),
                                            WatchCache("Staw na ulicy Fiołkowej", "OPA00A"),
                                            WatchCache("GORUSZKA", "OPAEFA"),
                                            WatchCache("Cerkwiska i Cmentarze -Smerek", "OPA34D"),
                                            ),
                                            logs = listOf(
                                                WatchLog("Wszystko gra", "1", "found"),
                                            WatchLog("super skrzyneczka", "2", "found"),
                                            WatchLog("cos tam", "3", "comment"),
                                            WatchLog("ni ma", "4", "not found")
                                            )
                                        )
                                    )
                                )
                            }
                        }
                    }
                })
        }
    }
}