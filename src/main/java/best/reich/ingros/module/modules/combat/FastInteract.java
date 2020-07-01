package best.reich.ingros.module.modules.combat;

import best.reich.ingros.events.network.PacketEvent;
import me.xenforu.kelo.module.ModuleCategory;
import me.xenforu.kelo.module.annotation.ModuleManifest;
import me.xenforu.kelo.module.type.ToggleableModule;
import net.b0at.api.event.Subscribe;

@ModuleManifest(label = "FastInteract", category = ModuleCategory.COMBAT, color = 0x66ff66)
public class FastInteract extends ToggleableModule {

    @Subscribe
    public void onPacket(PacketEvent event) {
        mc.rightClickDelayTimer = 0;
    }


}
