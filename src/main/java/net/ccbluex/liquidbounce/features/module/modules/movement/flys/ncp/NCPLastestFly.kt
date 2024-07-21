package net.ccbluex.liquidbounce.features.module.modules.movement.flys.ncp

import net.ccbluex.liquidbounce.event.EventTarget
import net.ccbluex.liquidbounce.event.MotionEvent
import net.ccbluex.liquidbounce.features.module.modules.movement.flys.FlyMode
import net.ccbluex.liquidbounce.utils.MovementUtils

class NCPLastestFly : FlyMode("LatestNCP") {
    protected var superfly = false
    private var moveSpeed = 0.0 // Or private var moveSpeed: Float = 0f
    @EventTarget
    override fun onMotion(event: MotionEvent) {
        val bb = mc.thePlayer.entityBoundingBox.offset(0.0, 1.0, 0.0)
        if (superfly) {
            mc.thePlayer.motionY += 0.025
            MovementUtils.strafe((moveSpeed * 0.935f).toFloat()) // Or MovementUtils.strafe(moveSpeed) if moveSpeed is Float
        }
        if (mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, bb).isEmpty() && !superfly) {
            superfly = true
            mc.thePlayer.jump()
            moveSpeed = 9.0
        }
    }

    override fun onEnable() {
        superfly = true
    }

    override fun onDisable() {
        superfly = false
        moveSpeed = 0.0
    }
}
