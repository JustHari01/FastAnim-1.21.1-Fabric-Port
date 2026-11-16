package net.lunade.fastanim.mixin;

import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.client.render.entity.model.DrownedEntityModel;
import net.minecraft.client.render.entity.model.ZombieEntityModel;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.BipedEntityModel.ArmPose;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DrownedEntityModel.class)
public abstract class DrownedEntityModelMixin<T extends ZombieEntity> extends ZombieEntityModel<T> {
   public DrownedEntityModelMixin(ModelPart modelPart) {
      super(modelPart);
   }

   @Inject(at = @At("HEAD"), method = "setAngles(Lnet/minecraft/entity/mob/ZombieEntity;FFFFF)V", cancellable = true)
   public void setAngles(T zombieEntity, float f, float g, float h, float i, float j, CallbackInfo info) {
      info.cancel();
      super.setAngles(zombieEntity, f, g, h, i, j);
      if (this.leftArmPose == ArmPose.THROW_SPEAR) {
         this.leftArm.pitch = this.leftArm.pitch * 0.5F - (float) Math.PI;
         this.leftArm.yaw = 0.0F;
      }

      if (this.rightArmPose == ArmPose.THROW_SPEAR) {
         this.rightArm.pitch = this.rightArm.pitch * 0.5F - (float) Math.PI;
         this.rightArm.yaw = 0.0F;
      }

      if (this.leaningPitch > 0.0F) {
         float sin = (float)Math.sin(0.1F * h);
         float zeroThreeFiveSin = this.leaningPitch * 0.35F * sin;
         this.rightArm.pitch = this.lerpAngle(this.leaningPitch, this.rightArm.pitch, (float) (-Math.PI * 4.0 / 5.0)) + zeroThreeFiveSin;
         this.leftArm.pitch = this.lerpAngle(this.leaningPitch, this.leftArm.pitch, (float) (-Math.PI * 4.0 / 5.0)) - zeroThreeFiveSin;
         this.rightArm.roll = this.lerpAngle(this.leaningPitch, this.rightArm.roll, -0.15F);
         this.leftArm.roll = this.lerpAngle(this.leaningPitch, this.leftArm.roll, 0.15F);
         float legPitch = this.leaningPitch * 0.55F * sin;
         this.leftLeg.pitch -= legPitch;
         this.rightLeg.pitch += legPitch;
         this.head.pitch = 0.0F;
      }
   }
}
