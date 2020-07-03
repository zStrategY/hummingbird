package best.reich.ingros.module.modules.render;

import best.reich.ingros.IngrosWare;
import best.reich.ingros.gui.hudeditor.HudEditorGUI;
import me.xenforu.kelo.module.ModuleCategory;
import me.xenforu.kelo.module.annotation.ModuleManifest;
import me.xenforu.kelo.module.type.ToggleableModule;
import me.xenforu.kelo.setting.annotation.Setting;
import org.lwjgl.input.Keyboard;

import java.awt.*;

@ModuleManifest(label = "HudEditor", category = ModuleCategory.RENDER, color = 0xFF05BAFF,bind = Keyboard.KEY_NEXT, hidden = true)
public class HudEditor extends ToggleableModule {

    @Setting("Color")
    public Color color = new Color(179,0,240);

    @Override
    public void onEnable() {
        super.onEnable();
        mc.displayGuiScreen(IngrosWare.INSTANCE.hudEditorGUI);
        toggle();
    }
}
