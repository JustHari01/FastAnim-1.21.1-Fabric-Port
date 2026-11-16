package net.lunade.fastanim.mixin;

import net.minecraft.entity.passive.PolarBearEntity;
import net.minecraft.client.render.entity.model.PolarBearEntityModel;
import net.minecraft.client.render.entity.model.QuadrupedEntityModel;
import net.minecraft.client.model.ModelPart;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = PolarBearEntityModel.class, priority = 900)
public abstract class PolarBearEntityModelMixin<T extends PolarBearEntity> extends QuadrupedEntityModel<T> {
   protected PolarBearEntityModelMixin(ModelPart modelPart, boolean bl, float f, float g, float h, float i, int j) {
      super(modelPart, bl, f, g, h, i, j);
   }

   @Inject(at = @At("HEAD"), method = "setAngles*", cancellable = true)
   public void setAngles(T polarBearEntity, float f, float g, float h, float i, float j, CallbackInfo info) {
      info.cancel();
      this.head.pitch = j * (float) (Math.PI / 180.0);
      this.head.yaw = i * (float) (Math.PI / 180.0);
      float fastLimbAngle = f * 0.6662F;
      float fastLimbDistance = 1.4F * g;
      float firstAngle = (float)Math.sin(fastLimbAngle + (float) (Math.PI / 2)) * fastLimbDistance;
      float secondAngle = (float)Math.sin(fastLimbAngle + (float) Math.PI + (float) (Math.PI / 2)) * fastLimbDistance;
      this.rightHindLeg.pitch = firstAngle;
      this.leftHindLeg.pitch = secondAngle;
      this.rightFrontLeg.pitch = secondAngle;
      this.leftFrontLeg.pitch = firstAngle;
      float k = h - polarBearEntity.age;
      float l = polarBearEntity.getWarningAnimationProgress(k);
      l *= l;
      float m = 1.0F - l;
      this.body.pitch = (float) (Math.PI / 2) - l * 1.0995574F;
      this.body.pivotY = 9.0F * m + 11.0F * l;
      float pivotY = 14.0F * m - 6.0F * l;
      float pivotZ = -8.0F * m - 4.0F * l;
      this.rightFrontLeg.pivotY = 14.0F * m - 6.0F * l;
      this.rightFrontLeg.pivotZ = -8.0F * m - 4.0F * l;
      float pitches = l * 1.4137167F;
      this.rightFrontLeg.pitch -= pitches;
      this.leftFrontLeg.pivotY = pivotY;
      this.leftFrontLeg.pivotZ = pivotZ;
      this.leftFrontLeg.pitch -= pitches;
      if (this.child) {
         this.head.pivotY = 10.0F * m - 9.0F * l;
         this.head.pivotZ = -16.0F * m - 7.0F * l;
      } else {
         this.head.pivotY = 10.0F * m - 14.0F * l;
         this.head.pivotZ = -16.0F * m - 3.0F * l;
      }

      this.head.pitch += l * 0.4712389F;
   }
}
