package net.lunade.fastanim.mixin.entity;

import net.minecraft.entity.passive.BeeEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BeeEntity.class)
public class BeeEntityMixin {
   @Shadow
   private float currentPitch;
   @Shadow
   private float lastPitch;

   @Inject(at = @At("HEAD"), method = "getBodyPitch", cancellable = true)
   public void getBodyPitch(float tickDelta, CallbackInfoReturnable<Float> info) {
      info.setReturnValue(this.lastPitch + tickDelta * (this.currentPitch - this.lastPitch));
   }
}
