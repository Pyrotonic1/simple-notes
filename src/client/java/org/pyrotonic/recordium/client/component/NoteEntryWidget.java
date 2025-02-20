package org.pyrotonic.recordium.client.component;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.AlwaysSelectedEntryListWidget;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Colors;
import net.minecraft.util.Util;
import org.pyrotonic.recordium.client.NoteDataHandler;
import org.pyrotonic.recordium.client.RecordiumClient;
import org.pyrotonic.recordium.client.screen.NoteEditorScreen;

import java.io.IOException;

public class NoteEntryWidget extends AlwaysSelectedEntryListWidget.Entry<NoteEntryWidget> {
    private static final MinecraftClient client = MinecraftClient.getInstance();
    private static final TextRenderer textRenderer = client.textRenderer;
    private long time;

    private final NoteDataHandler note;
    private final int x;
    private final int y;

    public NoteEntryWidget(int x, int y, NoteDataHandler note) {
        super();
        this.note = note;
        this.x = x;
        this.y = y;
    }

    private String getNoteTile() {
        if ((this.note.getFilename().replace(".txt", "")).length() >= 21) {
            return ((this.note.getFilename().replace(".txt", "")).substring(0, 24)) + "...";
        } else {
            return this.note.getFilename().replace(".txt", "");
        }
    }




    @Override
    public Text getNarration() {
        return null;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if ((Util.getMeasuringTimeMs() - time) >= 250L) {
            this.time = Util.getMeasuringTimeMs();
        } else {
            client.getSoundManager().play(PositionedSoundInstance.master(SoundEvents.UI_BUTTON_CLICK, 1.0F));
            client.setScreen(new NoteEditorScreen(false, note.getFilename()));
        }
        return true;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    @Override
    public void render(DrawContext context, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
        MatrixStack matrices = context.getMatrices();
        if (this.isFocused()) {
            context.drawBorder(this.getX(), this.getY(), 160, 47, -1);
            context.fill(this.getX() + 1, this.getY() + 1, this.getX() + 160 - 1, this.getY() + 47 - 1, -1778384896);
        } else if (this.isMouseOver(mouseX, mouseY)) {
            context.drawBorder(this.getX(), this.getY(), 160, 47, -6908266);
            context.fill(this.getX() + 1, this.getY() + 1, this.getX() + 160 - 1, this.getY() + 47 - 1, -1778384896);
        } else {
            context.drawBorder(this.getX(), this.getY(), 160, 47, -6908266);
        }
        context.drawText(textRenderer, this.getNoteTile(), this.getX() + 4, this.getY() + 3, Colors.WHITE, true);
        matrices.push();
        try {
            context.drawText(textRenderer, Text.translatable(RecordiumClient.ENTRY_CREATED, note.getCreated()), this.getX() + 6, this.getY() + 15, -857677600, true);
            context.drawText(textRenderer, Text.translatable(RecordiumClient.ENTRY_LASTMODIFIED, note.getLastModified()), this.getX() + 6, this.getY() + 25, -857677600, true);
            context.drawText(textRenderer, Text.translatable(RecordiumClient.ENTRY_SIZE, note.getFileSize()), this.getX() + 6, this.getY() + 35, -857677600, true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        matrices.pop();
    }
}
