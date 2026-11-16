package net.lunade.fastanim.mixin;

import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MathHelper.class)
public class MathHelperMixin {
   @Inject(at = @At("HEAD"), method = "lerpAngleDegrees", cancellable = true)
   private static void lerpAngleDegrees(float f, float g, float h, CallbackInfoReturnable<Float> info) {
      float angleCalc = (h - g) % 360.0F;
      if (angleCalc >= 180.0F) {
         angleCalc -= 360.0F;
      }

      if (angleCalc < -180.0F) {
         angleCalc += 360.0F;
      }

      info.setReturnValue(g + f * angleCalc);
   }
}
