package best.reich.ingros.module.modules.other;

import best.reich.ingros.events.entity.UpdateEvent;
import me.xenforu.kelo.module.ModuleCategory;
import me.xenforu.kelo.module.annotation.ModuleManifest;
import me.xenforu.kelo.module.type.ToggleableModule;
import me.xenforu.kelo.setting.annotation.Clamp;
import me.xenforu.kelo.setting.annotation.Setting;
import me.xenforu.kelo.util.math.TimerUtil;
import net.b0at.api.event.Subscribe;
import net.b0at.api.event.types.EventType;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;

import java.util.Comparator;

@ModuleManifest(label = "AutoCrystalArmor", category = ModuleCategory.OTHER, color = 0xffEfaAEF)
public class AutoCrystalArmor extends ToggleableModule {
    @Clamp(minimum = "5", maximum = "20")
    @Setting("CrystalRange")
    public int crystalRange = 10;
    private static final Item[] HELMETS = {Items.DIAMOND_HELMET};
    private static final Item[] CHESTPLATES = {Items.DIAMOND_CHESTPLATE};
    private static final Item[] LEGGINGS = {Items.DIAMOND_LEGGINGS};
    private static final Item[] BOOTS = {Items.DIAMOND_BOOTS};
    private final TimerUtil timer = new TimerUtil();

    @Subscribe
    public void onUpdate(UpdateEvent event) {
        if (mc.player == null || mc.world == null) return;
        final EntityEnderCrystal crystal = mc.world.loadedEntityList.stream()
                .filter(e -> e instanceof EntityEnderCrystal && mc.player.getDistanceToEntity(e) <= crystalRange)
                .map(entity -> (EntityEnderCrystal) entity)
                .min(Comparator.comparing(c -> mc.player.getDistanceToEntity(c)))
                .orElse(null);
        if (crystal != null && event.getType() == EventType.PRE) {
            int selectedSlotId = -1;
            if (timer.reach(1)) {
                if (mc.player.inventory.armorItemInSlot(2).getItem() == Items.AIR) {
                    for (Item item : CHESTPLATES) {
                        int slotId = getSlotID(item);
                        if (slotId != -1) {
                            selectedSlotId = slotId;
                        }
                    }
                }

                if (mc.player.inventory.armorItemInSlot(1).getItem() == Items.AIR) {
                    for (Item item : LEGGINGS) {
                        int slotId = getSlotID(item);
                        if (slotId != -1) {
                            selectedSlotId = slotId;
                        }
                    }
                }

                if (mc.player.inventory.armorItemInSlot(0).getItem() == Items.AIR) {
                    for (Item item : BOOTS) {
                        int slotId = getSlotID(item);
                        if (slotId != -1) {
                            selectedSlotId = slotId;
                        }
                    }
                }

                if (mc.player.inventory.armorItemInSlot(3).getItem() == Items.AIR) {
                    for (Item item : HELMETS) {
                        int slotId = getSlotID(item);
                        if (slotId != -1) {
                            selectedSlotId = slotId;
                        }
                    }
                }

                if (selectedSlotId != -1) {
                    if (selectedSlotId < 9)
                        selectedSlotId += 36;
                    mc.playerController.windowClick(0, selectedSlotId, 0, ClickType.QUICK_MOVE, mc.player);
                    timer.reset();
                }
            }
        }

    }

    public static int getSlotID(Item item) {
        for (int index = 0; index <= 36; index++) {
            final ItemStack stack = mc.player.inventory.getStackInSlot(index);
            if (stack.getItem() == Items.AIR) continue;
            if (stack.getItem() == item) {
                return index;
            }
        }
        return -1;
    }
}