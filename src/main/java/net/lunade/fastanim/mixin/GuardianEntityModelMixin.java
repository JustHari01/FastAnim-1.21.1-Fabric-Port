package net.lunade.fastanim.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.GuardianEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.render.entity.model.GuardianEntityModel;
import net.minecraft.client.model.ModelPart;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuardianEntityModel.class)
public abstract class GuardianEntityModelMixin extends SinglePartEntityModel<GuardianEntity> {
   @Final
   @Shadow
   private ModelPart root;
   @Final
   @Shadow
   private ModelPart head;
   @Final
   @Shadow
   private ModelPart eye;
   @Final
   @Shadow
   private ModelPart[] tail;

   @Inject(at = @At("HEAD"), method = "setAngles", cancellable = true)
   public void setAngles(GuardianEntity guardianEntity, float f, float g, float h, float i, float j, CallbackInfo info) {
      info.cancel();
      float k = h - guardianEntity.age;
      this.head.yaw = i * (float) (Math.PI / 180.0);
      this.head.pitch = j * (float) (Math.PI / 180.0);
      float l = (1.0F - guardianEntity.getSpikesExtension(k)) * 0.55F;
      this.updateSpikeExtensions(h, l);
      Entity entity = MinecraftClient.getInstance().getCameraEntity();
      if (guardianEntity.hasBeamTarget()) {
         entity = guardianEntity.getBeamTarget();
      }

      if (entity != null) {
         Vec3d vec3d = entity.getCameraPosVec(0.0F);
         Vec3d vec3d2 = guardianEntity.getCameraPosVec(0.0F);
         double d = vec3d.y - vec3d2.y;
         this.eye.pivotY = d > 0.0 ? 0.0F : 1.0F;
         Vec3d vec3d3 = guardianEntity.getRotationVec(0.0F);
         vec3d3 = new Vec3d(vec3d3.x, 0.0, vec3d3.z);
         Vec3d vec3d4 = new Vec3d(vec3d2.x - vec3d.x, 0.0, vec3d2.z - vec3d.z)
            .normalize()
            .rotateY((float) (Math.PI / 2));
         double e = vec3d3.dotProduct(vec3d4);
         this.eye.pivotX = (float)(Math.sqrt(Math.abs(e)) * 2.0 * Math.signum(e));
      }

      this.eye.visible = true;
      float tailAngleSin = (float)Math.sin(guardianEntity.getTailAngle(k));
      this.tail[0].yaw = tailAngleSin * (float) (Math.PI / 20);
      this.tail[1].yaw = tailAngleSin * (float) (Math.PI / 10);
      this.tail[2].yaw = tailAngleSin * 0.4712389F;
   }

   @Shadow
   private void updateSpikeExtensions(float f, float g) {
   }
}
