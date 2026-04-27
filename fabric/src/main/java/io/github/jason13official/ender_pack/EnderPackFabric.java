package io.github.jason13official.ender_pack;

import io.github.jason13official.ender_pack.impl.common.network.ModePacket;
import io.github.jason13official.ender_pack.impl.common.registry.ModBlocks;
import io.github.jason13official.ender_pack.impl.common.registry.ModEntities;
import io.github.jason13official.ender_pack.impl.common.registry.ModItems;
import io.github.jason13official.ender_pack.impl.common.registry.ModMenus;
import io.github.jason13official.ender_pack.impl.common.registry.ModParticles;
import io.github.jason13official.ender_pack.impl.common.registry.ModTabs;
import io.github.jason13official.ender_pack.impl.common.registry.ModTiles;
import io.github.jason13official.ender_pack.platform.FabricPlatformHelper;
import io.github.jason13official.ender_pack.platform.Services;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.impl.resource.DataResourceLoaderImpl;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.stats.Stats;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.inventory.PlayerEnderChestContainer;
import net.minecraft.world.level.Level;

public class EnderPackFabric implements ModInitializer {

  @Override
  public void onInitialize() {

    bind(BuiltInRegistries.BLOCK, ModBlocks::register);
    bind(BuiltInRegistries.ENTITY_TYPE, ModEntities::register);
    bind(BuiltInRegistries.ITEM, ModItems::register);
    bind(BuiltInRegistries.PARTICLE_TYPE, ModParticles::register);
    bind(BuiltInRegistries.BLOCK_ENTITY_TYPE, ModTiles::register);
    bind(BuiltInRegistries.MENU, ModMenus::register);
    bind(BuiltInRegistries.CREATIVE_MODE_TAB, ModTabs::register);

    EnderPack.init();

    PayloadTypeRegistry.serverboundPlay().register(ModePacket.TYPE, ModePacket.STREAM_CODEC);
    ServerPlayNetworking.registerGlobalReceiver(ModePacket.TYPE, (payload, context) -> {
      Player player = context.player();
      Level level = context.player().level();
      PlayerEnderChestContainer container = player.getEnderChestInventory();

      if (Services.PLATFORM.equipped(player) && container != null && level instanceof ServerLevel serverLevel) {
        container.setActiveChest(null);
        player.openMenu(new SimpleMenuProvider((containerId, inventory, p) -> ChestMenu.threeRows(containerId, inventory, container), Component.translatable("itemGroup.enderPack")));
        player.awardStat(Stats.OPEN_ENDERCHEST);
        PiglinAi.angerNearbyPiglins(serverLevel, player, true);
      }
    });

    DataResourceLoaderImpl.get(PackType.SERVER_DATA).registerReloadListener(EnderPack.identifier(Constants.MOD_ID), new ResourceReloadListener());
  }

  public <T> void bind(Registry<T> registry, Consumer<BiConsumer<T, Identifier>> source) {

    source.accept((t, rl) -> Registry.register(registry, rl, t));
  }

  public static class ResourceReloadListener extends SimplePreparableReloadListener<Void> {

    @Override
    public String getName() {
      return EnderPack.identifier(Constants.MOD_ID).toString();
    }

    @Override
    protected void apply(Void unused, ResourceManager resourceManager, ProfilerFiller profilerFiller) {
      // ModConfig.load(Services.PLATFORM.getConfigDirectory());
    }

    @Override
    protected Void prepare(ResourceManager resourceManager, ProfilerFiller profilerFiller) {
      return null;
    }
  }
}
