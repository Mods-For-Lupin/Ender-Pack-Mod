package io.github.jason13official.ender_pack;

import java.util.function.Consumer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

public class EnderPackClientNeoForge {

  public EnderPackClientNeoForge(final IEventBus modEventBus) {

    modEventBus.addListener((Consumer<FMLClientSetupEvent>) event -> EnderPackClient.init());
  }
}
