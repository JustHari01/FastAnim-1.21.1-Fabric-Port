package net.lunade.fastanim.mixin;

import net.minecraft.entity.passive.StriderEntity;
import net.minecraft.client.render.entity.model.StriderEntityModel;
import net.minecraft.client.model.ModelPart;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = StriderEntityModel.class, priority = 900)
public class StriderEntityModelMixin<T extends StriderEntity> {
   @Final
   @Shadow
   private ModelPart root;
   @Final
   @Shadow
   private ModelPart rightLeg;
   @Final
   @Shadow
   private ModelPart leftLeg;
   @Final
   @Shadow
   private ModelPart body;
   @Final
   @Shadow
   private ModelPart rightBottomBristle;
   @Final
   @Shadow
   private ModelPart rightMiddleBristle;
   @Final
   @Shadow
   private ModelPart rightTopBristle;
   @Final
   @Shadow
   private ModelPart leftTopBristle;
   @Final
   @Shadow
   private ModelPart leftMiddleBristle;
   @Final
   @Shadow
   private ModelPart leftBottomBristle;

   @Inject(at = @At("HEAD"), method = "setAngles*", cancellable = true)
   public void setAngles(T striderEntity, float f, float g, float h, float i, float j, CallbackInfo info) {
      info.cancel();
      g = Math.min(0.25F, g);
      if (!striderEntity.hasPassengers()) {
         this.body.pitch = j * (float) (Math.PI / 180.0);
         this.body.yaw = i * (float) (Math.PI / 180.0);
      } else {
         this.body.pitch = 0.0F;
         this.body.yaw = 0.0F;
      }

      float fastAngleOne = f * 1.5F;
      float fastAngleTwo = fastAngleOne * 0.5F;
      float fastAngleThree = fastAngleTwo + (float) Math.PI;
      float g2 = 2.0F * g;
      this.body.roll = 0.1F * (float)Math.sin(fastAngleOne) * 4.0F * g;
      this.body.pivotY = 2.0F;
      this.body.pivotY = this.body.pivotY - 2.0F * (float)Math.sin(fastAngleOne + (float) (Math.PI / 2)) * g2;
      float sinTwo = (float)Math.sin(fastAngleTwo) * g2;
      this.rightLeg.pivotY = 8.0F + 2.0F * sinTwo;
      this.leftLeg.roll = (float) (Math.PI / 18) * (float)Math.sin(fastAngleTwo + (float) (Math.PI / 2)) * g;
      this.leftLeg.pitch = sinTwo;
      float sinThree = (float)Math.sin(fastAngleThree) * g2;
      this.rightLeg.pitch = sinThree;
      this.rightLeg.roll = (float) (Math.PI / 18) * (float)Math.sin(fastAngleThree + (float) (Math.PI / 2)) * g;
      this.leftLeg.pivotY = 8.0F + 2.0F * sinThree;
      this.rightBottomBristle.roll = -1.2217305F;
      this.rightMiddleBristle.roll = -1.134464F;
      this.rightTopBristle.roll = -0.87266463F;
      this.leftTopBristle.roll = 0.87266463F;
      this.leftMiddleBristle.roll = 1.134464F;
      this.leftBottomBristle.roll = 1.2217305F;
      float l = (float)Math.cos(f * 1.5F + (float) Math.PI) * g;
      float l3 = l * 1.3F;
      float l2 = l * 1.2F;
      float l6 = l * 0.6F;
      this.rightBottomBristle.roll += l3;
      this.rightMiddleBristle.roll += l2;
      this.rightTopBristle.roll += l6;
      this.leftTopBristle.roll += l6;
      this.leftMiddleBristle.roll += l2;
      this.leftBottomBristle.roll += l3;
      float sinNeg4 = 0.05F * (float)Math.sin(h * -0.4F);
      float sin4 = 0.1F * (float)Math.sin(h * 0.4F);
      float sin2 = 0.1F * (float)Math.sin(h * 0.2F);
      this.rightBottomBristle.roll += sinNeg4;
      this.rightMiddleBristle.roll += sin2;
      this.rightTopBristle.roll += sin4;
      this.leftTopBristle.roll += sin4;
      this.leftMiddleBristle.roll += sin2;
      this.leftBottomBristle.roll += sinNeg4;
   }
}
