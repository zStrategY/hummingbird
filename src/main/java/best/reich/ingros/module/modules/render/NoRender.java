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

    @Subscribe
    public void stuff(UpdateEvent e) {
        if (mc.player == null | mc.world == null) return;
    }

    @Setting("NoHurtCam")
    public boolean noHurtCam = true;
    @Setting("NoBossBar")
    public boolean noBossBar = true;
    @Setting("NoArmor")
    public boolean noArmor = false;
    @Setting("NoFire")
    public boolean noFire = true;
    @Setting("NoFog")
    public boolean noFog = false;
    @Setting("NoLightLag")
    public boolean nolight = true;

    @Subscribe
    public void onHurtcam(HurtcamEvent event) {
        if (mc.player == null || mc.world == null) return;
        if (noHurtCam) {
            event.setCancelled(true);
        }
    }

    @Subscribe
    public void onBossbar(BossbarEvent event) {
        if (mc.player == null || mc.world == null) return;
        if (noBossBar) {
            event.setCancelled(true);
        }
    }

    @Subscribe
    public void onArmorRender(RenderArmorEvent event) {
        if (mc.player == null || mc.world == null) return;
        if (noArmor) {
            event.setCancelled(true);
        }
    }

    /**
     * @author TBM really epic guy helped me with nofire !
     */
    @Subscribe
    public void RenderBlockEvent(OverlayEvent event) {
        if (mc.player == null || mc.world == null) return;
        if (noFire) {
            event.setCancelled(true);
        }
    }

    @Subscribe
    public void onFog(FogEvent event) {
        if (mc.player == null || mc.world == null) return;
        if (noFog) {
            event.setCancelled(true);
        }
    }


    @Subscribe
    public void onLight(UpdateLightEvent event) {
        if (mc.player == null || mc.world == null) return;
        if (nolight) {
            event.setCancelled(true);
        }
    }
}
