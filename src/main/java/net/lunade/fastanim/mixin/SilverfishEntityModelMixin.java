package net.lunade.fastanim.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.client.render.entity.model.SilverfishEntityModel;
import net.minecraft.client.model.ModelPart;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = SilverfishEntityModel.class, priority = 900)
public class SilverfishEntityModelMixin<T extends Entity> {
   @Final
   @Shadow
   private final ModelPart[] body = new ModelPart[7];
   @Final
   @Shadow
   private final ModelPart[] scales = new ModelPart[3];

   @Inject(at = @At("HEAD"), method = "setAngles", cancellable = true)
   public void setAngles(T entity, float f, float g, float h, float i, float j, CallbackInfo info) {
      info.cancel();

      for (int k = 0; k < this.body.length; k++) {
         float abs = Math.abs(k - 2);
         float inside = h * 0.9F + k * 0.4712389F;
         this.body[k].yaw = (float)Math.sin(inside + (float) (Math.PI / 2)) * (float) (Math.PI / 20) * (1.0F + abs);
         this.body[k].pivotX = (float)Math.sin(inside) * (float) (Math.PI / 5) * abs;
      }

      this.scales[0].yaw = this.body[2].yaw;
      this.scales[1].yaw = this.body[4].yaw;
      this.scales[1].pivotX = this.body[4].pivotX;
      this.scales[2].yaw = this.body[1].yaw;
      this.scales[2].pivotX = this.body[1].pivotX;
   }
}
