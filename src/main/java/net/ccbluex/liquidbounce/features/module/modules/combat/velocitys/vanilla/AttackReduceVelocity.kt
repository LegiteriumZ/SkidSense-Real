package net.ccbluex.liquidbounce.features.module.modules.combat.velocitys.vanilla

import net.ccbluex.liquidbounce.event.PacketEvent
import net.ccbluex.liquidbounce.features.module.modules.combat.velocitys.VelocityMode
import net.ccbluex.liquidbounce.features.value.FloatValue
import net.minecraft.network.play.client.C02PacketUseEntity

class AttackReduceVelocity : VelocityMode("AttackReduce") {
    val reduceAmount = FloatValue("ReduceAmount", 0.8f, 0.3f, 1f)

    override fun onPacket(event: PacketEvent) {
        val packet = event.packet
        if (packet is C02PacketUseEntity) {
            if (packet.getAction() == C02PacketUseEntity.Action.ATTACK) {
                if (mc.thePlayer.hurtTime < 3)
                    return
                mc.thePlayer.motionX *= reduceAmount.get().toDouble()
                mc.thePlayer.motionZ *= reduceAmount.get().toDouble()
            }
        }
    }
}