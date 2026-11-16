package net.lunade.fastanim.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.client.render.entity.model.EvokerFangsEntityModel;
import net.minecraft.client.model.ModelPart;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EvokerFangsEntityModel.class)
public class EvokerFangsEntityModelMixin<T extends Entity> {
   @Final
   @Shadow
   private ModelPart root;
   @Final
   @Shadow
   private ModelPart base;
   @Final
   @Shadow
   private ModelPart upperJaw;
   @Final
   @Shadow
   private ModelPart lowerJaw;

   @Inject(at = @At("HEAD"), method = "setAngles", cancellable = true)
   public void setAngles(T entity, float limbAngle, float limbDistance, float h, float i, float j, CallbackInfo info) {
      info.cancel();
      float k = limbAngle * 2.0F;
      if (k > 1.0F) {
         k = 1.0F;
      }

      k = 1.0F - k * k * k;
      float k3Pi = k * 0.35F * (float) Math.PI;
      this.upperJaw.roll = (float) Math.PI - k3Pi;
      this.lowerJaw.roll = (float) Math.PI + k3Pi;
      float l = (limbAngle + (float)Math.sin(limbAngle * 2.7F)) * 7.2F;
      this.lowerJaw.pivotY = this.upperJaw.pivotY = this.base.pivotY = 24.0F - l;
   }
}
