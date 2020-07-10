package best.reich.ingros.module.modules.render;

import best.reich.ingros.events.entity.UpdateEvent;
import me.xenforu.kelo.module.ModuleCategory;
import me.xenforu.kelo.module.annotation.ModuleManifest;
import me.xenforu.kelo.module.type.ToggleableModule;
import me.xenforu.kelo.setting.annotation.Clamp;
import me.xenforu.kelo.setting.annotation.Setting;
import net.b0at.api.event.Subscribe;

@ModuleManifest(label = "LowOffhand", category = ModuleCategory.RENDER)
public class LowOffhand extends ToggleableModule {
    @Clamp(maximum = "1")
    @Setting("Height")
    public float height = 0.75f;

    @Subscribe
    public void f(UpdateEvent event) {
        mc.entityRenderer.itemRenderer.equippedProgressOffHand = height;
    }

}
