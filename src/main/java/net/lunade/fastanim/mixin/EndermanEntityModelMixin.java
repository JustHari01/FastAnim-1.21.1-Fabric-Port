package net.lunade.fastanim.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.client.render.entity.model.EndermanEntityModel;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.model.ModelPart;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EndermanEntityModel.class)
public abstract class EndermanEntityModelMixin<T extends LivingEntity> extends BipedEntityModel<LivingEntity> {
   @Shadow
   public boolean carryingBlock;
   @Shadow
   public boolean angry;

   public EndermanEntityModelMixin(ModelPart modelPart) {
      super(modelPart);
   }

   @Inject(at = @At("HEAD"), method = "setAngles", cancellable = true)
   public void setAngles(LivingEntity livingEntity, float limbAngle, float limbDistance, float h, float i, float j, CallbackInfo info) {
      info.cancel();
      super.setAngles(livingEntity, limbAngle, limbDistance, h, i, j);
      this.body.pitch = 0.0F;
      this.body.pivotY = -14.0F;
      this.body.pivotZ = -0.0F;
      this.rightArm.pitch *= 0.5F;
      this.leftArm.pitch *= 0.5F;
      this.rightLeg.pitch *= 0.5F;
      this.leftLeg.pitch *= 0.5F;
      if (this.rightArm.pitch > 0.4F) {
         this.rightArm.pitch = 0.4F;
      }

      if (this.leftArm.pitch > 0.4F) {
         this.leftArm.pitch = 0.4F;
      }

      if (this.rightArm.pitch < -0.4F) {
         this.rightArm.pitch = -0.4F;
      }

      if (this.leftArm.pitch < -0.4F) {
         this.leftArm.pitch = -0.4F;
      }

      if (this.rightLeg.pitch > 0.4F) {
         this.rightLeg.pitch = 0.4F;
      }

      if (this.leftLeg.pitch > 0.4F) {
         this.leftLeg.pitch = 0.4F;
      }

      if (this.rightLeg.pitch < -0.4F) {
         this.rightLeg.pitch = -0.4F;
      }

      if (this.leftLeg.pitch < -0.4F) {
         this.leftLeg.pitch = -0.4F;
      }

      if (this.carryingBlock) {
         this.rightArm.pitch = -0.5F;
         this.leftArm.pitch = -0.5F;
         this.rightArm.roll = 0.05F;
         this.leftArm.roll = -0.05F;
      }

      this.rightLeg.pivotZ = 0.0F;
      this.leftLeg.pivotZ = 0.0F;
      this.rightLeg.pivotY = -5.0F;
      this.leftLeg.pivotY = -5.0F;
      this.head.pivotZ = -0.0F;
      this.head.pivotY = -13.0F;
      this.hat.pivotX = this.head.pivotX;
      this.hat.pivotY = this.head.pivotY;
      this.hat.pivotZ = this.head.pivotZ;
      this.hat.pitch = this.head.pitch;
      this.hat.yaw = this.head.yaw;
      this.hat.roll = this.head.roll;
      if (this.angry) {
         this.head.pivotY -= 5.0F;
      }

      this.rightArm.setPivot(-5.0F, -12.0F, 0.0F);
      this.leftArm.setPivot(5.0F, -12.0F, 0.0F);
   }
}
