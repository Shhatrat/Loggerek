package com.shhatrat.loggerek.watch

import androidx.lifecycle.viewModelScope
import com.shhatrat.loggerek.base.BaseViewModel
import com.shhatrat.loggerek.base.ButtonAction
import com.shhatrat.loggerek.base.Error
import com.shhatrat.loggerek.base.Loader
import com.shhatrat.loggerek.manager.watch.GarminWatch
import com.shhatrat.loggerek.manager.watch.config.ConfigManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

data class WatchUiState(
    val loader: Loader = Loader(),
    val error: Error? = null,
    val updateDevicesButton: ButtonAction? = {},
    val selectButton: ButtonAction? = {},
    val devices: List<GarminItem> = listOf(),
    val savedDevice: GarminItem? = null,
    val removeSavedDeviceButtonAction: ButtonAction? = null
)

data class GarminItem(
    val device: GarminWatch.GarminDevice,
    val installedAppState: GarminWatch.InstalledAppState,
    val selected: Boolean,
    val selectable: Boolean,
    val onSelect: () -> Unit
)

class WatchViewModel(
    private val garminConfigManager: ConfigManager<GarminWatch.GarminDevice, GarminWatch.InstalledAppState>,
) : BaseViewModel<WatchUiState>(WatchUiState()) {

    override fun onStart() {
        super.onStart()
        updateUiState {
            copy(
                updateDevicesButton = { updateDevicesList() },
            )
        }
        viewModelScope.launch {
            garminConfigManager.init()
            updateDevicesList()
            loadSavedDevice()
        }
    }

    private fun onSelectedDevice(device: GarminWatch.GarminDevice) {
        val clickedDevice = state.value.devices.first { it.device.identifier == device.identifier }
        if (!clickedDevice.selected) {
            enableSaveSelectButton()
            updateUiState { copy(devices = devices.map { it.copy(selected = it.device.identifier == device.identifier) }) }
        }
    }

    private fun loadSavedDevice() {
        viewModelScope.launch {
            garminConfigManager.getSavedItem()?.let {
                GarminItem(
                    device = it.device,
                    installedAppState = it.isAppInstalled,
                    selected = false,
                    selectable = true,
                    onSelect = { onSelectedDevice(it.device) }
                )
            }?.let {
                updateUiState {
                    copy(savedDevice = it,
                        removeSavedDeviceButtonAction = { garminConfigManager.saveDevice(null); loadSavedDevice() })
                }
            }
        }

    }

    private fun enableSaveSelectButton() {
        updateUiState {
            copy(selectButton = {
                garminConfigManager.saveDevice(state.value.devices.first { it.selected }.device)
                loadSavedDevice()
            })
        }
    }

    private fun updateDevicesList() {
        viewModelScope.launch(Dispatchers.Default) {
            garminConfigManager.getDevices().map {
                if (it.device.connectedState == GarminWatch.GarminWatchState.CONNECTED) {
                    GarminItem(
                        device = it.device,
                        installedAppState = it.isAppInstalled,
                        selected = false,
                        selectable = true,
                        onSelect = { onSelectedDevice(it.device) }
                    )
                } else {
                    GarminItem(
                        device = it.device,
                        installedAppState = GarminWatch.InstalledAppState.UNKNOWN,
                        selected = false,
                        selectable = false,
                        onSelect = {}
                    )
                }
            }.run {
                updateUiState { copy(devices = this@run) }
            }
        }
    }
}