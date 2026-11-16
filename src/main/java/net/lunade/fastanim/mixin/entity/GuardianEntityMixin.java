package net.lunade.fastanim.mixin.entity;

import net.minecraft.entity.mob.GuardianEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GuardianEntity.class)
public class GuardianEntityMixin {
   @Shadow
   private float prevTailAngle;
   @Shadow
   private float tailAngle;

   @Inject(at = @At("HEAD"), method = "getTailAngle", cancellable = true)
   public void getTailAngle(float f, CallbackInfoReturnable<Float> info) {
      info.setReturnValue(this.prevTailAngle + f * (this.tailAngle - this.prevTailAngle));
   }
}
