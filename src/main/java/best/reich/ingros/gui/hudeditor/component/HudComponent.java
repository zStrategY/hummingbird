package best.reich.ingros.gui.hudeditor.component;

import best.reich.ingros.IngrosWare;
import best.reich.ingros.module.modules.render.HudEditor;
import best.reich.ingros.util.game.MouseUtil;
import best.reich.ingros.util.render.RenderUtil;
import com.google.gson.JsonObject;
import me.xenforu.kelo.setting.impl.ColorSetting;
import me.xenforu.kelo.setting.impl.StringSetting;
import org.apache.commons.lang3.StringEscapeUtils;

public class HudComponent {

    private final String name;
    private float x;
    private float y;
    private float width;
    private float height;

    private boolean dragging;
    private float lastX;
    private float lastY;

    public HudComponent(String name, float x, float y, float width, float height) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        if (isDragging()) {
            setX(mouseX + getLastX());
            setY(mouseY + getLastY());
        }
        final HudEditor hudEditor = (HudEditor) IngrosWare.INSTANCE.moduleManager.getModule("HudEditor");
        RenderUtil.drawRect(getX(), getY(), getWidth(), getHeight(), hudEditor.color.getRGB());
        RenderUtil.drawText(getName(), getX() + 5, getY() + 5, 0xFFFFFF, true);
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        final boolean withinBounds = MouseUtil.mouseWithinBounds(mouseX, mouseY, getX(), getY(), getWidth(), getHeight());
        if (withinBounds && mouseButton == 0) {
            setDragging(true);
            setLastX(getX() - mouseX);
            setLastY(getY() - mouseY);
        }
    }

    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0 && isDragging()) setDragging(false);
    }

    public void save(JsonObject destination) {
        destination.addProperty("X", getX());
        destination.addProperty("Y", getY());
    }


    public void load(JsonObject source) {
        if (source.has("X")) {
            setX(source.get("X").getAsFloat());
        }
        if (source.has("Y")) {
            setY(source.get("Y").getAsFloat());
        }
    }

    public String getName() {
        return name;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public boolean isDragging() {
        return dragging;
    }

    public void setDragging(boolean dragging) {
        this.dragging = dragging;
    }

    public float getLastX() {
        return lastX;
    }

    public void setLastX(float lastX) {
        this.lastX = lastX;
    }

    public float getLastY() {
        return lastY;
    }

    public void setLastY(float lastY) {
        this.lastY = lastY;
    }
}
