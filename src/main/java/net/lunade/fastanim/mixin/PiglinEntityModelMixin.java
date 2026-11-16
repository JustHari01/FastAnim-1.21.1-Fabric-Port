package net.lunade.fastanim.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PiglinActivity;
import net.minecraft.client.render.entity.model.PiglinEntityModel;
import net.minecraft.client.render.entity.model.CrossbowPosing;
import net.minecraft.entity.mob.AbstractPiglinEntity;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.model.ModelPart;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = PiglinEntityModel.class, priority = 900)
public abstract class PiglinEntityModelMixin<T extends MobEntity> extends PlayerEntityModel<T> {
   @Final
   @Shadow
   public ModelPart rightEar;
   @Final
   @Shadow
   private ModelPart leftEar;
   @Final
   @Shadow
   private ModelTransform bodyRotation;
   @Final
   @Shadow
   private ModelTransform headRotation;
   @Final
   @Shadow
   private ModelTransform leftArmRotation;
   @Final
   @Shadow
   private ModelTransform rightArmRotation;

   public PiglinEntityModelMixin(ModelPart modelPart, boolean bl) {
      super(modelPart, bl);
   }

   @Inject(at = @At("HEAD"), method = "setAngles*", cancellable = true)
   public void setAngles(T mobEntity, float limbAngle, float limbDistance, float h, float i, float j, CallbackInfo info) {
      info.cancel();
      this.body.setTransform(this.bodyRotation);
      this.head.setTransform(this.headRotation);
      this.leftArm.setTransform(this.leftArmRotation);
      this.rightArm.setTransform(this.rightArmRotation);
      super.setAngles(mobEntity, limbAngle, limbDistance, h, i, j);
      float l = h * 0.1F + limbAngle * 0.5F;
      float m = 0.08F + limbDistance * 0.4F;
      this.leftEar.roll = (float) (-Math.PI / 6) - (float)Math.sin(l * 1.2F + (float) (Math.PI / 2)) * m;
      this.rightEar.roll = (float) (Math.PI / 6) + (float)Math.sin(l + (float) (Math.PI / 2)) * m;
      if (mobEntity instanceof AbstractPiglinEntity abstractPiglinEntity) {
         PiglinActivity piglinActivity = abstractPiglinEntity.getActivity();
         if (piglinActivity == PiglinActivity.DANCING) {
            float n = h * 0.016666F;
            float n30 = n * 30.0F;
            this.rightEar.roll = (float) (Math.PI / 6) + (float) (Math.PI / 180.0) * (float)Math.sin(n30) * 10.0F;
            this.leftEar.roll = (float) (-Math.PI / 6) - (float) (Math.PI / 180.0) * (float)Math.sin(n30 + (float) (Math.PI / 2)) * 10.0F;
            this.head.pivotX = (float)Math.sin(n * 10.0F);
            float n40 = n * 40.0F;
            float sin = (float)Math.sin(n40);
            this.head.pivotY = sin + 0.4F;
            this.rightArm.roll = (float) (Math.PI / 180.0) * (70.0F + (float)Math.sin(n40 + (float) (Math.PI / 2)) * 10.0F);
            this.leftArm.roll = this.rightArm.roll * -1.0F;
            float armPivot = sin * 0.5F + 1.5F;
            this.rightArm.pivotY = armPivot;
            this.leftArm.pivotY = armPivot;
            this.body.pivotY = sin * 0.35F;
         } else if (piglinActivity == PiglinActivity.ATTACKING_WITH_MELEE_WEAPON && this.handSwingProgress == 0.0F) {
            this.rotateMainArm(mobEntity);
         } else if (piglinActivity == PiglinActivity.CROSSBOW_HOLD) {
            CrossbowPosing.hold(this.rightArm, this.leftArm, this.head, !mobEntity.isLeftHanded());
         } else if (piglinActivity == PiglinActivity.CROSSBOW_CHARGE) {
            CrossbowPosing.charge(this.rightArm, this.leftArm, mobEntity, !mobEntity.isLeftHanded());
         } else if (piglinActivity == PiglinActivity.ADMIRING_ITEM) {
            this.head.pitch = 0.5F;
            this.head.yaw = 0.0F;
            if (mobEntity.isLeftHanded()) {
               this.rightArm.yaw = -0.5F;
               this.rightArm.pitch = -0.9F;
            } else {
               this.leftArm.yaw = 0.5F;
               this.leftArm.pitch = -0.9F;
            }
         }
      } else if (mobEntity.getType() == EntityType.ZOMBIFIED_PIGLIN) {
         CrossbowPosing.meleeAttack(this.leftArm, this.rightArm, mobEntity.isAttacking(), this.handSwingProgress, h);
      }

      this.leftPants.copyTransform(this.leftLeg);
      this.rightPants.copyTransform(this.rightLeg);
      this.leftSleeve.copyTransform(this.leftArm);
      this.rightSleeve.copyTransform(this.rightArm);
      this.jacket.copyTransform(this.body);
      this.hat.copyTransform(this.head);
   }

   @Shadow
   public void animateArms(T mobEntity, float f) {
   }

   @Shadow
   private void rotateMainArm(T mobEntity) {
   }
}
