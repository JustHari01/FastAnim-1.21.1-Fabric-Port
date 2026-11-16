package net.lunade.fastanim.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.DolphinEntityModel;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DolphinEntityModel.class)
public class DolphinEntityModelMixin<T extends Entity> {
   @Final
   @Shadow
   private ModelPart body;
   @Final
   @Shadow
   private ModelPart tail;
   @Final
   @Shadow
   private ModelPart tailFin;

   @Inject(at = @At("HEAD"), method = "setAngles", cancellable = true)
   public void setAngles(T entity, float limbAngle, float limbDistance, float h, float i, float j, CallbackInfo info) {
      info.cancel();
      this.body.pitch = j * (float) (Math.PI / 180.0);
      this.body.yaw = i * (float) (Math.PI / 180.0);
      if (entity.getVelocity().horizontalLengthSquared() > 1.0E-7) {
         float cos = (float)Math.sin(h * 0.3F + (float) (Math.PI / 2));
         this.body.pitch += -0.05F - 0.05F * cos;
         this.tail.pitch = -0.1F * cos;
         this.tailFin.pitch = -0.2F * cos;
      }
   }
}
