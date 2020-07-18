package best.reich.ingros.module.modules.player;


import best.reich.ingros.events.other.TickEvent;
import me.xenforu.kelo.module.ModuleCategory;
import me.xenforu.kelo.module.annotation.ModuleManifest;
import me.xenforu.kelo.module.type.ToggleableModule;
import me.xenforu.kelo.setting.annotation.Clamp;
import me.xenforu.kelo.setting.annotation.Mode;
import me.xenforu.kelo.setting.annotation.Setting;
import net.b0at.api.event.Subscribe;
import net.minecraft.block.BlockAir;
import net.minecraft.util.math.BlockPos;

import static best.reich.ingros.util.game.BlockUtil.getBlock;

@ModuleManifest(label = "AntiVoid", category = ModuleCategory.PLAYER)
public class AntiVoid extends ToggleableModule {

    @Clamp(maximum = "0.2")
    @Setting("Height")
    public double height = 0.9D;


    @Subscribe
    public void onTick(TickEvent event) {
        if (mc.player != null) {
            boolean hasGround = false;
            for (int i = 1; i < this.mc.player.posY; i++) {
                BlockPos pos = new BlockPos(this.mc.player.posX, i, this.mc.player.posZ);
                if (!(getBlock(pos) instanceof BlockAir)) {
                    hasGround = true;
                }
            }
            if ((!hasGround) && (this.mc.player.posY <= 0.0D)) {
                this.mc.player.motionY = height;
            }
        }
    }
}
