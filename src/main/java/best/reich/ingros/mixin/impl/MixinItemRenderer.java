package best.reich.ingros.mixin.impl;

import best.reich.ingros.IngrosWare;
import best.reich.ingros.events.render.OverlayEvent;
import net.minecraft.client.renderer.ItemRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 *
 * @author TBM 03/07/2020
 *
 */

@Mixin(ItemRenderer.class)
public class MixinItemRenderer {

    @Inject(method = "renderFireInFirstPerson", at = @At("HEAD"), cancellable = true)
    public void onFirstPersonRenderFire(CallbackInfo ci) {
        OverlayEvent event = new OverlayEvent();
        IngrosWare.INSTANCE.bus.fireEvent(event);
        if (event.isCancelled()) ci.cancel();
    }
}
