package net.lunade.fastanim.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.client.render.entity.model.BoatEntityModel;
import net.minecraft.client.model.ModelPart;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BoatEntityModel.class)
public class BoatEntityModelMixin<T extends Entity> {
   @Final
   @Shadow
   private ModelPart leftPaddle;
   @Final
   @Shadow
   private ModelPart rightPaddle;

   @Inject(at = @At("HEAD"), method = "setAngles", cancellable = true)
   public void setAngles(BoatEntity boatEntity, float f, float g, float h, float i, float j, CallbackInfo info) {
      info.cancel();
      float interpolatePaddlePhase = boatEntity.interpolatePaddlePhase(0, f);
      this.leftPaddle.pitch = MathHelper.clampedLerp(
         (float) (-Math.PI / 3), (float) (-Math.PI / 12), ((float)Math.sin(-interpolatePaddlePhase) + 1.0F) * 0.5F
      );
      this.leftPaddle.yaw = MathHelper.clampedLerp(
         (float) (-Math.PI / 4), (float) (Math.PI / 4), ((float)Math.sin(-interpolatePaddlePhase + 1.0F) + 1.0F) * 0.5F
      );
      interpolatePaddlePhase = boatEntity.interpolatePaddlePhase(1, f);
      this.rightPaddle.pitch = MathHelper.clampedLerp(
         (float) (-Math.PI / 3), (float) (-Math.PI / 12), ((float)Math.sin(-interpolatePaddlePhase) + 1.0F) * 0.5F
      );
      this.rightPaddle.yaw = (float) Math.PI
         - MathHelper.clampedLerp((float) (-Math.PI / 4), (float) (Math.PI / 4), ((float)Math.sin(-interpolatePaddlePhase + 1.0F) + 1.0F) * 0.5F);
   }

   @Inject(at = @At("HEAD"), method = "setPaddleAngle", cancellable = true)
   private static void setPaddleAngle(BoatEntity boatEntity, int i, ModelPart modelPart, float f, CallbackInfo info) {
      info.cancel();
      float g = boatEntity.interpolatePaddlePhase(i, f);
      modelPart.pitch = MathHelper.clampedLerp((float) (-Math.PI / 3), (float) (-Math.PI / 12), ((float)Math.sin(-g) + 1.0F) / 2.0F);
      modelPart.yaw = MathHelper.clampedLerp((float) (-Math.PI / 4), (float) (Math.PI / 4), ((float)Math.sin(-g + 1.0F) + 1.0F) / 2.0F);
      if (i == 1) {
         modelPart.yaw = (float) Math.PI - modelPart.yaw;
      }
   }
}
