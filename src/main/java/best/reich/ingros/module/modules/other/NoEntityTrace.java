package best.reich.ingros.module.modules.other;

import best.reich.ingros.events.other.TraceEntityEvent;
import me.xenforu.kelo.module.ModuleCategory;
import me.xenforu.kelo.module.annotation.ModuleManifest;
import me.xenforu.kelo.module.type.ToggleableModule;
import me.xenforu.kelo.setting.annotation.Mode;
import me.xenforu.kelo.setting.annotation.Setting;
import net.b0at.api.event.Subscribe;
import net.minecraft.init.Items;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;

@ModuleManifest(label = "NoEntityTrace", category = ModuleCategory.OTHER, color = 0xff3F63fe,hidden = true)
public class NoEntityTrace extends ToggleableModule {

    @Mode({"Pickaxe", "Everything"})
    @Setting("Mode")
    public String mode = "Pickaxe";

    @Subscribe
    public void onEntityTrace(TraceEntityEvent event) {
        if (mc.player != null || mc.world != null) {
            if (mode.equals("Everything")) {
                event.setCancelled(true);
            }
            if (mode.equals("Pickaxe")) {
                if (mc.player.getHeldItemMainhand().getItem() == Items.DIAMOND_PICKAXE) {
                    event.setCancelled(true);
                }
            }
        }
    }
}
