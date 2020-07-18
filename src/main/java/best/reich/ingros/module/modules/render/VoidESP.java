package best.reich.ingros.module.modules.render;

import best.reich.ingros.events.entity.UpdateEvent;
import best.reich.ingros.events.render.Render3DEvent;
import best.reich.ingros.util.render.RenderUtil;
import me.xenforu.kelo.module.ModuleCategory;
import me.xenforu.kelo.module.annotation.ModuleManifest;
import me.xenforu.kelo.module.type.ToggleableModule;
import me.xenforu.kelo.setting.annotation.Clamp;
import me.xenforu.kelo.setting.annotation.Mode;
import me.xenforu.kelo.setting.annotation.Setting;
import net.b0at.api.event.Subscribe;
import net.minecraft.block.BlockAir;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@ModuleManifest(label = "VoidESP", category = ModuleCategory.RENDER)
public class VoidESP extends ToggleableModule {

    @Setting("Mode")
    @Mode({"Both", "Fill", "Outline"})
    public String mode = "Both";
    @Setting("Bottom")
    public boolean bottom = true;

    @Setting("Color")
    public Color color = new Color(255, 0, 0);

    @Setting("Radius")
    @Clamp(maximum = "32")
    public int radius = 8;

    public final List<Hole> holes = new ArrayList<>();

    @Subscribe
    public void onUpdate(UpdateEvent event) {
        if (mc.player == null) {
            return;
        }
        this.holes.clear();
        final Vec3i playerPos = new Vec3i(mc.player.posX, mc.player.posY, mc.player.posZ);
        for (int x = playerPos.getX() - this.radius; x < playerPos.getX() + this.radius; ++x) {
            for (int z = playerPos.getZ() - this.radius; z < playerPos.getZ() + this.radius; ++z) {
                for (int y = playerPos.getY(); y > playerPos.getY() - 4; --y) {
                    final BlockPos blockPos = new BlockPos(x, y, z);
                    final IBlockState blockState = mc.world.getBlockState(blockPos);
                    if (this.isBlockValid(blockState, blockPos)) {
                        this.holes.add(new Hole(blockPos.getX(), blockPos.getY(), blockPos.getZ()));
                    }
                }
            }
        }
    }

    @Subscribe
    public void onRender(Render3DEvent event) {
        for (final Hole hole : this.holes) {
            final AxisAlignedBB bb = new AxisAlignedBB(hole.getX() - mc.getRenderManager().viewerPosX, hole.getY() - mc.getRenderManager().viewerPosY, hole.getZ() - mc.getRenderManager().viewerPosZ, hole.getX() + 1 - mc.getRenderManager().viewerPosX, hole.getY() + (bottom ? 0.2 : 1) - mc.getRenderManager().viewerPosY, hole.getZ() + 1 - mc.getRenderManager().viewerPosZ); {
                if (mode.equalsIgnoreCase("fill") || mode.equalsIgnoreCase("both"))
                    RenderUtil.drawESP(bb, color.getRed(), color.getGreen(), color.getBlue(), 40F);
                if (mode.equalsIgnoreCase("outline") || mode.equalsIgnoreCase("both"))
                    RenderUtil.drawESPOutline(bb, color.getRed(), color.getGreen(), color.getBlue(), 255f, 1f);
            }
        }
    }

    private boolean isBlockValid(final IBlockState blockState, final BlockPos blockPos) {
        if (this.holes.contains(blockPos)) {
            return false;
        }
        if (blockState.getBlock() != Blocks.AIR) {
            return false;
        }
        if (mc.player.getDistanceSq(blockPos) < 1.0) {
            return false;
        }
        if (mc.world.getBlockState(blockPos.up()).getBlock() != Blocks.AIR) {
            return false;
        }
        if (mc.world.getBlockState(blockPos.up(2)).getBlock() != Blocks.AIR) {
            return false;
        }
        return (isVoidHole(blockPos));
    }


    private boolean isVoidHole(BlockPos blockPos) {
        return blockPos.getY() == 0 && mc.world.getBlockState(blockPos.down()).getBlock() instanceof BlockAir;
    }

    private class Hole extends Vec3i {
        Hole(final int x, final int y, final int z) {
            super(x, y, z);
        }
    }
}
