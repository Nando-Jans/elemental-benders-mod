package com.example.gui.screen;

import com.example.ElementalBendersMod;
import com.example.gui.widget.ImageButtonWidget;
import com.example.networking.Client2ServerModMessages;
import com.example.util.BendingData;
import com.example.util.EntityDataSaver;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ChooseBendTypeScreen extends Screen {

    private static final Identifier ELEMENTS_BACKGROUND = new Identifier(ElementalBendersMod.MOD_ID, "textures/gui/elements_interface.png");

    // Identifiers for the element icons
    private static final Identifier FIRE_ICON = new Identifier(ElementalBendersMod.MOD_ID, "textures/gui/icon/fire/fire_icon.png");
    private static final Identifier WATER_ICON = new Identifier(ElementalBendersMod.MOD_ID, "textures/icon/water/gui/water_icon.png");
    private static final Identifier AIR_ICON = new Identifier(ElementalBendersMod.MOD_ID, "textures/icon/air/gui/air_icon.png");
    private static final Identifier EARTH_ICON = new Identifier(ElementalBendersMod.MOD_ID, "textures/icon/earth/gui/earth_icon.png");
    // Screen size and position
    private static final int GUI_WIDTH = 176;
    private static final int GUI_HEIGHT = 89;
    private int guiLeft;
    private int guiTop;
    
    public ChooseBendTypeScreen() {
        super(Text.literal("Choose your bending type"));
    }

    public ImageButtonWidget fireButton;
    public ImageButtonWidget waterButton;
    public ImageButtonWidget airButton;
    public ImageButtonWidget earthButton;

    @Override
    protected void init() {
        super.init();
        this.guiLeft = (this.width - GUI_WIDTH) / 2;
        this.guiTop = (this.height - GUI_HEIGHT) / 2;

        // Add the fire, water, air and earth buttons to the screen
        fireButton = ImageButtonWidget.builder(
                        Text.literal("Fire"),
                        (button) -> selectElement("fire"),
                        FIRE_ICON
                )
                .dimensions(guiLeft + 20, guiTop + 20, 18, 18)
                .tooltip(Text.literal("Fire bending is the art of controlling fire and lightning"))
                .build();
        waterButton = ImageButtonWidget.builder(
                        Text.literal("Water"),
                        (button) -> selectElement("water"),
                        WATER_ICON
                )
                .dimensions(guiLeft + 50, guiTop + 20, 18, 18)
                .tooltip(Text.literal("Water bending is the art of controlling water and ice"))
                .build();
        airButton = ImageButtonWidget.builder(
                        Text.literal("Air"),
                        (button) -> selectElement("air"),
                        AIR_ICON
                )
                .dimensions(guiLeft + 80, guiTop + 20, 18, 18)
                .tooltip(Text.literal("Air bending is the art of controlling air and wind"))
                .build();
        earthButton = ImageButtonWidget.builder(
                        Text.literal("Earth"),
                        (button) -> selectElement("earth"),
                        EARTH_ICON
                )
                .dimensions(guiLeft + 110, guiTop + 20, 18, 18)
                .tooltip(Text.literal("Earth bending is the art of controlling earth and metal"))
                .build();

        addDrawableChild(fireButton);
        addDrawableChild(waterButton);
        addDrawableChild(airButton);
        addDrawableChild(earthButton);
    }

    private void selectElement(String element) {
        // Close the screen
        if (client != null) {
            BendingData.setBendingType((EntityDataSaver) client.player, element);
            BendingData.setBendingLevel((EntityDataSaver) client.player, 1);
            client.setScreen(null);
            // Sync the bending type with the server
            ClientPlayNetworking.send(Client2ServerModMessages.SYNC_NBT, PacketByteBufs.create().writeNbt(BendingData.get((EntityDataSaver) client.player)));
        }
    }

    @Override
    public void renderBackground(DrawContext context, int mouseX, int mouseY, float delta) {
        super.renderBackground(context, mouseX, mouseY, delta);
        context.drawTexture(ELEMENTS_BACKGROUND, guiLeft, guiTop, 0, 0, 0, GUI_WIDTH, GUI_HEIGHT, guiLeft, guiTop);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}
