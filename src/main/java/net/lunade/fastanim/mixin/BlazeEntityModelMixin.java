package net.lunade.fastanim.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.client.render.entity.model.BlazeEntityModel;
import net.minecraft.client.model.ModelPart;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlazeEntityModel.class)
public class BlazeEntityModelMixin<T extends Entity> {
   @Final
   @Shadow
   private ModelPart[] rods;
   @Final
   @Shadow
   private ModelPart head;

   @Inject(at = @At("HEAD"), method = "setAngles", cancellable = true)
   public void setAngles(T entity, float f, float g, float h, float i, float j, CallbackInfo info) {
      info.cancel();
      float k = h * (float) (-Math.PI / 10);

      for (int l = 0; l < 4; l++) {
         this.rods[l].pivotY = -2.0F + (float)Math.sin((l * 2 + h) * 0.25F + (float) (Math.PI / 2));
         this.rods[l].pivotX = (float)Math.sin(k + (float) (Math.PI / 2)) * 9.0F;
         this.rods[l].pivotZ = (float)Math.sin(k) * 9.0F;
         k += (float) (Math.PI / 2);
      }

      k = (float) (Math.PI / 4) + h * 0.09424778F;

      for (int var10 = 4; var10 < 8; var10++) {
         this.rods[var10].pivotY = 2.0F + (float)Math.sin((var10 * 2 + h) * 0.25F + (float) (Math.PI / 2));
         this.rods[var10].pivotX = (float)Math.sin(k + (float) (Math.PI / 2)) * 7.0F;
         this.rods[var10].pivotZ = (float)Math.sin(k) * 7.0F;
         k += (float) (Math.PI / 2);
      }

      k = 0.47123894F + h * (float) (-Math.PI / 20);

      for (int var11 = 8; var11 < 12; var11++) {
         this.rods[var11].pivotY = 11.0F + (float)Math.sin((var11 * 1.5F + h) * 0.5F + (float) (Math.PI / 2));
         this.rods[var11].pivotX = (float)Math.sin(k + (float) (Math.PI / 2)) * 5.0F;
         this.rods[var11].pivotZ = (float)Math.sin(k) * 5.0F;
         k += (float) (Math.PI / 2);
      }

      this.head.yaw = i * (float) (Math.PI / 180.0);
      this.head.pitch = j * (float) (Math.PI / 180.0);
   }
}
