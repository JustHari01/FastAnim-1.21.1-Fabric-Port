package net.lunade.fastanim.mixin;

import net.minecraft.entity.passive.TurtleEntity;
import net.minecraft.client.render.entity.model.QuadrupedEntityModel;
import net.minecraft.client.render.entity.model.TurtleEntityModel;
import net.minecraft.client.model.ModelPart;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = TurtleEntityModel.class, priority = 900)
public abstract class TurtleEntityModelMixin<T extends TurtleEntity> extends QuadrupedEntityModel<T> {
   @Final
   @Shadow
   private ModelPart plastron;

   protected TurtleEntityModelMixin(ModelPart modelPart, boolean bl, float f, float g, float h, float i, int j) {
      super(modelPart, bl, f, g, h, i, j);
   }

   @Inject(at = @At("HEAD"), method = "setAngles*", cancellable = true)
   public void setAngles(T turtleEntity, float limbAngle, float limbDistance, float h, float i, float j, CallbackInfo info) {
      info.cancel();
      this.head.pitch = j * (float) (Math.PI / 180.0);
      this.head.yaw = i * (float) (Math.PI / 180.0);
      if (!turtleEntity.isTouchingWater() && turtleEntity.isOnGround()) {
         boolean digging = turtleEntity.isDiggingSand();
         float k = digging ? 4.0F : 1.0F;
         float l = digging ? 2.0F : 1.0F;
         float limb5 = limbAngle * 5.0F;
         float kLimb5 = k * limb5;
         float eightLimbL = 8.0F * limbDistance * l;
         float threeLimb = 3.0F * limbDistance;
         this.rightHindLeg.pitch = 0.0F;
         this.leftHindLeg.pitch = 0.0F;
         this.rightFrontLeg.roll = 0.0F;
         this.leftFrontLeg.roll = 0.0F;
         this.rightFrontLeg.yaw = (float)Math.sin(kLimb5 + (float) Math.PI + (float) (Math.PI / 2)) * eightLimbL;
         this.leftFrontLeg.yaw = (float)Math.sin(kLimb5 + (float) (Math.PI / 2)) * eightLimbL;
         this.rightHindLeg.yaw = (float)Math.sin(limb5 + (float) Math.PI + (float) (Math.PI / 2)) * threeLimb;
         this.leftHindLeg.yaw = (float)Math.sin(limb5 + (float) (Math.PI / 2)) * threeLimb;
      } else {
         float fastLimbAngle = limbAngle * 0.39972F;
         float fastLimbDistance = 0.5F * limbDistance;
         float firstAngle = (float)Math.sin(fastLimbAngle + (float) (Math.PI / 2)) * fastLimbDistance;
         float secondAngle = (float)Math.sin(fastLimbAngle + (float) Math.PI + (float) (Math.PI / 2)) * fastLimbDistance;
         this.rightHindLeg.pitch = firstAngle;
         this.leftHindLeg.pitch = secondAngle;
         this.rightFrontLeg.roll = secondAngle;
         this.leftFrontLeg.roll = firstAngle;
         this.rightFrontLeg.yaw = 0.0F;
         this.leftFrontLeg.yaw = 0.0F;
         this.rightHindLeg.yaw = 0.0F;
         this.leftHindLeg.yaw = 0.0F;
      }

      this.plastron.visible = !this.child && turtleEntity.hasEgg();
   }
}
