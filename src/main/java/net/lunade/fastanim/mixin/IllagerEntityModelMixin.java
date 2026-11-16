package net.lunade.fastanim.mixin;

import net.minecraft.util.Arm;
import net.minecraft.entity.mob.IllagerEntity;
import net.minecraft.client.render.entity.model.ModelWithArms;
import net.minecraft.client.render.entity.model.ModelWithHead;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.render.entity.model.CrossbowPosing;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.render.entity.model.IllagerEntityModel;
import net.minecraft.client.model.ModelPart;
import net.minecraft.entity.mob.IllagerEntity.State;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(IllagerEntityModel.class)
public abstract class IllagerEntityModelMixin<T extends IllagerEntity> extends SinglePartEntityModel<T> implements ModelWithArms, ModelWithHead {
   @Final
   @Shadow
   private ModelPart root;
   @Final
   @Shadow
   private ModelPart head;
   @Final
   @Shadow
   private ModelPart hat;
   @Final
   @Shadow
   private ModelPart arms;
   @Final
   @Shadow
   private ModelPart leftLeg;
   @Final
   @Shadow
   private ModelPart rightLeg;
   @Final
   @Shadow
   private ModelPart rightArm;
   @Final
   @Shadow
   private ModelPart leftArm;

   @Inject(at = @At("HEAD"), method = "setAngles", cancellable = true)
   public void setAngles(T illagerEntity, float f, float g, float h, float i, float j, CallbackInfo info) {
      info.cancel();
      this.head.yaw = i * (float) (Math.PI / 180.0);
      this.head.pitch = j * (float) (Math.PI / 180.0);
      if (this.riding) {
         this.rightArm.pitch = (float) (-Math.PI / 5);
         this.rightArm.yaw = 0.0F;
         this.rightArm.roll = 0.0F;
         this.leftArm.pitch = (float) (-Math.PI / 5);
         this.leftArm.yaw = 0.0F;
         this.leftArm.roll = 0.0F;
         this.rightLeg.pitch = -1.4137167F;
         this.rightLeg.yaw = (float) (Math.PI / 10);
         this.rightLeg.roll = 0.07853982F;
         this.leftLeg.pitch = -1.4137167F;
         this.leftLeg.yaw = (float) (-Math.PI / 10);
         this.leftLeg.roll = -0.07853982F;
      } else {
         float fastAngle = f * 0.6662F;
         float oneFourG = 0.7F * g;
         float cosOne = (float)Math.sin(fastAngle + (float) (Math.PI / 2));
         float cosTwo = (float)Math.sin(fastAngle + (float) Math.PI + (float) (Math.PI / 2));
         this.rightLeg.pitch = cosOne * oneFourG;
         this.leftArm.pitch = cosOne;
         this.rightArm.pitch = cosTwo;
         this.leftLeg.pitch = cosTwo * oneFourG;
         this.rightArm.yaw = 0.0F;
         this.rightArm.roll = 0.0F;
         this.leftArm.yaw = 0.0F;
         this.leftArm.roll = 0.0F;
         this.rightLeg.yaw = 0.0F;
         this.rightLeg.roll = 0.0F;
         this.leftLeg.yaw = 0.0F;
         this.leftLeg.roll = 0.0F;
      }

      State state = illagerEntity.getState();
      if (state == State.ATTACKING) {
         if (illagerEntity.getMainHandStack().isEmpty()) {
            CrossbowPosing.meleeAttack(this.leftArm, this.rightArm, true, this.handSwingProgress, h);
         } else {
            CrossbowPosing.meleeAttack(this.rightArm, this.leftArm, illagerEntity, this.handSwingProgress, h);
         }
      } else if (state == State.SPELLCASTING) {
         this.rightArm.pivotZ = 0.0F;
         this.rightArm.pivotX = -5.0F;
         this.leftArm.pivotZ = 0.0F;
         this.leftArm.pivotX = 5.0F;
         float cos = (float)Math.sin(h * 0.6662F + (float) (Math.PI / 2)) * 0.25F;
         this.rightArm.pitch = cos;
         this.leftArm.pitch = cos;
         this.rightArm.roll = (float) (Math.PI * 3.0 / 4.0);
         this.leftArm.roll = (float) (-Math.PI * 3.0 / 4.0);
         this.rightArm.yaw = 0.0F;
         this.leftArm.yaw = 0.0F;
      } else if (state == State.BOW_AND_ARROW) {
         this.rightArm.yaw = -0.1F + this.head.yaw;
         this.rightArm.pitch = (float) (-Math.PI / 2) + this.head.pitch;
         this.leftArm.pitch = -0.9424779F + this.head.pitch;
         this.leftArm.yaw = this.head.yaw - 0.4F;
         this.leftArm.roll = (float) (Math.PI / 2);
      } else if (state == State.CROSSBOW_HOLD) {
         CrossbowPosing.hold(this.rightArm, this.leftArm, this.head, true);
      } else if (state == State.CROSSBOW_CHARGE) {
         CrossbowPosing.charge(this.rightArm, this.leftArm, illagerEntity, true);
      } else if (state == State.CELEBRATING) {
         this.rightArm.pivotZ = 0.0F;
         this.rightArm.pivotX = -5.0F;
         float cos = (float)Math.sin(h * 0.6662F + (float) (Math.PI / 2)) * 0.05F;
         this.rightArm.pitch = cos;
         this.rightArm.roll = 2.670354F;
         this.rightArm.yaw = 0.0F;
         this.leftArm.pivotZ = 0.0F;
         this.leftArm.pivotX = 5.0F;
         this.leftArm.pitch = cos;
         this.leftArm.roll = (float) (-Math.PI * 3.0 / 4.0);
         this.leftArm.yaw = 0.0F;
      }

      boolean bl;
      this.arms.visible = bl = state == State.CROSSED;
      this.leftArm.visible = !bl;
      this.rightArm.visible = !bl;
   }

   @Shadow
   private ModelPart getAttackingArm(Arm arm) {
      return arm == Arm.LEFT ? this.leftArm : this.rightArm;
   }

   public ModelPart getHead() {
      return this.head;
   }

   public void setArmAngle(Arm arm, MatrixStack matrixStack) {
      this.getAttackingArm(arm).rotate(matrixStack);
   }

   public ModelPart getPart() {
      return this.root;
   }
}
