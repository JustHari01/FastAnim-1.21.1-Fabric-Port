package net.lunade.fastanim.mixin;

import net.minecraft.entity.passive.ParrotEntity;
import net.minecraft.client.render.entity.model.ParrotEntityModel;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.ParrotEntityModel.Pose;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ParrotEntityModel.class, priority = 900)
public class ParrotEntityModelMixin<T extends ParrotEntity> {
   @Final
   @Shadow
   private ModelPart root;
   @Final
   @Shadow
   private ModelPart body;
   @Final
   @Shadow
   private ModelPart tail;
   @Final
   @Shadow
   private ModelPart leftWing;
   @Final
   @Shadow
   private ModelPart rightWing;
   @Final
   @Shadow
   private ModelPart head;
   @Final
   @Shadow
   private ModelPart feather;
   @Final
   @Shadow
   private ModelPart leftLeg;
   @Final
   @Shadow
   private ModelPart rightLeg;

   @Inject(at = @At("HEAD"), method = "setAngles*", cancellable = true)
   private void setAngles(Pose pose, int i, float f, float g, float h, float j, float k, CallbackInfo info) {
      info.cancel();
      this.head.pitch = k * (float) (Math.PI / 180.0);
      this.head.yaw = j * (float) (Math.PI / 180.0);
      this.head.roll = 0.0F;
      this.head.pivotX = 0.0F;
      this.body.pivotX = 0.0F;
      this.tail.pivotX = 0.0F;
      this.rightWing.pivotX = -1.5F;
      this.leftWing.pivotX = 1.5F;
      switch (pose) {
         case SITTING:
            break;
         case PARTY:
            float l = (float)Math.sin(i + (float) (Math.PI / 2));
            float m = (float)Math.sin(i);
            this.head.pivotX = l;
            this.head.pivotY = 15.69F + m;
            this.head.pitch = 0.0F;
            this.head.yaw = 0.0F;
            this.head.roll = m * 0.4F;
            this.body.pivotX = l;
            this.body.pivotY = 16.5F + m;
            this.leftWing.roll = -0.0873F - h;
            this.leftWing.pivotX = 1.5F + l;
            this.leftWing.pivotY = 16.94F + m;
            this.rightWing.roll = 0.0873F + h;
            this.rightWing.pivotX = -1.5F + l;
            this.rightWing.pivotY = 16.94F + m;
            this.tail.pivotX = l;
            this.tail.pivotY = 21.07F + m;
            break;
         case STANDING:
            float oneFourG = 1.4F * g;
            float fastAngle = f * 0.6662F;
            this.leftLeg.pitch = this.leftLeg.pitch + (float)Math.sin(fastAngle + (float) (Math.PI / 2)) * oneFourG;
            this.rightLeg.pitch = this.rightLeg.pitch + (float)Math.sin(fastAngle + (float) Math.PI + (float) (Math.PI / 2)) * oneFourG;
         default:
            float n = h * 0.3F;
            this.head.pivotY = 15.69F + n;
            this.tail.pitch = 1.015F + (float)Math.sin(f * 0.6662F + (float) (Math.PI / 2)) * 0.3F * g;
            this.tail.pivotY = 21.07F + n;
            this.body.pivotY = 16.5F + n;
            this.leftWing.roll = -0.0873F - h;
            this.leftWing.pivotY = 16.94F + n;
            this.rightWing.roll = 0.0873F + h;
            this.rightWing.pivotY = 16.94F + n;
            this.leftLeg.pivotY = 22.0F + n;
            this.rightLeg.pivotY = 22.0F + n;
      }
   }

   @Inject(at = @At("HEAD"), method = "animateModel*", cancellable = true)
   private void animateModel(Pose pose, CallbackInfo info) {
      info.cancel();
      this.feather.pitch = -0.2214F;
      this.body.pitch = 0.4937F;
      this.leftWing.pitch = -0.6981F;
      this.leftWing.yaw = (float) -Math.PI;
      this.rightWing.pitch = -0.6981F;
      this.rightWing.yaw = (float) -Math.PI;
      this.leftLeg.pitch = -0.0299F;
      this.rightLeg.pitch = -0.0299F;
      this.leftLeg.pivotY = 22.0F;
      this.rightLeg.pivotY = 22.0F;
      this.leftLeg.roll = 0.0F;
      this.rightLeg.roll = 0.0F;
      switch (pose) {
         case SITTING:
            float f = 1.9F;
            this.head.pivotY = 17.59F;
            this.tail.pitch = 1.5388988F;
            this.tail.pivotY = 22.97F;
            this.body.pivotY = 18.4F;
            this.leftWing.roll = -0.0873F;
            this.leftWing.pivotY = 18.84F;
            this.rightWing.roll = 0.0873F;
            this.rightWing.pivotY = 18.84F;
            this.leftLeg.pivotY += 1.9F;
            this.rightLeg.pivotY += 1.9F;
            this.leftLeg.pitch += (float) (Math.PI / 2);
            this.rightLeg.pitch += (float) (Math.PI / 2);
            break;
         case PARTY:
            this.leftLeg.roll = (float) (-Math.PI / 9);
            this.rightLeg.roll = (float) (Math.PI / 9);
         case STANDING:
         default:
            break;
         case FLYING:
            this.leftLeg.pitch += (float) (Math.PI * 2.0 / 9.0);
            this.rightLeg.pitch += (float) (Math.PI * 2.0 / 9.0);
      }
   }
}
