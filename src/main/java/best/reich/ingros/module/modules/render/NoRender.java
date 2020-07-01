package best.reich.ingros.module.modules.render;

import best.reich.ingros.events.entity.UpdateEvent;
import best.reich.ingros.events.network.PacketEvent;
import best.reich.ingros.events.render.HurtcamEvent;
import me.xenforu.kelo.module.ModuleCategory;
import me.xenforu.kelo.module.annotation.ModuleManifest;
import me.xenforu.kelo.module.type.ToggleableModule;
import me.xenforu.kelo.setting.annotation.Setting;
import net.b0at.api.event.Subscribe;
import net.minecraftforge.client.event.RenderBlockOverlayEvent;
import best.reich.ingros.events.network.PacketEvent;
import net.b0at.api.event.Event;



@ModuleManifest(label = "NoRender", category = ModuleCategory.RENDER, color = 0xFF05B21C)
public class NoRender extends ToggleableModule { /*
    @Setting("Fire")
    public boolean fire = true;
    @Setting("NoHurtCam")
    public boolean nohurtcam = true;
    @Setting("NoTotemAnimation")
    public boolean nototemanimation = false;
    */

}
