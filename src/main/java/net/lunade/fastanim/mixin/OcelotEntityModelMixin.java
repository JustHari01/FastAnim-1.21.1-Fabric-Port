package net.lunade.fastanim.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.client.render.entity.model.AnimalModel;
import net.minecraft.client.render.entity.model.OcelotEntityModel;
import net.minecraft.client.model.ModelPart;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(OcelotEntityModel.class)
public abstract class OcelotEntityModelMixin<T extends Entity> extends AnimalModel<T> {
   @Final
   @Shadow
   public ModelPart leftHindLeg;
   @Final
   @Shadow
   public ModelPart rightHindLeg;
   @Final
   @Shadow
   public ModelPart leftFrontLeg;
   @Final
   @Shadow
   public ModelPart rightFrontLeg;
   @Final
   @Shadow
   public ModelPart upperTail;
   @Final
   @Shadow
   public ModelPart lowerTail;
   @Final
   @Shadow
   public ModelPart head;
   @Final
   @Shadow
   public ModelPart body;
   @Shadow
   public int animationState = 1;

   @Inject(at = @At("HEAD"), method = "setAngles", cancellable = true)
   public void setAngles(T entity, float f, float g, float h, float i, float j, CallbackInfo info) {
      info.cancel();
      this.head.pitch = j * (float) (Math.PI / 180.0);
      this.head.yaw = i * (float) (Math.PI / 180.0);
      if (this.animationState != 3) {
         this.body.pitch = (float) (Math.PI / 2);
         float legPitch = (float)(Math.sin(f * 0.6662F + (float) Math.PI + (float) (Math.PI / 2)) * g);
         if (this.animationState == 2) {
            this.leftHindLeg.pitch = (float)(Math.sin(f * 0.6662F * g) + (float) (Math.PI / 2));
            this.rightHindLeg.pitch = (float)(Math.sin(f * 0.6662F + 0.3F + (float) (Math.PI / 2)) * g);
            this.leftFrontLeg.pitch = (float)(Math.sin(f * 0.6662F + (float) Math.PI + 0.3F + (float) (Math.PI / 2)) * g);
            this.rightFrontLeg.pitch = legPitch;
            this.lowerTail.pitch = (float)(1.7278761F + (float) (Math.PI / 10) * Math.sin(f + (float) (Math.PI / 2)) * g);
         } else {
            this.leftHindLeg.pitch = (float)(Math.sin(f * 0.6662F + (float) (Math.PI / 2)) * g);
            this.rightHindLeg.pitch = legPitch;
            this.leftFrontLeg.pitch = legPitch;
            this.rightFrontLeg.pitch = (float)(Math.sin(f * 0.6662F + (float) (Math.PI / 2)) * g);
            if (this.animationState == 1) {
               this.lowerTail.pitch = (float)(1.7278761F + (float) (Math.PI / 4) * Math.sin(f + (float) (Math.PI / 2)) * g);
            } else {
               this.lowerTail.pitch = (float)(1.7278761F + 0.47123894F * Math.sin(f + (float) (Math.PI / 2)) * g);
            }
         }
      }
   }

   @Inject(at = @At("HEAD"), method = "animateModel", cancellable = true)
   public void animateModel(T entity, float f, float g, float h, CallbackInfo info) {
      info.cancel();
      this.body.pivotY = 12.0F;
      this.body.pivotZ = -10.0F;
      this.head.pivotY = 15.0F;
      this.head.pivotZ = -9.0F;
      this.upperTail.pivotY = 15.0F;
      this.upperTail.pivotZ = 8.0F;
      this.lowerTail.pivotY = 20.0F;
      this.lowerTail.pivotZ = 14.0F;
      this.upperTail.pitch = 0.9F;
      if (entity.isInSneakingPose()) {
         this.body.pivotY = 13.0F;
         this.head.pivotY = 17.0F;
         this.upperTail.pivotY = 16.0F;
         this.lowerTail.pivotY = 16.0F;
         this.lowerTail.pivotZ = 16.0F;
         this.upperTail.pitch = (float) (Math.PI / 2);
         this.lowerTail.pitch = (float) (Math.PI / 2);
         this.animationState = 0;
      } else if (entity.isSprinting()) {
         this.lowerTail.pivotY = 20.0F;
         this.lowerTail.pivotZ = 16.0F;
         this.upperTail.pitch = (float) (Math.PI / 2);
         this.lowerTail.pitch = (float) (Math.PI / 2);
         this.animationState = 2;
      } else {
         this.animationState = 0;
      }
   }
}
