package io.github.jason13official.ender_pack.impl.common.registry;

import io.github.jason13official.ender_pack.Constants;
import io.github.jason13official.ender_pack.EnderPack;
import io.github.jason13official.ender_pack.impl.common.block.EnderPackBlock;
import java.util.function.BiConsumer;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class ModBlocks {

  public static Block ENDER_PACK;

  public static void register(BiConsumer<Block, Identifier> consumer) {

    ENDER_PACK = new EnderPackBlock(BlockBehaviour.Properties.of().setId(ResourceKey.create(Registries.BLOCK, EnderPack.identifier(Constants.MOD_ID))));
    consumer.accept(ENDER_PACK, EnderPack.identifier(Constants.MOD_ID));
  }
}
