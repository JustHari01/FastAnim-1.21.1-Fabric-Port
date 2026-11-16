package net.lunade.fastanim.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.client.render.entity.model.QuadrupedEntityModel;
import net.minecraft.client.model.ModelPart;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = QuadrupedEntityModel.class, priority = 900)
public class QuadEntityModelMixin<T extends Entity> {
   @Final
   @Shadow
   private ModelPart head;
   @Final
   @Shadow
   private ModelPart body;
   @Final
   @Shadow
   private ModelPart rightHindLeg;
   @Final
   @Shadow
   private ModelPart leftHindLeg;
   @Final
   @Shadow
   private ModelPart rightFrontLeg;
   @Final
   @Shadow
   private ModelPart leftFrontLeg;

   @Inject(at = @At("HEAD"), method = "setAngles", cancellable = true)
   public void setAngles(T entity, float limbAngle, float limbDistance, float h, float i, float j, CallbackInfo info) {
      info.cancel();
      this.head.pitch = j * (float) (Math.PI / 180.0);
      this.head.yaw = i * (float) (Math.PI / 180.0);
      float fastLimbAngle = limbAngle * 0.6662F;
      float fastLimbDistance = 1.4F * limbDistance;
      float firstAngle = (float)Math.sin(fastLimbAngle + (float) (Math.PI / 2)) * fastLimbDistance;
      float secondAngle = (float)Math.sin(fastLimbAngle + (float) Math.PI + (float) (Math.PI / 2)) * fastLimbDistance;
      this.rightHindLeg.pitch = firstAngle;
      this.leftHindLeg.pitch = secondAngle;
      this.rightFrontLeg.pitch = secondAngle;
      this.leftFrontLeg.pitch = firstAngle;
   }
}
