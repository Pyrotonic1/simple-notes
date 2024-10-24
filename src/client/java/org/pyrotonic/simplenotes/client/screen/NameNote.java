package org.pyrotonic.simplenotes.client.screen;

import io.wispforest.owo.ui.base.BaseOwoScreen;
import io.wispforest.owo.ui.component.Components;
import io.wispforest.owo.ui.component.TextBoxComponent;
import io.wispforest.owo.ui.container.Containers;
import io.wispforest.owo.ui.container.FlowLayout;
import io.wispforest.owo.ui.core.*;
import net.minecraft.text.Text;
import org.jetbrains.annotations.NotNull;
import org.pyrotonic.simplenotes.client.NoteDataHandler;
import org.pyrotonic.simplenotes.client.SimplenotesClient;

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class NameNote extends BaseOwoScreen<FlowLayout> {
    @Override
    protected @NotNull OwoUIAdapter<FlowLayout> createAdapter() {
        return OwoUIAdapter.create(this, Containers::verticalFlow);
    }
    @Override
    protected void build(FlowLayout rootComponent) {
        NoteDataHandler Note = new NoteDataHandler(NoteDataHandler.readNote(NoteList.Filename), NoteList.Filename);
        final String NameBoxPlaceholder = "Enter Filename Here!";
        TextBoxComponent TextBox = Components.textBox(Sizing.fixed(116), "Enter Filename Here!");
        Component NameBox = Components.button(Text.literal("Create!"), buttonComponent -> {
            CreateNote.Filename = TextBox.getText();
            assert client != null;
            client.setScreen(new CreateNote());
            });

        TextBox.margins(Insets.of(5));
        NameBox.margins(Insets.of(5));

        ParentComponent NameDialog = Containers.horizontalFlow(Sizing.content(), Sizing.content())
                .child(TextBox)
                .child(NameBox)
                .surface(Surface.DARK_PANEL);

        rootComponent
            .child(NameDialog)
            .surface(Surface.OPTIONS_BACKGROUND)
            .horizontalAlignment(HorizontalAlignment.CENTER)
            .verticalAlignment(VerticalAlignment.CENTER);

        TimerTask FocusCheck = new TimerTask() {
            @Override
            public void run() {
                if (TextBox.isFocused() & Objects.equals(TextBox.getText(), NameBoxPlaceholder)) {
                    TextBox.setText("");
                }

            }
        };
        TimerTask UnfocusCheck = new TimerTask() {
            @Override
            public void run() {
                if (!TextBox.isFocused() & Objects.equals(TextBox.getText(), "")) {
                    TextBox.setText(NameBoxPlaceholder);
                }
            }
        };
        Timer Timer = new Timer();
        if (SimplenotesClient.IsCreated) {
            Timer.schedule(FocusCheck, 0, 250);
            Timer.schedule(UnfocusCheck, 0, 250);
        } else {
            TextBox.setText(Note.getFilename());
        }
    }
}
