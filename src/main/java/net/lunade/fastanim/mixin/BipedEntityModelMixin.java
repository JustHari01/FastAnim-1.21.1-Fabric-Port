package net.lunade.fastanim.mixin;

import net.minecraft.util.Hand;
import net.minecraft.util.Arm;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.client.render.entity.model.ModelWithArms;
import net.minecraft.client.render.entity.model.ModelWithHead;
import net.minecraft.client.render.entity.model.AnimalModel;
import net.minecraft.client.render.entity.model.CrossbowPosing;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.BipedEntityModel.ArmPose;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BipedEntityModel.class)
public abstract class BipedEntityModelMixin<T extends LivingEntity> extends AnimalModel<T> implements ModelWithArms, ModelWithHead {
   @Final
   @Shadow
   public ModelPart head;
   @Final
   @Shadow
   public ModelPart hat;
   @Final
   @Shadow
   public ModelPart body;
   @Final
   @Shadow
   public ModelPart rightArm;
   @Final
   @Shadow
   public ModelPart leftArm;
   @Final
   @Shadow
   public ModelPart rightLeg;
   @Final
   @Shadow
   public ModelPart leftLeg;
   @Shadow
   public ArmPose leftArmPose;
   @Shadow
   public ArmPose rightArmPose;
   @Shadow
   public boolean sneaking;
   @Shadow
   public float leaningPitch;

   @Inject(at = @At("HEAD"), method = "setAngles", cancellable = true)
   public void setAngles(T livingEntity, float f, float g, float h, float i, float j, CallbackInfo info) {
      info.cancel();
      boolean roll4 = livingEntity.getFallFlyingTicks() > 4;
      boolean swimming = livingEntity.isInSwimmingPose();
      this.head.yaw = i * (float) (Math.PI / 180.0);
      if (roll4) {
         this.head.pitch = (float) (-Math.PI / 4);
      } else if (this.leaningPitch > 0.0F) {
         this.head.pitch = swimming
            ? this.lerpAngle(this.leaningPitch, this.head.pitch, (float) (-Math.PI / 4))
            : this.lerpAngle(this.leaningPitch, this.head.pitch, j * (float) (Math.PI / 180.0));
      } else {
         this.head.pitch = j * (float) (Math.PI / 180.0);
      }

      this.body.yaw = 0.0F;
      this.rightArm.pivotZ = 0.0F;
      this.rightArm.pivotX = -5.0F;
      this.leftArm.pivotZ = 0.0F;
      this.leftArm.pivotX = 5.0F;
      float k = 1.0F;
      if (roll4) {
         k = (float)livingEntity.getVelocity().lengthSquared();
         k /= 0.2F;
         k *= k * k;
      }

      if (k < 1.0F) {
         k = 1.0F;
      }

      float fastf = f * 0.6662F;
      float twoLimbFiveK = 2.0F * g * 0.5F / k;
      float oneFourLimbK = 1.4F * g / k;
      float cosOne = (float)Math.sin(fastf + (float) (Math.PI / 2));
      float cosTwo = (float)Math.sin(fastf + (float) Math.PI + (float) (Math.PI / 2));
      this.rightArm.pitch = cosTwo * twoLimbFiveK;
      this.leftArm.pitch = cosOne * twoLimbFiveK;
      this.rightArm.roll = 0.0F;
      this.leftArm.roll = 0.0F;
      this.rightLeg.pitch = cosOne * oneFourLimbK;
      this.leftLeg.pitch = cosTwo * oneFourLimbK;
      this.rightLeg.yaw = 0.0F;
      this.leftLeg.yaw = 0.0F;
      this.rightLeg.roll = 0.0F;
      this.leftLeg.roll = 0.0F;
      if (this.riding) {
         this.rightArm.pitch -= (float) (Math.PI / 5);
         this.leftArm.pitch -= (float) (Math.PI / 5);
         this.rightLeg.pitch = -1.4137167F;
         this.rightLeg.yaw = (float) (Math.PI / 10);
         this.rightLeg.roll = 0.07853982F;
         this.leftLeg.pitch = -1.4137167F;
         this.leftLeg.yaw = (float) (-Math.PI / 10);
         this.leftLeg.roll = -0.07853982F;
      }

      this.rightArm.yaw = 0.0F;
      this.leftArm.yaw = 0.0F;
      boolean rightHanded = livingEntity.getMainArm() == Arm.RIGHT;
      if (livingEntity.isUsingItem()) {
         boolean bl4 = livingEntity.getActiveHand() == Hand.MAIN_HAND;
         if (bl4 == rightHanded) {
            this.positionRightArm(livingEntity);
         } else {
            this.positionLeftArm(livingEntity);
         }
      } else {
         boolean bl4 = rightHanded ? this.leftArmPose.isTwoHanded() : this.rightArmPose.isTwoHanded();
         if (rightHanded != bl4) {
            this.positionLeftArm(livingEntity);
            this.positionRightArm(livingEntity);
         } else {
            this.positionRightArm(livingEntity);
            this.positionLeftArm(livingEntity);
         }
      }

      this.animateArms(livingEntity, h);
      if (this.sneaking) {
         this.body.pitch = 0.5F;
         this.rightArm.pitch += 0.4F;
         this.leftArm.pitch += 0.4F;
         this.rightLeg.pivotZ = 4.0F;
         this.leftLeg.pivotZ = 4.0F;
         this.rightLeg.pivotY = 12.2F;
         this.leftLeg.pivotY = 12.2F;
         this.head.pivotY = 4.2F;
         this.body.pivotY = 3.2F;
         this.leftArm.pivotY = 5.2F;
         this.rightArm.pivotY = 5.2F;
      } else {
         this.body.pitch = 0.0F;
         this.rightLeg.pivotZ = 0.1F;
         this.leftLeg.pivotZ = 0.1F;
         this.rightLeg.pivotY = 12.0F;
         this.leftLeg.pivotY = 12.0F;
         this.head.pivotY = 0.0F;
         this.body.pivotY = 0.0F;
         this.leftArm.pivotY = 2.0F;
         this.rightArm.pivotY = 2.0F;
      }

      if (this.rightArmPose != ArmPose.SPYGLASS) {
         CrossbowPosing.swingArm(this.rightArm, h, 1.0F);
      }

      if (this.leftArmPose != ArmPose.SPYGLASS) {
         CrossbowPosing.swingArm(this.leftArm, h, -1.0F);
      }

      if (this.leaningPitch > 0.0F) {
         float l = f % 26.0F;
         Arm arm = this.getPreferredArm(livingEntity);
         float m = arm == Arm.RIGHT && this.handSwingProgress > 0.0F ? 0.0F : this.leaningPitch;
         float n = arm == Arm.LEFT && this.handSwingProgress > 0.0F ? 0.0F : this.leaningPitch;
         if (!livingEntity.isUsingItem()) {
            if (l < 14.0F) {
               float methodL = 1.8707964F * (-65.0F * l + l * l) / -714.0F;
               this.leftArm.pitch = this.lerpAngle(n, this.leftArm.pitch, 0.0F);
               this.rightArm.pitch = this.rightArm.pitch + m * (0.0F - this.rightArm.pitch);
               this.leftArm.yaw = this.lerpAngle(n, this.leftArm.yaw, (float) Math.PI);
               this.rightArm.yaw = this.rightArm.yaw + m * ((float) Math.PI - this.rightArm.yaw);
               this.leftArm.roll = this.lerpAngle(n, this.leftArm.roll, (float) Math.PI + methodL);
               this.rightArm.roll = this.rightArm.roll + m * ((float) Math.PI - methodL - this.rightArm.roll);
            } else if (l >= 14.0F && l < 22.0F) {
               float o = (l - 14.0F) / 8.0F;
               float one5O = (float) (Math.PI / 2) * o;
               float one8O = 1.8707964F * o;
               this.leftArm.pitch = this.lerpAngle(n, this.leftArm.pitch, one5O);
               this.rightArm.pitch = this.rightArm.pitch + m * (one5O - this.rightArm.pitch);
               this.leftArm.yaw = this.lerpAngle(n, this.leftArm.yaw, (float) Math.PI);
               this.rightArm.yaw = this.rightArm.yaw + m * ((float) Math.PI - this.rightArm.yaw);
               this.leftArm.roll = this.lerpAngle(n, this.leftArm.roll, 5.012389F - one8O);
               this.rightArm.roll = this.rightArm.roll + m * (1.2707963F + one8O - this.rightArm.roll);
            } else if (l >= 22.0F && l < 26.0F) {
               float o = (l - 22.0F) / 4.0F;
               float one5O = (float) (Math.PI / 2) - (float) (Math.PI / 2) * o;
               this.leftArm.pitch = this.lerpAngle(n, this.leftArm.pitch, one5O);
               this.rightArm.pitch = this.rightArm.pitch + m * (one5O - this.rightArm.pitch);
               this.leftArm.yaw = this.lerpAngle(n, this.leftArm.yaw, (float) Math.PI);
               this.rightArm.yaw = this.rightArm.yaw + m * ((float) Math.PI - this.rightArm.yaw);
               this.leftArm.roll = this.lerpAngle(n, this.leftArm.roll, (float) Math.PI);
               this.rightArm.roll = this.rightArm.roll + m * ((float) Math.PI - this.rightArm.roll);
            }
         }

         float limb3 = f * 0.33333334F;
         this.leftLeg.pitch = this.leftLeg.pitch
            + this.leaningPitch * (0.3F * (float)Math.sin(limb3 + (float) Math.PI + (float) (Math.PI / 2)) - this.leftLeg.pitch);
         this.rightLeg.pitch = this.rightLeg.pitch
            + this.leaningPitch * (0.3F * (float)Math.sin(limb3 + (float) (Math.PI / 2)) - this.rightLeg.pitch);
      }

      this.hat.copyTransform(this.head);
   }

   @Inject(at = @At("HEAD"), method = "positionRightArm", cancellable = true)
   private void positionRightArm(T livingEntity, CallbackInfo info) {
      info.cancel();
      switch (this.rightArmPose) {
         case EMPTY:
            this.rightArm.yaw = 0.0F;
            break;
         case BLOCK:
            this.rightArm.pitch = this.rightArm.pitch * 0.5F - 0.9424779F;
            this.rightArm.yaw = (float) (-Math.PI / 6);
            break;
         case ITEM:
            this.rightArm.pitch = this.rightArm.pitch * 0.5F - (float) (Math.PI / 10);
            this.rightArm.yaw = 0.0F;
            break;
         case THROW_SPEAR:
            this.rightArm.pitch = this.rightArm.pitch * 0.5F - (float) Math.PI;
            this.rightArm.yaw = 0.0F;
            break;
         case BOW_AND_ARROW:
            float yaws = 0.1F + this.head.yaw;
            this.rightArm.yaw = yaws;
            this.leftArm.yaw = yaws + 0.4F;
            float pitches = (float) (-Math.PI / 2) + this.head.pitch;
            this.rightArm.pitch = pitches;
            this.leftArm.pitch = pitches;
            break;
         case CROSSBOW_CHARGE:
            CrossbowPosing.charge(this.rightArm, this.leftArm, livingEntity, true);
            break;
         case CROSSBOW_HOLD:
            CrossbowPosing.hold(this.rightArm, this.leftArm, this.head, true);
            break;
         case SPYGLASS:
            this.rightArm.pitch = MathHelper.clamp(
               this.head.pitch - 1.9198622F - (livingEntity.isInSneakingPose() ? (float) (Math.PI / 12) : 0.0F), -2.4F, 3.3F
            );
            this.rightArm.yaw = this.head.yaw - (float) (Math.PI / 12);
            break;
         case TOOT_HORN:
            this.rightArm.pitch = MathHelper.clamp(this.head.pitch, -1.2F, 1.2F) - 1.4835298F;
            this.rightArm.yaw = this.head.yaw - (float) (Math.PI / 6);
      }
   }

   @Inject(at = @At("HEAD"), method = "positionLeftArm", cancellable = true)
   private void positionLeftArm(T livingEntity, CallbackInfo info) {
      info.cancel();
      switch (this.leftArmPose) {
         case EMPTY:
            this.leftArm.yaw = 0.0F;
            break;
         case BLOCK:
            this.leftArm.pitch = this.leftArm.pitch * 0.5F - 0.9424779F;
            this.leftArm.yaw = (float) (Math.PI / 6);
            break;
         case ITEM:
            this.leftArm.pitch = this.leftArm.pitch * 0.5F - (float) (Math.PI / 10);
            this.leftArm.yaw = 0.0F;
            break;
         case THROW_SPEAR:
            this.leftArm.pitch = this.leftArm.pitch * 0.5F - (float) Math.PI;
            this.leftArm.yaw = 0.0F;
            break;
         case BOW_AND_ARROW:
            float yaws = 0.1F + this.head.yaw;
            this.rightArm.yaw = -yaws - 0.4F;
            this.leftArm.yaw = yaws;
            float pitches = (float) (-Math.PI / 2) + this.head.pitch;
            this.rightArm.pitch = pitches;
            this.leftArm.pitch = pitches;
            break;
         case CROSSBOW_CHARGE:
            CrossbowPosing.charge(this.rightArm, this.leftArm, livingEntity, false);
            break;
         case CROSSBOW_HOLD:
            CrossbowPosing.hold(this.rightArm, this.leftArm, this.head, false);
            break;
         case SPYGLASS:
            this.leftArm.pitch = MathHelper.clamp(
               this.head.pitch - 1.9198622F - (livingEntity.isInSneakingPose() ? (float) (Math.PI / 12) : 0.0F), -2.4F, 3.3F
            );
            this.leftArm.yaw = this.head.yaw + (float) (Math.PI / 12);
            break;
         case TOOT_HORN:
            this.leftArm.pitch = MathHelper.clamp(this.head.pitch, -1.2F, 1.2F) - 1.4835298F;
            this.leftArm.yaw = this.head.yaw + (float) (Math.PI / 6);
      }
   }

   @Inject(at = @At("HEAD"), method = "animateArms", cancellable = true)
   public void animateArms(T livingEntity, float f, CallbackInfo info) {
      info.cancel();
      if (!(this.handSwingProgress <= 0.0F)) {
         Arm arm = this.getPreferredArm(livingEntity);
         ModelPart modelPart = this.getArm(arm);
         float g = this.handSwingProgress;
         this.body.yaw = (float)Math.sin((float)Math.sqrt(g) * (float) (Math.PI * 2)) * 0.2F;
         if (arm == Arm.LEFT) {
            this.body.yaw *= -1.0F;
         }

         float sin = (float)Math.sin(this.body.yaw) * 5.0F;
         float cos = (float)Math.sin(this.body.yaw + (float) (Math.PI / 2)) * 5.0F;
         this.rightArm.pivotZ = sin;
         this.rightArm.pivotX = -cos;
         this.leftArm.pivotZ = -sin;
         this.leftArm.pivotX = cos;
         this.rightArm.yaw = this.rightArm.yaw + this.body.yaw;
         this.leftArm.yaw = this.leftArm.yaw + this.body.yaw;
         this.leftArm.pitch = this.leftArm.pitch + this.body.yaw;
         g = 1.0F - this.handSwingProgress;
         g *= g;
         g *= g;
         g = 1.0F - g;
         float sinHand3 = (float)Math.sin(this.handSwingProgress * (float) Math.PI);
         float i = sinHand3 * -(this.head.pitch - 0.7F) * 0.75F;
         modelPart.pitch = modelPart.pitch - ((float)Math.sin(g * (float) Math.PI) * 1.2F + i);
         modelPart.yaw = modelPart.yaw + this.body.yaw * 2.0F;
         modelPart.roll += sinHand3 * -0.4F;
      }
   }

   @Shadow
   private void positionLeftArm(T livingEntity) {
   }

   @Shadow
   public ModelPart getArm(Arm arm) {
      throw new AssertionError("Mixin injection failed - FastAnim BipedEntityModelMixin");
   }

   @Shadow
   public float lerpAngle(float f, float g, float h) {
      throw new AssertionError("Mixin injection failed - FastAnim BipedEntityModelMixin");
   }

   @Shadow
   private Arm getPreferredArm(T livingEntity) {
      throw new AssertionError("Mixin injection failed - FastAnim BipedEntityModelMixin");
   }

   @Shadow
   public void animateArms(T livingEntity, float f) {
   }

   @Shadow
   private void positionRightArm(T livingEntity) {
   }
}
