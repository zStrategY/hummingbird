package best.reich.ingros.mixin.impl;

import best.reich.ingros.IngrosWare;
import best.reich.ingros.events.other.TraceEntityEvent;
import best.reich.ingros.events.render.*;
import best.reich.ingros.mixin.accessors.IEntityRenderer;
import best.reich.ingros.util.render.GLUProjection;
import com.google.common.base.Predicate;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.sound.sampled.Clip;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;


@Mixin(EntityRenderer.class)
@Implements(@Interface(iface = IEntityRenderer.class, prefix = "ext$"))
public abstract class MixinEntityRenderer {

    @Shadow
    private void orientCamera(float partialTicks) {}

    @Intrinsic(displace = true)
    public void ext$cameraOrientation(float partialTicks) {
        orientCamera(partialTicks);
    }

    @Redirect(method = "updateCameraAndRender", at = @At(value = "INVOKE", target = "net/minecraft/client/gui/GuiIngame.renderGameOverlay(F)V"))
    private void updateCameraAndRender$renderGameOverlay(GuiIngame guiIngame, float partialTicks) {
        guiIngame.renderGameOverlay(partialTicks);
        IngrosWare.INSTANCE.bus.fireEvent(new Render2DEvent(partialTicks,new ScaledResolution(Minecraft.getMinecraft())));
    }
    @Inject(
            method = "hurtCameraEffect",
            at = @At("HEAD"),
            cancellable = true
    )
    private void hurtCameraEffect(float partialTicks, CallbackInfo ci) {
        final HurtcamEvent event = new HurtcamEvent();
        IngrosWare.INSTANCE.bus.fireEvent(event);
        if (event.isCancelled())
            ci.cancel();
    }

    /**
     * @author https://github.com/ionar2/salhack/blob/master/src/main/java/me/ionar/salhack/mixin/client/MixinEntityRenderer.java
     */

    @Inject(method = "updateLightmap", at = @At("HEAD"), cancellable = true)
    private void updateLightmap(float partialTicks, CallbackInfo ci)
    {
        UpdateLightEvent event = new UpdateLightEvent();
        IngrosWare.INSTANCE.bus.fireEvent(event);
        if (event.isCancelled())
            ci.cancel();
    }


    @Inject(method = "setupFog", at = @At("HEAD"), cancellable = true)
    public void setupFog(int startCoords, float partialTicks, CallbackInfo ci) {
        FogEvent event = new FogEvent();
        IngrosWare.INSTANCE.bus.fireEvent(event);
        if (event.isCancelled()) ci.cancel();
    }


    @Inject(method = "renderWorldPass", at = @At(value = "INVOKE_STRING", target = "net/minecraft/profiler/Profiler.endStartSection(Ljava/lang/String;)V", args = {"ldc=hand"}))
    private void onStartHand(int pass, float partialTicks, long finishTimeNano, CallbackInfo ci) {
        final GLUProjection projection = GLUProjection.getInstance();
        final IntBuffer viewPort = GLAllocation.createDirectIntBuffer(16);
        final FloatBuffer modelView = GLAllocation.createDirectFloatBuffer(16);
        final FloatBuffer projectionPort = GLAllocation.createDirectFloatBuffer(16);
        GL11.glGetFloat(GL11.GL_MODELVIEW_MATRIX, modelView);
        GL11.glGetFloat(GL11.GL_PROJECTION_MATRIX, projectionPort);
        GL11.glGetInteger(GL11.GL_VIEWPORT, viewPort);
        final ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
        projection.updateMatrices(viewPort, modelView, projectionPort, scaledResolution.getScaledWidth() / (double) Minecraft.getMinecraft().displayWidth,
                scaledResolution.getScaledHeight() / (double) Minecraft.getMinecraft().displayHeight);
        IngrosWare.INSTANCE.bus.fireEvent(new Render3DEvent(partialTicks));
    }

    @Redirect(method = "getMouseOver", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/WorldClient;getEntitiesInAABBexcluding(Lnet/minecraft/entity/Entity;Lnet/minecraft/util/math/AxisAlignedBB;Lcom/google/common/base/Predicate;)Ljava/util/List;"))
    public List<Entity> getEntitiesInAABBexcluding(WorldClient worldClient, Entity entityIn, AxisAlignedBB boundingBox, Predicate predicate) {
        final TraceEntityEvent traceEntityEvent = new TraceEntityEvent();
        IngrosWare.INSTANCE.bus.fireEvent(traceEntityEvent);
        if (traceEntityEvent.isCancelled())
            return new ArrayList<>();
        else
            return worldClient.getEntitiesInAABBexcluding(entityIn, boundingBox, predicate);
    }

    @Redirect(method = "orientCamera", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/WorldClient;rayTraceBlocks(Lnet/minecraft/util/math/Vec3d;Lnet/minecraft/util/math/Vec3d;)Lnet/minecraft/util/math/RayTraceResult;"), expect = 0)
    private RayTraceResult rayTraceBlocks(WorldClient worldClient, Vec3d start, Vec3d end)
    {
        ClipViewEvent event = new ClipViewEvent();
        IngrosWare.INSTANCE.bus.fireEvent(event);
        if (event.isCancelled())
            return null;
        else
            return worldClient.rayTraceBlocks(start, end);
    }

}
