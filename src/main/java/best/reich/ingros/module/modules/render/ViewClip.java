package best.reich.ingros.module.modules.render;

import best.reich.ingros.events.render.ClipViewEvent;
import me.xenforu.kelo.module.ModuleCategory;
import me.xenforu.kelo.module.annotation.ModuleManifest;
import me.xenforu.kelo.module.type.ToggleableModule;
import net.b0at.api.event.Subscribe;

@ModuleManifest(label = "ViewClip", category = ModuleCategory.RENDER)
public class ViewClip extends ToggleableModule {

    @Subscribe
    public void onRaytrace(ClipViewEvent event) {
        event.setCancelled(true);
    }
}
