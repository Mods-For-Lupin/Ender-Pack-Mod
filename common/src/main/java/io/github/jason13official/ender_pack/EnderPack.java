package io.github.jason13official.ender_pack;

import net.minecraft.resources.Identifier;

public class EnderPack {

  public static void init() {
  }

  public static Identifier identifier(final String path) {
    return Identifier.fromNamespaceAndPath(Constants.MOD_ID, path);
  }
}