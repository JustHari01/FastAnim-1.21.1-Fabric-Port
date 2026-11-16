package net.lunade.fastanim.mixin.entity;

import net.minecraft.entity.passive.WolfEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = WolfEntity.class, priority = 900)
public class WolfEntityMixin {
   @Shadow
   private float lastShakeProgress;
   @Shadow
   private float shakeProgress;
   @Shadow
   private float lastBegAnimationProgress;
   @Shadow
   private float begAnimationProgress;
   private static final float fifteenPi = 0.47123894F;

   @Inject(at = @At("HEAD"), method = "getShakeAnimationProgress", cancellable = true)
   public void getShakeAnimationProgress(float f, float g, CallbackInfoReturnable<Float> info) {
      float h = (this.lastShakeProgress + f * (this.shakeProgress - this.lastShakeProgress) + g) * 0.5555F;
      if (h < 0.0F) {
         h = 0.0F;
      } else if (h > 1.0F) {
         h = 1.0F;
      }

      float hPi = h * (float) Math.PI;
      info.setReturnValue((float)(Math.sin(hPi) * Math.sin(hPi * 11.0F) * 0.47123894F));
   }

   @Inject(at = @At("HEAD"), method = "getBegAnimationProgress", cancellable = true)
   public void getBegAnimationProgress(float f, CallbackInfoReturnable<Float> info) {
      info.setReturnValue((this.lastBegAnimationProgress + f * (this.begAnimationProgress - this.lastBegAnimationProgress)) * 0.47123894F);
   }
}
