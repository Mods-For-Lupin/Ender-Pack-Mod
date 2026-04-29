package io.github.jason13official.ender_pack.mixin;

import io.github.jason13official.ender_pack.EnderPackClient;
import java.awt.Container;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.ContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ChestMenu;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ContainerScreen.class)
public abstract class ContainerScreenMixin extends AbstractContainerScreen<ChestMenu> {

  @Shadow
  @Final
  private int containerRows;

  public ContainerScreenMixin(ChestMenu menu, Inventory inventory, Component title) {
    super(menu, inventory, title);
  }

  public ContainerScreenMixin(ChestMenu menu, Inventory inventory, Component title, int imageWidth, int imageHeight) {
    super(menu, inventory, title, imageWidth, imageHeight);
  }

  @Inject(at = @At("TAIL"), method = "extractBackground")
  private void ender_pack$extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a, CallbackInfo ci) {

    ContainerScreen self = (ContainerScreen) (Object) this;

    if (self.getTitle().getString().contains("ender_pack") || self.getTitle().getString().contains("Ender Pack")) {
      int xo = (self.width - this.imageWidth) / 2;
      int yo = (self.height - this.imageHeight) / 2;
      graphics.blit(RenderPipelines.GUI_TEXTURED, EnderPackClient.CONTAINER_BACKGROUND, xo, yo, 0.0F, 0.0F, this.imageWidth, this.containerRows * 18 + 17, 256, 256);
      graphics.blit(RenderPipelines.GUI_TEXTURED, EnderPackClient.CONTAINER_BACKGROUND, xo, yo + this.containerRows * 18 + 17, 0.0F, 126.0F, this.imageWidth, 96, 256, 256);
    }
  }
}
