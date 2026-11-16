package net.lunade.fastanim.mixin;

import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.client.render.entity.model.WitherEntityModel;
import net.minecraft.client.model.ModelPart;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = WitherEntityModel.class, priority = 900)
public class WitherEntityModelMixin<T extends WitherEntity> {
   @Final
   @Shadow
   private ModelPart centerHead;
   @Final
   @Shadow
   private ModelPart rightHead;
   @Final
   @Shadow
   private ModelPart leftHead;
   @Final
   @Shadow
   private ModelPart ribcage;
   @Final
   @Shadow
   private ModelPart tail;

   @Inject(at = @At("HEAD"), method = "setAngles*", cancellable = true)
   public void setAngles(T witherEntity, float f, float g, float h, float i, float j, CallbackInfo info) {
      info.cancel();
      float k = (float)Math.sin(h * 0.1F + (float) (Math.PI / 2));
      float ribPitch = (0.065F + 0.05F * k) * (float) Math.PI;
      this.ribcage.pitch = ribPitch;
      this.tail.setPivot(-2.0F, 6.9F + (float)Math.sin(ribPitch + (float) (Math.PI / 2)) * 10.0F, -0.5F + (float)Math.sin(ribPitch) * 10.0F);
      this.tail.pitch = (0.265F + 0.1F * k) * (float) Math.PI;
      this.centerHead.yaw = i * (float) (Math.PI / 180.0);
      this.centerHead.pitch = j * (float) (Math.PI / 180.0);
      float bodyYaw = witherEntity.bodyYaw;
      this.rightHead.yaw = (witherEntity.getHeadYaw(0) - bodyYaw) * (float) (Math.PI / 180.0);
      this.rightHead.pitch = witherEntity.getHeadPitch(0) * (float) (Math.PI / 180.0);
      this.leftHead.yaw = (witherEntity.getHeadYaw(1) - bodyYaw) * (float) (Math.PI / 180.0);
      this.leftHead.pitch = witherEntity.getHeadPitch(1) * (float) (Math.PI / 180.0);
   }

   @Inject(at = @At("HEAD"), method = "animateModel*", cancellable = true)
   public void animateModel(T witherEntity, float f, float g, float h, CallbackInfo info) {
      info.cancel();
   }

   @Inject(at = @At("HEAD"), method = "rotateHead", cancellable = true)
   private static <T extends WitherEntity> void rotateHead(T witherEntity, ModelPart modelPart, int i, CallbackInfo info) {
      info.cancel();
      modelPart.yaw = (witherEntity.getHeadYaw(i) - witherEntity.bodyYaw) * (float) (Math.PI / 180.0);
      modelPart.pitch = witherEntity.getHeadPitch(i) * (float) (Math.PI / 180.0);
   }
}
