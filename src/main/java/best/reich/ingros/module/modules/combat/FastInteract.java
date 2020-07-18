package best.reich.ingros.module.modules.combat;

import best.reich.ingros.events.entity.UpdateEvent;
import best.reich.ingros.mixin.accessors.IMinecraft;
import best.reich.ingros.mixin.accessors.IPlayerControllerMP;
import me.xenforu.kelo.module.ModuleCategory;
import me.xenforu.kelo.module.annotation.ModuleManifest;
import me.xenforu.kelo.module.type.ToggleableModule;
import me.xenforu.kelo.setting.annotation.Setting;
import net.b0at.api.event.Subscribe;
import net.b0at.api.event.types.EventType;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;

@ModuleManifest(label = "FastInteract", category = ModuleCategory.COMBAT, color = 0x66ff66)
public class FastInteract extends ToggleableModule {

    @Setting("All")
    public boolean all = false;
    @Setting("Xp")
    public boolean xp = true;
    @Setting("Crystal")
    public boolean crystal = true;
    @Setting("SpeedMine")
    public boolean speedMine = true;
    @Setting("Bow")
    public boolean bow = true;

    @Subscribe
    public void onUpdate(UpdateEvent event) {
        if (event.getType() == EventType.PRE) {
            if (all) {
                ((IMinecraft) mc).setRightClickDelayTimer(0);
            }
            if (xp && mc.player.getHeldItemMainhand().getItem() == Items.EXPERIENCE_BOTTLE) {
                ((IMinecraft) mc).setRightClickDelayTimer(0);
            }
            if (crystal && mc.player.getHeldItemMainhand().getItem() == Items.DIAMOND_PICKAXE && mc.player.getHeldItemOffhand().getItem() == Items.END_CRYSTAL) {
                ((IMinecraft) mc).setRightClickDelayTimer(0);
            }
            if (speedMine) {
                if (event.getType() == EventType.PRE) {
                    ((IPlayerControllerMP) mc.playerController).setBlockHitDelay(0);
                    boolean item = mc.player.getHeldItem(EnumHand.MAIN_HAND) == ItemStack.EMPTY;
                    mc.player.addPotionEffect(new PotionEffect(MobEffects.HASTE, 100, item ? 1 : 0));
                }
            }
            if (bow) {
                if (mc.player.inventory.getCurrentItem().getItem() instanceof ItemBow) {
                    if (mc.player.isHandActive() && mc.player.getItemInUseMaxCount() >= 3) {
                        mc.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, mc.player.getHorizontalFacing()));
                        mc.player.connection.sendPacket(new CPacketPlayerTryUseItem(mc.player.getActiveHand()));
                        mc.player.stopActiveHand();
                    }
                }
            }
        }
    }
}
