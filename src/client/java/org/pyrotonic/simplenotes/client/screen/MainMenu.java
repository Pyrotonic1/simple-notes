package org.pyrotonic.simplenotes.client.screen;

import io.wispforest.owo.ui.base.BaseOwoScreen;
import io.wispforest.owo.ui.component.Components;
import io.wispforest.owo.ui.container.Containers;
import io.wispforest.owo.ui.container.FlowLayout;
import io.wispforest.owo.ui.core.*;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

public class MainMenu extends BaseOwoScreen<FlowLayout> {
    @Override
    protected @NotNull OwoUIAdapter<FlowLayout> createAdapter() {
        return OwoUIAdapter.create(this, Containers::verticalFlow);
    }
    @Override
    protected void build(FlowLayout rootComponent) {
        Component MenuTitle = Components.texture(Identifier.of("simplenotes:textures/gui/menutitle.png"), 0, 0, 330, 60, 330, 60)
                        .id("menu-title")
                        .positioning(Positioning.relative(50, 25));

        FlowLayout MenuButtons = (FlowLayout) Containers.horizontalFlow(Sizing.content(), Sizing.content())
            .gap(10)
            .horizontalAlignment(HorizontalAlignment.CENTER)
            .verticalAlignment(VerticalAlignment.CENTER);

        Component CreateNoteButton = Components.button(Text.literal("Create Note"), buttonComponent -> {
                assert client != null;
                client.setScreen(new CreateNote());
            })
                .id("create-button")
                .horizontalSizing(Sizing.fixed(68));
        Component OpenNoteButton = Components.button(Text.literal("Open Note"), buttonComponent -> {
                assert client != null;
                client.setScreen(new NoteList());
            })
                .id("open-button")
                .horizontalSizing(Sizing.fixed(68));
        Component ExitButton = Components.button(Text.literal("Exit"), buttonComponent -> {
                assert client != null;
                client.setScreen(null);})
                .id("exit-button")
                .horizontalSizing(Sizing.fixed(68));


        MenuButtons
            .child(CreateNoteButton)
            .child(OpenNoteButton)
            .child(ExitButton);

        rootComponent
                .child(MenuTitle)
                .child(MenuButtons)
                .surface(Surface.OPTIONS_BACKGROUND)
                .verticalAlignment(VerticalAlignment.CENTER)
                .horizontalAlignment(HorizontalAlignment.CENTER);

    }
}
