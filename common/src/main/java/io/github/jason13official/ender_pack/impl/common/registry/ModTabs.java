package io.github.jason13official.ender_pack.impl.common.registry;

import io.github.jason13official.ender_pack.Constants;
import io.github.jason13official.ender_pack.EnderPack;
import io.github.jason13official.ender_pack.platform.Services;
import java.util.function.BiConsumer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModTabs {

  public static CreativeModeTab ENDER_PACK;

  public static void register(BiConsumer<CreativeModeTab, Identifier> consumer) {

    ENDER_PACK = Services.registry().tabBuilder((itemDisplayParameters, output) -> {
      output.accept(ModItems.ENDER_PACK);
    }).icon(() -> new ItemStack(ModItems.ENDER_PACK)).title(Component.translatable("itemGroup.enderPack")).build();

    consumer.accept(ENDER_PACK, EnderPack.identifier(Constants.MOD_ID));
  }
}
