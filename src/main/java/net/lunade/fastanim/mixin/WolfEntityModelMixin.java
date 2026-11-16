package net.lunade.fastanim.mixin;

import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.client.render.entity.model.WolfEntityModel;
import net.minecraft.client.model.ModelPart;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = WolfEntityModel.class, priority = 900)
public class WolfEntityModelMixin<T extends WolfEntity> {
   @Final
   @Shadow
   private ModelPart head;
   @Final
   @Shadow
   private ModelPart realHead;
   @Final
   @Shadow
   private ModelPart torso;
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
   @Final
   @Shadow
   private ModelPart realTail;
   @Final
   @Shadow
   private ModelPart neck;

   @Inject(at = @At("HEAD"), method = "animateModel*", cancellable = true)
   public void animateModel(T wolfEntity, float f, float g, float h, CallbackInfo info) {
      info.cancel();
      float angleOne = 0.0F;
      float angleTwo = 0.0F;
      boolean hasAnger = wolfEntity.hasAngerTime();
      if (!hasAnger) {
         float fastAngle = f * 0.6662F;
         float fastDistance = 1.4F * g;
         angleOne = (float)Math.sin(fastAngle + (float) (Math.PI / 2)) * fastDistance;
         angleTwo = (float)Math.sin(fastAngle + (float) Math.PI + (float) (Math.PI / 2)) * fastDistance;
      }

      this.tail.yaw = angleOne;
      if (wolfEntity.isInSittingPose()) {
         this.neck.setPivot(-1.0F, 16.0F, -3.0F);
         this.neck.pitch = (float) (Math.PI * 2.0 / 5.0);
         this.neck.yaw = 0.0F;
         this.torso.setPivot(0.0F, 18.0F, 0.0F);
         this.torso.pitch = (float) (Math.PI / 4);
         this.tail.setPivot(-1.0F, 21.0F, 6.0F);
         this.rightHindLeg.setPivot(-2.5F, 22.7F, 2.0F);
         this.rightHindLeg.pitch = (float) (Math.PI * 3.0 / 2.0);
         this.leftHindLeg.setPivot(0.5F, 22.7F, 2.0F);
         this.leftHindLeg.pitch = (float) (Math.PI * 3.0 / 2.0);
         this.rightFrontLeg.pitch = 5.811947F;
         this.rightFrontLeg.setPivot(-2.49F, 17.0F, -4.0F);
         this.leftFrontLeg.pitch = 5.811947F;
         this.leftFrontLeg.setPivot(0.51F, 17.0F, -4.0F);
      } else {
         this.torso.setPivot(0.0F, 14.0F, 2.0F);
         this.torso.pitch = (float) (Math.PI / 2);
         this.neck.setPivot(-1.0F, 14.0F, -3.0F);
         this.neck.pitch = this.torso.pitch;
         this.tail.setPivot(-1.0F, 12.0F, 8.0F);
         this.rightHindLeg.setPivot(-2.5F, 16.0F, 7.0F);
         this.leftHindLeg.setPivot(0.5F, 16.0F, 7.0F);
         this.rightFrontLeg.setPivot(-2.5F, 16.0F, -4.0F);
         this.leftFrontLeg.setPivot(0.5F, 16.0F, -4.0F);
         if (hasAnger) {
            float fastAngle = f * 0.6662F;
            float fastDistance = 1.4F * g;
            angleOne = (float)Math.sin(fastAngle + (float) (Math.PI / 2)) * fastDistance;
            angleTwo = (float)Math.sin(fastAngle + (float) Math.PI + (float) (Math.PI / 2)) * fastDistance;
         }

         this.rightHindLeg.pitch = angleOne;
         this.leftHindLeg.pitch = angleTwo;
         this.rightFrontLeg.pitch = angleTwo;
         this.leftFrontLeg.pitch = angleOne;
      }

      this.realHead.roll = wolfEntity.getBegAnimationProgress(h) + wolfEntity.getShakeAnimationProgress(h, 0.0F);
      this.neck.roll = wolfEntity.getShakeAnimationProgress(h, -0.08F);
      this.torso.roll = wolfEntity.getShakeAnimationProgress(h, -0.16F);
      this.realTail.roll = wolfEntity.getShakeAnimationProgress(h, -0.2F);
   }
}
