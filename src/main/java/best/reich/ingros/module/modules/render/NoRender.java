package best.reich.ingros.module.modules.render;


import best.reich.ingros.events.entity.UpdateEvent;
import best.reich.ingros.events.render.*;
import me.xenforu.kelo.module.ModuleCategory;
import me.xenforu.kelo.module.annotation.ModuleManifest;
import me.xenforu.kelo.module.type.ToggleableModule;
import me.xenforu.kelo.setting.annotation.Setting;
import net.b0at.api.event.Subscribe;
import net.b0at.api.event.types.EventType;
import net.minecraftforge.client.event.RenderBlockOverlayEvent;

@ModuleManifest(label = "NoRender", category = ModuleCategory.RENDER)
public class NoRender extends ToggleableModule {

    @Setting("NoHurtCam")
    public boolean noHurtCam = true;
    @Setting("NoBossBar")
    public boolean noBossBar = true;
    @Setting("NoArmor")
    public boolean noArmor = false;
    @Setting("NoFire")
    public boolean noFire = true;
    @Setting("NoHands")
    public boolean noHands = false;
    @Setting("NoItem")
    public boolean noItem = false;
    @Setting("NoFog")
    public boolean noFog = true;
    @Setting("NoViewBob")
    public boolean noBob = true;

    @Subscribe
    public void onHurtcam(HurtcamEvent event) {
        if (mc.player != null);
        if (noHurtCam) event.setCancelled(true);
    }

    @Subscribe
    public void onBossbar(BossbarEvent event) {
        if (mc.world != null);
        if (noBossBar) event.setCancelled(true);
    }

    @Subscribe
    public void onArmorRender(RenderArmorEvent event) {
        if (mc.world != null);
        if (noArmor) event.setCancelled(true);
    }

    /**
     * @author TBM really epic guy helped me with nofire !
     */
    @Subscribe
    public void RenderBlockEvent(OverlayEvent event) {
        if (mc.player != null);
        if (noFire) event.setCancelled(true);
    }

    @Subscribe
    public void onArmRender(RenderArmEvent event) {
        if (mc.player != null);
        if (noHands) event.setCancelled(true);
    }

    @Subscribe
    public void onItemRender(ItemRenderEvent event) {
        if (mc.player != null);
        if (noItem) event.setCancelled(true);
    }

    @Subscribe
    public void onFog(FogEvent event) {
        if (mc.world != null);
        if (noFog) event.setCancelled(true);
    }

    @Subscribe //salhack https://github.com/ionar2/salhack/blob/master/src/main/java/me/ionar/salhack/module/render/NoBobModule.java
    public void onBob(UpdateEvent event) {
        if (noBob)
        mc.player.distanceWalkedModified = 4.0f;
    }

}
