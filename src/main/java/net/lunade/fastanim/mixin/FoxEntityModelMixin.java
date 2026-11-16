package net.lunade.fastanim.mixin;

import net.minecraft.entity.passive.FoxEntity;
import net.minecraft.client.render.entity.model.FoxEntityModel;
import net.minecraft.client.render.entity.model.AnimalModel;
import net.minecraft.client.model.ModelPart;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FoxEntityModel.class)
public abstract class FoxEntityModelMixin<T extends FoxEntity> extends AnimalModel<T> {
   @Final
   @Shadow
   public ModelPart head;
   @Final
   @Shadow
   private ModelPart body;
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
   private ModelPart tail;
   @Shadow
   private float legPitchModifier;

   @Inject(at = @At("HEAD"), method = "animateModel", cancellable = true)
   public void animateModel(T foxEntity, float f, float g, float h, CallbackInfo info) {
      info.cancel();
      boolean sleeping = foxEntity.isSleeping();
      boolean sitting = foxEntity.isSitting();
      float tailPitch = -0.05235988F;
      float headPivotX = -1.0F;
      float headPivotY = 16.5F;
      float headPivotZ = -3.0F;
      float headYaw = 0.0F;
      this.rightHindLeg.visible = !sleeping;
      this.leftHindLeg.visible = !sleeping;
      this.rightFrontLeg.visible = !sleeping;
      this.leftFrontLeg.visible = !sleeping;
      float bodyPitch = (float) (Math.PI / 2);
      float bodyPivotY = 16.0F;
      float bodyPivotZ = -6.0F;
      float bodyRoll = 0.0F;
      float rightPivotY = 17.5F;
      float rightPivotZ = 7.0F;
      float leftPivotY = 17.5F;
      float leftPivotZ = 7.0F;
      if (foxEntity.isInSneakingPose()) {
         bodyPitch = 1.6755161F;
         float i = foxEntity.getBodyRotationHeightOffset(h);
         headPivotY = 16.5F + i;
         bodyPivotY = 16.0F + i;
      } else if (sleeping) {
         bodyRoll = (float) (-Math.PI / 2);
         tailPitch = (float) (-Math.PI * 5.0 / 6.0);
         bodyPivotY = 21.0F;
         if (this.child) {
            tailPitch = -2.1816616F;
            bodyPivotZ = -2.0F;
         }

         headPivotX = 1.0F;
         headPivotY = 19.49F;
         this.head.pitch = 0.0F;
         headYaw = (float) (-Math.PI * 2.0 / 3.0);
         this.head.roll = 0.0F;
      } else if (sitting) {
         bodyPitch = (float) (Math.PI / 6);
         bodyPivotY = 9.0F;
         bodyPivotZ = -3.0F;
         tailPitch = (float) (Math.PI / 4);
         this.tail.setPivot(-4.0F, 15.0F, -2.0F);
         this.head.pitch = 0.0F;
         if (this.child) {
            headPivotY = 13.0F;
            headPivotZ = -3.75F;
         } else {
            headPivotY = 10.0F;
            headPivotZ = -0.25F;
         }

         rightPivotY = 21.5F;
         rightPivotZ = 6.75F;
         leftPivotY = 21.5F;
         leftPivotZ = 6.75F;
      }

      if (sitting) {
         this.rightHindLeg.pitch = (float) (-Math.PI * 5.0 / 12.0);
         this.leftHindLeg.pitch = (float) (-Math.PI * 5.0 / 12.0);
         this.rightFrontLeg.pitch = (float) (-Math.PI / 12);
         this.leftFrontLeg.pitch = (float) (-Math.PI / 12);
      } else if (!sleeping && !foxEntity.isWalking()) {
         float fastLimbAngle = f * 0.6662F;
         float fastLimbDistance = 1.4F * g;
         float firstAngle = (float)Math.sin(fastLimbAngle + (float) (Math.PI / 2)) * fastLimbDistance;
         float secondAngle = (float)Math.sin(fastLimbAngle + (float) Math.PI + (float) (Math.PI / 2)) * fastLimbDistance;
         this.rightHindLeg.pitch = firstAngle;
         this.leftHindLeg.pitch = secondAngle;
         this.rightFrontLeg.pitch = secondAngle;
         this.leftFrontLeg.pitch = firstAngle;
      }

      this.body.pitch = bodyPitch;
      this.body.roll = bodyRoll;
      this.body.setPivot(0.0F, bodyPivotY, bodyPivotZ);
      this.tail.pitch = tailPitch;
      this.head.setPivot(headPivotX, headPivotY, headPivotZ);
      this.head.yaw = headYaw;
      this.head.roll = sleeping ? 0.0F : foxEntity.getHeadRoll(h);
      this.rightHindLeg.setPivot(-5.0F, rightPivotY, rightPivotZ);
      this.leftHindLeg.setPivot(-1.0F, leftPivotY, leftPivotZ);
   }

   @Inject(at = @At("HEAD"), method = "setAngles", cancellable = true)
   public void setAngles(T foxEntity, float f, float g, float h, float i, float j, CallbackInfo info) {
      info.cancel();
      boolean sleeping = foxEntity.isSleeping();
      boolean sneaking = foxEntity.isInSneakingPose();
      boolean walking = foxEntity.isWalking();
      if (!sleeping && !walking && !sneaking) {
         this.head.pitch = j * (float) (Math.PI / 180.0);
         this.head.yaw = i * (float) (Math.PI / 180.0);
      }

      if (sleeping) {
         this.head.pitch = 0.0F;
         this.head.yaw = (float) (-Math.PI * 2.0 / 3.0);
         this.head.roll = (float)Math.sin(h * 0.027F + (float) (Math.PI / 2)) * 0.04555F;
      }

      if (sneaking) {
         float k = (float)Math.sin(h + (float) (Math.PI / 2)) * 0.01F;
         float k2 = k * 0.5F;
         this.body.yaw = k;
         this.rightHindLeg.roll = k;
         this.leftHindLeg.roll = k;
         this.rightFrontLeg.roll = k2;
         this.leftFrontLeg.roll = k2;
      }

      if (walking) {
         this.legPitchModifier += 0.67F;
         float fastPitch = this.legPitchModifier * 0.4662F;
         float pitchOne = (float)Math.sin(fastPitch + (float) (Math.PI / 2)) * 0.1F;
         float pitchTwo = (float)Math.sin(fastPitch + (float) Math.PI + (float) (Math.PI / 2)) * 0.1F;
         this.rightHindLeg.pitch = pitchOne;
         this.leftHindLeg.pitch = pitchTwo;
         this.rightFrontLeg.pitch = pitchTwo;
         this.leftFrontLeg.pitch = pitchOne;
      }
   }
}
