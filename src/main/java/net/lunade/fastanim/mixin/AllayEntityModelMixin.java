package net.lunade.fastanim.mixin;

import net.minecraft.util.math.MathHelper;
import net.minecraft.client.render.entity.model.ModelWithArms;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.entity.passive.AllayEntity;
import net.minecraft.client.render.entity.model.AllayEntityModel;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AllayEntityModel.class)
public abstract class AllayEntityModelMixin extends SinglePartEntityModel<AllayEntity> implements ModelWithArms {
   @Final
   @Shadow
   private ModelPart root;
   @Final
   @Shadow
   private ModelPart head;
   @Final
   @Shadow
   private ModelPart body;
   @Final
   @Shadow
   private ModelPart rightArm;
   @Final
   @Shadow
   private ModelPart leftArm;
   @Final
   @Shadow
   private ModelPart rightWing;
   @Final
   @Shadow
   private ModelPart leftWing;

   @Inject(at = @At("HEAD"), method = "getTexturedModelData()Lnet/minecraft/client/model/TexturedModelData;", cancellable = true)
   private static void getTexturedModelData(CallbackInfoReturnable<TexturedModelData> info) {
      ModelData modelData = new ModelData();
      ModelPartData modelPartData = modelData.getRoot();
      ModelPartData modelPartData2 = modelPartData.addChild("root", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 23.5F, 0.0F));
      modelPartData2.addChild(
         "head",
         ModelPartBuilder.create().uv(0, 0).cuboid(-2.5F, -5.0F, -2.5F, 5.0F, 5.0F, 5.0F, new Dilation(0.0F)),
         ModelTransform.pivot(0.0F, -3.99F, 0.0F)
      );
      ModelPartData modelPartData3 = modelPartData2.addChild(
         "body",
         ModelPartBuilder.create()
            .uv(0, 10)
            .cuboid(-1.5F, 0.0F, -1.0F, 3.0F, 4.0F, 2.0F, new Dilation(0.0F))
            .uv(0, 16)
            .cuboid(-1.5F, 0.0F, -1.0F, 3.0F, 5.0F, 2.0F, new Dilation(-0.2F)),
         ModelTransform.pivot(0.0F, -4.0F, 0.0F)
      );
      modelPartData3.addChild(
         "right_arm",
         ModelPartBuilder.create().uv(23, 0).cuboid(-0.75F, -0.5F, -1.0F, 1.0F, 4.0F, 2.0F, new Dilation(-0.01F)),
         ModelTransform.pivot(-1.75F, 0.5F, 0.0F)
      );
      modelPartData3.addChild(
         "left_arm",
         ModelPartBuilder.create().uv(23, 6).cuboid(-0.25F, -0.5F, -1.0F, 1.0F, 4.0F, 2.0F, new Dilation(-0.01F)),
         ModelTransform.pivot(1.75F, 0.5F, 0.0F)
      );
      modelPartData3.addChild(
         "right_wing",
         ModelPartBuilder.create().uv(16, 14).cuboid(0.0F, 1.0F, 0.0F, 0.0F, 5.0F, 8.0F, new Dilation(0.0F)),
         ModelTransform.of(-0.5F, 0.0F, 0.65F, 0.43633232F, 0.0F, 0.0F)
      );
      modelPartData3.addChild(
         "left_wing",
         ModelPartBuilder.create().uv(16, 14).cuboid(0.0F, 1.0F, 0.0F, 0.0F, 5.0F, 8.0F, new Dilation(0.0F)),
         ModelTransform.of(0.5F, 0.0F, 0.65F, 0.43633232F, 0.0F, 0.0F)
      );
      info.setReturnValue(TexturedModelData.of(modelData, 32, 32));
   }

   @Inject(at = @At("HEAD"), method = "setAngles(Lnet/minecraft/entity/passive/AllayEntity;FFFFF)V", cancellable = true)
   public void setAngles(
      AllayEntity allayEntity, float limbAngle, float limbDistance, float animationProgress, float headPitch, float headYaw, CallbackInfo info
   ) {
      info.cancel();
      this.getPart().traverse().forEach(ModelPart::resetTransform);
      float m = animationProgress - allayEntity.age;
      float n = animationProgress * 0.15707962F;
      float o = Math.min(limbDistance * 3.3333F, 1.0F);
      float p = 1.0F - o;
      float q = allayEntity.method_43397(m);
      if (allayEntity.isDancing()) {
         float cos = (float)Math.sin(animationProgress * 0.13962634F + limbDistance + (float) (Math.PI / 2));
         float danceProgress = allayEntity.method_44368(m);
         this.root.yaw = allayEntity.isSpinning() ? (float) (Math.PI * 4) * danceProgress : this.root.yaw;
         float danceProg2 = 1.0F - danceProgress;
         this.root.roll = cos * 0.27925268F * danceProg2;
         this.head.yaw = cos * 0.52359873F * danceProg2;
         this.head.roll = cos * 0.24434608F * danceProg2;
      } else {
         this.head.pitch = headYaw * (float) (Math.PI / 180.0);
         this.head.yaw = headPitch * (float) (Math.PI / 180.0);
      }

      this.rightWing.pitch = 0.43633232F;
      this.leftWing.pitch = 0.43633232F;
      float wingYaws = -0.61086524F + (float)Math.sin(animationProgress * (float) (Math.PI / 9) + limbDistance + (float) (Math.PI / 2)) * 0.4712389F;
      this.rightWing.yaw = wingYaws;
      this.leftWing.yaw = -wingYaws;
      float r = o * (float) (Math.PI * 2.0 / 9.0);
      this.body.pitch = r;
      this.root.pivotY = 23.5F + (float)Math.sin(n + (float) (Math.PI / 2)) * 0.25F * p;
      float pitches = MathHelper.lerp(q, r, MathHelper.lerp(o, (float) (-Math.PI / 3), (float) (-Math.PI / 4)));
      this.rightArm.pitch = pitches;
      this.leftArm.pitch = pitches;
      float rolls = 0.43633232F - (float)Math.sin(n + (float) (Math.PI * 3.0 / 2.0) + (float) (Math.PI / 2)) * 0.23561946F * p * (1.0F - q);
      this.rightArm.roll = rolls;
      this.leftArm.roll = -rolls;
      float yaws = 0.27925268F * q;
      this.rightArm.yaw = yaws;
      this.leftArm.yaw = -yaws;
   }
}
