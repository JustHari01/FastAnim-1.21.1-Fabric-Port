package net.lunade.fastanim.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.client.render.entity.model.VillagerResemblingModel;
import net.minecraft.client.model.ModelPart;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = VillagerResemblingModel.class, priority = 900)
public class VillagerResemblingModelMixin<T extends Entity> {
   @Final
   @Shadow
   private ModelPart root;
   @Final
   @Shadow
   private ModelPart head;
   @Final
   @Shadow
   private ModelPart hat;
   @Final
   @Shadow
   private ModelPart hatRim;
   @Final
   @Shadow
   private ModelPart rightLeg;
   @Final
   @Shadow
   private ModelPart leftLeg;
   @Final
   @Shadow
   public ModelPart nose;

   @Inject(at = @At("HEAD"), method = "setAngles", cancellable = true)
   public void setAngles(T entity, float f, float g, float h, float i, float j, CallbackInfo info) {
      info.cancel();
      boolean bl = false;
      if (entity instanceof MerchantEntity merchant) {
         bl = merchant.getHeadRollingTimeLeft() > 0;
      }

      this.head.yaw = i * (float) (Math.PI / 180.0);
      if (bl) {
         this.head.roll = 0.3F * (float)Math.sin(0.45F * h);
         this.head.pitch = 0.4F;
      } else {
         this.head.roll = 0.0F;
         this.head.pitch = j * (float) (Math.PI / 180.0);
      }

      float fastAngle = f * 0.6662F;
      float one4FG = 0.7F * g;
      this.rightLeg.pitch = (float)Math.sin(fastAngle + (float) (Math.PI / 2)) * one4FG;
      this.leftLeg.pitch = (float)Math.sin(fastAngle + (float) Math.PI + (float) (Math.PI / 2)) * one4FG;
   }
}
