package io.github.jason13official.ender_pack.impl.common.registry;

import io.github.jason13official.ender_pack.Constants;
import io.github.jason13official.ender_pack.EnderPack;
import io.github.jason13official.ender_pack.impl.common.item.EnderPackBlockItem;
import java.util.function.BiConsumer;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.equipment.ArmorMaterials;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.Equippable;

public class ModItems {

  public static Item ENDER_PACK;

  public static void register(BiConsumer<Item, Identifier> consumer) {

    ENDER_PACK = new EnderPackBlockItem(ModBlocks.ENDER_PACK, new Properties()
        .attributes(ArmorMaterials.LEATHER.createAttributes(ArmorType.CHESTPLATE)).enchantable(ArmorMaterials.LEATHER.enchantmentValue())
        .component(DataComponents.EQUIPPABLE, Equippable.builder(EquipmentSlot.CHEST).setSwappable(false).setEquipSound(SoundEvents.ARMOR_EQUIP_LEATHER).build())
        .repairable(Items.PHANTOM_MEMBRANE)
        .setId(ResourceKey.create(Registries.ITEM, EnderPack.identifier(Constants.MOD_ID))));

    consumer.accept(ENDER_PACK, EnderPack.identifier(Constants.MOD_ID));
  }
}
