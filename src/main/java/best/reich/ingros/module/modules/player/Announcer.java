/*package best.reich.ingros.module.modules.player;

import best.reich.ingros.events.entity.JumpEvent;
import best.reich.ingros.events.entity.UpdateEvent;
import me.xenforu.kelo.module.ModuleCategory;
import me.xenforu.kelo.module.annotation.ModuleManifest;
import me.xenforu.kelo.module.type.ToggleableModule;
import me.xenforu.kelo.setting.annotation.Setting;
import net.b0at.api.event.Subscribe;


@ModuleManifest(label = "Announcer", category = ModuleCategory.PLAYER)
public class Announcer extends ToggleableModule {

    @Setting("Jump")
    public boolean jump = true;

    @Setting("Placing")
    public boolean place = true;

    @Subscribe
    public void onJump(JumpEvent event) {
        if (jump) {
            mc.player.sendChatMessage("I just jumped thanks to Hummingbird!");
        }
    }
} */
