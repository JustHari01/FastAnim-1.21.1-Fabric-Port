package net.lunade.fastanim.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.client.render.entity.model.VillagerResemblingModel;
import net.minecraft.client.render.entity.model.WitchEntityModel;
import net.minecraft.client.model.ModelPart;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = WitchEntityModel.class, priority = 900)
public abstract class WitchEntityModelMixin<T extends Entity> extends VillagerResemblingModel<T> {
   @Shadow
   private boolean liftingNose;

   public WitchEntityModelMixin(ModelPart modelPart) {
      super(modelPart);
   }

   @Inject(at = @At("HEAD"), method = "setAngles", cancellable = true)
   public void setAngles(T entity, float f, float g, float h, float i, float j, CallbackInfo info) {
      info.cancel();
      super.setAngles(entity, f, g, h, i, j);
      float k = 0.01F * (entity.getId() % 10);
      float newAge = entity.age * k;
      this.nose.roll = (float)Math.sin(newAge + (float) (Math.PI / 2)) * 0.04363323F;
      if (this.liftingNose) {
         this.nose.setPivot(0.0F, 1.0F, -1.5F);
         this.nose.pitch = -0.9F;
      } else {
         this.nose.pitch = (float)Math.sin(newAge) * 0.07853981F;
         this.nose.setPivot(0.0F, -2.0F, 0.0F);
      }
   }
}
