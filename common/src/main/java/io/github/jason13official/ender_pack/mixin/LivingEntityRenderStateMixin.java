package io.github.jason13official.ender_pack.mixin;

import io.github.jason13official.ender_pack.impl.api.client.renderer.EnderPackRenderStateAccessor;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(LivingEntityRenderState.class)
public class LivingEntityRenderStateMixin implements EnderPackRenderStateAccessor {

  @Unique
  private boolean ender_pack$hasEnderPack = false;
  @Unique
  private boolean ender_pack$shouldRenderEnderPack = false;

  @Unique
  public final ItemStackRenderState ender_pack$ender_pack = new ItemStackRenderState();

  @Override
  public boolean ender_pack$hasEnderPack() {
    return this.ender_pack$hasEnderPack;
  }

  @Override
  public void ender_pack$setEnderPack(boolean render) {
    this.ender_pack$hasEnderPack = render;
  }

  @Override
  public ItemStackRenderState ender_pack$getEnderPack() {
    return this.ender_pack$ender_pack;
  }

  @Override
  public boolean ender_pack$shouldRenderEnderPack() {
    return this.ender_pack$shouldRenderEnderPack;
  }

  @Override
  public void ender_pack$setRenderEnderPack(boolean render) {
    this.ender_pack$shouldRenderEnderPack = render;
  }
}
