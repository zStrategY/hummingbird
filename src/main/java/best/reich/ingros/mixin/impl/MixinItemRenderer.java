package best.reich.ingros.mixin.impl;

import best.reich.ingros.IngrosWare;
import best.reich.ingros.events.render.*;
import com.google.common.base.MoreObjects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.client.ForgeHooksClient;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
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

    @Inject(method = "renderWaterOverlayTexture", at = @At("HEAD"), cancellable = true)
    public void onFogEvent(CallbackInfo ci) {
        FogEvent event = new FogEvent();
        IngrosWare.INSTANCE.bus.fireEvent(event);
        if (event.isCancelled()) ci.cancel();
    }

}
