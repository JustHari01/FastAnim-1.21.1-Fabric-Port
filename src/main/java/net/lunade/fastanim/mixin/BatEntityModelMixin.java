package net.lunade.fastanim.mixin;

import net.minecraft.entity.passive.BatEntity;
import net.minecraft.client.render.entity.model.BatEntityModel;
import net.minecraft.client.model.ModelPart;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BatEntityModel.class)
public class BatEntityModelMixin {
   @Final
   @Shadow
   private ModelPart head;
   @Final
   @Shadow
   private ModelPart body;
   @Final
   @Shadow
   private ModelPart rightWing;
   @Final
   @Shadow
   private ModelPart leftWing;
   @Final
   @Shadow
   private ModelPart rightWingTip;
   @Final
   @Shadow
   private ModelPart leftWingTip;

   @Inject(at = @At("HEAD"), method = "setAngles", cancellable = true)
   public void setAngles(BatEntity batEntity, float f, float g, float h, float i, float j, CallbackInfo info) {
      info.cancel();
      this.head.pitch = j * (float) (Math.PI / 180.0);
      if (batEntity.isRoosting()) {
         this.head.yaw = (float) Math.PI - i * (float) (Math.PI / 180.0);
         this.head.roll = (float) Math.PI;
         this.head.setPivot(0.0F, -2.0F, 0.0F);
         this.rightWing.setPivot(-3.0F, 0.0F, 3.0F);
         this.leftWing.setPivot(3.0F, 0.0F, 3.0F);
         this.body.pitch = (float) Math.PI;
         this.rightWing.pitch = (float) (-Math.PI / 20);
         this.rightWing.yaw = (float) (-Math.PI * 2.0 / 5.0);
         this.rightWingTip.yaw = -1.7278761F;
         this.leftWing.pitch = (float) (-Math.PI / 20);
         this.leftWing.yaw = (float) (Math.PI * 2.0 / 5.0);
         this.leftWingTip.yaw = 1.7278761F;
      } else {
         this.head.yaw = i * (float) (Math.PI / 180.0);
         this.head.roll = 0.0F;
         this.head.setPivot(0.0F, 0.0F, 0.0F);
         this.rightWing.setPivot(0.0F, 0.0F, 0.0F);
         this.leftWing.setPivot(0.0F, 0.0F, 0.0F);
         this.body.pitch = (float) (Math.PI / 4) + (float)Math.cos(h * 0.1F + (float) (Math.PI / 2)) * 0.15F;
         this.body.yaw = 0.0F;
         float yaw = (float)Math.sin(h * 1.3F + (float) (Math.PI / 2)) * (float) (Math.PI / 4);
         this.rightWing.yaw = yaw;
         this.leftWing.yaw = -yaw;
         float yawHalf = yaw * 0.5F;
         this.rightWingTip.yaw = yawHalf;
         this.leftWingTip.yaw = -yawHalf;
      }
   }
}
