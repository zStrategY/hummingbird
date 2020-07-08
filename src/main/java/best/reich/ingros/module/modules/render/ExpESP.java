package best.reich.ingros.module.modules.render;

import best.reich.ingros.util.render.RenderUtil;
import me.xenforu.kelo.module.ModuleCategory;
import me.xenforu.kelo.module.annotation.ModuleManifest;
import me.xenforu.kelo.module.type.ToggleableModule;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityExpBottle;
import net.minecraft.util.math.AxisAlignedBB;

@ModuleManifest(label = "ExpESP", category = ModuleCategory.RENDER)
public class ExpESP extends ToggleableModule { /*
    public void onUpdate(){
        if (mc.player != null || mc.world != null) {
            for (Entity entity : mc.world.loadedEntityList) {
                if (entity instanceof EntityExpBottle) {
                    EntityExpBottle expBottle = (EntityExpBottle) entity;
                }
            }
        }
    } */
}
