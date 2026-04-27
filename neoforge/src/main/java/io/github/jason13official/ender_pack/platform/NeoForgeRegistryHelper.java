package io.github.jason13official.ender_pack.platform;

import io.github.jason13official.ender_pack.platform.services.IRegistryHelper;
import java.util.function.BiConsumer;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTab.Builder;
import net.minecraft.world.item.CreativeModeTab.ItemDisplayParameters;
import net.minecraft.world.item.CreativeModeTab.Output;

public class NeoForgeRegistryHelper implements IRegistryHelper {

  @Override
  public Builder tabBuilder(BiConsumer<ItemDisplayParameters, Output> displayed) {
    return CreativeModeTab.builder().displayItems(displayed::accept);
  }
}
