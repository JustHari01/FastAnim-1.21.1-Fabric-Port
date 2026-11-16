package net.lunade.fastanim.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.client.render.entity.model.MediumPufferfishEntityModel;
import net.minecraft.client.model.ModelPart;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MediumPufferfishEntityModel.class)
public class MediumPufferfishEntityModelMixin<T extends Entity> {
   @Final
   @Shadow
   private ModelPart leftBlueFin;
   @Final
   @Shadow
   private ModelPart rightBlueFin;

   @Inject(at = @At("HEAD"), method = "setAngles", cancellable = true)
   public void setAngles(T entity, float limbAngle, float limbDistance, float h, float i, float j, CallbackInfo info) {
      info.cancel();
      float sin = (float)Math.sin(h * 0.2F);
      this.leftBlueFin.roll = -0.2F + 0.4F * sin;
      this.rightBlueFin.roll = 0.2F - 0.4F * sin;
   }
}
