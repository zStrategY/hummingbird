package best.reich.ingros.module.modules.render;

import me.xenforu.kelo.module.ModuleCategory;
import me.xenforu.kelo.module.annotation.ModuleManifest;
import me.xenforu.kelo.module.type.ToggleableModule;
import net.minecraft.item.ItemSword;

@ModuleManifest(label = "ViewModelChanger", category = ModuleCategory.RENDER, hidden = true, color = 0xffffff33)
public class ViewModelChanger extends ToggleableModule {

    public void onUpdate(){
        if (mc.player.getHeldItemMainhand().getItem() instanceof ItemSword && mc.entityRenderer.itemRenderer.prevEquippedProgressMainHand >= 0.9) {
            mc.entityRenderer.itemRenderer.equippedProgressMainHand = 1;
            mc.entityRenderer.itemRenderer.itemStackMainHand = mc.player.getHeldItemMainhand();
        }
    }
}
