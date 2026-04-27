package io.github.jason13official.ender_pack.impl.common.registry;

import io.github.jason13official.ender_pack.Constants;
import io.github.jason13official.ender_pack.EnderPack;
import java.util.function.BiConsumer;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;

public class ModItems {

  public static Item ENDER_PACK;

  public static void register(BiConsumer<Item, Identifier> consumer) {

    ENDER_PACK = new BlockItem(ModBlocks.ENDER_PACK, new Properties().setId(ResourceKey.create(Registries.ITEM, EnderPack.identifier(Constants.MOD_ID))));
    consumer.accept(ENDER_PACK, EnderPack.identifier(Constants.MOD_ID));
  }
}
