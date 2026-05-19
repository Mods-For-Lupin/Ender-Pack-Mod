package io.github.jason13official.ender_pack.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import io.github.jason13official.ender_pack.EnderPackClient;
import io.github.jason13official.ender_pack.impl.api.client.renderer.EnderPackRenderStateAccessor;
import io.github.jason13official.ender_pack.impl.common.registry.ModItems;
import io.github.jason13official.ender_pack.platform.Services;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/// AvatarRenderer calls to this submit as a superclass
/// but elytra rotation extraction also comes
/// from HumanoidMobRenderer
@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRendererMixin<T extends LivingEntity, S extends LivingEntityRenderState, M extends EntityModel<? super S>> extends EntityRenderer<T, S> implements
    RenderLayerParent<S, M> {

  @Final
  @Shadow
  protected ItemModelResolver itemModelResolver;

  protected LivingEntityRendererMixin(Context context) {
    super(context);
  }

  @Inject(at = @At("TAIL"), method = "extractRenderState(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/client/renderer/entity/state/LivingEntityRenderState;F)V")
  private void ender_pack$extractRenderState(T entity, S state, float partialTicks, CallbackInfo ci) {
    EnderPackRenderStateAccessor stateAccessor = (EnderPackRenderStateAccessor) state;

    if (Services.PLATFORM.equipped(entity) && Services.PLATFORM.shouldRenderOnBack(entity)) {
      stateAccessor.ender_pack$setEnderPack(true);
      stateAccessor.ender_pack$setRenderEnderPack(true);
      this.itemModelResolver.updateForLiving(stateAccessor.ender_pack$getEnderPack(), new ItemStack(ModItems.ENDER_PACK), ItemDisplayContext.FIXED, entity);
    } else {
      stateAccessor.ender_pack$setEnderPack(false);
      stateAccessor.ender_pack$setRenderEnderPack(false);
      stateAccessor.ender_pack$getEnderPack().clear();
    }
  }

  @Inject(at = @At("TAIL"), method = "submit(Lnet/minecraft/client/renderer/entity/state/LivingEntityRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;Lnet/minecraft/client/renderer/state/level/CameraRenderState;)V")
  private void ender_pack$submit(S state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState camera, CallbackInfo ci) {
    EnderPackClient.submitEnderPack(state, poseStack, submitNodeCollector);
  }
}
