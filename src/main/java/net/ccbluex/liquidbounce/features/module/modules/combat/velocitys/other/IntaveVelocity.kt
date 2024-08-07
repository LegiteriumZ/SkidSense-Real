package net.ccbluex.liquidbounce.features.module.modules.combat.velocitys.intave

import net.minecraft.network.play.server.S12PacketEntityVelocity
import net.ccbluex.liquidbounce.event.AttackEvent
import net.ccbluex.liquidbounce.event.KnockbackEvent
import net.ccbluex.liquidbounce.event.MoveInputEvent
import net.ccbluex.liquidbounce.event.EntityDamageEvent
import net.ccbluex.liquidbounce.features.module.modules.combat.velocitys.VelocityMode
import net.ccbluex.liquidbounce.utils.RaycastUtils
import net.ccbluex.liquidbounce.features.value.FloatValue
import net.ccbluex.liquidbounce.features.value.BoolValue
import net.minecraft.util.MovingObjectPosition


class IntaveVelocity : VelocityMode("Intave") {
    private val targetRange = FloatValue("TargetRange", 3f, 0f, 5f)
    private val hurtTime = BoolValue("KeepSprintOnlyHurtTime", false)
    private var blockVelocity = false
    private var isRaytracedToEntity = false

    override fun onEnable() {
        isRaytracedToEntity = false
    }

    override fun onDisable() {
        mc.thePlayer.movementInput.jump = false
    }

    fun onUpdate() {
        blockVelocity = true
        isRaytracedToEntity = false



        if (isRaytracedToEntity && mc.thePlayer.hurtTime == 9 && !mc.thePlayer.isBurning)
            mc.thePlayer.movementInput.jump = true
    }

    fun onEntityDamage(event: EntityDamageEvent) {
        if (isRaytracedToEntity && mc.thePlayer.hurtTime == 9 && !mc.thePlayer.isBurning)
            mc.thePlayer.movementInput.jump = true
    }

    fun onAttack(event: AttackEvent) {
        if (mc.thePlayer.hurtTime > 0 && blockVelocity) {
            mc.thePlayer.isSprinting = false
            mc.thePlayer.motionX *= 0.6
            mc.thePlayer.motionZ *= 0.6
            blockVelocity = false
        }
    }

     fun onMoveInput(event: MoveInputEvent) {
        if (mc.thePlayer.hurtTime > 0 && isRaytracedToEntity) {
            event.forward = 1.0F
            event.strafe = 0.0F
        }
    }

     fun onKnockback(event: KnockbackEvent) {
        if (mc.thePlayer.hurtTime <= 0)
            event.isCancelled = true

        if (hurtTime.get() && mc.thePlayer.hurtTime == 0)
            event.isCancelled = false

        event.reduceY = true
    }
}