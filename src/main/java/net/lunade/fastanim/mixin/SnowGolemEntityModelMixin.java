package net.lunade.fastanim.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.client.render.entity.model.SnowGolemEntityModel;
import net.minecraft.client.model.ModelPart;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = SnowGolemEntityModel.class, priority = 900)
public class SnowGolemEntityModelMixin<T extends Entity> {
   @Final
   @Shadow
   private ModelPart root;
   @Final
   @Shadow
   private ModelPart upperBody;
   @Final
   @Shadow
   private ModelPart head;
   @Final
   @Shadow
   private ModelPart leftArm;
   @Final
   @Shadow
   private ModelPart rightArm;

   @Inject(at = @At("HEAD"), method = "setAngles", cancellable = true)
   public void setAngles(T entity, float f, float g, float h, float i, float j, CallbackInfo info) {
      info.cancel();
      float i180 = i * (float) (Math.PI / 180.0);
      this.head.yaw = i180;
      this.head.pitch = j * (float) (Math.PI / 180.0);
      float upperYaw = i180 * 0.25F;
      this.upperBody.yaw = upperYaw;
      float k = (float)Math.sin(this.upperBody.yaw);
      float l = (float)Math.sin(this.upperBody.yaw + (float) (Math.PI / 2));
      this.leftArm.yaw = upperYaw;
      this.rightArm.yaw = upperYaw + (float) Math.PI;
      float l5 = l * 5.0F;
      float k5 = k * 5.0F;
      this.leftArm.pivotX = l5;
      this.leftArm.pivotZ = -k5;
      this.rightArm.pivotX = -l5;
      this.rightArm.pivotZ = k5;
   }
}
