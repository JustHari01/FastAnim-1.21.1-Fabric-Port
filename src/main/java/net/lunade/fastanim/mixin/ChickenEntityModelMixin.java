package net.lunade.fastanim.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.client.render.entity.model.ChickenEntityModel;
import net.minecraft.client.model.ModelPart;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChickenEntityModel.class)
public class ChickenEntityModelMixin<T extends Entity> {
   @Final
   @Shadow
   private ModelPart head;
   @Final
   @Shadow
   private ModelPart rightLeg;
   @Final
   @Shadow
   private ModelPart leftLeg;
   @Final
   @Shadow
   private ModelPart rightWing;
   @Final
   @Shadow
   private ModelPart leftWing;
   @Final
   @Shadow
   private ModelPart beak;
   @Final
   @Shadow
   private ModelPart wattle;

   @Inject(at = @At("HEAD"), method = "setAngles", cancellable = true)
   public void setAngles(T entity, float limbAngle, float limbDistance, float h, float i, float j, CallbackInfo info) {
      info.cancel();
      float pitches = j * (float) (Math.PI / 180.0);
      this.head.pitch = pitches;
      this.beak.pitch = pitches;
      this.wattle.pitch = pitches;
      float yaws = i * (float) (Math.PI / 180.0);
      this.head.yaw = yaws;
      this.beak.yaw = yaws;
      this.wattle.yaw = yaws;
      float fastLimbAngle = limbAngle * 0.6662F;
      float fastLimbDistance = 1.4F * limbDistance;
      this.rightLeg.pitch = (float)Math.sin(fastLimbAngle + (float) (Math.PI / 2)) * fastLimbDistance;
      this.leftLeg.pitch = (float)Math.sin(fastLimbAngle + (float) Math.PI + (float) (Math.PI / 2)) * fastLimbDistance;
      this.rightWing.roll = h;
      this.leftWing.roll = -h;
   }
}
