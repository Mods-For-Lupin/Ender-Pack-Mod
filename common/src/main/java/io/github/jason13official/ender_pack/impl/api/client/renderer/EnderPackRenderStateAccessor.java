package io.github.jason13official.ender_pack.impl.api.client.renderer;

import net.minecraft.client.renderer.item.ItemStackRenderState;

public interface EnderPackRenderStateAccessor {

  boolean ender_pack$hasEnderPack();

  void ender_pack$setEnderPack(boolean render);

  ItemStackRenderState ender_pack$getEnderPack();

  boolean ender_pack$shouldRenderEnderPack();

  void ender_pack$setRenderEnderPack(boolean render);
}
