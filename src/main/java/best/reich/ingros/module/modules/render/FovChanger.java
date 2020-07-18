package best.reich.ingros.module.modules.render;

import best.reich.ingros.events.entity.UpdateEvent;
import best.reich.ingros.events.render.DisplayGuiEvent;
import me.xenforu.kelo.module.ModuleCategory;
import me.xenforu.kelo.module.annotation.ModuleManifest;
import me.xenforu.kelo.module.type.ToggleableModule;
import me.xenforu.kelo.setting.annotation.Clamp;
import me.xenforu.kelo.setting.annotation.Setting;
import net.b0at.api.event.Subscribe;

/**
 * yoink helped with :)) very cool
 */

@ModuleManifest(label = "FovChanger", category = ModuleCategory.RENDER)
public class FovChanger extends ToggleableModule {
    @Clamp(minimum = "100", maximum = "250")
    @Setting("Fov")
    public int fov = 120;

    private float oldFov;

    @Subscribe
    public void onUpdate(DisplayGuiEvent event) {
        mc.gameSettings.fovSetting = fov;
    }

    @Override
    public void onEnable() {
        super.onEnable();
        oldFov = mc.gameSettings.fovSetting;
    }

    @Override
    public void onDisable() {
        super.onDisable();
        mc.gameSettings.fovSetting = oldFov;

    }
}
