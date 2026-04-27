package io.github.jason13official.ender_pack.impl.common.imc;

import io.github.jason13official.ender_pack.impl.common.registry.ModItems;
import net.minecraft.world.entity.LivingEntity;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;

public class CuriosEnderPackSupport {

  public static boolean isWearingEnderPack(LivingEntity living) {
    ICuriosItemHandler handler = CuriosApi.getCuriosInventoryOrNull(living);
    return handler != null && handler.isEquipped(ModItems.ENDER_PACK);
  }
}
