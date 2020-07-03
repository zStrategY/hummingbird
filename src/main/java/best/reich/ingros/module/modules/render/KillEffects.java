package best.reich.ingros.module.modules.render;

import best.reich.ingros.events.network.PacketEvent;
import me.xenforu.kelo.module.ModuleCategory;
import me.xenforu.kelo.module.annotation.ModuleManifest;
import me.xenforu.kelo.module.type.ToggleableModule;
import me.xenforu.kelo.setting.annotation.Mode;
import me.xenforu.kelo.setting.annotation.Setting;
import net.b0at.api.event.Subscribe;
import net.b0at.api.event.types.EventType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;

/**
 * @author auto on 3/25/2020
 */
@ModuleManifest(label = "KillEffects", category = ModuleCategory.RENDER)
public class KillEffects extends ToggleableModule {

    @Setting("Mode")
    @Mode({"Lightning"})
    public String mode = "Lightning";

    @Setting("Sounds")
    public boolean sounds = true;

    @Setting("Death")
    public boolean death = true;

    @Subscribe
    public void onPacket(PacketEvent event) {
        if (event.getType() == EventType.PRE)
            if (event.getPacket() instanceof CPacketUseEntity) {
                final CPacketUseEntity packet = (CPacketUseEntity) event.getPacket();
                if (packet.getAction() == CPacketUseEntity.Action.ATTACK) {
                    Entity entity = packet.getEntityFromWorld(mc.world);
                    switch (mode.toLowerCase()) {
                        case "lightning":
                            if (death && !entity.isDead)
                                return;

                            final EntityLightningBolt lightning = new EntityLightningBolt(mc.world, entity.posX, entity.posY, entity.posZ, true);
                            mc.world.spawnEntity(lightning);
                            if (sounds) {
                                final ResourceLocation thunderLocal = new ResourceLocation("minecraft", "entity.lightning.thunder");
                                final SoundEvent thunderSound = new SoundEvent(thunderLocal);
                                final ResourceLocation lightningImpactLocal = new ResourceLocation("minecraft", "entity.lightning.impact");
                                final SoundEvent lightningImpactSound = new SoundEvent(lightningImpactLocal);
                                mc.world.playSound(mc.player, new BlockPos(entity.posX, entity.posY, entity.posZ), thunderSound, SoundCategory.WEATHER, 1.0f, 1.0f);
                                mc.world.playSound(mc.player, new BlockPos(entity.posX, entity.posY, entity.posZ), lightningImpactSound, SoundCategory.WEATHER, 1.0f, 1.0f);
                            }
                            break;
                    }
                }
            }
        }
    }