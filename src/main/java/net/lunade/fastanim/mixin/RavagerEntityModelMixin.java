package net.lunade.fastanim.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.RavagerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.client.render.entity.model.RavagerEntityModel;
import net.minecraft.client.model.ModelPart;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = RavagerEntityModel.class, priority = 900)
public class RavagerEntityModelMixin<T extends Entity> {
   @Final
   @Shadow
   private ModelPart root;
   @Final
   @Shadow
   private ModelPart head;
   @Final
   @Shadow
   private ModelPart jaw;
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
   @Final
   @Shadow
   private ModelPart neck;

   @Inject(at = @At("HEAD"), method = "setAngles*", cancellable = true)
   public void setAngles(RavagerEntity ravagerEntity, float f, float g, float h, float i, float j, CallbackInfo info) {
      info.cancel();
      this.head.pitch = j * (float) (Math.PI / 180.0);
      this.head.yaw = i * (float) (Math.PI / 180.0);
      float fastAngle = f * 0.6662F;
      float k = 0.4F * g;
      float firstAngle = (float)Math.sin(fastAngle + (float) (Math.PI / 2)) * k;
      float secondAngle = (float)Math.sin(fastAngle + (float) Math.PI + (float) (Math.PI / 2)) * k;
      this.rightHindLeg.pitch = firstAngle;
      this.leftHindLeg.pitch = secondAngle;
      this.rightFrontLeg.pitch = secondAngle;
      this.leftFrontLeg.pitch = firstAngle;
   }

   @Inject(at = @At("HEAD"), method = "animateModel*", cancellable = true)
   public void animateModel(RavagerEntity ravagerEntity, float f, float g, float h, CallbackInfo info) {
      info.cancel();
      int stunTick = ravagerEntity.getStunTick();
      int roarTick = ravagerEntity.getRoarTick();
      int attackTick = ravagerEntity.getAttackTick();
      if (attackTick > 0) {
         float n = MathHelper.wrap(attackTick - h, 10.0F);
         float o = (1.0F + n) * 0.5F;
         float p = o * o * o * 12.0F;
         float q = p * (float)Math.sin(this.neck.pitch);
         this.neck.pivotZ = -6.5F + p;
         this.neck.pivotY = -7.0F - q;
         this.jaw.pitch = attackTick > 5
            ? (float)Math.sin((-4 + attackTick - h) / 4.0F) * (float) (Math.PI * 2.0 / 5.0)
            : (float) (Math.PI / 20) * (float)Math.sin((float) Math.PI * (attackTick - h) * 0.01F);
      } else {
         float o = -1.0F * (float)Math.sin(this.neck.pitch);
         this.neck.pivotX = 0.0F;
         this.neck.pivotY = -7.0F - o;
         this.neck.pivotZ = 5.5F;
         boolean bl = stunTick > 0;
         this.neck.pitch = bl ? 0.21991149F : 0.0F;
         this.jaw.pitch = (float) Math.PI * (bl ? 0.05F : 0.01F);
         if (bl) {
            double d = stunTick / 40.0;
            this.neck.pivotX = (float)Math.sin(d * 10.0) * 3.0F;
         } else if (roarTick > 0) {
            float q = (float)Math.sin((20 - roarTick - h) * 0.03926991F);
            this.jaw.pitch = (float) (Math.PI / 2) * q;
         }
      }
   }
}
