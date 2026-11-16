package net.lunade.fastanim.mixin;

import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.util.math.EulerAngle;
import net.minecraft.client.render.entity.model.ArmorStandArmorEntityModel;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.model.ModelPart;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ArmorStandArmorEntityModel.class)
public abstract class ArmorStandArmorEntityModelMixin extends BipedEntityModel<ArmorStandEntity> {
   public ArmorStandArmorEntityModelMixin(ModelPart modelPart) {
      super(modelPart);
   }

   @Inject(at = @At("HEAD"), method = "setAngles", cancellable = true)
   public void setAngles(ArmorStandEntity armorStandEntity, float limbAngle, float limbDistance, float h, float i, float j, CallbackInfo info) {
      info.cancel();
      EulerAngle head = armorStandEntity.getHeadRotation();
      this.head.pitch = (float) (Math.PI / 180.0) * head.getPitch();
      this.head.yaw = (float) (Math.PI / 180.0) * head.getYaw();
      this.head.roll = (float) (Math.PI / 180.0) * head.getRoll();
      EulerAngle body = armorStandEntity.getBodyRotation();
      this.body.pitch = (float) (Math.PI / 180.0) * body.getPitch();
      this.body.yaw = (float) (Math.PI / 180.0) * body.getYaw();
      this.body.roll = (float) (Math.PI / 180.0) * body.getRoll();
      EulerAngle leftArm = armorStandEntity.getLeftArmRotation();
      this.leftArm.pitch = (float) (Math.PI / 180.0) * leftArm.getPitch();
      this.leftArm.yaw = (float) (Math.PI / 180.0) * leftArm.getYaw();
      this.leftArm.roll = (float) (Math.PI / 180.0) * leftArm.getRoll();
      EulerAngle rightArm = armorStandEntity.getRightArmRotation();
      this.rightArm.pitch = (float) (Math.PI / 180.0) * rightArm.getPitch();
      this.rightArm.yaw = (float) (Math.PI / 180.0) * rightArm.getYaw();
      this.rightArm.roll = (float) (Math.PI / 180.0) * rightArm.getRoll();
      EulerAngle leftLeg = armorStandEntity.getLeftLegRotation();
      this.leftLeg.pitch = (float) (Math.PI / 180.0) * leftLeg.getPitch();
      this.leftLeg.yaw = (float) (Math.PI / 180.0) * leftLeg.getYaw();
      this.leftLeg.roll = (float) (Math.PI / 180.0) * leftLeg.getRoll();
      EulerAngle rightLeg = armorStandEntity.getRightLegRotation();
      this.rightLeg.pitch = (float) (Math.PI / 180.0) * rightLeg.getPitch();
      this.rightLeg.yaw = (float) (Math.PI / 180.0) * rightLeg.getYaw();
      this.rightLeg.roll = (float) (Math.PI / 180.0) * rightLeg.getRoll();
      this.hat.copyTransform(this.head);
   }
}
