/*package best.reich.ingros.module.modules.render;

import best.reich.ingros.events.entity.UpdateEvent;
import me.xenforu.kelo.module.ModuleCategory;
import me.xenforu.kelo.module.annotation.ModuleManifest;
import me.xenforu.kelo.module.type.ToggleableModule;
import me.xenforu.kelo.setting.annotation.Clamp;
import me.xenforu.kelo.setting.annotation.Setting;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.b0at.api.event.Subscribe;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.awt.*;

@ModuleManifest(label = "Fogger", category = ModuleCategory.RENDER, hidden = true)
public class Fogger extends ToggleableModule {

    @Clamp(maximum = "10")
    @Setting("Density")
    public int density = 5;

    @Clamp(maximum = "255")
    @Setting("Red")
    public float red = 255;

    @Clamp(maximum = "255")
    @Setting("Green")
    public float green = 0;

    @Clamp(maximum = "255")
    @Setting("Blue")
    public float blue = 230;

    @Subscribe
    public void onFogDensity(EntityViewRenderEvent.FogDensity event) {
        event.setDensity(density);
    }


    @Subscribe
    public void onFogColor(EntityViewRenderEvent.FogColors event) {
        if (mc.player != null || mc.world != null) {
            event.setRed(255);
            event.setGreen(12);
            event.setBlue(190);
        }
    }
} */