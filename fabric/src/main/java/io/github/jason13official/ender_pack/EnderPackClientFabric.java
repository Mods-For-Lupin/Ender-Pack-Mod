package io.github.jason13official.ender_pack;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.player.Player;
import org.lwjgl.glfw.GLFW;

public class EnderPackClientFabric implements ClientModInitializer {

  public static final KeyMapping.Category ENDER_PACK_CATEGORY = KeyMapping.Category.register(EnderPack.identifier(Constants.MOD_ID));
  public static final KeyMapping OPEN_BACKPACK = KeyMappingHelper.registerKeyMapping(
      new KeyMapping("key.ender_pack.ender_pack", // The translation key for the key mapping.
      InputConstants.Type.KEYSYM, // // The type of the keybinding; KEYSYM for keyboard, MOUSE for mouse.
      GLFW.GLFW_KEY_F9, // The GLFW keycode of the key.
      ENDER_PACK_CATEGORY // The category of the mapping.
  ));

  @Override
  public void onInitializeClient() {

    EnderPackClient.serverBoundPacketSender = ClientPlayNetworking::send;
    EnderPackClient.init();

    ClientTickEvents.END_CLIENT_TICK.register(client -> {
      while (OPEN_BACKPACK.consumeClick()) {
        LocalPlayer local = Minecraft.getInstance().player;
        Screen display = Minecraft.getInstance().screen;
        if (local != null && display == null) EnderPackClient.sendModePacketToServer();
      }
    });
  }
}
