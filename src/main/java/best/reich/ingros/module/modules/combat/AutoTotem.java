package best.reich.ingros.module.modules.combat;

import best.reich.ingros.events.entity.UpdateEvent;
import me.xenforu.kelo.module.ModuleCategory;
import me.xenforu.kelo.module.annotation.ModuleManifest;
import me.xenforu.kelo.module.type.ToggleableModule;
import me.xenforu.kelo.setting.annotation.Clamp;
import me.xenforu.kelo.setting.annotation.Setting;
import net.b0at.api.event.Subscribe;
import net.b0at.api.event.types.EventType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
/**
 * Author Seth
 * 4/30/2019 @ 3:37 AM.
 */
@ModuleManifest(label = "AutoTotem", category = ModuleCategory.COMBAT, color = 0xffffff10)
public class AutoTotem extends ToggleableModule {
    @Subscribe
    public void onUpdate(UpdateEvent event) {
        if (event.getType() == EventType.PRE) {
            final Minecraft mc = Minecraft.getMinecraft();
            if(mc.currentScreen == null || mc.currentScreen instanceof GuiInventory) {
                    final ItemStack offHand = mc.player.getHeldItemOffhand();
                    if (offHand.getItem() == Items.TOTEM_OF_UNDYING) {
                        return;
                    }
                    final int slot = this.getItemSlot(Items.TOTEM_OF_UNDYING);
                    if(slot != -1) {
                        mc.playerController.windowClick(mc.player.inventoryContainer.windowId, slot, 0, ClickType.PICKUP, mc.player);
                        mc.playerController.windowClick(mc.player.inventoryContainer.windowId, 45, 0, ClickType.PICKUP, mc.player);
                        mc.playerController.windowClick(mc.player.inventoryContainer.windowId, slot, 0, ClickType.PICKUP, mc.player);
                        mc.playerController.updateController();
                    }
                }
            }
        }


    private int getItemSlot(Item input) {
        for(int i = 0; i < 36; i++) {
            final Item item = Minecraft.getMinecraft().player.inventory.getStackInSlot(i).getItem();
            if(item == input) {
                if (i < 9) {
                    i += 36;
                }
                return i;
            }
        }
        return -1;
    }

    private int getItemCount(Item input) {
        int items = 0;

        for(int i = 0; i < 45; i++) {
            final ItemStack stack = Minecraft.getMinecraft().player.inventory.getStackInSlot(i);
            if(stack.getItem() == input) {
                items += stack.getCount();
            }
        }

        return items;
    }

}