package io.github.takusan23.devicecontrolalldevicetypesample

import android.app.PendingIntent
import android.content.Intent
import android.service.controls.Control
import android.service.controls.ControlsProviderService
import android.service.controls.DeviceTypes
import android.service.controls.actions.BooleanAction
import android.service.controls.actions.ControlAction
import android.service.controls.templates.ControlButton
import android.service.controls.templates.ToggleTemplate
import io.reactivex.Flowable
import io.reactivex.processors.ReplayProcessor
import org.reactivestreams.FlowAdapters
import java.util.concurrent.Flow
import java.util.function.Consumer

class DeviceControlsService : ControlsProviderService() {

    lateinit var updatePublisher: ReplayProcessor<Control>

    /** DeviceType全種類 */
    val DEVICE_TYPE_ID_LIST = arrayListOf(
        DeviceTypes.TYPE_AC_HEATER,
        DeviceTypes.TYPE_AC_UNIT,
        DeviceTypes.TYPE_AIR_FRESHENER,
        DeviceTypes.TYPE_AIR_PURIFIER,
        DeviceTypes.TYPE_AWNING,
        DeviceTypes.TYPE_BLINDS,
        DeviceTypes.TYPE_CAMERA,
        DeviceTypes.TYPE_CLOSET,
        DeviceTypes.TYPE_COFFEE_MAKER,
        DeviceTypes.TYPE_CURTAIN,
        DeviceTypes.TYPE_DEHUMIDIFIER,
        DeviceTypes.TYPE_DISHWASHER,
        DeviceTypes.TYPE_DISPLAY,
        DeviceTypes.TYPE_DOOR,
        DeviceTypes.TYPE_DOORBELL,
        DeviceTypes.TYPE_DRAWER,
        DeviceTypes.TYPE_DRYER,
        DeviceTypes.TYPE_FAN,
        DeviceTypes.TYPE_GARAGE,
        DeviceTypes.TYPE_GATE,
        DeviceTypes.TYPE_GENERIC_ARM_DISARM,
        DeviceTypes.TYPE_GENERIC_LOCK_UNLOCK,
        DeviceTypes.TYPE_GENERIC_ON_OFF,
        DeviceTypes.TYPE_GENERIC_OPEN_CLOSE,
        DeviceTypes.TYPE_GENERIC_START_STOP,
        DeviceTypes.TYPE_GENERIC_TEMP_SETTING,
        DeviceTypes.TYPE_GENERIC_VIEWSTREAM,
        DeviceTypes.TYPE_HEATER,
        DeviceTypes.TYPE_HOOD,
        DeviceTypes.TYPE_HUMIDIFIER,
        DeviceTypes.TYPE_KETTLE,
        DeviceTypes.TYPE_LIGHT,
        DeviceTypes.TYPE_LOCK,
        DeviceTypes.TYPE_MICROWAVE,
        DeviceTypes.TYPE_MOP,
        DeviceTypes.TYPE_MOWER,
        DeviceTypes.TYPE_MULTICOOKER,
        DeviceTypes.TYPE_OUTLET,
        DeviceTypes.TYPE_PERGOLA,
        DeviceTypes.TYPE_RADIATOR,
        DeviceTypes.TYPE_REFRIGERATOR,
        DeviceTypes.TYPE_REMOTE_CONTROL,
        DeviceTypes.TYPE_ROUTINE,
        DeviceTypes.TYPE_SECURITY_SYSTEM,
        DeviceTypes.TYPE_SET_TOP,
        DeviceTypes.TYPE_SHOWER,
        DeviceTypes.TYPE_SHUTTER,
        DeviceTypes.TYPE_SPRINKLER,
        DeviceTypes.TYPE_STANDMIXER,
        DeviceTypes.TYPE_STYLER,
        DeviceTypes.TYPE_SWITCH,
        DeviceTypes.TYPE_THERMOSTAT,
        DeviceTypes.TYPE_TV,
        DeviceTypes.TYPE_UNKNOWN,
        DeviceTypes.TYPE_VACUUM,
        DeviceTypes.TYPE_VALVE,
        DeviceTypes.TYPE_WASHER,
        DeviceTypes.TYPE_WATER_HEATER,
        DeviceTypes.TYPE_WINDOW
    )

    /** DeviceType名前 */
    val DEVICE_TYPE_ID_NAME = arrayListOf(
        "TYPE_AC_HEATER",
        "TYPE_AC_UNIT",
        "TYPE_AIR_FRESHENER",
        "TYPE_AIR_PURIFIER",
        "TYPE_AWNING",
        "TYPE_BLINDS",
        "TYPE_CAMERA",
        "TYPE_CLOSET",
        "TYPE_COFFEE_MAKER",
        "TYPE_CURTAIN",
        "TYPE_DEHUMIDIFIER",
        "TYPE_DISHWASHER",
        "TYPE_DISPLAY",
        "TYPE_DOOR",
        "TYPE_DOORBELL",
        "TYPE_DRAWER",
        "TYPE_DRYER",
        "TYPE_FAN",
        "TYPE_GARAGE",
        "TYPE_GATE",
        "TYPE_GENERIC_ARM_DISARM",
        "TYPE_GENERIC_LOCK_UNLOCK",
        "TYPE_GENERIC_ON_OFF",
        "TYPE_GENERIC_OPEN_CLOSE",
        "TYPE_GENERIC_START_STOP",
        "TYPE_GENERIC_TEMP_SETTING",
        "TYPE_GENERIC_VIEWSTREAM",
        "TYPE_HEATER",
        "TYPE_HOOD",
        "TYPE_HUMIDIFIER",
        "TYPE_KETTLE",
        "TYPE_LIGHT",
        "TYPE_LOCK",
        "TYPE_MICROWAVE",
        "TYPE_MOP",
        "TYPE_MOWER",
        "TYPE_MULTICOOKER",
        "TYPE_OUTLET",
        "TYPE_PERGOLA",
        "TYPE_RADIATOR",
        "TYPE_REFRIGERATOR",
        "TYPE_REMOTE_CONTROL",
        "TYPE_ROUTINE",
        "TYPE_SECURITY_SYSTEM",
        "TYPE_SET_TOP",
        "TYPE_SHOWER",
        "TYPE_SHUTTER",
        "TYPE_SPRINKLER",
        "TYPE_STANDMIXER",
        "TYPE_STYLER",
        "TYPE_SWITCH",
        "TYPE_THERMOSTAT",
        "TYPE_TV",
        "TYPE_UNKNOWN",
        "TYPE_VACUUM",
        "TYPE_VALVE",
        "TYPE_WASHER",
        "TYPE_WATER_HEATER",
        "TYPE_WINDOW"
    )

    val SUB_TITLE = "DeviceType"

    /**
     * 追加可能コントローラーを用意する。
     * */
    override fun createPublisherForAllAvailable(): Flow.Publisher<Control> {

        // コントローラーを長押しした時に表示するActivity
        val intent = Intent(baseContext, MainActivity::class.java)
        val pendingIntent =
            PendingIntent.getActivity(baseContext, 10, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        // まとめてコントローラーを追加するので配列に
        val controlList = mutableListOf<Control>()
        repeat(DEVICE_TYPE_ID_LIST.size) { i ->
            val deviceType = DEVICE_TYPE_ID_LIST[i]
            val deviceName = DEVICE_TYPE_ID_NAME[i]
            // ON/OFFサンプル。
            val toggleControl = Control.StatelessBuilder(deviceName, pendingIntent)
                .setTitle(deviceName) // たいとる
                .setSubtitle(SUB_TITLE) // サブタイトル
                .setDeviceType(deviceType) // あいこんといろの設定。
                .build()
            // 配列に追加
            controlList.add(toggleControl)
        }
        return FlowAdapters.toFlowPublisher(Flowable.fromIterable(controlList))
    }

    override fun performControlAction(p0: String, p1: ControlAction, p2: Consumer<Int>) {
        // コントローラーを長押ししたときに表示するActivity
        val intent = Intent(baseContext, MainActivity::class.java)
        val pendingIntent =
            PendingIntent.getActivity(baseContext, 11, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        // システムに処理中とおしえる
        p2.accept(ControlAction.RESPONSE_OK)
        // コントローラー分岐
        if (p1 is BooleanAction) {
            // ONかどうか
            val isOn = p1.newState
            val message = if (isOn) "ONです" else "OFFです"
            val toggle = ToggleTemplate("toggle_template", ControlButton(isOn, message))
            // DeviceType
            val index = DEVICE_TYPE_ID_NAME.indexOf(p0)
            val deviceType = DEVICE_TYPE_ID_LIST[index]
            // Control更新
            val control = Control.StatefulBuilder(p0, pendingIntent)
                .setTitle(p0) // たいとる
                .setSubtitle(SUB_TITLE) // サブタイトル
                .setDeviceType(deviceType) // 多分アイコンに使われてる？
                .setStatus(Control.STATUS_OK) // 現在の状態
                .setControlTemplate(toggle) // 今回はON/OFFボタン
                .setStatusText(message)
                .build()
            updatePublisher.onNext(control)
        }

    }

    override fun createPublisherFor(p0: MutableList<String>): Flow.Publisher<Control> {
        // コントローラーを長押ししたときに表示するActivity
        val intent = Intent(baseContext, MainActivity::class.java)
        val pendingIntent =
            PendingIntent.getActivity(baseContext, 12, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        // 知識不足でわからん
        updatePublisher = ReplayProcessor.create()
        // コントローラー
        // ON/OFF
        val toggle = ToggleTemplate("toggle_template", ControlButton(false, "OFF"))
        p0.forEach { name ->
            val index = DEVICE_TYPE_ID_NAME.indexOf(name)
            val deviceType = DEVICE_TYPE_ID_LIST[index]
            // ここで作るControlは StatefulBuilder を使う。
            val control = Control.StatefulBuilder(name, pendingIntent)
                .setTitle(name) // たいとる
                .setSubtitle(SUB_TITLE) // サブタイトル
                .setDeviceType(deviceType) // 多分アイコンに使われてる？
                .setStatus(Control.STATUS_OK) // 現在の状態
                .setControlTemplate(toggle) // 今回はON/OFFボタン
                .build()
            updatePublisher.onNext(control)
        }

        return FlowAdapters.toFlowPublisher(updatePublisher)
    }
}
