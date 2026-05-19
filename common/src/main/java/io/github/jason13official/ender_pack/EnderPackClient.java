package io.github.jason13official.ender_pack;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import io.github.jason13official.ender_pack.impl.api.client.renderer.EnderPackRenderStateAccessor;
import io.github.jason13official.ender_pack.impl.common.network.ModePacket;
import java.util.function.Consumer;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public class EnderPackClient {

  public static final Identifier CONTAINER_BACKGROUND = EnderPack.identifier("textures/gui/container/ender_pack.png");
  public static Consumer<CustomPacketPayload> serverBoundPacketSender;

  public static void init() {
  }

  public static void sendModePacketToServer() {
    if (serverBoundPacketSender != null) {
      serverBoundPacketSender.accept(new ModePacket());
    }
  }

  public static <S extends LivingEntityRenderState> void submitEnderPack(S state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector) {
    EnderPackRenderStateAccessor stateAccessor = (EnderPackRenderStateAccessor) state;
    if (!stateAccessor.ender_pack$hasEnderPack()) {
      return;
    }

    if (!stateAccessor.ender_pack$shouldRenderEnderPack()) {
      return;
    }

    ItemStackRenderState enderPack = stateAccessor.ender_pack$getEnderPack();
    if (enderPack.isEmpty()) {
      return;
    }

    poseStack.pushPose();

    poseStack.mulPose(Axis.YP.rotationDegrees(180.0F - state.bodyRot));

    boolean isFallFlying = false;
    if (state instanceof AvatarRenderState avState) {
      if (avState.isFallFlying) {
        isFallFlying = true;
        float flyScale = avState.fallFlyingScale();
        poseStack.mulPose(Axis.XP.rotationDegrees(flyScale * (-90.0F - state.xRot)));
        if (avState.shouldApplyFlyingYRot) {
          poseStack.mulPose(Axis.YP.rotation(avState.flyingYRot));
        }
      } else if (avState.isCrouching) {
        poseStack.mulPose(Axis.XP.rotation(-0.5f));
        poseStack.translate(0, 0.0625 * -1, 0.0625 * 9);
      }
    }

    // boundingBoxHeight shrinks to 0.6 during fall flying; use natural standing height instead
    float spineY = (isFallFlying ? state.scale * 1.8f : state.boundingBoxHeight) - 0.0625f * 11;
    poseStack.translate(0, spineY, 0.0625 * 6);
    poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
    enderPack.submit(poseStack, submitNodeCollector, state.lightCoords, OverlayTexture.NO_OVERLAY, state.outlineColor);
    poseStack.popPose();
  }
}