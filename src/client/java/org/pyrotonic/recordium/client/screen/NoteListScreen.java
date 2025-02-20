package org.pyrotonic.recordium.client.screen;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.minecraft.util.Colors;
import org.pyrotonic.recordium.Recordium;
import org.pyrotonic.recordium.client.NoteDataHandler;
import org.pyrotonic.recordium.client.RecordiumClient;
import org.pyrotonic.recordium.client.component.NoteEntryWidget;
import org.pyrotonic.recordium.client.component.TransparantButtonWidget;

import java.util.ArrayList;
import java.util.Collections;

public class NoteListScreen extends Screen {
    /* private final MinecraftClient client = MinecraftClient.getInstance();
    private final TextRenderer textRenderer = client.textRenderer;
    private final ArrayList<NoteDataHandler> notes = new ArrayList<>();
    private int noteIndex = 0;
    private int pageNumber = 0;
    private boolean backButtonPressed = false;

    protected NoteListScreen(Screen parent) {
        super(Text.translatable("screen.recordium.noteselector"));
        this.initNotes();
        this.initNormalComponents();
    }

    private TransparantButtonWidget nextButton;
    private TransparantButtonWidget backButton;
    private final ArrayList<NoteEntryWidget> noteEntryWidgets = new ArrayList<>();
    private final ArrayList<ArrayList<NoteEntryWidget>> pages = new ArrayList<>();


    private int calculateMaxWidgets() {
        int y = 25;
        int maxWidgets = 0;
        while (y + 47 >= this.height - 53) {
            maxWidgets++;
            y += 57;
        }
        return maxWidgets;
    }

    private void initNotes() {
        this.notes.clear();
        ArrayList<String> filenames = NoteDataHandler.readFilenames();
        for (String filename : filenames) {
            this.notes.add(new NoteDataHandler(NoteDataHandler.readNote(filename), filename));
        }
    }

    private void initNormalComponents() {
        this.nextButton = new TransparantButtonWidget((width / 2) + 50, height - 30, 100, 20, Text.translatable(RecordiumClient.NEXT_PAGE_BUTTON), button -> {
            this.pageNumber++;
            this.backButtonPressed = false;
            this.clearAndInit();
        });
        this.nextButton.active = false;

        this.backButton = new TransparantButtonWidget((width / 2) - 150, height - 30, 100, 20, Text.translatable(RecordiumClient.BACK_PAGE_BUTTON), button -> {
          this.pageNumber--;
          this.backButtonPressed = true;
          this.clearAndInit();
        });
        if (pageNumber == 0) {
            this.backButton.active = false;
        }
    }

    private void addPage() {
        ArrayList<NoteEntryWidget> page = new ArrayList<>();
        for (int i = 0; (i < this.noteEntryWidgets.size()) && (i <= this.calculateMaxWidgets()); i++, noteIndex++) {
            page.add(this.noteEntryWidgets.get(this.noteIndex));

        }
        this.pages.add(page);
    }

    private void initNoteWidgets() {
        this.noteEntryWidgets.clear();
        int topY = 25;
        int currentRelativeWidgetIndex = 0;
        for (NoteDataHandler note : this.notes) {
            this.noteEntryWidgets.add(new NoteEntryWidget((width / 2) - 80, topY, note));
            topY += 57;
            currentRelativeWidgetIndex++;
            if (currentRelativeWidgetIndex > this.calculateMaxWidgets()) {
                topY = 25;
            }
        }
    }

    @Override
    protected void init() {
        super.init();
        this.initNoteWidgets();
        Recordium.LOGGER.info("init!");
        Recordium.LOGGER.info("pages size: {}, pageNumber: {}", this.pages.size(), this.pageNumber);
        if ((!backButtonPressed) && !((this.noteIndex - 1) == this.noteEntryWidgets.size())) {
            this.addPage();
        }
        for (NoteEntryWidget noteEntryWidget : this.pages.get(pageNumber)) {
            this.addDrawableChild(noteEntryWidget);
        }
        for (ArrayList<NoteEntryWidget> arrayList : this.pages) {
            Recordium.LOGGER.info(arrayList.toString());
        }
        this.addDrawableChild(this.nextButton);
        this.addDrawableChild(this.backButton);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        context.drawText(textRenderer, Text.translatable("screen.recordium.noteselector"), (width / 2) - ((textRenderer.getWidth(Text.translatable("screen.recordium.noteselector"))) / 2), 6, Colors.WHITE, true);
        context.drawHorizontalLine(0, this.width, 20, -857677600);
        context.drawHorizontalLine(0, this.width, this.height - 48, -857677600);
        context.fill(0, 20, this.width, this.height - 48, 1258291200);
    }

    @Override
    protected void refreshWidgetPositions() {
        this.noteIndex = 0;
        this.notes.clear();
        this.noteEntryWidgets.clear();
        super.refreshWidgetPositions();
    } */
}