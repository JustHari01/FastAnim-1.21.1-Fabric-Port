package net.lunade.fastanim.mixin.entity;

import net.minecraft.entity.mob.ShulkerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ShulkerEntity.class)
public class ShulkerEntityMixin {
   @Shadow
   private float prevOpenProgress;
   @Shadow
   private float openProgress;

   @Inject(at = @At("HEAD"), method = "getOpenProgress", cancellable = true)
   public void getOpenProgress(float f, CallbackInfoReturnable<Float> info) {
      info.setReturnValue(this.prevOpenProgress + f * (this.openProgress - this.prevOpenProgress));
   }
}
