package net.lunade.fastanim.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.client.render.entity.model.SpiderEntityModel;
import net.minecraft.client.model.ModelPart;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = SpiderEntityModel.class, priority = 900)
public class SpiderEntityModelMixin<T extends Entity> {
   @Final
   @Shadow
   private ModelPart root;
   @Final
   @Shadow
   private ModelPart head;
   @Final
   @Shadow
   private ModelPart rightHindLeg;
   @Final
   @Shadow
   private ModelPart leftHindLeg;
   @Final
   @Shadow
   private ModelPart rightMiddleLeg;
   @Final
   @Shadow
   private ModelPart leftMiddleLeg;
   @Final
   @Shadow
   private ModelPart rightMiddleFrontLeg;
   @Final
   @Shadow
   private ModelPart leftMiddleFrontLeg;
   @Final
   @Shadow
   private ModelPart rightFrontLeg;
   @Final
   @Shadow
   private ModelPart leftFrontLeg;

   @Inject(at = @At("HEAD"), method = "setAngles", cancellable = true)
   public void setAngles(T entity, float f, float g, float h, float i, float j, CallbackInfo info) {
      info.cancel();
      this.head.yaw = i * (float) (Math.PI / 180.0);
      this.head.pitch = j * (float) (Math.PI / 180.0);
      float fastAngle = f * 0.6662F;
      float fastAngleTwo = fastAngle * 2.0F;
      float hindYaw = (float) (Math.PI / 4) - (float)Math.sin(fastAngleTwo + (float) (Math.PI / 2)) * 0.4F * g;
      float middleYaw = (float) (Math.PI / 8) - (float)Math.sin(fastAngleTwo + (float) Math.PI + (float) (Math.PI / 2)) * 0.4F * g;
      float middleFrontYaw = (float) (-Math.PI / 8) - (float)Math.sin(fastAngleTwo + (float) (Math.PI / 2) + (float) (Math.PI / 2)) * 0.4F * g;
      float frontYaw = (float) (-Math.PI / 4) - (float)Math.sin(fastAngleTwo + (float) (Math.PI * 3.0 / 2.0) + (float) (Math.PI / 2)) * 0.4F * g;
      this.rightHindLeg.yaw = hindYaw;
      this.leftHindLeg.yaw = -hindYaw;
      this.rightMiddleLeg.yaw = middleYaw;
      this.leftMiddleLeg.yaw = -middleYaw;
      this.rightMiddleFrontLeg.yaw = middleFrontYaw;
      this.leftMiddleFrontLeg.yaw = -middleFrontYaw;
      this.rightFrontLeg.yaw = frontYaw;
      this.leftFrontLeg.yaw = -frontYaw;
      float hindRoll = (float) (-Math.PI / 4) + Math.abs((float)Math.sin(fastAngle) * 0.4F) * g;
      float middleRoll = -0.58119464F + Math.abs((float)Math.sin(fastAngle + (float) Math.PI) * 0.4F) * g;
      float middleFrontRoll = -0.58119464F + Math.abs((float)Math.sin(fastAngle + (float) (Math.PI / 2)) * 0.4F) * g;
      float frontRoll = (float) (-Math.PI / 4) + Math.abs((float)Math.sin(fastAngle + (float) (Math.PI * 3.0 / 2.0)) * 0.4F) * g;
      this.rightHindLeg.roll = hindRoll;
      this.leftHindLeg.roll = -hindRoll;
      this.rightMiddleLeg.roll = middleRoll;
      this.leftMiddleLeg.roll = -middleRoll;
      this.rightMiddleFrontLeg.roll = middleFrontRoll;
      this.leftMiddleFrontLeg.roll = -middleFrontRoll;
      this.rightFrontLeg.roll = frontRoll;
      this.leftFrontLeg.roll = -frontRoll;
   }
}
