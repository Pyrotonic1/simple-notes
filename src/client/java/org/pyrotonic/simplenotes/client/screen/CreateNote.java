package org.pyrotonic.simplenotes.client.screen;

import io.wispforest.owo.ui.base.BaseOwoScreen;
import io.wispforest.owo.ui.component.Components;
import io.wispforest.owo.ui.component.TextAreaComponent;
import io.wispforest.owo.ui.component.TextBoxComponent;
import io.wispforest.owo.ui.container.Containers;
import io.wispforest.owo.ui.container.FlowLayout;
import io.wispforest.owo.ui.core.*;
import net.minecraft.text.Text;
import org.jetbrains.annotations.NotNull;
import org.pyrotonic.simplenotes.Simplenotes;
import org.pyrotonic.simplenotes.client.NoteDataHandler;
import org.pyrotonic.simplenotes.client.SimplenotesClient;

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class CreateNote extends BaseOwoScreen<FlowLayout> {

    @Override
    protected @NotNull OwoUIAdapter<FlowLayout> createAdapter() {
        return OwoUIAdapter.create(this, Containers::verticalFlow);
    }
    @Override
    protected void build(FlowLayout rootComponent) {
        final String ContentPlaceholder = "Enter content here!";
        final String FilenamePlaceholder = "Enter filename here!";

        TextAreaComponent TextArea = Components.textArea(Sizing.fixed(200), Sizing.fixed(200), ContentPlaceholder);
        TextBoxComponent FilenameBox = Components.textBox(Sizing.fixed(116), FilenamePlaceholder);
        Component SaveButton = Components.button(Text.literal("Save & Exit"), buttonComponent -> {
            NoteDataHandler.saveContent(TextArea.getText(), FilenameBox.getText());
            assert client != null;
            if (SimplenotesClient.IsIngame) {
                client.setScreen(null);
            } else {
                client.setScreen(new MainMenu());
            }
            Simplenotes.LOGGER.info("Saved Successfully!");
        });
        TextArea.margins(Insets.of(5));
        SaveButton.margins(Insets.of(6));
        FilenameBox.margins(Insets.of(5));
        rootComponent
                .surface(Surface.OPTIONS_BACKGROUND)
                .horizontalAlignment(HorizontalAlignment.CENTER)
                .verticalAlignment(VerticalAlignment.CENTER);


        FlowLayout NameNSave = (FlowLayout) Containers.horizontalFlow(Sizing.content(), Sizing.content())
            .child(FilenameBox)
            .child(SaveButton)
            .verticalAlignment(VerticalAlignment.TOP)
            .sizing(Sizing.content(), Sizing.fixed(40));
        Component EditBox =
            Containers.grid(Sizing.content(), Sizing.content(), 2, 2)
                .child(NameNSave, 0, 0)
                .child(TextArea, 1, 0)
                .padding(Insets.of(15))
                .surface(Surface.DARK_PANEL)
                .id("text-box");

        rootComponent.child(EditBox);

        TimerTask FocusCheck = new TimerTask() {
            @Override
            public void run() {
                    if (TextArea.isFocused() & Objects.equals(TextArea.getText(), ContentPlaceholder)) {
                        TextArea.setText("");
                    }
                    if (FilenameBox.isFocused() & Objects.equals(FilenameBox.getText(), FilenamePlaceholder)) {
                        FilenameBox.setText("");
                    }

            }
        };
        TimerTask UnfocusCheck = new TimerTask() {
            @Override
            public void run() {
                    if (!TextArea.isFocused() & Objects.equals(TextArea.getText(), "")) {
                        TextArea.setText(ContentPlaceholder);
                    }
                    if (!FilenameBox.isFocused() & Objects.equals(FilenameBox.getText(), "")) {
                        FilenameBox.setText(FilenamePlaceholder);
                    }
            }
        };
        Timer Timer = new Timer();
        Timer.schedule(FocusCheck, 0, 250);
        Timer.schedule(UnfocusCheck, 0, 250);
    }
}
