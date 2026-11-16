package net.lunade.fastanim.mixin;

import net.minecraft.entity.mob.ShulkerEntity;
import net.minecraft.client.render.entity.model.ShulkerEntityModel;
import net.minecraft.client.model.ModelPart;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ShulkerEntityModel.class, priority = 900)
public class ShulkerEntityModelMixin<T extends ShulkerEntity> {
   @Final
   @Shadow
   private ModelPart lid;
   @Final
   @Shadow
   private ModelPart head;

   @Inject(at = @At("HEAD"), method = "setAngles*", cancellable = true)
   public void setAngles(T shulkerEntity, float f, float g, float h, float i, float j, CallbackInfo info) {
      info.cancel();
      float k = h - shulkerEntity.age;
      float l = (0.5F + shulkerEntity.getOpenProgress(k)) * (float) Math.PI;
      float m = -1.0F + (float)Math.sin(l);
      float n = 0.0F;
      if (l > (float) Math.PI) {
         n = (float)Math.sin(h * 0.1F) * 0.7F;
      }

      this.lid.setPivot(0.0F, 16.0F + (float)Math.sin(l) * 8.0F + n, 0.0F);
      this.lid.yaw = shulkerEntity.getOpenProgress(k) > 0.3F ? m * m * m * m * (float) Math.PI * 0.125F : 0.0F;
      this.head.pitch = j * (float) (Math.PI / 180.0);
      this.head.yaw = (shulkerEntity.headYaw - 180.0F - shulkerEntity.bodyYaw) * (float) (Math.PI / 180.0);
   }
}
