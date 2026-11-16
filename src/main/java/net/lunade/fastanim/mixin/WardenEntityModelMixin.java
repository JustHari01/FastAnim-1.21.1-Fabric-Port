package net.lunade.fastanim.mixin;

import net.minecraft.client.model.ModelPart;
import net.minecraft.entity.mob.WardenEntity;
import net.minecraft.client.render.entity.model.WardenEntityModel;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = WardenEntityModel.class, priority = 900)
public class WardenEntityModelMixin<T extends WardenEntity> {
   @Final
   @Shadow
   public ModelPart body;
   @Final
   @Shadow
   public ModelPart head;
   @Final
   @Shadow
   public ModelPart leftLeg;
   @Final
   @Shadow
   public ModelPart leftArm;
   @Final
   @Shadow
   public ModelPart rightArm;
   @Final
   @Shadow
   public ModelPart rightLeg;

   @Inject(at = @At("HEAD"), method = "setHeadAndBodyAngles", cancellable = true)
   private void setHeadAndBodyAngles(float f, CallbackInfo info) {
      info.cancel();
      float g = f * 0.1F;
      float h = (float)Math.sin(g + (float) (Math.PI / 2));
      float i = (float)Math.sin(g);
      this.head.roll += 0.06F * h;
      this.head.pitch += 0.06F * i;
      this.body.roll += 0.025F * i;
      this.body.pitch += 0.025F * h;
   }

   @Inject(at = @At("HEAD"), method = "setLimbAngles", cancellable = true)
   private void setLimbAngles(float f, float g, CallbackInfo info) {
      info.cancel();
      float h = Math.min(0.5F, 3.0F * g);
      float i = f * 0.8662F;
      float cos = (float)Math.sin(i + (float) (Math.PI / 2));
      float sin = (float)Math.sin(i);
      float l = Math.min(0.35F, h);
      float sinh = sin * h;
      float cosh = cos * h;
      this.head.roll += 0.3F * sinh;
      this.head.pitch = this.head.pitch + 1.2F * (float)Math.sin(i + (float) (Math.PI / 2) + (float) (Math.PI / 2)) * l;
      this.body.roll = 0.1F * sinh;
      this.body.pitch = cos * l;
      this.leftLeg.pitch = cosh;
      this.rightLeg.pitch = (float)Math.sin(i + (float) Math.PI + (float) (Math.PI / 2)) * h;
      this.leftArm.pitch = -(0.8F * cosh);
      this.leftArm.roll = 0.0F;
      this.rightArm.pitch = -(0.8F * sinh);
      this.rightArm.roll = 0.0F;
      this.leftArm.yaw = 0.0F;
      this.leftArm.pivotZ = 1.0F;
      this.leftArm.pivotX = 13.0F;
      this.leftArm.pivotY = -13.0F;
      this.rightArm.yaw = 0.0F;
      this.rightArm.pivotZ = 1.0F;
      this.rightArm.pivotX = -13.0F;
      this.rightArm.pivotY = -13.0F;
   }
}
