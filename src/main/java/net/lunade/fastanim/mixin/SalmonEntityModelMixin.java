package net.lunade.fastanim.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.client.render.entity.model.SalmonEntityModel;
import net.minecraft.client.model.ModelPart;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = SalmonEntityModel.class, priority = 900)
public class SalmonEntityModelMixin<T extends Entity> {
   @Final
   @Shadow
   private ModelPart tail;

   @Inject(at = @At("HEAD"), method = "setAngles", cancellable = true)
   public void setAngles(T entity, float limbAngle, float limbDistance, float h, float i, float j, CallbackInfo info) {
      info.cancel();
      if (!entity.isTouchingWater()) {
         this.tail.yaw = (float)(-0.325F * Math.sin(1.02F * h));
      } else {
         this.tail.yaw = (float)(-0.25 * Math.sin(0.6F * h));
      }
   }
}
