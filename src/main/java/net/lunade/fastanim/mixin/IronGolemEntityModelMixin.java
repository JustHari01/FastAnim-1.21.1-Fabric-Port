package net.lunade.fastanim.mixin;

import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.client.render.entity.model.IronGolemEntityModel;
import net.minecraft.client.model.ModelPart;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(IronGolemEntityModel.class)
public class IronGolemEntityModelMixin<T extends IronGolemEntity> {
   @Final
   @Shadow
   private ModelPart head;
   @Final
   @Shadow
   private ModelPart rightArm;
   @Final
   @Shadow
   private ModelPart leftArm;
   @Final
   @Shadow
   private ModelPart rightLeg;
   @Final
   @Shadow
   private ModelPart leftLeg;

   @Inject(at = @At("HEAD"), method = "setAngles", cancellable = true)
   public void setAngles(T ironGolemEntity, float f, float g, float h, float i, float j, CallbackInfo info) {
      info.cancel();
      this.head.yaw = i * (float) (Math.PI / 180.0);
      this.head.pitch = j * (float) (Math.PI / 180.0);
      float legPitches = 1.5F * (Math.abs(f % 13.0F - 6.5F) - 3.25F) * 0.30769232F * g;
      this.rightLeg.pitch = -legPitches;
      this.leftLeg.pitch = legPitches;
      this.rightLeg.yaw = 0.0F;
      this.leftLeg.yaw = 0.0F;
   }

   @Inject(at = @At("HEAD"), method = "animateModel", cancellable = true)
   public void animateModel(T ironGolemEntity, float f, float g, float h, CallbackInfo info) {
      info.cancel();
      int attackTicksLeft = ironGolemEntity.getAttackTicksLeft();
      if (attackTicksLeft > 0) {
         float attackAngle = -2.0F + 1.5F * MathHelper.wrap(attackTicksLeft - h, 10.0F);
         this.rightArm.pitch = attackAngle;
         this.leftArm.pitch = attackAngle;
      } else {
         int villagerTicks = ironGolemEntity.getLookingAtVillagerTicks();
         if (villagerTicks > 0) {
            this.rightArm.pitch = -0.8F + 0.025F * MathHelper.wrap(villagerTicks, 70.0F);
            this.leftArm.pitch = 0.0F;
         } else {
            float angle = (Math.abs(f % 13.0F - 6.5F) - 3.25F) * 0.30769232F;
            this.rightArm.pitch = (-0.2F + angle) * g;
            this.leftArm.pitch = (-0.2F - angle) * g;
         }
      }
   }
}
