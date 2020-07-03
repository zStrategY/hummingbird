package best.reich.ingros.module.modules.combat;


import best.reich.ingros.events.network.PacketEvent;
import best.reich.ingros.mixin.accessors.IMinecraft;
import me.xenforu.kelo.module.ModuleCategory;
import me.xenforu.kelo.module.annotation.ModuleManifest;
import me.xenforu.kelo.module.type.ToggleableModule;
import net.b0at.api.event.Subscribe;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBlock;

/**
*h0lloww and auto
 */

@ModuleManifest(label = "SmartFastCrystal", category = ModuleCategory.COMBAT, color = 0x717171)
public class SmartFastCrystal extends ToggleableModule {
    @Subscribe
    public void onUpdate(PacketEvent event) {
        if (mc.player != null && mc.player.getHeldItemOffhand().getItem() == Items.END_CRYSTAL && mc.player.getHeldItemMainhand().getItem() == Items.DIAMOND_PICKAXE) {
            ((IMinecraft)mc).setRightClickDelayTimer(0);
        }
    }

}
