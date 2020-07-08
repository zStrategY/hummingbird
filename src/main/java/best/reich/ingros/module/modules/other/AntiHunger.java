package best.reich.ingros.module.modules.other;

import best.reich.ingros.events.network.PacketEvent;
import me.xenforu.kelo.module.ModuleCategory;
import me.xenforu.kelo.module.annotation.ModuleManifest;
import me.xenforu.kelo.module.type.ToggleableModule;
import net.b0at.api.event.Subscribe;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayer;

/**
 * seppuku
 * https://github.com/seppukudevelopment/seppuku/blob/005e2da/src/main/java/me/rigamortis/seppuku/impl/module/player/NoHungerModule.java
 */


@ModuleManifest(label = "AntiHunger", category = ModuleCategory.OTHER)
public class AntiHunger extends ToggleableModule {

    @Subscribe
    public void onPacket(PacketEvent event) {
        if (event.getPacket() instanceof CPacketPlayer) {
            if (mc.player.fallDistance > 0 || mc.playerController.isHittingBlock) {
                mc.player.onGround = true;
            } else {
                mc.player.onGround = false;
            }
        }
        if (event.getPacket() instanceof CPacketEntityAction) {
            final CPacketEntityAction packet = (CPacketEntityAction) event.getPacket();
            if (packet.getAction() == CPacketEntityAction.Action.START_SPRINTING || packet.getAction() == CPacketEntityAction.Action.STOP_SPRINTING) {
                event.setCancelled(true);
            }
        }
    }
}
