package com.example.gui.widget;

import com.example.ElementalBendersMod;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Colors;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class BendingTreeWidget extends ClickableWidget {
    private final Identifier texture;
    private final Identifier background = new Identifier(ElementalBendersMod.MOD_ID, "textures/gui/button/button.png");
    private final Identifier disabledBackground = new Identifier(ElementalBendersMod.MOD_ID, "textures/gui/button/button_disabled.png");
    private final Identifier backgroundSelected = new Identifier(ElementalBendersMod.MOD_ID, "textures/gui/button/button_selected.png");
    private BendingTreeWidget.PressAction onPress;
    private BendingTreeWidget parent;
    private BendingTreeWidget[] children;

    protected BendingTreeWidget(int x, int y, int width, int height, Text message, BendingTreeWidget.PressAction onPress, Identifier texture) {
        super(x, y, width, height, message);
        this.onPress = onPress;
        this.texture = texture;
    }

    public void onPress() {
        if (this.active && this.onPress != null) {
            this.onPress.onPress(this);
        }
    }

    @Override
    public void onClick(double mouseX, double mouseY) {
        onPress();
    }

    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {

    }

    @Override
    protected void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        MinecraftClient minecraftClient = MinecraftClient.getInstance();
        context.setShaderColor(1.0f, 1.0f, 1.0f, this.alpha);
        RenderSystem.enableBlend();
        RenderSystem.enableDepthTest();

        context.drawTexture(background, getX(), getY(), 1, 0, 0, this.getWidth(), this.getHeight(), 18, 18);
        if (!this.active) {
            context.drawTexture(disabledBackground, getX(), getY(), 1, 0, 0, this.getWidth(), this.getHeight(), 18, 18);
        } else if (hovered) {
            context.drawTexture(backgroundSelected, getX(), getY(), 1, 0, 0, this.getWidth(), this.getHeight(), 18, 18);
        }

        context.drawTexture(texture, getX()+1, getY()+1, 2, 0, 0, this.getWidth(), this.getHeight(), 16, 16);
        context.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        int i = this.active ? 0xFFFFFF : 0xA0A0A0;
        drawMessage(context, minecraftClient.textRenderer, i | MathHelper.ceil((float)(this.alpha * 255.0f)) << 24);
    }

    public void renderLines(DrawContext context, int x, int y, boolean border) {
        if (this.parent != null) {
            int n;
            int i = x + this.parent.getX() + 13;
            int j = x + this.parent.getX() + 26 + 4;
            int k = y + this.parent.getY() + 13;
            int l = x + this.getX() + 13;
            int m = y + this.getY() + 13;
            int n2 = n = border ? Colors.BLACK : Colors.WHITE;
            if (border) {
                context.drawHorizontalLine(j, i, k - 1, n);
                context.drawHorizontalLine(j + 1, i, k, n);
                context.drawHorizontalLine(j, i, k + 1, n);
                context.drawHorizontalLine(l, j - 1, m - 1, n);
                context.drawHorizontalLine(l, j - 1, m, n);
                context.drawHorizontalLine(l, j - 1, m + 1, n);
                context.drawVerticalLine(j - 1, m, k, n);
                context.drawVerticalLine(j + 1, m, k, n);
            } else {
                context.drawHorizontalLine(j, i, k, n);
                context.drawHorizontalLine(l, j, m, n);
                context.drawVerticalLine(j, m, k, n);
            }
        }
        for (BendingTreeWidget advancementWidget : this.children) {
            advancementWidget.renderLines(context, x, y, border);
        }
    }

    public void drawMessage(DrawContext context, TextRenderer textRenderer, int color) {

    }

    public static BendingTreeWidget.Builder builder(Text message, BendingTreeWidget.PressAction onPress, Identifier texture) {
        return new BendingTreeWidget.Builder(message, onPress, texture);
    }

    public static class Builder {
        private final Text message;
        private final BendingTreeWidget.PressAction onPress;
        private final Identifier texture;
        private int x;
        private int y;
        private int width;
        private int height;
        private Tooltip tooltip;

        public Builder(Text message, BendingTreeWidget.PressAction onPress, Identifier texture) {
            this.message = message;
            this.onPress = onPress;
            this.texture = texture;
        }

        public BendingTreeWidget.Builder dimensions(int x, int y, int width, int height) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            return this;
        }

        public BendingTreeWidget.Builder tooltip(Text text) {
            this.tooltip = Tooltip.of(text);
            return this;
        }

        public BendingTreeWidget build() {
            BendingTreeWidget imageButtonWidget = new BendingTreeWidget(x, y, width, height, message, onPress, texture);
            imageButtonWidget.setTooltip(tooltip);
            return imageButtonWidget;
        }
    }

    @Environment(value= EnvType.CLIENT)
    public static interface PressAction {
        public void onPress(BendingTreeWidget var1);
    }
}
