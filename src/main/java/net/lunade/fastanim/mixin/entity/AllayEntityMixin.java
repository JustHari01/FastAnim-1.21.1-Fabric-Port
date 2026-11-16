package net.lunade.fastanim.mixin.entity;

import net.minecraft.entity.passive.AllayEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AllayEntity.class)
public class AllayEntityMixin {
   @Shadow
   private float field_38936;
   @Shadow
   private float field_38935;
   @Shadow
   private float field_39474;
   @Shadow
   private float field_39473;

   @Inject(at = @At("HEAD"), method = "method_43397", cancellable = true)
   public void method_43397(float f, CallbackInfoReturnable<Float> info) {
      info.setReturnValue((this.field_38936 + f * (this.field_38935 - this.field_38936)) * 0.2F);
   }

   @Inject(at = @At("HEAD"), method = "method_44368", cancellable = true)
   public void method_44368(float f, CallbackInfoReturnable<Float> info) {
      info.setReturnValue((this.field_39474 + f * (this.field_39473 - this.field_39474)) * 0.06666667F);
   }
}
