package best.reich.ingros.mixin.impl;

import best.reich.ingros.IngrosWare;
import best.reich.ingros.module.modules.render.Visuals;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLivingBase;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.awt.*;

@Mixin(RenderLivingBase.class)
public abstract class MixinRenderLivingBase extends Render {

    @Shadow protected abstract boolean isVisible(EntityLivingBase p_193115_1_);

    @Shadow protected ModelBase mainModel;

    protected MixinRenderLivingBase(RenderManager renderManager) {
        super(renderManager);
    }

    /**
     * @author auto 14/07/2020
     * for hummingbird
     * @reason chams
     */


    @Overwrite
    protected void renderModel(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
        boolean flag = this.isVisible(entitylivingbaseIn);
        boolean flag1 = !flag && !entitylivingbaseIn.isInvisibleToPlayer(Minecraft.getMinecraft().player);
        if (flag || flag1) {
            if (!this.bindEntityTexture(entitylivingbaseIn)) {
                return;
            }

            if (flag1) {
                GlStateManager.enableBlendProfile(GlStateManager.Profile.TRANSPARENT_MODEL);
            }

            Visuals visuals = (Visuals) IngrosWare.INSTANCE.moduleManager.getToggleByName("Visuals");
            if (visuals.isEnabled() && visuals.chams && visuals.isValid(entitylivingbaseIn) && visuals.colorChams && entitylivingbaseIn != Minecraft.getMinecraft().player) {
                Color invisibleColor = visuals.invisibleColor;
                Color visibleColor = visuals.visibleColor;
                int alpha = visuals.alpha;
                GL11.glPushAttrib(1048575);
                GL11.glDisable(3008);
                GL11.glDisable(3553);
                GL11.glDisable(2896);
                GL11.glEnable(3042);
                GL11.glBlendFunc(770, 771);
                GL11.glDepthMask(false);
                GL11.glLineWidth(1.5f);
                GL11.glEnable(2960);
                GL11.glClear(1024);
                GL11.glClearStencil(15);
                GL11.glStencilFunc(512, 1, 15);
                GL11.glStencilOp(7681, 7681, 7681);
                GL11.glPolygonMode(1028, 6913);
                GL11.glStencilFunc(512, 0, 15);
                GL11.glStencilOp(7681, 7681, 7681);
                GL11.glPolygonMode(1028, 6914);
                GL11.glStencilFunc(514, 1, 15);
                GL11.glStencilOp(7680, 7680, 7680);
                GL11.glPolygonMode(1028, 6913);
                GL11.glDisable(GL11.GL_DEPTH_TEST);
                GL11.glDepthMask(false);
                GL11.glEnable(10754);
                OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0f, 240.0f);
                GL11.glColor4f(1, 0, 0, 1);
                GL11.glColor4d(((float) invisibleColor.getRed() / 255), ((float) invisibleColor.getGreen() / 255), ((float) invisibleColor.getBlue() / 255), ((float) alpha / 255));
                this.mainModel.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
                GL11.glEnable(GL11.GL_DEPTH_TEST);
                GL11.glDepthMask(true);
                GL11.glColor4d(((float) visibleColor.getRed() / 255), ((float) visibleColor.getGreen() / 255), ((float) visibleColor.getBlue() / 255), ((float) alpha / 255));
                this.mainModel.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
                GL11.glEnable(3042);
                GL11.glEnable(2896);
                GL11.glEnable(3553);
                GL11.glEnable(3008);
                GL11.glPopAttrib();
            } else {
                this.mainModel.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
            }

            if (flag1) {
                GlStateManager.disableBlendProfile(GlStateManager.Profile.TRANSPARENT_MODEL);
            }
        }

    }

}
