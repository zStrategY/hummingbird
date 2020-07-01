package best.reich.ingros.module.modules.combat;

import best.reich.ingros.events.network.PacketEvent;
import best.reich.ingros.mixin.accessors.IMinecraft;
import me.xenforu.kelo.module.ModuleCategory;
import me.xenforu.kelo.module.annotation.ModuleManifest;
import me.xenforu.kelo.module.type.ToggleableModule;
import net.b0at.api.event.Subscribe;
import net.b0at.api.event.types.EventType;
import net.minecraft.init.Items;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.util.EnumHand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.RayTraceResult;
import org.lwjgl.input.Mouse;
import net.minecraftforge.fml.common.gameevent.InputEvent;


/**
 * By h0lloww for hummingbird 01/07/2020
 */

@ModuleManifest(label = "SmartFastCrystal", category = ModuleCategory.COMBAT, color = 0x4BF7FF)

public class SmartFastCrystal extends ToggleableModule {

    //thanks neva lack for giving some advice

    @Subscribe
    public void onPacket(PacketEvent UpdateEvent) {
        if (UpdateEvent.getType() == EventType.PRE)
            if (mc.player.getHeldItemMainhand().getItem() == Items.DIAMOND_PICKAXE && mc.player.getHeldItemOffhand().getItem() == Items.END_CRYSTAL)
            { ((IMinecraft)mc).setRightClickDelayTimer(0);
        }
    }
}