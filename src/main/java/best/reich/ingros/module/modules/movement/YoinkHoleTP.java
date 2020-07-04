package best.reich.ingros.module.modules.movement;


import best.reich.ingros.events.entity.UpdateEvent;
import me.xenforu.kelo.module.ModuleCategory;
import me.xenforu.kelo.module.annotation.ModuleManifest;
import me.xenforu.kelo.module.type.ToggleableModule;
import net.b0at.api.event.Subscribe;


/**
 * YOINK
 */

@ModuleManifest(label = "YoinkHoleTP", category = ModuleCategory.MOVEMENT, color = 0xffffff33)
public class YoinkHoleTP extends ToggleableModule {
    @Subscribe
    public void onUpdate(UpdateEvent event) {
        if (mc.player.isInWater() || mc.player.isInLava()) return;
        if (mc.player.onGround) {
            --mc.player.motionY;
        }
    }

}
