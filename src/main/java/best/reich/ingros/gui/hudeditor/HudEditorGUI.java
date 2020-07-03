package best.reich.ingros.gui.hudeditor;

import best.reich.ingros.IngrosWare;
import best.reich.ingros.gui.hudeditor.component.HudComponent;
import best.reich.ingros.module.persistent.Commands;
import best.reich.ingros.module.persistent.Keybinds;
import best.reich.ingros.module.persistent.Overlay;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.client.gui.GuiScreen;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class HudEditorGUI extends GuiScreen {

    private ArrayList<HudComponent> hudComponents = new ArrayList<>();

    public void load() {
        hudComponents.add(new HudComponent("ComponentTest", 5, 5, 100, 25));
        load(new File(IngrosWare.INSTANCE.path.toFile(), "hudcomponents").toPath());
    }

    public void unload() {
        save(new File(IngrosWare.INSTANCE.path.toFile(), "hudcomponents").toPath());
    }

    public void load(Path source) {
        hudComponents.forEach(hudComponent -> {
            Path pluginConfiguration = new File(source.toFile(), hudComponent.getName().toLowerCase() + ".json").toPath();
            if (Files.exists(source) && Files.exists(pluginConfiguration)) {
                try (Reader reader = new FileReader(pluginConfiguration.toFile())) {
                    JsonElement element = new JsonParser().parse(reader);
                    if (element.isJsonObject()) {
                        hudComponent.load(element.getAsJsonObject());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void save(Path destination) {
        if (!Files.exists(destination)) {
            try {
                Files.createDirectory(destination);
            } catch (IOException ignored) { }
        }
        File[] configurations = destination.toFile().listFiles();
        if (!Files.exists(destination)) {
            try {
                Files.createDirectory(destination);
            } catch (IOException ignored) { }
        } else if (configurations != null) {
            for (File configuration : configurations) {
                configuration.delete();
            }
        }

        hudComponents.forEach(hudComponent -> {
            Path pluginConfiguration = new File(destination.toFile(), hudComponent.getName() + ".json").toPath();
            JsonObject object = new JsonObject();
            hudComponent.save(object);
            if (!object.entrySet().isEmpty()) {
                try {
                    Files.createFile(pluginConfiguration);
                } catch (IOException e) {
                    return;
                }
                try (Writer writer = new FileWriter(pluginConfiguration.toFile())) {
                    writer.write(new GsonBuilder()
                            .setPrettyPrinting()
                            .create()
                            .toJson(object));
                } catch (IOException ignored) { }
            }
        });
        configurations = destination.toFile().listFiles();
        if (configurations == null || configurations.length == 0) {
            try {
                Files.delete(destination);
            } catch (IOException ignored) {}
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        hudComponents.forEach(hudComponent -> hudComponent.drawScreen(mouseX, mouseY, partialTicks));
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        hudComponents.forEach(hudComponent -> hudComponent.mouseClicked(mouseX, mouseY, mouseButton));
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        super.mouseReleased(mouseX, mouseY, mouseButton);
        hudComponents.forEach(frame -> frame.mouseReleased(mouseX, mouseY, mouseButton));
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

}
