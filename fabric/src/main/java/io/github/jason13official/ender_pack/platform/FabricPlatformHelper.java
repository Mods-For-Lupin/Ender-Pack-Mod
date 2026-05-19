package io.github.jason13official.ender_pack.platform;

import io.github.jason13official.ender_pack.impl.common.registry.ModItems;
import io.github.jason13official.ender_pack.platform.services.IPlatformHelper;
import java.nio.file.Path;
import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTab;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab.Builder;

public class FabricPlatformHelper implements IPlatformHelper {

  @Override
  public String getPlatformName() {

    return "Fabric";
  }

  @Override
  public boolean isModLoaded(String modId) {

    return FabricLoader.getInstance().isModLoaded(modId);
  }

  @Override
  public boolean isDevelopmentEnvironment() {

    return FabricLoader.getInstance().isDevelopmentEnvironment();
  }

  @Override
  public Path getGameDirectory() {

    return FabricLoader.getInstance().getGameDir();
  }

  @Override
  public Builder tabBuilder() {

    return FabricCreativeModeTab.builder();
  }

  @Override
  public boolean equipped(LivingEntity living) {
    if (living instanceof Player player && player.getInventory().contains(stack -> stack.is(ModItems.ENDER_PACK))) return true;
    if (living.isHolding(ModItems.ENDER_PACK)) return true;
    if (living.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.ENDER_PACK)) return true;
    return false;
  }

  @Override
  public boolean shouldRenderOnBack(LivingEntity living) {
    return true;
  }
}
