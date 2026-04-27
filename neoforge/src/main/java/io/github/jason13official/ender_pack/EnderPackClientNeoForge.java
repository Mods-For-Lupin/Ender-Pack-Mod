package io.github.jason13official.ender_pack;

import com.mojang.blaze3d.platform.InputConstants;
import java.util.function.Consumer;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.KeyMapping.Category;
import net.minecraft.client.Minecraft;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.settings.KeyConflictContext;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.util.Lazy;
import org.lwjgl.glfw.GLFW;

public class EnderPackClientNeoForge {

  public static final Category ENDER_PACK_CATEGORY = new Category(EnderPack.identifier(Constants.MOD_ID));
  public static final Lazy<KeyMapping> OPEN_BACKPACK = Lazy.of(() -> new KeyMapping(
      "key.ender_pack.ender_pack", // Will be localized using this translation key
      KeyConflictContext.IN_GAME, // Will only fire outside of menus
      InputConstants.Type.KEYSYM, // Default mapping is on the keyboard
      GLFW.GLFW_KEY_B, // Default key is B
      ENDER_PACK_CATEGORY) // Mapping will be in the ender_pack category
  );

  public EnderPackClientNeoForge(final IEventBus modEventBus) {

    modEventBus.addListener((Consumer<FMLClientSetupEvent>) event -> EnderPackClient.init());

    modEventBus.addListener((Consumer<RegisterKeyMappingsEvent>) event -> {

      event.registerCategory(ENDER_PACK_CATEGORY);
      event.register(OPEN_BACKPACK.get());
    });

    NeoForge.EVENT_BUS.addListener((Consumer<ClientTickEvent.Post>) event -> {

      while (OPEN_BACKPACK.get().consumeClick()) {
        if (Minecraft.getInstance().player != null) {
          // TODO send open backpack packet
        }
      }
    });
  }
}
