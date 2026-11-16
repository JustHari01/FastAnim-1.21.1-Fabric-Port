package net.lunade.fastanim.mixin;

import net.minecraft.util.math.MathHelper;
import net.minecraft.client.model.ModelPart;
import net.minecraft.entity.passive.TadpoleEntity;
import net.minecraft.client.render.entity.model.TadpoleEntityModel;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = TadpoleEntityModel.class, priority = 900)
public class TadpoleEntityModelMixin<T extends TadpoleEntity> {
   @Final
   @Shadow
   private ModelPart tail;

   @Inject(at = @At("HEAD"), method = "setAngles*", cancellable = true)
   public void setAngles(T tadpoleEntity, float limbAngle, float limbDistance, float h, float i, float j, CallbackInfo info) {
      info.cancel();
      if (tadpoleEntity.isTouchingWater()) {
         this.tail.yaw = -0.25F * MathHelper.sin(0.3F * h);
      } else {
         this.tail.yaw = -0.375F * MathHelper.sin(0.3F * h);
      }
   }
}
