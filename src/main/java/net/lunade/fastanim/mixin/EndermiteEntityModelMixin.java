package net.lunade.fastanim.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.client.render.entity.model.EndermiteEntityModel;
import net.minecraft.client.model.ModelPart;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EndermiteEntityModel.class)
public class EndermiteEntityModelMixin<T extends Entity> {
   @Final
   @Shadow
   private ModelPart[] bodySegments;

   @Inject(at = @At("HEAD"), method = "setAngles", cancellable = true)
   public void setAngles(T entity, float f, float g, float h, float i, float j, CallbackInfo info) {
      info.cancel();

      for (int k = 0; k < this.bodySegments.length; k++) {
         float abs = Math.abs(k - 2);
         float inside = h * 0.9F + k * 0.4712389F;
         this.bodySegments[k].yaw = (float)Math.sin(inside + (float) (Math.PI / 2)) * 0.03141593F * (1.0F + abs);
         this.bodySegments[k].pivotX = (float)Math.sin(inside) * (float) (Math.PI / 10) * abs;
      }
   }
}
