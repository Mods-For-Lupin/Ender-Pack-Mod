package io.github.jason13official.ender_pack;

import net.fabricmc.api.ClientModInitializer;

public class EnderPackClientFabric implements ClientModInitializer {

  @Override
  public void onInitializeClient() {

    EnderPackClient.init();
  }
}
