package net.lunade.fastanim.mixin;

import net.minecraft.entity.mob.PhantomEntity;
import net.minecraft.client.render.entity.model.PhantomEntityModel;
import net.minecraft.client.model.ModelPart;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PhantomEntityModel.class)
public class PhantomEntityModelMixin<T extends PhantomEntity> {
   @Final
   @Shadow
   private ModelPart leftWingBase;
   @Final
   @Shadow
   private ModelPart leftWingTip;
   @Final
   @Shadow
   private ModelPart rightWingBase;
   @Final
   @Shadow
   private ModelPart rightWingTip;
   @Final
   @Shadow
   private ModelPart tailBase;
   @Final
   @Shadow
   private ModelPart tailTip;

   @Inject(at = @At("HEAD"), method = "setAngles*", cancellable = true)
   public void setAngles(T phantomEntity, float f, float g, float h, float i, float j, CallbackInfo info) {
      info.cancel();
      float k = (phantomEntity.getWingFlapTickOffset() + h) * 7.448451F * (float) (Math.PI / 180.0);
      float rolls = (float)Math.sin(k + (float) (Math.PI / 2)) * 0.27925268F;
      this.leftWingBase.roll = rolls;
      this.leftWingTip.roll = rolls;
      this.rightWingBase.roll = -rolls;
      this.rightWingTip.roll = -rolls;
      float pitches = -(5.0F + (float)Math.sin(k * 2.0F + (float) (Math.PI / 2)) * 5.0F) * (float) (Math.PI / 180.0);
      this.tailBase.pitch = pitches;
      this.tailTip.pitch = pitches;
   }
}
