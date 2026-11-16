package net.lunade.fastanim.mixin.entity;

import net.minecraft.entity.mob.WardenEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WardenEntity.class)
public class WardenEntityMixin {
   @Shadow
   private int lastTendrilPitch;
   @Shadow
   private int tendrilPitch;
   @Shadow
   private int lastHeartbeatCooldown;
   @Shadow
   private int heartbeatCooldown;

   @Inject(at = @At("HEAD"), method = "getTendrilPitch", cancellable = true)
   public void getTendrilPitch(float f, CallbackInfoReturnable<Float> info) {
      info.setReturnValue((this.lastTendrilPitch + f * (this.tendrilPitch - this.lastTendrilPitch)) * 0.1F);
   }

   @Inject(at = @At("HEAD"), method = "getHeartPitch", cancellable = true)
   public void getHeartPitch(float f, CallbackInfoReturnable<Float> info) {
      info.setReturnValue((this.lastHeartbeatCooldown + f * (this.heartbeatCooldown - this.lastHeartbeatCooldown)) * 0.1F);
   }
}
