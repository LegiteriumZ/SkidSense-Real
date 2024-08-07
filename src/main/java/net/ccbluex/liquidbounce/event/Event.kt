/*
 * FDPClient Hacked Client
 * A free open source mixin-based injection hacked client for Minecraft using Minecraft Forge by LiquidBounce.
 * https://github.com/SkidderMC/FDPClient/
 */
package net.ccbluex.liquidbounce.event

open class Event

class KnockbackEvent(var reduceY: Boolean) : CancellableEvent()

class MoveInputEvent(var forward: Float, var strafe: Float, var jump: Boolean, var sneak: Boolean, var sneakMultiplier: Double) : Event()

open class CancellableEvent : Event() {




    /**
     * Let you know if the event is cancelled
     *
     * @return state of cancel
     */
    var isCancelled: Boolean = false

    /**
     * Allows you to cancel a event
     */
    fun cancelEvent() {
        isCancelled = true
    }
}

enum class EventState(val stateName: String) {
    PRE("PRE"), POST("POST")
}
