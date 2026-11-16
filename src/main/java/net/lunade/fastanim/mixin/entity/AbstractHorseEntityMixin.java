package net.lunade.fastanim.mixin.entity;

import net.minecraft.entity.passive.AbstractHorseEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractHorseEntity.class)
public class AbstractHorseEntityMixin {
   @Shadow
   private float lastEatingGrassAnimationProgress;
   @Shadow
   private float eatingGrassAnimationProgress;
   @Shadow
   private float lastAngryAnimationProgress;
   @Shadow
   private float angryAnimationProgress;
   @Shadow
   private float lastEatingAnimationProgress;
   @Shadow
   private float eatingAnimationProgress;

   @Inject(at = @At("HEAD"), method = "getEatingGrassAnimationProgress", cancellable = true)
   public void getEatingGrassAnimationProgress(float f, CallbackInfoReturnable<Float> info) {
      info.setReturnValue(this.lastEatingGrassAnimationProgress + f * (this.eatingGrassAnimationProgress - this.lastEatingGrassAnimationProgress));
   }

   @Inject(at = @At("HEAD"), method = "getAngryAnimationProgress", cancellable = true)
   public void getAngryAnimationProgress(float f, CallbackInfoReturnable<Float> info) {
      info.setReturnValue(this.lastAngryAnimationProgress + f * (this.angryAnimationProgress - this.lastAngryAnimationProgress));
   }

   @Inject(at = @At("HEAD"), method = "getEatingAnimationProgress", cancellable = true)
   public void getEatingAnimationProgress(float f, CallbackInfoReturnable<Float> info) {
      info.setReturnValue(this.lastEatingAnimationProgress + f * (this.eatingAnimationProgress - this.lastEatingAnimationProgress));
   }
}
