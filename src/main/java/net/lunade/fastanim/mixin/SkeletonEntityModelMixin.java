package net.lunade.fastanim.mixin;

import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.client.render.entity.model.CrossbowPosing;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.SkeletonEntityModel;
import net.minecraft.client.model.ModelPart;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = SkeletonEntityModel.class, priority = 900)
public abstract class SkeletonEntityModelMixin<T extends MobEntity & RangedAttackMob> extends BipedEntityModel<T> {
   public SkeletonEntityModelMixin(ModelPart modelPart) {
      super(modelPart);
   }

   @Inject(at = @At("HEAD"), method = "setAngles*", cancellable = true)
   public void setAngles(T mobEntity, float f, float g, float h, float i, float j, CallbackInfo info) {
      info.cancel();
      super.setAngles(mobEntity, f, g, h, i, j);
      ItemStack itemStack = mobEntity.getMainHandStack();
      if (mobEntity.isAttacking() && (itemStack.isEmpty() || !itemStack.isOf(Items.BOW))) {
         float k = (float)Math.sin(this.handSwingProgress * (float) Math.PI);
         float l = (float)Math.sin((1.0F - (1.0F - this.handSwingProgress) * (1.0F - this.handSwingProgress)) * (float) Math.PI);
         this.rightArm.roll = 0.0F;
         this.leftArm.roll = 0.0F;
         float yaws = 0.1F - k * 0.6F;
         this.rightArm.yaw = -yaws;
         this.leftArm.yaw = yaws;
         this.rightArm.pitch = (float) (-Math.PI / 2);
         this.leftArm.pitch = (float) (-Math.PI / 2);
         float pitches = k * 1.2F - l * 0.4F;
         this.rightArm.pitch -= pitches;
         this.leftArm.pitch -= pitches;
         CrossbowPosing.swingArms(this.rightArm, this.leftArm, h);
      }
   }
}
