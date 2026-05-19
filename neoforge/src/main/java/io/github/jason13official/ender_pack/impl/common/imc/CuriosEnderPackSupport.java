package io.github.jason13official.ender_pack.impl.common.imc;

import io.github.jason13official.ender_pack.impl.common.registry.ModItems;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import net.minecraft.world.entity.LivingEntity;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;

public class CuriosEnderPackSupport {

  public static boolean isWearingEnderPack(LivingEntity living) {
    ICuriosItemHandler handler = CuriosApi.getCuriosInventoryOrNull(living);

    if (handler == null) {
      return false;
    }

    return handler.isEquipped(ModItems.ENDER_PACK);
  }

  public static boolean shouldRenderOnBack(LivingEntity living) {

    ICuriosItemHandler handler = CuriosApi.getCuriosInventoryOrNull(living);

    if (handler == null) {
      System.out.println("failed to find curios inventory");
      return false;
    }

    AtomicBoolean shouldRender = new AtomicBoolean(false);

    Map<String, ICurioStacksHandler> curios = handler.getCurios();

//    curios.forEach((s, stackHandler) -> {
//
//    });

    ICurioStacksHandler backCurios = curios.get("back");
    if (backCurios != null) {
      IDynamicStackHandler backCuriosHandler = backCurios.getStacks();
      int backCuriosSlots = backCuriosHandler.getSlots();
      for (int i = 0; i < backCuriosSlots; i++) {
        if (backCuriosHandler.getStackInSlot(i).is(ModItems.ENDER_PACK) && backCurios.getRenders().get(i)) {
          // System.out.println("shouldRender true!");
          shouldRender.set(true);
        }
      }
    }

//
//    Optional<SlotResult> result = handler.findFirstCurio(ModItems.ENDER_PACK);
//    var curio = result.orElse(null);
//
//    if (handler.isSlotActive(CuriosApi.getSlotId(curio.slotContext()))) {}

    return shouldRender.get();
  }
}
