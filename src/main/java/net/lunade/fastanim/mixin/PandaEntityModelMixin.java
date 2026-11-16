package net.lunade.fastanim.mixin;

import net.minecraft.entity.passive.PandaEntity;
import net.minecraft.client.model.ModelUtil;
import net.minecraft.client.render.entity.model.PandaEntityModel;
import net.minecraft.client.render.entity.model.QuadrupedEntityModel;
import net.minecraft.client.model.ModelPart;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = PandaEntityModel.class, priority = 900)
public abstract class PandaEntityModelMixin<T extends PandaEntity> extends QuadrupedEntityModel<T> {
   @Shadow
   private float sittingAnimationProgress;
   @Shadow
   private float lieOnBackAnimationProgress;
   @Shadow
   private float playAnimationProgress;

   protected PandaEntityModelMixin(ModelPart modelPart, boolean bl, float f, float g, float h, float i, int j) {
      super(modelPart, bl, f, g, h, i, j);
   }

   @Inject(at = @At("HEAD"), method = "setAngles*", cancellable = true)
   public void setAngles(T pandaEntity, float limbAngle, float limbDistance, float h, float i, float j, CallbackInfo info) {
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
      boolean askingForBamboo = pandaEntity.getAskForBambooTicks() > 0;
      boolean sneezing = pandaEntity.isSneezing();
      int sneezeProgress = pandaEntity.getSneezeProgress();
      boolean eating = pandaEntity.isEating();
      boolean scared = pandaEntity.isScaredByThunderstorm();
      if (askingForBamboo) {
         float headYawRoll = 0.35F * (float)Math.sin(0.6F * h);
         this.head.yaw = headYawRoll;
         this.head.roll = headYawRoll;
         float frontLegPitches = 0.75F * (float)Math.sin(0.3F * h);
         this.rightFrontLeg.pitch = -frontLegPitches;
         this.leftFrontLeg.pitch = frontLegPitches;
      } else {
         this.head.roll = 0.0F;
      }

      if (sneezing) {
         if (sneezeProgress < 15) {
            this.head.pitch = (float) (-Math.PI / 4) * sneezeProgress * 0.071433F;
         } else if (sneezeProgress < 20) {
            float l = (sneezeProgress - 15) * 0.2F;
            this.head.pitch = (float) (-Math.PI / 4) + (float) (Math.PI / 4) * l;
         }
      }

      if (this.sittingAnimationProgress > 0.0F) {
         this.body.pitch = ModelUtil.interpolateAngle(this.body.pitch, 1.7407963F, this.sittingAnimationProgress);
         this.head.pitch = ModelUtil.interpolateAngle(this.head.pitch, (float) (Math.PI / 2), this.sittingAnimationProgress);
         this.rightFrontLeg.roll = -0.27079642F;
         this.leftFrontLeg.roll = 0.27079642F;
         this.rightHindLeg.roll = 0.5707964F;
         this.leftHindLeg.roll = -0.5707964F;
         if (eating) {
            float sinTwo = 0.2F * (float)Math.sin(h * 0.6F);
            this.head.pitch = (float) (Math.PI / 2) + sinTwo;
            float legPitches = -0.4F - sinTwo;
            this.rightFrontLeg.pitch = legPitches;
            this.leftFrontLeg.pitch = legPitches;
         }

         if (scared) {
            this.head.pitch = 2.1707964F;
            this.rightFrontLeg.pitch = -0.9F;
            this.leftFrontLeg.pitch = -0.9F;
         }
      } else {
         this.rightHindLeg.roll = 0.0F;
         this.leftHindLeg.roll = 0.0F;
         this.rightFrontLeg.roll = 0.0F;
         this.leftFrontLeg.roll = 0.0F;
      }

      if (this.lieOnBackAnimationProgress > 0.0F) {
         float hind = 0.6F * (float)Math.sin(h * 0.15F);
         this.rightHindLeg.pitch = -hind;
         this.leftHindLeg.pitch = hind;
         float front = 0.3F * (float)Math.sin(h * 0.25F);
         this.rightFrontLeg.pitch = front;
         this.leftFrontLeg.pitch = -front;
         this.head.pitch = ModelUtil.interpolateAngle(this.head.pitch, (float) (Math.PI / 2), this.lieOnBackAnimationProgress);
      }

      if (this.playAnimationProgress > 0.0F) {
         this.head.pitch = ModelUtil.interpolateAngle(this.head.pitch, 2.0561945F, this.playAnimationProgress);
         float legs = 0.5F * (float)Math.sin(h * 0.5F);
         this.rightHindLeg.pitch = -legs;
         this.leftHindLeg.pitch = legs;
         this.rightFrontLeg.pitch = legs;
         this.leftFrontLeg.pitch = -legs;
      }
   }
}
