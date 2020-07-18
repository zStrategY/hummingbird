package best.reich.ingros.module.modules.render;

import best.reich.ingros.events.entity.UpdateEvent;
import best.reich.ingros.events.render.RenderModelEvent;
import me.xenforu.kelo.module.ModuleCategory;
import me.xenforu.kelo.module.annotation.ModuleManifest;
import me.xenforu.kelo.module.type.ToggleableModule;
import me.xenforu.kelo.setting.annotation.Clamp;
import me.xenforu.kelo.setting.annotation.Setting;
import net.b0at.api.event.Subscribe;
import net.minecraft.client.gui.inventory.GuiContainer;
import org.lwjgl.opengl.GL11;

@ModuleManifest(label = "ViewModelChanger", category = ModuleCategory.RENDER)
public class ViewModelChanger extends ToggleableModule {

    @Setting("Swing")
    public boolean swing;

    @Clamp(minimum = "0.0", maximum = "25")
    @Setting("SwingProgress")
    public float s = 6.0f;


    @Subscribe
    public void onRender(UpdateEvent event) {
        if (mc.player == null || mc.world == null || (mc.currentScreen instanceof GuiContainer)) return;
        if (swing) {
            mc.player.swingProgress = s;


        }


    }

    @Subscribe
    public void onRender(RenderModelEvent event) {
        GL11.glTranslated(0.5, 0.5, 0.5);
    }

}
