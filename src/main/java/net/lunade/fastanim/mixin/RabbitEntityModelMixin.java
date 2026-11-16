package net.lunade.fastanim.mixin;

import net.minecraft.entity.passive.RabbitEntity;
import net.minecraft.client.render.entity.model.RabbitEntityModel;
import net.minecraft.client.model.ModelPart;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = RabbitEntityModel.class, priority = 900)
public class RabbitEntityModelMixin<T extends RabbitEntity> {
   @Final
   @Shadow
   private ModelPart leftHindLeg;
   @Final
   @Shadow
   private ModelPart rightHindLeg;
   @Final
   @Shadow
   private ModelPart leftHaunch;
   @Final
   @Shadow
   private ModelPart rightHaunch;
   @Final
   @Shadow
   private ModelPart body;
   @Final
   @Shadow
   private ModelPart leftFrontLeg;
   @Final
   @Shadow
   private ModelPart rightFrontLeg;
   @Final
   @Shadow
   private ModelPart head;
   @Final
   @Shadow
   private ModelPart rightEar;
   @Final
   @Shadow
   private ModelPart leftEar;
   @Final
   @Shadow
   private ModelPart tail;
   @Final
   @Shadow
   private ModelPart nose;
   @Shadow
   private float jumpProgress;

   @Inject(at = @At("HEAD"), method = "setAngles*", cancellable = true)
   public void setAngles(T rabbitEntity, float f, float g, float h, float i, float j, CallbackInfo info) {
      info.cancel();
      float k = h - rabbitEntity.age;
      float jPi = j * (float) (Math.PI / 180.0);
      this.nose.pitch = jPi;
      this.head.pitch = jPi;
      this.rightEar.pitch = jPi;
      this.leftEar.pitch = jPi;
      float iPi = i * (float) (Math.PI / 180.0);
      this.nose.yaw = iPi;
      this.head.yaw = iPi;
      this.rightEar.yaw = this.nose.yaw - (float) (Math.PI / 12);
      this.leftEar.yaw = this.nose.yaw + (float) (Math.PI / 12);
      float jumpProgress = (float)Math.sin(rabbitEntity.getJumpProgress(k) * (float) Math.PI);
      float jump50 = jumpProgress * 50.0F;
      float jump5021 = (jump50 - 21.0F) * (float) (Math.PI / 180.0);
      this.leftHaunch.pitch = jump5021;
      this.rightHaunch.pitch = jump5021;
      float jump50Pi180 = jump50 * (float) (Math.PI / 180.0);
      this.leftHindLeg.pitch = jump50Pi180;
      this.rightHindLeg.pitch = jump50Pi180;
      float jump40 = (jumpProgress * -40.0F - 11.0F) * (float) (Math.PI / 180.0);
      this.leftFrontLeg.pitch = jump40;
      this.rightFrontLeg.pitch = jump40;
      this.jumpProgress = jumpProgress;
   }

   @Inject(at = @At("HEAD"), method = "animateModel*", cancellable = true)
   public void animateModel(T rabbitEntity, float f, float g, float h, CallbackInfo info) {
      info.cancel();
   }
}
