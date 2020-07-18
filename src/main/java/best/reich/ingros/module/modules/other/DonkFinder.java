package best.reich.ingros.module.modules.other;

import best.reich.ingros.events.other.EntityChunkEvent;
import best.reich.ingros.util.logging.Logger;
import me.xenforu.kelo.module.ModuleCategory;
import me.xenforu.kelo.module.annotation.ModuleManifest;
import me.xenforu.kelo.module.type.ToggleableModule;
import me.xenforu.kelo.setting.annotation.Setting;
import net.b0at.api.event.Subscribe;
import net.b0at.api.event.types.EventType;
import net.minecraft.entity.passive.EntityDonkey;
import net.minecraft.entity.passive.EntityLlama;
import net.minecraft.entity.passive.EntityMule;

import static me.xenforu.kelo.traits.Minecraftable.mc;

@ModuleManifest(label = "DonkFinder", category = ModuleCategory.OTHER, hidden = true)
public class DonkFinder extends ToggleableModule {

    /**
     *
     * @author proto
     *
     */



    @Setting("FindChestEntities")
    public boolean FindChestableEntities = true;

    @Subscribe
    public void onEntityEnterChunk(EntityChunkEvent event) {
        if (mc.world == null || mc.player == null) return;
        if (event.getType() == EventType.PRE) {
            if (FindChestableEntities) {
                if (event.getEntity() instanceof EntityDonkey || event.getEntity() instanceof EntityMule || event.getEntity() instanceof EntityLlama) {
                    Logger.printMessage("A chestable entity has entered your view distance", true);
                }
            }
        }
    }



}
