package best.reich.ingros.module.modules.render;


import best.reich.ingros.events.render.BossbarEvent;
import best.reich.ingros.events.render.HurtcamEvent;
import best.reich.ingros.events.render.OverlayEvent;
import best.reich.ingros.events.render.RenderArmorEvent;
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

    @Subscribe
    public void onHurtcam(HurtcamEvent event) {
        if (mc.player != null);
        if (noHurtCam) event.setCancelled(true);
    }

    @Subscribe
    public void onBossbar(BossbarEvent event) {
        if (mc.player != null);
        if (noBossBar) event.setCancelled(true);
    }

    @Subscribe
    public void onArmorRender(RenderArmorEvent event) {
        if (mc.player != null);
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
}
