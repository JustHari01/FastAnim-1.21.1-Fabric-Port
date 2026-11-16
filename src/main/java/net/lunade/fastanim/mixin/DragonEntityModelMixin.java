package net.lunade.fastanim.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.model.ModelPart;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.client.render.entity.EnderDragonEntityRenderer.DragonEntityModel;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DragonEntityModel.class)
public class DragonEntityModelMixin<T extends Entity> {
   @Final
   @Shadow
   private ModelPart head;
   @Final
   @Shadow
   private ModelPart neck;
   @Final
   @Shadow
   private ModelPart jaw;
   @Final
   @Shadow
   private ModelPart body;
   @Final
   @Shadow
   private ModelPart leftWing;
   @Final
   @Shadow
   private ModelPart leftWingTip;
   @Final
   @Shadow
   private ModelPart leftFrontLeg;
   @Final
   @Shadow
   private ModelPart leftFrontLegTip;
   @Final
   @Shadow
   private ModelPart leftFrontFoot;
   @Final
   @Shadow
   private ModelPart leftHindLeg;
   @Final
   @Shadow
   private ModelPart leftHindLegTip;
   @Final
   @Shadow
   private ModelPart leftHindFoot;
   @Final
   @Shadow
   private ModelPart rightWing;
   @Final
   @Shadow
   private ModelPart rightWingTip;
   @Final
   @Shadow
   private ModelPart rightFrontLeg;
   @Final
   @Shadow
   private ModelPart rightFrontLegTip;
   @Final
   @Shadow
   private ModelPart rightFrontFoot;
   @Final
   @Shadow
   private ModelPart rightHindLeg;
   @Final
   @Shadow
   private ModelPart rightHindLegTip;
   @Final
   @Shadow
   private ModelPart rightHindFoot;
   @Shadow
   @Nullable
   private EnderDragonEntity dragon;
   @Shadow
   private float tickDelta;

   @Inject(at = @At("HEAD"), method = "render", cancellable = true)
   public void render(MatrixStack matrixStack, VertexConsumer vertexConsumer, int i, int j, int color, CallbackInfo info) {
      info.cancel();
      matrixStack.push();
      float l = MathHelper.lerp(this.tickDelta, this.dragon.prevWingPosition, this.dragon.wingPosition);
      float lPi2 = l * (float) (Math.PI * 2);
      this.jaw.pitch = (float)(Math.sin(lPi2) + 1.0) * 0.2F;
      float m = (float)(Math.sin(lPi2 - 1.0F) + 1.0);
      m = (m * m + m * 2.0F) * 0.05F;
      matrixStack.translate(0.0, m - 2.0F, -3.0);
      matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(m * 2.0F));
      float n = 0.0F;
      float o = 20.0F;
      float p = -12.0F;
      double[] ds = this.dragon.getSegmentProperties(6, this.tickDelta);
      float r = (float)MathHelper.wrapDegrees(this.dragon.getSegmentProperties(5, this.tickDelta)[0] - this.dragon.getSegmentProperties(10, this.tickDelta)[0]);
      float s = (float)MathHelper.wrapDegrees(this.dragon.getSegmentProperties(5, this.tickDelta)[0] + r / 2.0F);

      for (int u = 0; u < 5; u++) {
         double[] es = this.dragon.getSegmentProperties(5 - u, this.tickDelta);
         float v = (float)Math.sin(u * 0.45F + lPi2 + (float) (Math.PI / 2)) * 0.15F;
         this.neck.yaw = (float)(MathHelper.wrapDegrees(es[0] - ds[0]) * 0.026179938F);
         this.neck.pitch = v + this.dragon.getChangeInNeckPitch(u, ds, es) * 0.13089968F;
         this.neck.roll = (float)(-MathHelper.wrapDegrees(es[0] - s) * 0.13089968F);
         this.neck.pivotY = o;
         this.neck.pivotZ = p;
         this.neck.pivotX = n;
         o = (float)(o + Math.sin(this.neck.pitch) * 10.0);
         float cosNeckPitch = (float)Math.cos(this.neck.pitch) * 10.0F;
         p = (float)(p - Math.sin(this.neck.yaw + (float) (Math.PI / 2)) * cosNeckPitch);
         n = (float)(n - Math.sin(this.neck.yaw) * cosNeckPitch);
         this.neck.render(matrixStack, vertexConsumer, i, j, color);
      }

      this.head.pivotY = o;
      this.head.pivotZ = p;
      this.head.pivotX = n;
      double[] fs = this.dragon.getSegmentProperties(0, this.tickDelta);
      this.head.yaw = (float)(MathHelper.wrapDegrees(fs[0] - ds[0]) * (float) (Math.PI / 180.0));
      this.head.pitch = MathHelper.wrapDegrees(this.dragon.getChangeInNeckPitch(6, ds, fs)) * 0.13089968F;
      this.head.roll = (float)(-MathHelper.wrapDegrees(fs[0] - s) * (float) (Math.PI / 180.0));
      this.head.render(matrixStack, vertexConsumer, i, j, color);
      matrixStack.push();
      matrixStack.translate(0.0, 1.0, 0.0);
      matrixStack.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(-r * 1.5F));
      matrixStack.translate(0.0, -1.0, 0.0);
      this.body.roll = 0.0F;
      this.body.render(matrixStack, vertexConsumer, i, j, color);
      this.leftWing.pitch = 0.125F - (float)Math.cos(lPi2) * 0.2F;
      this.leftWing.yaw = -0.25F;
      this.leftWing.roll = -((float)(Math.sin(lPi2) + 0.125)) * 0.8F;
      this.leftWingTip.roll = (float)(Math.sin(lPi2 + 2.0F) + 0.5) * 0.75F;
      this.rightWing.pitch = this.leftWing.pitch;
      this.rightWing.yaw = -this.leftWing.yaw;
      this.rightWing.roll = -this.leftWing.roll;
      this.rightWingTip.roll = -this.leftWingTip.roll;
      this.renderLimbs(
         matrixStack,
         vertexConsumer,
         i,
         j,
         m,
         this.leftWing,
         this.leftFrontLeg,
         this.leftFrontLegTip,
         this.leftFrontFoot,
         this.leftHindLeg,
         this.leftHindLegTip,
         this.leftHindFoot,
         color
      );
      this.renderLimbs(
         matrixStack,
         vertexConsumer,
         i,
         j,
         m,
         this.rightWing,
         this.rightFrontLeg,
         this.rightFrontLegTip,
         this.rightFrontFoot,
         this.rightHindLeg,
         this.rightHindLegTip,
         this.rightHindFoot,
         color
      );
      matrixStack.pop();
      o = 10.0F;
      p = 60.0F;
      n = 0.0F;
      ds = this.dragon.getSegmentProperties(11, this.tickDelta);

      for (int x = 0; x < 12; x++) {
         fs = this.dragon.getSegmentProperties(12 + x, this.tickDelta);
         this.neck.yaw = (float)((MathHelper.wrapDegrees(fs[0] - ds[0]) * 1.5 + 180.0) * (float) (Math.PI / 180.0));
         this.neck.pitch = (float)(Math.sin(x * 0.45F + lPi2) * 0.05F + (float)(fs[1] - ds[1]) * 0.13089968F);
         this.neck.roll = (float)(MathHelper.wrapDegrees(fs[0] - s) * (float) (Math.PI / 180.0) * 1.5);
         this.neck.pivotY = o;
         this.neck.pivotZ = p;
         this.neck.pivotX = n;
         o += (float)Math.sin(this.neck.pitch) * 10.0F;
         float cosNeckPitch = (float)(Math.sin(this.neck.pitch + (float) (Math.PI / 2)) * 10.0);
         p -= (float)Math.sin(this.neck.yaw + (float) (Math.PI / 2)) * cosNeckPitch;
         n -= (float)Math.sin(this.neck.yaw) * cosNeckPitch;
         this.neck.render(matrixStack, vertexConsumer, i, j, color);
      }

      matrixStack.pop();
   }

   @Shadow
   private void renderLimbs(
      MatrixStack matrixStack,
      VertexConsumer vertexConsumer,
      int i,
      int j,
      float f,
      ModelPart modelPart,
      ModelPart modelPart2,
      ModelPart modelPart3,
      ModelPart modelPart4,
      ModelPart modelPart5,
      ModelPart modelPart6,
      ModelPart modelPart7,
      int color
   ) {
   }

   @Inject(at = @At("HEAD"), method = "renderLimbs", cancellable = true)
   private void fastAnim$renderLimbs(
      MatrixStack matrixStack,
      VertexConsumer vertexConsumer,
      int light,
      int overlay,
      float offset,
      ModelPart wing,
      ModelPart frontLeg,
      ModelPart frontLegTip,
      ModelPart frontFoot,
      ModelPart hindLeg,
      ModelPart hindLegTip,
      ModelPart hindFoot,
      int color,
      CallbackInfo info
   ) {
      info.cancel();
      float limbOffset = offset * 0.1F;
      hindLeg.pitch = 1.0F + limbOffset;
      hindLegTip.pitch = 0.5F + limbOffset;
      hindFoot.pitch = 0.75F + limbOffset;
      frontLeg.pitch = 1.3F + limbOffset;
      frontLegTip.pitch = -0.5F - limbOffset;
      frontFoot.pitch = 0.75F + limbOffset;
      wing.render(matrixStack, vertexConsumer, light, overlay, color);
      frontLeg.render(matrixStack, vertexConsumer, light, overlay, color);
      hindLeg.render(matrixStack, vertexConsumer, light, overlay, color);
   }
}
