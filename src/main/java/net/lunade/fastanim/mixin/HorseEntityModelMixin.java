package net.lunade.fastanim.mixin;

import net.minecraft.entity.passive.AbstractHorseEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.client.render.entity.model.HorseEntityModel;
import net.minecraft.client.model.ModelPart;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HorseEntityModel.class)
public class HorseEntityModelMixin<T extends AbstractHorseEntity> {
   @Final
   @Shadow
   public ModelPart body;
   @Final
   @Shadow
   public ModelPart head;
   @Final
   @Shadow
   private ModelPart rightHindLeg;
   @Final
   @Shadow
   private ModelPart leftHindLeg;
   @Final
   @Shadow
   private ModelPart rightFrontLeg;
   @Final
   @Shadow
   private ModelPart leftFrontLeg;
   @Final
   @Shadow
   private ModelPart rightHindBabyLeg;
   @Final
   @Shadow
   private ModelPart leftHindBabyLeg;
   @Final
   @Shadow
   private ModelPart rightFrontBabyLeg;
   @Final
   @Shadow
   private ModelPart leftFrontBabyLeg;
   @Final
   @Shadow
   private ModelPart tail;

   @Inject(at = @At("HEAD"), method = "animateModel", cancellable = true)
   public void animateModel(T abstractHorseEntity, float limbAngle, float limbDistance, float h, CallbackInfo info) {
      info.cancel();
      float bodyYaw = MathHelper.lerpAngleDegrees(abstractHorseEntity.prevBodyYaw, abstractHorseEntity.bodyYaw, h);
      float headYaw = MathHelper.lerpAngleDegrees(abstractHorseEntity.prevHeadYaw, abstractHorseEntity.headYaw, h);
      float k = abstractHorseEntity.prevPitch + h * (abstractHorseEntity.getPitch() - abstractHorseEntity.prevPitch);
      float headMinusBodyYaws = headYaw - bodyYaw;
      float m = k * (float) (Math.PI / 180.0);
      if (headMinusBodyYaws > 20.0F) {
         headMinusBodyYaws = 20.0F;
      }

      if (headMinusBodyYaws < -20.0F) {
         headMinusBodyYaws = -20.0F;
      }

      if (limbDistance > 0.2F) {
         m += (float)Math.sin(limbAngle * 0.4F + (float) (Math.PI / 2)) * 0.15F * limbDistance;
      }

      float eatingProgress = abstractHorseEntity.getEatingGrassAnimationProgress(h);
      float angryProgress = abstractHorseEntity.getAngryAnimationProgress(h);
      float p = 1.0F - angryProgress;
      float q = abstractHorseEntity.getEatingAnimationProgress(h);
      boolean bl = abstractHorseEntity.tailWagTicks != 0;
      float age = abstractHorseEntity.age + h;
      this.body.pitch = 0.0F;
      this.head.pitch = (float) (Math.PI / 6) + m;
      float headMinBodYawsRadians = headMinusBodyYaws * (float) (Math.PI / 180.0);
      this.head.yaw = headMinBodYawsRadians;
      float s = abstractHorseEntity.isTouchingWater() ? 0.2F : 1.0F;
      float t = MathHelper.cos(s * limbAngle * 0.6662F + (float) Math.PI);
      float u = t * 0.8F * limbDistance;
      float oneMathMaxEatingProg = 1.0F - Math.max(angryProgress, eatingProgress);
      float sinAge = MathHelper.sin(age);
      this.head.pitch = angryProgress * ((float) (Math.PI / 12) + eatingProgress)
         + eatingProgress * (2.1816616F + sinAge * 0.05F)
         + oneMathMaxEatingProg * ((float) (Math.PI / 6) + m + q * sinAge * 0.05F);
      this.head.yaw = angryProgress * headMinBodYawsRadians + oneMathMaxEatingProg * this.head.yaw;
      float angry4 = angryProgress * -4.0F;
      this.head.pivotY = angry4 + eatingProgress * 11.0F + oneMathMaxEatingProg * 4.0F;
      this.head.pivotZ = angry4 + eatingProgress * -12.0F + oneMathMaxEatingProg * -12.0F;
      this.body.pitch = angryProgress * (float) (-Math.PI / 4) + p * this.body.pitch;
      float w = (float) (Math.PI / 12) * angryProgress;
      float x = (float)Math.cos(age * 0.6F + (float) Math.PI);
      this.leftFrontLeg.pivotY = 2.0F * angryProgress + 14.0F * p;
      this.leftFrontLeg.pivotZ = -6.0F * angryProgress - 10.0F * p;
      this.rightFrontLeg.pivotY = this.leftFrontLeg.pivotY;
      this.rightFrontLeg.pivotZ = this.leftFrontLeg.pivotZ;
      float up = u * p;
      float y = ((float) (-Math.PI / 3) + x) * angryProgress + up;
      float z = ((float) (-Math.PI / 3) - x) * angryProgress - up;
      float t5LimbDistanceP = t * 0.5F * limbDistance * p;
      this.leftHindLeg.pitch = w - t5LimbDistanceP;
      this.rightHindLeg.pitch = w + t5LimbDistanceP;
      this.leftFrontLeg.pitch = y;
      this.rightFrontLeg.pitch = z;
      this.tail.pitch = (float) (Math.PI / 6) + limbDistance * 0.75F;
      this.tail.pivotY = -5.0F + limbDistance;
      this.tail.pivotZ = 2.0F + limbDistance * 2.0F;
      this.tail.yaw = bl ? (float)Math.sin(age * 0.7F + (float) (Math.PI / 2)) : 0.0F;
      this.rightHindBabyLeg.pivotY = this.rightHindLeg.pivotY;
      this.rightHindBabyLeg.pivotZ = this.rightHindLeg.pivotZ;
      this.rightHindBabyLeg.pitch = this.rightHindLeg.pitch;
      this.leftHindBabyLeg.pivotY = this.leftHindLeg.pivotY;
      this.leftHindBabyLeg.pivotZ = this.leftHindLeg.pivotZ;
      this.leftHindBabyLeg.pitch = this.leftHindLeg.pitch;
      this.rightFrontBabyLeg.pivotY = this.rightFrontLeg.pivotY;
      this.rightFrontBabyLeg.pivotZ = this.rightFrontLeg.pivotZ;
      this.rightFrontBabyLeg.pitch = this.rightFrontLeg.pitch;
      this.leftFrontBabyLeg.pivotY = this.leftFrontLeg.pivotY;
      this.leftFrontBabyLeg.pivotZ = this.leftFrontLeg.pivotZ;
      this.leftFrontBabyLeg.pitch = this.leftFrontLeg.pitch;
      boolean bl2 = abstractHorseEntity.isBaby();
      this.rightHindLeg.visible = !bl2;
      this.leftHindLeg.visible = !bl2;
      this.rightFrontLeg.visible = !bl2;
      this.leftFrontLeg.visible = !bl2;
      this.rightHindBabyLeg.visible = bl2;
      this.leftHindBabyLeg.visible = bl2;
      this.rightFrontBabyLeg.visible = bl2;
      this.leftFrontBabyLeg.visible = bl2;
      this.body.pivotY = bl2 ? 10.8F : 0.0F;
   }
}
