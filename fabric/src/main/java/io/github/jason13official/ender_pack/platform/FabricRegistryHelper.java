package io.github.jason13official.ender_pack.platform;

import io.github.jason13official.ender_pack.platform.services.IRegistryHelper;
import java.util.function.BiConsumer;
import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTab;
import net.minecraft.world.item.CreativeModeTab.Builder;
import net.minecraft.world.item.CreativeModeTab.ItemDisplayParameters;
import net.minecraft.world.item.CreativeModeTab.Output;

public class FabricRegistryHelper implements IRegistryHelper {

  @Override
  public Builder tabBuilder(BiConsumer<ItemDisplayParameters, Output> displayed) {
    return FabricCreativeModeTab.builder().displayItems(displayed::accept);
  }
}
