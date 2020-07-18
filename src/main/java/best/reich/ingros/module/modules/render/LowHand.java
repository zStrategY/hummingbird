package best.reich.ingros.module.modules.render;

import best.reich.ingros.events.entity.UpdateEvent;
import me.xenforu.kelo.module.ModuleCategory;
import me.xenforu.kelo.module.annotation.ModuleManifest;
import me.xenforu.kelo.module.type.ToggleableModule;
import me.xenforu.kelo.setting.annotation.Clamp;
import me.xenforu.kelo.setting.annotation.Setting;
import net.b0at.api.event.Subscribe;
import scala.collection.script.Update;

@ModuleManifest(label = "LowHand", category = ModuleCategory.RENDER)
public class LowHand extends ToggleableModule {

    @Clamp(maximum = "3")
    @Setting("MainhandHeight")
    public float mainHeight = 1;

    @Setting("Mainhand")
    public boolean mainHand;

    @Setting("Offhand")
    public boolean offhand = true;

    @Clamp(maximum = "3")
    @Setting("OffhandHeight")
    public float offHeight = 1;


    @Subscribe
    public void f(UpdateEvent event) {
        if (mainHand) {
            mc.entityRenderer.itemRenderer.equippedProgressMainHand = mainHeight;
        }
        if (offhand) {
            mc.entityRenderer.itemRenderer.equippedProgressOffHand = offHeight;
        }
    }
}
