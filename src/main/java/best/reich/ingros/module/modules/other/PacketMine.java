package best.reich.ingros.module.modules.other;

import best.reich.ingros.events.entity.UpdateEvent;
import best.reich.ingros.events.other.ClickBlockEvent;
import best.reich.ingros.events.other.DamageBlockEvent;
import best.reich.ingros.events.other.ResetBlockRemovingEvent;
import me.xenforu.kelo.module.ModuleCategory;
import me.xenforu.kelo.module.annotation.ModuleManifest;
import me.xenforu.kelo.module.type.ToggleableModule;
import me.xenforu.kelo.setting.annotation.Mode;
import me.xenforu.kelo.setting.annotation.Setting;
import net.b0at.api.event.Subscribe;
import net.b0at.api.event.types.EventType;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;


@ModuleManifest(label = "PacketMine", category = ModuleCategory.OTHER, color = 0xfffED3)
public class PacketMine extends ToggleableModule {

    @Mode({"Smart", "Normal"})
    public String mode = "Smart";

    @Setting("Reset")
    public boolean reset = true;

    @Subscribe
    public void onUpdate(UpdateEvent event) {
        if (mc.world == null || mc.player == null) return;
        if (event.getType() == EventType.PRE) {
            mc.playerController.blockHitDelay = 0;
            if (this.reset && mc.gameSettings.keyBindUseItem.isKeyDown()) {
                mc.playerController.isHittingBlock = false;
            }
        }
    }

    @Subscribe
    public void damageBlock(DamageBlockEvent event) {
        if (mc.world == null || mc.player == null) return;
        if (mc.world.getBlockState(event.getPos()).getBlock() == Blocks.PORTAL) return;
        if (canBreak(event.getPos())) {
            if (this.reset) {
                mc.playerController.isHittingBlock = false; }
                    if (mc.player != null);
                    mc.player.swingArm(EnumHand.MAIN_HAND);
                    final boolean canSwing = mc.player.getHeldItemMainhand().getItem() == Items.DIAMOND_PICKAXE;
                    if (mode.equals("Smart") && !canSwing) return;
                    mc.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, event.getPos(), event.getFacing()));
                    mc.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, event.getPos(), event.getFacing()));
                    event.setCancelled(true);
            }
        }

    private boolean canBreak(BlockPos pos) {
        final IBlockState blockState = mc.world.getBlockState(pos);
        final Block block = blockState.getBlock();

        return block.getBlockHardness(blockState, mc.world, pos) != -1;
    }

}
