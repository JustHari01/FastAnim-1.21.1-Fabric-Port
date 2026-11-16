package net.lunade.fastanim.mixin;

import net.minecraft.entity.mob.SlimeEntity;
import net.minecraft.client.render.entity.model.MagmaCubeEntityModel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(MagmaCubeEntityModel.class)
public class MagmaCubeEntityModelMixin<T extends SlimeEntity> {
   @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/MathHelper;lerp(FFF)F"), method = "animateModel")
   public float animateModel(float h, float par1, float par2) {
      return par1 + h * (par2 - par1);
   }
}
