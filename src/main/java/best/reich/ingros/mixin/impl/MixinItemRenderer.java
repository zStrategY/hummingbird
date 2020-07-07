package best.reich.ingros.mixin.impl;

import best.reich.ingros.IngrosWare;
import best.reich.ingros.events.render.ItemRenderEvent;
import best.reich.ingros.events.render.OverlayEvent;
import best.reich.ingros.events.render.RenderArmEvent;
import net.minecraft.client.renderer.ItemRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author TBM 03/07/2020
 */

@Mixin(ItemRenderer.class)
public abstract class MixinItemRenderer {

    @Inject(method = "renderFireInFirstPerson", at = @At("HEAD"), cancellable = true)
    public void onFirstPersonRenderFire(CallbackInfo ci) {
        OverlayEvent event = new OverlayEvent();
        IngrosWare.INSTANCE.bus.fireEvent(event);
        if (event.isCancelled()) ci.cancel();
    }

    @Inject(method = "renderArmFirstPerson", at = @At("HEAD"), cancellable = true)
    public void onArmFirstPerson(CallbackInfo ci) {
        RenderArmEvent event = new RenderArmEvent();
        IngrosWare.INSTANCE.bus.fireEvent(event);
        if (event.isCancelled()) ci.cancel();
    }

    @Inject(method = "renderItemInFirstPerson(Lnet/minecraft/client/entity/AbstractClientPlayer;FFLnet/minecraft/util/EnumHand;FLnet/minecraft/item/ItemStack;F)V", at = @At("HEAD"), cancellable = true)
    public void onRenderItem(CallbackInfo ci) {
        ItemRenderEvent event = new ItemRenderEvent();
        IngrosWare.INSTANCE.bus.fireEvent(event);
        if (event.isCancelled()) ci.cancel();
    }

}
