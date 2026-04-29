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

    ItemStackRenderState enderPack = stateAccessor.ender_pack$getEnderPack();
    if (enderPack.isEmpty()) {
      return;
    }

    poseStack.pushPose();

    // flip it around jack

    poseStack.mulPose(Axis.YP.rotationDegrees(180.0F - state.bodyRot));

    if (state instanceof AvatarRenderState avState && avState.isCrouching) {
      poseStack.mulPose(Axis.XP.rotation(-0.5f));
      poseStack.translate(0, 0.0625 * -1, 0.0625 * 9);
    }

    poseStack.translate(0.0625 * 0, state.boundingBoxHeight - (0.0625 * 11), 0.0625 * 6);
    poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
    enderPack.submit(poseStack, submitNodeCollector, state.lightCoords, OverlayTexture.NO_OVERLAY, state.outlineColor);
    poseStack.popPose();
  }
}