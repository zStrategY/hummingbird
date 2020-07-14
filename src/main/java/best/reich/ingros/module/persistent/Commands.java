package best.reich.ingros.module.persistent;

import best.reich.ingros.IngrosWare;
import best.reich.ingros.events.network.PacketEvent;
import best.reich.ingros.events.other.ProcessKeyBindsEvent;
import me.xenforu.kelo.setting.annotation.Setting;
import net.b0at.api.event.types.EventType;
import best.reich.ingros.util.logging.Logger;
import me.xenforu.kelo.command.Command;
import me.xenforu.kelo.module.ModuleCategory;
import me.xenforu.kelo.module.annotation.ModuleManifest;
import me.xenforu.kelo.module.type.PersistentModule;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.CPacketChatMessage;
import net.b0at.api.event.Subscribe;
import org.lwjgl.input.Keyboard;

/**
 * made by Xen for IngrosWare
 * at 1/3/2020
 **/
@ModuleManifest(label = "Commands", category = ModuleCategory.OTHER)
public class Commands extends PersistentModule {

    @Setting("OpenChat")
    public boolean chat = true;

    @Subscribe
    public void onProccessKeyBinds(ProcessKeyBindsEvent event) {
        if (chat) {
            if (Minecraft.getMinecraft().gameSettings.chatVisibility != EntityPlayer.EnumChatVisibility.HIDDEN) {
                if (Minecraft.getMinecraft().currentScreen == null && Keyboard.isKeyDown(Keyboard.KEY_MINUS)) {
                    Minecraft.getMinecraft().displayGuiScreen(new GuiChat("-"));
                }
            }
        }
    }

    @Subscribe
    public void onSendPacket(PacketEvent event) {
        if (event.getType() == EventType.PRE) {
            if (event.getPacket() instanceof CPacketChatMessage) {
                checkCommands(((CPacketChatMessage) event.getPacket()).getMessage(), event);
            }
        }
    }


    private void checkCommands(String message, PacketEvent event) {
        if (message.startsWith("-")) {
            String[] args = message.split(" ");
            String input = message.split(" ")[0].substring(1);
            for (Command command : IngrosWare.INSTANCE.commandManager.getValues()) {
                if (input.equalsIgnoreCase(command.getLabel())) {
                    event.setCancelled(true);
                    command.execute(args);
                } else {
                    for (String alias : command.getHandles()) {
                        if (input.equalsIgnoreCase(alias)) {
                            event.setCancelled(true);
                            command.execute(args);
                        }
                    }
                }
            }
            if (!event.isCancelled()) {
                Logger.printMessage("Command \"" + message + "\" was not found!",true);
                event.setCancelled(true);
            }
            event.setCancelled(true);
        }
    }

}
