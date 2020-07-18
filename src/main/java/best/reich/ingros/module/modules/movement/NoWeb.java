package best.reich.ingros.module.modules.movement;

import best.reich.ingros.events.entity.UpdateEvent;
import me.xenforu.kelo.module.ModuleCategory;
import me.xenforu.kelo.module.annotation.ModuleManifest;
import me.xenforu.kelo.module.type.ToggleableModule;
import me.xenforu.kelo.setting.annotation.Mode;
import me.xenforu.kelo.setting.annotation.Setting;
import net.b0at.api.event.Subscribe;

@ModuleManifest(label = "NoWeb", category = ModuleCategory.MOVEMENT)
public class NoWeb extends ToggleableModule {


    /**
     * https://github.com/CCBlueX/LiquidBounce/blob/master/1.8.9-Forge/src/main/java/net/ccbluex/liquidbounce/features/module/modules/movement/NoWeb.kt
     */

    @Mode({"AAC"})
    @Setting("Mode")
    public String mode = "AAC";

    @Subscribe
    public void onUpdate(UpdateEvent event) {
        if (mc.player == null || mc.world == null) return;
        if (mc.player.isInWeb) {
            if (mode.equalsIgnoreCase("aac")) {
                mc.player.jumpMovementFactor = 0.59f;
                if (mc.gameSettings.keyBindSneak.isKeyDown()) {
                    mc.player.motionY = .0;
                }
            }
        }
    }
}
