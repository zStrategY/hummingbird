package best.reich.ingros.mixin.impl;

import best.reich.ingros.IngrosWare;
import best.reich.ingros.events.entity.TeleportEvent;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemChorusFruit;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemChorusFruit.class)
public abstract class MixinItemChorusFruit {

    @Inject(method = "onItemUseFinish", at = @At("HEAD"), cancellable = true)
    public void onItemUseFinish(ItemStack p_onItemUseFinish_1_, World p_onItemUseFinish_2_, EntityLivingBase p_onItemUseFinish_3_, CallbackInfoReturnable<ItemStack> cir) {
        TeleportEvent event = new TeleportEvent();
        IngrosWare.INSTANCE.bus.fireEvent(event);
    }

}
