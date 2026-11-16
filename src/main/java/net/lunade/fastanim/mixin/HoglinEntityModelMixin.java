package net.lunade.fastanim.mixin;

import net.minecraft.entity.mob.MobEntity;
import net.minecraft.client.render.entity.model.HoglinEntityModel;
import net.minecraft.entity.mob.Hoglin;
import net.minecraft.client.model.ModelPart;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HoglinEntityModel.class)
public class HoglinEntityModelMixin<T extends MobEntity & Hoglin> {
   @Final
   @Shadow
   private ModelPart head;
   @Final
   @Shadow
   private ModelPart rightEar;
   @Final
   @Shadow
   private ModelPart leftEar;
   @Final
   @Shadow
   private ModelPart rightFrontLeg;
   @Final
   @Shadow
   private ModelPart leftFrontLeg;
   @Final
   @Shadow
   private ModelPart rightHindLeg;
   @Final
   @Shadow
   private ModelPart leftHindLeg;
   @Final
   @Shadow
   private ModelPart mane;

   @Inject(at = @At("HEAD"), method = "setAngles(Lnet/minecraft/entity/mob/MobEntity;FFFFF)V", cancellable = true)
   public void setAngles(T mobEntity, float f, float g, float h, float i, float j, CallbackInfo info) {
      info.cancel();
      float distanceBySinAngle = g * (float)Math.sin(f);
      this.rightEar.roll = (float) (-Math.PI * 2.0 / 9.0) - distanceBySinAngle;
      this.leftEar.roll = (float) (Math.PI * 2.0 / 9.0) + distanceBySinAngle;
      this.head.yaw = i * (float) (Math.PI / 180.0);
      float l = 1.0F - Math.abs(10 - 2 * mobEntity.getMovementCooldownTicks()) * 0.1F;
      this.head.pitch = 0.87266463F + l * -1.2217305F;
      if (mobEntity.isBaby()) {
         this.head.pivotY = 2.0F + l * 3.0F;
         this.mane.pivotZ = -3.0F;
      } else {
         this.head.pivotY = 2.0F;
         this.mane.pivotZ = -7.0F;
      }

      float onePointTwoLimb = 1.2F * g;
      float firstAngle = (float)Math.sin(f + (float) (Math.PI / 2)) * onePointTwoLimb;
      float secondAngle = (float)Math.sin(f + (float) Math.PI + (float) (Math.PI / 2)) * onePointTwoLimb;
      this.rightFrontLeg.pitch = firstAngle;
      this.leftHindLeg.pitch = firstAngle;
      this.rightHindLeg.pitch = secondAngle;
      this.leftFrontLeg.pitch = secondAngle;
   }
}
