package net.lunade.fastanim.mixin;

import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.client.render.entity.model.BeeEntityModel;
import net.minecraft.client.model.ModelPart;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BeeEntityModel.class)
public class BeeEntityModelMixin<T extends BeeEntity> {
   @Final
   @Shadow
   private ModelPart bone;
   @Final
   @Shadow
   private ModelPart rightWing;
   @Final
   @Shadow
   private ModelPart leftWing;
   @Final
   @Shadow
   private ModelPart frontLegs;
   @Final
   @Shadow
   private ModelPart middleLegs;
   @Final
   @Shadow
   private ModelPart backLegs;
   @Final
   @Shadow
   private ModelPart stinger;
   @Final
   @Shadow
   private ModelPart leftAntenna;
   @Final
   @Shadow
   private ModelPart rightAntenna;

   @Inject(at = @At("HEAD"), method = "animateModel", cancellable = true)
   public void animateModel(T beeEntity, float f, float g, float h, CallbackInfo info) {
      info.cancel();
   }

   @Inject(at = @At("HEAD"), method = "setAngles", cancellable = true)
   public void setAngles(T beeEntity, float f, float g, float h, float i, float j, CallbackInfo info) {
      info.cancel();
      float bodyPitch = beeEntity.getBodyPitch(h);
      this.stinger.visible = !beeEntity.hasStung();
      this.leftAntenna.pitch = 0.0F;
      this.rightAntenna.pitch = 0.0F;
      this.bone.pitch = 0.0F;
      boolean bl = beeEntity.isOnGround() && beeEntity.getVelocity().lengthSquared() < 1.0E-7;
      if (bl) {
         this.rightWing.yaw = -0.2618F;
         this.rightWing.roll = 0.0F;
         this.leftWing.yaw = 0.2618F;
         this.leftWing.roll = 0.0F;
         this.frontLegs.pitch = 0.0F;
         this.middleLegs.pitch = 0.0F;
         this.backLegs.pitch = 0.0F;
      } else {
         this.rightWing.yaw = 0.0F;
         float roll = (float)Math.sin(h * 2.1F + (float) (Math.PI / 2)) * 0.4712389F;
         this.rightWing.roll = roll;
         this.leftWing.yaw = 0.0F;
         this.leftWing.roll = -roll;
         this.frontLegs.pitch = (float) (Math.PI / 4);
         this.middleLegs.pitch = (float) (Math.PI / 4);
         this.backLegs.pitch = (float) (Math.PI / 4);
         this.bone.pitch = 0.0F;
         this.bone.yaw = 0.0F;
         this.bone.roll = 0.0F;
      }

      if (!beeEntity.hasAngerTime()) {
         this.bone.pitch = 0.0F;
         this.bone.yaw = 0.0F;
         this.bone.roll = 0.0F;
         if (!bl) {
            float k = (float)Math.sin(h * 0.18F + (float) (Math.PI / 2));
            float k3 = k * 0.09424778F;
            this.bone.pitch = 0.1F + k * 0.07853982F;
            this.leftAntenna.pitch = k3;
            this.rightAntenna.pitch = k3;
            this.frontLegs.pitch = -k * (float) (Math.PI / 10) + (float) (Math.PI / 8);
            this.backLegs.pitch = -k * (float) (Math.PI / 20) + (float) (Math.PI / 4);
            this.bone.pivotY = 19.0F - k * 0.9F;
         }
      }

      if (bodyPitch > 0.0F) {
         float fl = (bodyPitch - 3.0915928F) % (float) (Math.PI * 2);
         if (fl < (float) -Math.PI) {
            fl += (float) (Math.PI * 2);
         }

         if (fl >= (float) Math.PI) {
            fl -= (float) (Math.PI * 2);
         }

         this.bone.pitch = 3.0915928F + this.bone.pitch * fl;
      }
   }
}
