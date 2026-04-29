package io.github.jason13official.ender_pack.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.AbstractContainerMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractContainerScreen.class)
public abstract class AbstractContainerScreenMixin<T extends AbstractContainerMenu> extends Screen implements MenuAccess<T> {

  @Shadow
  protected int titleLabelY;

  @Shadow
  protected int inventoryLabelY;

  @Inject(at = @At("TAIL"), method = "init") @SuppressWarnings("unchecked")
  private void ender_pack$init(CallbackInfo ci) {

    AbstractContainerScreen<T> self = (AbstractContainerScreen<T>) (Object) this;

    if (self.getTitle().getString().contains("ender_pack") || self.getTitle().getString().contains("Ender Pack")) {
      this.titleLabelY += 9999;
      this.inventoryLabelY += 9999;
    }
  }

  protected AbstractContainerScreenMixin(Component title) {
    super(title);
  }

  protected AbstractContainerScreenMixin(Minecraft minecraft, Font font, Component title) {
    super(minecraft, font, title);
  }
}
