package net.ccbluex.liquidbounce.features.module.modules.combat.velocitys.grim

import net.ccbluex.liquidbounce.LiquidBounce
import net.ccbluex.liquidbounce.event.PacketEvent
import net.ccbluex.liquidbounce.event.EntityDamageEvent
import net.ccbluex.liquidbounce.features.module.modules.combat.velocitys.VelocityMode
import net.ccbluex.liquidbounce.utils.PacketUtils
import net.ccbluex.liquidbounce.features.value.ListValue
import net.minecraft.network.play.server.S08PacketPlayerPosLook
import net.minecraft.network.play.server.S12PacketEntityVelocity
import net.minecraft.network.play.server.S27PacketExplosion
import net.minecraft.network.play.client.C07PacketPlayerDigging
import net.minecraft.network.play.client.C03PacketPlayer
import net.minecraft.network.play.client.C03PacketPlayer.C06PacketPlayerPosLook
import net.minecraft.util.EnumFacing
import net.minecraft.util.BlockPos

import java.util.*
import kotlin.concurrent.schedule

class GrimC07Velocity : VelocityMode("GrimC07") {
    private var packetPayloadValue = ListValue("PacketPayload", arrayOf("C03", "C06"), "C03")
    private var canCancel = false
    private var canSpoof = false

    override fun onEnable() {
        canCancel = false
        canSpoof = false
    }

     fun onUpdate() {
        if (canSpoof) {
            val pos = BlockPos(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ)

            when (packetPayloadValue.get().lowercase()) {
                "c03" -> PacketUtils.sendPacketNoEvent(C03PacketPlayer(mc.thePlayer.onGround))
                "c06" -> PacketUtils.sendPacketNoEvent(C06PacketPlayerPosLook(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch, mc.thePlayer.onGround))
            }
            PacketUtils.sendPacketNoEvent(C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, pos, mc.thePlayer.horizontalFacing.opposite))
            canSpoof = false
        }
    }

     fun onEntityDamage(event: EntityDamageEvent) {
        if (event.damagedEntity == mc.thePlayer)
            canCancel = true
    }

    override fun onPacket(event: PacketEvent) {
        val packet = event.packet

        if (!canCancel)
            return

        if (packet is S12PacketEntityVelocity && packet.entityID == mc.thePlayer.entityId) {
            canSpoof = true
            canCancel = false
            event.cancelEvent()
        }

        if (packet is S27PacketExplosion) {
            canSpoof = true
            canCancel = false
            event.cancelEvent()
        }
    }
}