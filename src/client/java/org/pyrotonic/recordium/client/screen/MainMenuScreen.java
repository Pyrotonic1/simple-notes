package org.pyrotonic.recordium.client.screen;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.pyrotonic.recordium.client.RecordiumClient;
import org.pyrotonic.recordium.client.component.TransparantButtonWidget;
import org.pyrotonic.recordium.client.exceptions.NoteNameNotSpecifiedException;

public class MainMenuScreen extends Screen {
    private final MinecraftClient client = MinecraftClient.getInstance();

    Screen parent;

    public MainMenuScreen(Screen parent) {
        super(Text.translatable("screen.recordium.mainmenu"));
        this.parent = parent;
    }

    private static Identifier texture;
    private TransparantButtonWidget openNote;
    private TransparantButtonWidget createNote;
    private TransparantButtonWidget exit;

    private void initComponents() {
        texture = Identifier.of("recordium", "menutitle.png");

        createNote = new TransparantButtonWidget((width / 2 - 38), 130, 76, 20, Text.translatable(RecordiumClient.CREATE_NOTE_BUTTON), button -> {
            try {
                client.setScreen(new NoteEditorScreen(true));
            } catch (NoteNameNotSpecifiedException e) {
                throw new RuntimeException(e);
            }
        });

        openNote = new TransparantButtonWidget((width / 2 - 38), 160, 76, 20, Text.translatable(RecordiumClient.OPEN_NOTE_BUTTON), button -> client.setScreen(new NoteListScreen(this)));

        exit = new TransparantButtonWidget((width / 2 - 38), 190, 76, 20, Text.translatable(RecordiumClient.EXIT_BUTTON), button -> client.setScreen(parent));
    }

    @Override
    protected void init() {
        initComponents();

        this.addDrawableChild(createNote);
        this.addDrawableChild(openNote);
        this.addDrawableChild(exit);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);

        context.drawTexture(RenderLayer::getGuiTextured, texture, (width / 2 - 195), 30, 390, 60, 390, 60, 390, 60);
    }
}
