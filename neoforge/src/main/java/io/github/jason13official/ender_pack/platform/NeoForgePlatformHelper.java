package io.github.jason13official.ender_pack.platform;

import io.github.jason13official.ender_pack.impl.common.imc.CuriosEnderPackSupport;
import io.github.jason13official.ender_pack.impl.common.registry.ModItems;
import io.github.jason13official.ender_pack.platform.services.IPlatformHelper;
import java.nio.file.Path;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTab.Builder;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLLoader;

public class NeoForgePlatformHelper implements IPlatformHelper {

  @Override
  public String getPlatformName() {

    return "NeoForge";
  }

  @Override
  public boolean isModLoaded(String modId) {

    return ModList.get().isLoaded(modId);
  }

  @Override
  public boolean isDevelopmentEnvironment() {

    return !FMLLoader.getCurrent().isProduction();
  }

  @Override
  public Path getGameDirectory() {

    return FMLLoader.getCurrent().getGameDir();
  }

  @Override
  public Builder tabBuilder() {

    return CreativeModeTab.builder();
  }

  @Override
  public boolean equipped(LivingEntity living) {

    if (living instanceof Player player && player.getInventory().contains(stack -> stack.is(ModItems.ENDER_PACK))) return true;
    if (living.isHolding(ModItems.ENDER_PACK)) return true;
    if (living.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.ENDER_PACK)) return true;
    if (isModLoaded("curios") && CuriosEnderPackSupport.isWearingEnderPack(living)) return true;

    return false;
  }

  @Override
  public boolean shouldRenderOnBack(LivingEntity living) {

    if (living.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.ENDER_PACK)) return true;

    return isModLoaded("curios") && CuriosEnderPackSupport.shouldRenderOnBack(living);
  }
}