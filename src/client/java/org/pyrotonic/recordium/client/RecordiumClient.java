package org.pyrotonic.recordium.client;


import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ButtonTextures;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;
import org.pyrotonic.recordium.Recordium;

import java.io.File;

public class RecordiumClient implements ClientModInitializer {
    public static final MinecraftClient client = MinecraftClient.getInstance();
    public static boolean IsCreated;
    public static boolean IsIngame;
    public static KeyBinding OpenMenuKeybind;
    public static KeyBinding QuickCreateKeybind;
    public static final String EXIT_BUTTON = "button.exit";
    public static final String RENAME_BUTTON = "button.rename";
    public static final String COORDS_BUTTON = "button.coords";
    public static final String SAVE_BUTTON = "button.save";
    public static final String CREATE_NOTE_BUTTON = "button.createnote";
    public static final String OPEN_NOTE_BUTTON = "button.opennote";
    public static final String NEXT_PAGE_BUTTON = "button.nextpage";
    public static final String BACK_PAGE_BUTTON = "button.backpage";
    public static final String KEY_CREATE_NOTE = "key.recordium.createnote";
    public static final String KEY_CATEGORY_SIMPLENOTES = "key.category.recordium.notes";
    public static final String KEY_OPEN_NOTE_SELECTOR = "key.recordium.noteselector";
    public static final String KEY_TEXT_EDITBOX_PLACEHOLDER = "text.editbox.placeholder";
    public static final String KEY_TEXTFIELD_PLACEHOLDER = "text.createnote.textfield.placeholder";
    public static final String KEY_RENAMENOTE_TEXTWIDGET = "text.renamenote.textwidget";
    public static final String TOAST_SUCCESS = "toast.success";
    public static final String TOAST_FAILURE = "toast.failure";
    public static final String TOAST_RENAME_SUCCESS = "toast.rename.success";
    public static final String TOAST_RENAME_FAILURE = "toast.rename.failure";
    public static final String TOAST_RENAME_DUPLICATE = "toast.rename.duplicate";
    public static final String TOAST_SAVE_SUCCESS = "toast.save.success";
    public static final String TOAST_SAVE_FAILURE = "toast.save.failure";
    public static final String TOAST_SAVE_DUPLICATE = "toast.save.duplicate";
    public static final String ENTRY_LASTMODIFIED = "entry.lastmodified";
    public static final String ENTRY_CREATED = "entry.created";
    public static final String ENTRY_SIZE = "entry.size";
    public static final String NOTE_DIRECTORY_PATH = "recordium/notes/";
    public static final ButtonTextures MAIN_MENU_BUTTON_TEXTURE = new ButtonTextures(
        Identifier.of("recordium", "main_menu/unfocused"),
        Identifier.of("recordium", "main_menu/focused")
    );
    public static final ButtonTextures TRANSPARANT_BUTTON_TEXTURES = new ButtonTextures(
            Identifier.of("recordium:widget/transparant_button"),
            Identifier.of("recordium:widget/transparant_button_disabled"),
            Identifier.of("recordium:widget/transparant_button_highlighted")
    );
    public static final ButtonTextures EDIT_BOX_TEXTURES = new ButtonTextures(
            Identifier.of( "recordium:widget/transparant_textfield"),
            Identifier.of("recordium:widget/transparant_textfield_highlighted")
    );
    public static final ButtonTextures NOTE_ENTRY_TEXTURES = new ButtonTextures(
            Identifier.of("recordium:widget/note-entry/note_entry"),
            Identifier.of("recordium:widget/note-entry/note_entry_highlighted")
    );
    @Override
    public void onInitializeClient() {

        OpenMenuKeybind = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                KEY_OPEN_NOTE_SELECTOR,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_O,
                KEY_CATEGORY_SIMPLENOTES
        ));
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (OpenMenuKeybind.wasPressed()) {
                IsIngame = true;
                client.setScreen(null);
            }});
        QuickCreateKeybind = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            KEY_CREATE_NOTE,
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_N,
            KEY_CATEGORY_SIMPLENOTES
        ));
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (QuickCreateKeybind.wasPressed()) {
                IsIngame = true;
                IsCreated = true;
                client.setScreen(null);
            }
        });
        File NoteDirectory = new File(NOTE_DIRECTORY_PATH);
        if (NoteDirectory.mkdirs()) {
            Recordium.LOGGER.info("Directories created!");
        }
    }
}
