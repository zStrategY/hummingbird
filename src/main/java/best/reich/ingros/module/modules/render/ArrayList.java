/*package best.reich.ingros.module.modules.render;

import best.reich.ingros.IngrosWare;
import best.reich.ingros.events.render.Render2DEvent;
import best.reich.ingros.util.render.RenderUtil;
import com.mojang.realmsclient.gui.ChatFormatting;
import me.xenforu.kelo.module.ModuleCategory;
import me.xenforu.kelo.module.annotation.ModuleManifest;
import me.xenforu.kelo.module.type.ToggleableModule;
import me.xenforu.kelo.setting.annotation.Clamp;
import me.xenforu.kelo.setting.annotation.Mode;
import me.xenforu.kelo.setting.annotation.Setting;
import net.b0at.api.event.Subscribe;

import java.util.Comparator;

@ModuleManifest(label = "ArrayList", category = ModuleCategory.RENDER, hidden = true)
public class ArrayList extends ToggleableModule {

    @Setting("ColorMode")
    @Mode({"NORMAL", "CLIENT", "RAINBOW"})
    public String colormode = "RAINBOW";

    @Setting("Font")
    public boolean font = true;

    @Setting("ShowSuffix")
    public boolean showsuffix = true;

    @Clamp(minimum = "1", maximum = "1920")
    @Setting("X")
    public double x = 0.00;

    @Clamp(minimum = "1", maximum = "1080")
    @Setting("Y")
    public double y = 0.00;

    @Subscribe
    public void onRender(Render2DEvent event) {
        int togglesY = (int) (x + y - RenderUtil.getTextHeight(font) - 2);
        java.util.ArrayList<ToggleableModule> modules;
        modules = new java.util.ArrayList<>(IngrosWare.INSTANCE.moduleManager.getToggles());
        modules.sort(Comparator.comparingDouble(m -> -RenderUtil.getTextWidth(getRenderLabel(m), font)));
        for (ToggleableModule module : modules) {
            if (!module.isEnabled() || module.isHidden()) continue;
            RenderUtil.drawText(getRenderLabel(module), event.getScaledResolution().getScaledWidth() - RenderUtil.getTextWidth(getRenderLabel(module), font) - 2, togglesY += RenderUtil.getTextHeight(font) + 2, getArrayListColor(module, togglesY), font);
        }
    }

    public String getRenderLabel(ToggleableModule module) {
        final StringBuilder sb;
        sb = new StringBuilder(module.getLabel());
        if (module.getSuffix() != null && showsuffix)
            sb.append(ChatFormatting.GRAY).append(" [").append(ChatFormatting.WHITE).append(module.getSuffix()).append(ChatFormatting.GRAY).append("]");
        return sb.toString();
    }

    private int getArrayListColor(ToggleableModule toggleableModule, int offset) {
        switch (colormode.toUpperCase()) {
            case "NORMAL":
                return toggleableModule.getColor();
            case "RAINBOW":
                return RenderUtil.getRainbow(3000, 10 * offset, 0.75f);
        }
        return -1;
    }
} */
