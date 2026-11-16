package net.lunade.fastanim.mixin.entity;

import net.minecraft.entity.passive.PolarBearEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PolarBearEntity.class)
public class PolarBearEntityMixin {
   @Shadow
   private float lastWarningAnimationProgress;
   @Shadow
   private float warningAnimationProgress;

   @Inject(at = @At("HEAD"), method = "getWarningAnimationProgress", cancellable = true)
   public void getWarningAnimationProgress(float f, CallbackInfoReturnable<Float> info) {
      info.setReturnValue((this.lastWarningAnimationProgress + f * (this.warningAnimationProgress - this.lastWarningAnimationProgress)) * 0.16666667F);
   }
}
