package best.reich.ingros.module.modules.other;

import best.reich.ingros.IngrosWare;
import best.reich.ingros.events.entity.UpdateEvent;
import best.reich.ingros.util.game.MotionUtil;
import club.minnced.discord.rpc.DiscordEventHandlers;
import club.minnced.discord.rpc.DiscordRichPresence;
import me.xenforu.kelo.module.ModuleCategory;
import me.xenforu.kelo.module.annotation.ModuleManifest;
import me.xenforu.kelo.module.type.ToggleableModule;
import me.xenforu.kelo.setting.annotation.Setting;
import me.xenforu.kelo.util.math.MathUtil;
import me.xenforu.kelo.util.math.TimerUtil;
import net.b0at.api.event.Subscribe;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.multiplayer.ServerData;

import java.util.Objects;
import java.util.Random;

@ModuleManifest(label = "DiscordRPC", category = ModuleCategory.OTHER, hidden = true)
public class DiscordRPC extends ToggleableModule {
    private static club.minnced.discord.rpc.DiscordRPC LIB;
    private DiscordRichPresence presence;
    private ServerData serverData;
    private long lastTime;
    private boolean afk;
    private TimerUtil timer = new TimerUtil();

    @Setting("Server")
    public boolean server = true;

    @Setting("Name")
    public boolean name = true;

    @Override
    public void onEnable() {
        DiscordRPC.LIB = club.minnced.discord.rpc.DiscordRPC.INSTANCE;
        this.lastTime = System.currentTimeMillis() / 1000L;
        final String applicationId = "727869455908077570";
        final DiscordEventHandlers handlers = new DiscordEventHandlers();
        DiscordRPC.LIB.Discord_Initialize(applicationId, handlers, true, "");
        new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                this.presence = new DiscordRichPresence();
                this.presence.startTimestamp = lastTime;
                this.presence.largeImageKey = String.format("d%s".replace("%s", String.valueOf(1 + new Random().nextInt(16))));
                this.presence.largeImageText = String.format("%s %s | 1.12.2", IngrosWare.INSTANCE.getLabel(), IngrosWare.INSTANCE.getVersion());
                this.presence.details = (afk || mc.currentScreen instanceof GuiMainMenu || mc.currentScreen instanceof GuiMultiplayer) ? "Currently AFK" : "Currently Exploring";
                serverData = mc.getCurrentServerData();
                if (serverData != null) {
                    StringBuilder sb = new StringBuilder("Multiplayer");
                    if (this.server)
                        sb.append(": " + serverData.serverIP);
                    if (name)
                        sb.append(String.format(" (%s)", mc.getSession().getUsername()));
                    this.presence.state = sb.toString();
                } else if (mc.isSingleplayer()) {
                    this.presence.state = "Singleplayer";
                } else if (mc.currentScreen != null) {
                    if (mc.currentScreen instanceof GuiMainMenu) {
                        this.presence.state = "Main Menu";
                    }
                    if (mc.currentScreen instanceof GuiMultiplayer) {
                        this.presence.state = "Multiplayer";
                    }
                }
                DiscordRPC.LIB.Discord_UpdatePresence(this.presence);
                DiscordRPC.LIB.Discord_RunCallbacks();
                try {
                    Thread.sleep(5000L);
                } catch (InterruptedException ignored) {
                    ignored.printStackTrace();
                }
            }
        }, "RPC-Callback-Handler").start();
    }

}