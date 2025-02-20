package org.pyrotonic.recordium.client.screen;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.toast.SystemToast;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;
import org.pyrotonic.recordium.client.NoteDataHandler;
import org.pyrotonic.recordium.client.RecordiumClient;
import org.pyrotonic.recordium.client.component.*;
import org.pyrotonic.recordium.client.exceptions.NoteNameNotSpecifiedException;

import java.util.ArrayList;

public class NoteEditorScreen extends Screen {
    // utils, used for rendering text and getting information about the client
    private final MinecraftClient client = MinecraftClient.getInstance();
    private final TextRenderer textRenderer = client.textRenderer;

    // components, each variable is a gui component on the screen itself
    private TransparantButtonWidget saveButton;
    private TransparantEditBoxWidget editBoxWidget;
    private TransparantButtonWidget coordsButton;
    private TransparantButtonWidget exitButton;
    private TransparantButtonWidget renameButton;
    private TransparantTextFieldWidget nameField;
    private TextWidget noteText;

    // variables for parameters when creating an instance of this class
    private final boolean isNew;
    private String noteName;

    // This will run if no NoteName parameter is specified. Used for if the user is creating a new note
    public NoteEditorScreen(boolean isNew) throws NoteNameNotSpecifiedException {
        super(Text.translatable("screen.recordium.createnote"));
        this.isNew = isNew;
        if (!isNew) {
            throw new NoteNameNotSpecifiedException("If the note isn't new, you must specify a string for noteName.");
        }
    }

    // This will run if a NoteName parameter is specified. Used for if the user is editing an already existing note.
    public NoteEditorScreen(boolean isNew, String noteName) {
        super(Text.translatable("screen.recordium.editnote"));
        this.isNew = isNew;
        this.noteName = noteName;
    }

    private String getFilename() {
        if (this.isNew) {
            return nameField.getText();
        } else {
            return noteName;
        }
    }

    static boolean doesFilenameExist = false;
    // The components work in wonky ways if they are not initialized in their own method
    private void initComponents() {
        saveButton = new TransparantButtonWidget((width - 137) + 28, 41, 100, 20, Text.translatable(RecordiumClient.SAVE_BUTTON), button -> {
            ArrayList<String> filenames = NoteDataHandler.readFilenames();
            for (String filename : filenames) {
                if (filename.replace(".txt", "").equals(nameField.getText())) {
                    doesFilenameExist = true;
                    break;
                }
                doesFilenameExist = false;
            }
            NoteDataHandler.saveContent(editBoxWidget.getText(), getFilename());
            if (doesFilenameExist) {
                client.getToastManager().add(
                        SystemToast.create(client, SystemToast.Type.NARRATOR_TOGGLE, Text.translatable(RecordiumClient.TOAST_FAILURE), Text.translatable(RecordiumClient.TOAST_SAVE_DUPLICATE)));
            } else if (NoteDataHandler.saveContent(editBoxWidget.getText(), getFilename())) {
                client.getToastManager().add(
                        SystemToast.create(client, SystemToast.Type.NARRATOR_TOGGLE, Text.translatable(RecordiumClient.TOAST_SUCCESS), Text.translatable(RecordiumClient.TOAST_SAVE_SUCCESS, getFilename()))
                );
            } else if (!NoteDataHandler.saveContent(editBoxWidget.getText(), getFilename())) {
                client.getToastManager().add(
                        SystemToast.create(client, SystemToast.Type.NARRATOR_TOGGLE, Text.translatable(RecordiumClient.TOAST_FAILURE), Text.translatable(RecordiumClient.TOAST_SAVE_FAILURE, getFilename()))
                );
            }
            // disable the button when it is pressed because it will be re-activated the next time the editBoxWidget is changed
            button.active = false;
        });

        nameField = new TransparantTextFieldWidget(textRenderer, 17, 10, width - 137, 20, Text.translatable(RecordiumClient.KEY_TEXTFIELD_PLACEHOLDER), Text.empty());
        nameField.setMaxLength(45);

        editBoxWidget = new TransparantEditBoxWidget(textRenderer, 17, 40, width - 137, height - 49, Text.translatable(RecordiumClient.KEY_TEXT_EDITBOX_PLACEHOLDER), Text.empty());
        if (!isNew) {
            editBoxWidget.setText(NoteDataHandler.readNote(getFilename()));
        }

        coordsButton = new TransparantButtonWidget((width - 137) + 28, 71, 100, 20, Text.translatable(RecordiumClient.COORDS_BUTTON), button -> {
            assert client.player != null;
            String xyz = "X: " + Math.floor(client.player.getX()) + " Y: " + Math.floor(client.player.getY()) + " Z: " + Math.floor(client.player.getZ());
            editBoxWidget.setText(editBoxWidget.getText() + xyz);
            saveButton.active = true;
        });

        exitButton = new TransparantButtonWidget((width - 137) + 28, 101, 100, 20, Text.translatable(RecordiumClient.EXIT_BUTTON), button -> client.setScreen(null));

        if (!this.isNew) {
            renameButton = new TransparantButtonWidget((width - 137) + 28, 11, 100, 20, Text.translatable(RecordiumClient.RENAME_BUTTON), button -> client.setScreen(new RenameScreen(this.getFilename())));
            noteText = new TextWidget(14, 8, 1.5f, "Editing: " + this.noteName);
        }
    }

    @Override
    protected void init() {
        // initialize components
        initComponents();

        // disable the save button by default
        saveButton.active = false;

        // If the content in the edit box has changed and the edit box is not empty, set the save button to be active
        editBoxWidget.setChangeListener((text) -> saveButton.active = !text.isEmpty());

        // add components to the screen
        this.addDrawableChild(coordsButton);
        this.addDrawableChild(editBoxWidget);
        this.addDrawableChild(saveButton);
        this.addDrawableChild(exitButton);
        if (this.isNew) {
            this.addDrawableChild(nameField);
        }
        if (!this.isNew) {
            this.addDrawableChild(noteText);
            this.addDrawableChild(renameButton);
        }

    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == GLFW.GLFW_KEY_ESCAPE) {
            this.editBoxWidget.setText("");
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }
}
