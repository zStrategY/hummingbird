package best.reich.ingros.module.modules.combat;

import best.reich.ingros.IngrosWare;
import best.reich.ingros.events.network.PacketEvent;
import me.xenforu.kelo.module.ModuleCategory;
import me.xenforu.kelo.module.annotation.ModuleManifest;
import me.xenforu.kelo.module.type.ToggleableModule;
import net.b0at.api.event.Subscribe;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.CPacketChatMessage;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

//@ModuleManifest(label = "AutoGG", category = ModuleCategory.COMBAT, hidden = true)
public class AutoGG extends ToggleableModule { /*
    static List<String> AutoGgMessages = new ArrayList<>();
    private ConcurrentHashMap targetedPlayers = null;
    int index = -1;



    @Subscribe
    public void onPacket(PacketEvent event) {
        if (mc.player != null) {
            if (this.targetedPlayers == null) {
                this.targetedPlayers = new ConcurrentHashMap();
            }

            if (event.getPacket() instanceof CPacketUseEntity) {
                CPacketUseEntity cPacketUseEntity = (CPacketUseEntity)event.getPacket();
                if (cPacketUseEntity.getAction().equals(CPacketUseEntity.Action.ATTACK)) {
                    Entity targetEntity = cPacketUseEntity.getEntityFromWorld(mc.world);
                    if (targetEntity instanceof EntityPlayer) {
                        this.addTargetedPlayer(targetEntity.getName());
                    }
                }
            }
        }
    }


    @Subscribe
    public void onDeath(LivingDeathEvent event) {
        if (mc.player != null) {
            if (this.targetedPlayers == null) {
                this.targetedPlayers = new ConcurrentHashMap();
            }

            EntityLivingBase entity = event.getEntityLiving();
            if (entity != null) {
                if (entity instanceof EntityPlayer) {
                    EntityPlayer player = (EntityPlayer)entity;
                    if (player.getHealth() <= 0.0F) {
                        String name = player.getName();
                        if (this.shouldAnnounce(name)) {
                            this.doAnnounce(name);
                        }

                    }
                }
            }
        }
    }

    public void addTargetedPlayer(String name) {
        if (!Objects.equals(name, mc.player.getName())) {
            if (this.targetedPlayers == null) {
                this.targetedPlayers = new ConcurrentHashMap();
            }

            targetedPlayers.put(name, 20);
        }
    }

    public void onUpdate() {
        if (this.targetedPlayers == null) {
            this.targetedPlayers = new ConcurrentHashMap();
        }

        Iterator var1 = mc.world.getLoadedEntityList().iterator();

        while(var1.hasNext()) {
            Entity entity = (Entity)var1.next();
            if (entity instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer)entity;
                if (player.getHealth() <= 0.0F) {
                    String name = player.getName();
                    if (this.shouldAnnounce(name)) {
                        this.doAnnounce(name);
                        break;
                    }
                }
            }
        }

        targetedPlayers.forEach((namex, timeout) -> {
            if ((int)timeout <= 0) {
                this.targetedPlayers.remove(namex);
            } else {
                this.targetedPlayers.put(namex, (int)timeout - 1);
            }

        });
    }

    private boolean shouldAnnounce(String name) {
        return this.targetedPlayers.containsKey(name);
    }

    private void doAnnounce(String name) {
        targetedPlayers.remove(name);
        if (index >= (AutoGgMessages.size() - 1)) index = 1;
        index++;
        String message = null;
        if (AutoGgMessages.size() > 0)
            message = "Sorry, but i just killed you with Hummingbird!";
        String messageSanitized = message.replaceAll("ยง", "").replace("{name}", name);
        if (messageSanitized.length() > 255) {
            messageSanitized = messageSanitized.substring(0, 255);
        }

        mc.player.connection.sendPacket(new CPacketChatMessage(messageSanitized));
    } */
}
