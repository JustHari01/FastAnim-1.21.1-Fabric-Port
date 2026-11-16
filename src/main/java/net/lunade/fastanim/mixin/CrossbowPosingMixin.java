package net.lunade.fastanim.mixin;

import net.minecraft.util.Arm;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.CrossbowItem;
import net.minecraft.util.math.MathHelper;
import net.minecraft.client.render.entity.model.CrossbowPosing;
import net.minecraft.client.model.ModelPart;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CrossbowPosing.class)
public class CrossbowPosingMixin {
   @Inject(at = @At("HEAD"), method = "charge", cancellable = true)
   private static void charge(ModelPart modelPart, ModelPart modelPart2, LivingEntity livingEntity, boolean bl, CallbackInfo info) {
      info.cancel();
      ModelPart modelPart3 = bl ? modelPart : modelPart2;
      ModelPart modelPart4 = bl ? modelPart2 : modelPart;
      modelPart3.yaw = bl ? -0.8F : 0.8F;
      modelPart3.pitch = -0.97079635F;
      modelPart4.pitch = modelPart3.pitch;
      float f = CrossbowItem.getPullTime(livingEntity.getActiveItem(), livingEntity);
      float g = MathHelper.clamp(livingEntity.getItemUseTime(), 0.0F, f);
      float h = g / f;
      modelPart4.yaw = (0.4F + h * 0.45F) * (bl ? 1 : -1);
      modelPart4.pitch = modelPart4.pitch + h * ((float) (-Math.PI / 2) - modelPart4.pitch);
   }

   @Inject(at = @At("HEAD"), method = "meleeAttack", cancellable = true)
   private static <T extends MobEntity> void meleeAttack(ModelPart modelPart, ModelPart modelPart2, T mobEntity, float f, float g, CallbackInfo info) {
      info.cancel();
      float h = (float)Math.sin(f * (float) Math.PI);
      float i = (float)Math.sin((1.0F - (1.0F - f) * (1.0F - f)) * (float) Math.PI);
      modelPart.roll = 0.0F;
      modelPart2.roll = 0.0F;
      modelPart.yaw = (float) (Math.PI / 20);
      modelPart2.yaw = (float) (-Math.PI / 20);
      float i04 = i * 0.4F;
      if (mobEntity.getMainArm() == Arm.RIGHT) {
         modelPart.pitch = -1.8849558F + (float)Math.sin(g * 0.09F + (float) (Math.PI / 2)) * 0.15F;
         modelPart2.pitch = -0.0F + (float)Math.sin(g * 0.19F + (float) (Math.PI / 2)) * 0.5F;
         modelPart.pitch += h * 2.2F - i04;
         modelPart2.pitch += h * 1.2F - i04;
      } else {
         modelPart.pitch = -0.0F + (float)Math.sin(g * 0.19F + (float) (Math.PI / 2)) * 0.5F;
         modelPart2.pitch = -1.8849558F + (float)Math.sin(g * 0.09F + (float) (Math.PI / 2)) * 0.15F;
         modelPart.pitch += h * 1.2F - i04;
         modelPart2.pitch += h * 2.2F - i04;
      }

      swingArms(modelPart, modelPart2, g);
   }

   @Inject(at = @At("HEAD"), method = "meleeAttack(Lnet/minecraft/client/model/ModelPart;Lnet/minecraft/client/model/ModelPart;ZFF)V", cancellable = true)
   private static void meleeAttack(ModelPart modelPart, ModelPart modelPart2, boolean bl, float f, float g, CallbackInfo info) {
      info.cancel();
      float h = (float)Math.sin(f * (float) Math.PI);
      float oneMinusF = 1.0F - f;
      float i = (float)Math.sin((1.0F - oneMinusF * oneMinusF) * (float) Math.PI);
      modelPart2.roll = 0.0F;
      modelPart.roll = 0.0F;
      float yaws = 0.1F - h * 0.6F;
      modelPart2.yaw = -yaws;
      modelPart.yaw = yaws;
      float j = (float) -Math.PI / (bl ? 1.5F : 2.25F);
      modelPart2.pitch = j;
      modelPart.pitch = j;
      float pitches = h * 1.2F - i * 0.4F;
      modelPart2.pitch += pitches;
      modelPart.pitch += pitches;
      swingArms(modelPart2, modelPart, g);
   }

   @Inject(at = @At("HEAD"), method = "swingArms", cancellable = true)
   private static void swingArms(ModelPart modelPart, ModelPart modelPart2, float f, CallbackInfo info) {
      info.cancel();
      float roll = (float)Math.sin(f * 0.09F + (float) (Math.PI / 2)) * 0.05F + 0.05F;
      float pitch = (float)Math.sin(f * 0.067F) * 0.05F;
      modelPart.roll += roll;
      modelPart.pitch += pitch;
      modelPart2.roll -= roll;
      modelPart2.pitch -= pitch;
   }

   @Shadow
   private static void swingArms(ModelPart modelPart, ModelPart modelPart2, float f) {
   }
}
