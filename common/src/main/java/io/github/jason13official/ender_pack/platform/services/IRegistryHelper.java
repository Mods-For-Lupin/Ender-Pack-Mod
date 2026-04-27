package io.github.jason13official.ender_pack.platform.services;

import java.util.function.BiConsumer;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTab.ItemDisplayParameters;
import net.minecraft.world.item.CreativeModeTab.Output;

public interface IRegistryHelper {

  CreativeModeTab.Builder tabBuilder(BiConsumer<ItemDisplayParameters, Output> displayed);
}
