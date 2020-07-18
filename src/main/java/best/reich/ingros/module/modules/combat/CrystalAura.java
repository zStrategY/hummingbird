/*package best.reich.ingros.module.modules.combat;

import best.reich.ingros.IngrosWare;
import best.reich.ingros.events.entity.UpdateEvent;
import best.reich.ingros.events.network.PacketEvent;
import best.reich.ingros.events.render.Render3DEvent;
import best.reich.ingros.mixin.accessors.IRenderManager;
import best.reich.ingros.util.game.KillUtil;
import best.reich.ingros.util.game.StopwatchUtil;
import best.reich.ingros.util.logging.Logger;
import best.reich.ingros.util.render.RenderUtil;
import me.xenforu.kelo.module.ModuleCategory;
import me.xenforu.kelo.module.annotation.ModuleManifest;
import me.xenforu.kelo.module.type.ToggleableModule;
import me.xenforu.kelo.setting.annotation.Clamp;
import me.xenforu.kelo.setting.annotation.Setting;
import net.b0at.api.event.Subscribe;
import net.b0at.api.event.types.EventType;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemEndCrystal;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.network.play.server.SPacketSoundEffect;
import net.minecraft.potion.Potion;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.Explosion;

import java.awt.*;
import java.util.List;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;


@ModuleManifest(label = "CrystalAura", category = ModuleCategory.COMBAT, color = 0x4BF7FF)
public class CrystalAura extends ToggleableModule {

    @Clamp(minimum = "1.0", maximum = "6.0")
    @Setting("EnemyRange")
    public float enemyRange = 6.0f;

    @Clamp(minimum = "1.0", maximum = "6.0")
    @Setting("PlaceRange")
    public float placeRange = 6.0f;

    @Clamp(minimum = "1.0", maximum = "6.0")
    @Setting("BreakRange")
    public float breakRange = 6.0f;

    @Clamp(minimum = "1.0", maximum = "6.0")
    @Setting("WallRange")
    public float wallRange = 3.5f;

    @Clamp(minimum = "1", maximum = "100")
    @Setting("Aps")
    public int aps = 20;

    @Clamp(minimum = "1", maximum = "16")
    @Setting("MinimumDamage")
    public double minDamage = 4.0;

    @Clamp(minimum = "1", maximum = "36")
    @Setting("Faceplace")
    public int facePlace = 4;

    @Clamp(minimum = "1")
    @Setting("MaxSelfDamage")
    public int maxDamage = 11;

    @Setting("pSilent")
    public boolean pSilent = false;

    @Setting("Announcer")
    public boolean announcer = true;

    @Setting("Color")
    public Color color = new Color(255, 0, 0);

    @Setting("DmgRenderColor")
    public Color dmgColor = new Color(255, 0, 0);


    private final StopwatchUtil stopwatch = new StopwatchUtil();
    private BlockPos render;
    private String dmg;
    private boolean switchCooldown;
    private BlockPos placePos = null;
    private float yaw = 0.0F;
    private float pitch = 0.0F;
    private boolean shouldRotate = false;
    private BlockPos renderPos = null;
    public EntityPlayer target = null;
    private EntityPlayer possibleTarget = null;


    @Subscribe
    public void onUpdate(UpdateEvent event) {
        if (mc.player != null && mc.world != null && mc.player.getHealth() + mc.player.getAbsorptionAmount() > 0.0F && !mc.player.isDead) {
            if (breakRange > 0.0D) {
                for (final Entity c : mc.world.loadedEntityList) {
                    if (c instanceof EntityEnderCrystal) {
                        if (mc.player.getDistanceToEntity(c) > breakRange) {
                            if (!mc.player.canEntityBeSeen(c) && mc.player.getDistanceToEntity(c) >= wallRange) return;
                            if (StopwatchUtil.hasCompleted((1000 / aps))) {
                                mc.playerController.attackEntity(mc.player, c);
                                mc.player.swingArm(EnumHand.MAIN_HAND);
                                stopwatch.reset();
                            }
                        }
                    }
                }
            }
            if (placeRange > 0.0D) {
                if (!this.switchCooldown) {
                    this.placePos = null;
                    this.renderPos = null;
                    float dmg = 0.0F;
                    Iterator var3 = ((List) mc.world.loadedEntityList.stream().filter((p) -> {
                        return !p.equals(mc.player);
                    }).filter((p) -> {
                        return !p.isDead;
                    }).filter((p) -> {
                        return !IngrosWare.INSTANCE.friendManager.isFriend(target.getName());
                    }).sorted(Comparator.comparing((p) -> {
                        return mc.player.getDistanceToEntity(p) >= enemyRange;
                    })).collect(Collectors.toList())).iterator();

                    label123:
                    while (var3.hasNext()) {
                        EntityPlayer player = (EntityPlayer) var3.next();
                        Iterator var5 = this.findCrystalBlocks().iterator();
                        while (true) {
                            BlockPos pos;
                            float d;
                            do {
                                do {
                                    if (!var5.hasNext()) {
                                        continue label123;
                                    }

                                    pos = (BlockPos) var5.next();
                                    d = this.calculateDamage(pos, player);
                                } while (d <= dmg);
                            } while(d < minDamage && (double) (player.getHealth() + player.getAbsorptionAmount()) > (this.facePlace));

                            if ((double) this.calculateDamage(pos, player) <= this.maxDamage) {
                                dmg = d;
                                render = placePos;
                            }
                        }
                    }
                }


                this.switchCooldown = false;
                if (this.placePos == null) {
                    return;
                }

                boolean offHand = mc.player.getHeldItemOffhand().getItem() instanceof ItemEndCrystal;
                boolean mainHand = mc.player.getHeldItemMainhand().getItem() instanceof ItemEndCrystal;
                    for (int i = 0; i < 9; ++i) {
                        if (mc.player.inventory.getStackInSlot(i).getItem() instanceof ItemEndCrystal) {
                            mc.player.inventory.currentItem = i;
                            this.switchCooldown = true;
                            return;
                        }
                    }

                if (offHand || mainHand) {
                    this.renderPos = this.placePos;
                    if (this.possibleTarget != null) {
                        this.target = this.possibleTarget;
                        KillUtil.INSTANCE.addTarget(this.target);
                    }

                    if (!pSilent) {
                        this.rotateTo((double) this.placePos.getX() + 0.5D, (double) this.placePos.getY() - 0.5D, (double) this.placePos.getZ() + 0.5D);
                    }

                    mc.player.connection.sendPacket(new CPacketPlayerTryUseItemOnBlock(this.placePos, EnumFacing.UP, offHand ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND, 0.0F, 0.0F, 0.0F));
                    if (!pSilent) {
                        this.resetRotation();
                    }
                }
            }

        }
    }
    @Subscribe
    public void onPacket(final PacketEvent event) {
        if (event.getType() == EventType.POST && event.getPacket() instanceof SPacketSoundEffect) {
            final SPacketSoundEffect packet = (SPacketSoundEffect) event.getPacket();
            if (packet.getCategory() == SoundCategory.BLOCKS && packet.getSound() == SoundEvents.ENTITY_GENERIC_EXPLODE) {
                for (final Entity e : mc.world.loadedEntityList) {
                    if (e instanceof EntityEnderCrystal && e.getDistance(packet.getX(), packet.getY(), packet.getZ()) <= 6.0) {
                        e.setDead();
                    }
                }
            }
        }
    }

    @Subscribe
    public void onRender3D(Render3DEvent event) {
        if (render != null) {
            final AxisAlignedBB bb = new AxisAlignedBB(render.getX() - mc.getRenderManager().viewerPosX, render.getY() - mc.getRenderManager().viewerPosY + 1, render.getZ() - mc.getRenderManager().viewerPosZ, render.getX() + 1 - mc.getRenderManager().viewerPosX, render.getY() + 1.2 - mc.getRenderManager().viewerPosY, render.getZ() + 1 - mc.getRenderManager().viewerPosZ);
            if (RenderUtil.isInViewFrustrum(new AxisAlignedBB(bb.minX + mc.getRenderManager().viewerPosX, bb.minY + mc.getRenderManager().viewerPosY, bb.minZ + mc.getRenderManager().viewerPosZ, bb.maxX + mc.getRenderManager().viewerPosX, bb.maxY + mc.getRenderManager().viewerPosY, bb.maxZ + mc.getRenderManager().viewerPosZ))) {
                RenderUtil.drawESP(bb, color.getRed(), color.getGreen(), color.getBlue(), 40F);
                RenderUtil.drawESPOutline(bb, color.getRed(), color.getGreen(), color.getBlue(), 255f, 1f);
                final double posX = render.getX() - ((IRenderManager) mc.getRenderManager()).getRenderPosX();
                final double posY = render.getY() - ((IRenderManager) mc.getRenderManager()).getRenderPosY();
                final double posZ = render.getZ() - ((IRenderManager) mc.getRenderManager()).getRenderPosZ();
                RenderUtil.renderTag(dmg, posX + 0.5, posY, posZ + 0.5, dmgColor.getRGB());
                GlStateManager.enableDepth();
                GlStateManager.depthMask(true);
                GlStateManager.enableLighting();
                GlStateManager.disableBlend();
                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                RenderHelper.disableStandardItemLighting();
            }
        }
    }


    @Subscribe
    public void onRotate(PacketEvent event) {
        if (event.getPacket() instanceof CPacketPlayer) {
            CPacketPlayer packet = (CPacketPlayer) event.getPacket();
            if (shouldRotate) {
                packet.yaw = this.yaw;
                packet.pitch = this.pitch;

            }

        }
    }
        public void rotate (double x, double y, double z) {

    }
    public void reset() {
        this.shouldRotate = false;
        if (mc.player != null) {
            this.yaw = mc.player.rotationYaw;
            this.pitch = mc.player.rotationPitch;
        }
    }

    private void rotateTo(double x, double y, double z) {
       this.rotate(x, y, z);
    }


    private void rotateTo(Entity target) {
        this.rotateTo(target.posX, target.posY, target.posZ);
    }

    private void resetRotation() {
        this.reset();
    }

    private boolean canPlaceCrystal(BlockPos blockPos) {
        BlockPos boost = blockPos.add(0, 1, 0);
        BlockPos boost2 = blockPos.add(0, 2, 0);
        return (mc.world.getBlockState(blockPos).getBlock() == Blocks.OBSIDIAN || mc.world.getBlockState(blockPos).getBlock() == Blocks.BEDROCK)  || mc.world.getBlockState(blockPos).getBlock() == Blocks.OBSIDIAN && mc.world.getBlockState(boost).getBlock() == Blocks.AIR && mc.world.getBlockState(boost2).getBlock() == Blocks.AIR && mc.world.getEntitiesWithinAABB((Class) Entity.class, new AxisAlignedBB(boost)).isEmpty() && mc.world.getEntitiesWithinAABB((Class) Entity.class, new AxisAlignedBB(boost2)).isEmpty();
    }

    private BlockPos getPlayerPos() {
        return new BlockPos(Math.floor(mc.player.posX), Math.floor(mc.player.posY), Math.floor(mc.player.posZ));
    }

    private List<BlockPos> findCrystalBlocks() {
        NonNullList<BlockPos> circleblocks = NonNullList.create();
        BlockPos loc = this.getPlayerPos();
        float r = ((Float)this.placeRange);
        float h = this.placeRange;
        boolean hollow = false;
        boolean sphere = true;
        int plus_y = 0;
        int cx = loc.getX();
        int cy = loc.getY();
        int cz = loc.getZ();

        for(int x = cx - (int)r; (float)x <= (float)cx + r; ++x) {
            for(int z = cz - (int)r; (float)z <= (float)cz + r; ++z) {
                for(int y = sphere ? cy - (int)r : cy; (float)y < (sphere ? (float)cy + r : (float)(cy + h)); ++y) {
                    double dist = (double)((cx - x) * (cx - x) + (cz - z) * (cz - z) + (sphere ? (cy - y) * (cy - y) : 0));
                    if (dist < (double)(r * r) && (!hollow || dist >= (double)((r - 1.0F) * (r - 1.0F)))) {
                        BlockPos l = new BlockPos(x, y + plus_y, z);
                        if (this.canPlaceCrystal(l)) {
                            circleblocks.add(l);
                        }
                    }
                }
            }
        }

        return circleblocks;
    }

    private float calculateDamage(double posX, double posY, double posZ, Entity entity) {
        float doubleExplosionSize = 12.0F;
        double distancedsize = entity.getDistance(posX, posY, posZ) / (double)doubleExplosionSize;
        Vec3d vec3d = new Vec3d(posX, posY, posZ);
        double blockDensity = (double)entity.world.getBlockDensity(vec3d, entity.getEntityBoundingBox());
        double v = (1.0D - distancedsize) * blockDensity;
        float damage = (float)((int)((v * v + v) / 2.0D * 7.0D * (double)doubleExplosionSize + 1.0D));
        double finald = 1.0D;
        if (entity instanceof EntityLivingBase) {
            finald = (double)this.getBlastReduction((EntityLivingBase)entity, this.getDamageMultiplied(damage), new Explosion(mc.world, (Entity)null, posX, posY, posZ, 6.0F, false, true));
        }

        return (float)finald;
    }

    private float getBlastReduction(EntityLivingBase entity, float damage, Explosion explosion) {
        if (entity instanceof EntityPlayer) {
            EntityPlayer ep = (EntityPlayer)entity;
            DamageSource ds = DamageSource.causeExplosionDamage(explosion);
            damage = CombatRules.getDamageAfterAbsorb(damage, (float)ep.getTotalArmorValue(), (float)ep.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getAttributeValue());
            int k = EnchantmentHelper.getEnchantmentModifierDamage(ep.getArmorInventoryList(), ds);
            float f = MathHelper.clamp((float)k, 0.0F, 20.0F);
            damage *= 1.0F - f / 25.0F;
            if (entity.isPotionActive(Potion.getPotionById(11))) {
                damage -= damage / 4.0F;
            }

            return damage;
        } else {
            damage = CombatRules.getDamageAfterAbsorb(damage, (float)entity.getTotalArmorValue(), (float)entity.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getAttributeValue());
            return damage;
        }
    }

    private float getDamageMultiplied(float damage) {
        int diff = mc.world.getDifficulty().getDifficultyId();
        return damage * (diff == 0 ? 0.0F : (diff == 2 ? 1.0F : (diff == 1 ? 0.5F : 1.5F)));
    }

    private float calculateDamage(BlockPos blockPos, Entity entity) {
        return this.calculateDamage((double)blockPos.getX() + 0.5D, (double)(blockPos.getY() + 1), (double)blockPos.getZ() + 0.5D, entity);
    }
    @Override
    public void onEnable() {
        super.onEnable();
        if (announcer) {
            Logger.printMessage("CA Enabled!", true);
        }
    }

    @Override
    public void onDisable() {
        super.onDisable();
        dmg = null;
        render = null;
        if (announcer) {
            Logger.printMessage("CA Disabled!", true);
        }
    }
} */