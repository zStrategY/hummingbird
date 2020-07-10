package best.reich.ingros.module.modules.player;

import best.reich.ingros.events.network.PacketEvent;
import me.xenforu.kelo.module.ModuleCategory;
import me.xenforu.kelo.module.annotation.ModuleManifest;
import me.xenforu.kelo.module.type.ToggleableModule;
import net.b0at.api.event.Subscribe;
import net.minecraft.network.play.client.CPacketCloseWindow;

@ModuleManifest(label = "XCarry", category = ModuleCategory.PLAYER)
public class XCarry extends ToggleableModule {
    @Subscribe
    public void onWindow(PacketEvent event) {
        if (event.getPacket() instanceof CPacketCloseWindow) {
            if (((CPacketCloseWindow) event.getPacket()).windowId == mc.player.inventoryContainer.windowId) {
                event.setCancelled(true);
            }
        }
    }
}
