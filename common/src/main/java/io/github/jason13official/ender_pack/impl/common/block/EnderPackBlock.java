package io.github.jason13official.ender_pack.impl.common.block;

import com.mojang.serialization.MapCodec;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Util;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.inventory.PlayerEnderChestContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class EnderPackBlock extends HorizontalDirectionalBlock {

  public static final MapCodec<EnderPackBlock> CODEC = simpleCodec(EnderPackBlock::new);
  private static final Map<Direction, VoxelShape> SHAPES = Util.make(() -> Shapes.rotateHorizontal(Shapes.or(Block.box(3, 0, 6, 16 - 3, 16 - 4, 16 - 5))));

  public EnderPackBlock(Properties properties) {
    super(properties);
  }

  @Override
  protected MapCodec<? extends HorizontalDirectionalBlock> codec() {
    return CODEC;
  }

  @Override
  protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
    builder.add(HorizontalDirectionalBlock.FACING);
  }

  @Override
  public BlockState getStateForPlacement(BlockPlaceContext context) {
    return (BlockState)this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
  }

  @Override
  protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
    return SHAPES.get(state.getValue(HorizontalDirectionalBlock.FACING));
  }

  @Override
  @SuppressWarnings("all")
  protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {

    PlayerEnderChestContainer container = player.getEnderChestInventory();

    if (container != null && level instanceof ServerLevel serverLevel) {
      container.setActiveChest(null);
      player.openMenu(new SimpleMenuProvider((containerId, inventory, p) -> ChestMenu.threeRows(containerId, inventory, container), Component.translatable("itemGroup.enderPack")));
      player.awardStat(Stats.OPEN_ENDERCHEST);
      PiglinAi.angerNearbyPiglins(serverLevel, player, true);
    }

    return super.useWithoutItem(state, level, pos, player, hitResult);
  }

  @Override
  protected boolean isRandomlyTicking(BlockState state) {
    return true;
  }

  @Override
  protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
    if (random.nextFloat() <= 0.02) {
      ItemEntity entity = new ItemEntity(level, pos.getX(), pos.getY() + 0.5, pos.getZ(), new ItemStack(Items.ENDER_PEARL));
      level.addFreshEntity(entity);
    }
  }

  @Override
  public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
    for (int i = 0; i < 3; ++i) {
      int flipX = random.nextInt(2) * 2 - 1;
      int flipZ = random.nextInt(2) * 2 - 1;
      double x = (double) pos.getX() + (double) 0.5F + (double) 0.25F * (double) flipX;
      double y = (float) pos.getY() + random.nextFloat();
      double z = (double) pos.getZ() + (double) 0.5F + (double) 0.25F * (double) flipZ;
      double xa = random.nextFloat() * (float) flipX;
      double ya = ((double) random.nextFloat() - (double) 0.5F) * (double) 0.125F;
      double za = random.nextFloat() * (float) flipZ;
      level.addParticle(ParticleTypes.PORTAL, x, y, z, xa, ya, za);
    }

  }
}
