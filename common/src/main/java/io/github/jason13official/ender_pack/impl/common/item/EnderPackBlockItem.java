package io.github.jason13official.ender_pack.impl.common.item;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.inventory.PlayerEnderChestContainer;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public class EnderPackBlockItem extends BlockItem {

  public EnderPackBlockItem(Block block, Properties properties) {
    super(block, properties);
  }

  @Override @SuppressWarnings("all")
  public InteractionResult use(Level level, Player player, InteractionHand hand) {

    PlayerEnderChestContainer container = player.getEnderChestInventory();

    if (container != null && level instanceof ServerLevel serverLevel) {
      container.setActiveChest(null);
      player.openMenu(new SimpleMenuProvider((containerId, inventory, p) -> ChestMenu.threeRows(containerId, inventory, container), Component.translatable("itemGroup.enderPack")));
      player.awardStat(Stats.OPEN_ENDERCHEST);
      PiglinAi.angerNearbyPiglins(serverLevel, player, true);
    }

    return super.use(level, player, hand);
  }

  @Override
  public InteractionResult useOn(UseOnContext context) {

    Player player = context.getPlayer();
    if (player == null) return InteractionResult.SUCCESS_SERVER;

    if (!player.isShiftKeyDown()) return InteractionResult.PASS;

    return super.useOn(context);
  }
}
