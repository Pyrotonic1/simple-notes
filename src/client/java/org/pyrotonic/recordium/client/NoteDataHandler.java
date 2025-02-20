package org.pyrotonic.recordium.client;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import org.apache.commons.io.FileUtils;
import org.pyrotonic.recordium.Recordium;

public class NoteDataHandler {
     private String content;
     private String filename;


    public NoteDataHandler(String content, String filename) {
        this.content = content;
        this.filename = filename;
    }


    public String getFilename() {
        return this.filename;
    }

    public static Boolean saveContent(String content, String filename) {
        try {
            if (filename.contains(".txt")) {
                filename = filename.replace(".txt", "");
            }
            FileWriter note = new FileWriter(RecordiumClient.NOTE_DIRECTORY_PATH + filename + ".txt");
            note.write(content);
            note.close();
        } catch (FileNotFoundException err) {
            Recordium.LOGGER.error("The note was not found. Try restarting.");
            return false;
        } catch (IOException err) {
            Recordium.LOGGER.error("An IOException error occurred while saving the file.", err.fillInStackTrace());
            return false;
        }
        return true;
    }
    
    public Boolean saveFilename(String oldFilename, String newFilename) throws IOException {
    File OldFile = new File(RecordiumClient.NOTE_DIRECTORY_PATH + oldFilename);
    File NewFile = new File(RecordiumClient.NOTE_DIRECTORY_PATH + newFilename);

    try {
        FileUtils.moveFile(OldFile, new File(NewFile + ".txt"));
        } catch (FileNotFoundException err) {
            Recordium.LOGGER.error("The file you tried to rename could not be found.", err.fillInStackTrace());
            return false;
        } catch (FileAlreadyExistsException err) {
            Recordium.LOGGER.error("There is a file that already has that name.", err.fillInStackTrace());
        }
        return true;
    } 

    public Boolean deleteFile(String note) {
        File Note = new File(RecordiumClient.NOTE_DIRECTORY_PATH + note);

        try {
            FileUtils.delete(Note);
        } catch (IOException err) {
            Recordium.LOGGER.error("An IOException occurred while deleting the file.", err.fillInStackTrace());
            return false;
        }
        return true;
    }

    public static String readNote(String filename) {
        if (!filename.contains(".txt")) {
            filename = filename + ".txt";
        }
        String data = "";
        try {
            data = new String(
                    Files.readAllBytes(Paths.get(RecordiumClient.NOTE_DIRECTORY_PATH + filename)));
        } catch (IOException err) {
            Recordium.LOGGER.error("An IOException error occurred while reading the file.", err.fillInStackTrace());
        }
        return data;
        }

    public static ArrayList<String> readFilenames() {
        File Path = new File("recordium/notes/");
        String[] namesArray = Path.list();
        assert namesArray != null;
        return new ArrayList<>(Arrays.asList(namesArray));
        }

    public String getLastModified() throws IOException {
        String filename;
        if (!this.filename.contains(".txt")) {
            filename = this.filename + ".txt";
        } else {
            filename = this.filename;
        }
        Path path = Paths.get("recordium/notes/" + filename);
        DateFormat dateFormat = new SimpleDateFormat();
        FileTime fileTime = Files.getLastModifiedTime(path);

        return dateFormat.format(fileTime.toMillis());
    }

    public String getCreated() throws IOException {
        String filename;
        if (!this.filename.contains(".txt")) {
            filename = this.filename + ".txt";
        } else {
            filename = this.filename;
        }
        Path path = Paths.get("recordium/notes/" + filename);
        DateFormat dateFormat = new SimpleDateFormat();
        BasicFileAttributes fileAttributes = Files.readAttributes(path.toRealPath(), BasicFileAttributes.class);

        return dateFormat.format(fileAttributes.creationTime().toMillis());
    }

    public String getFileSize() throws IOException {
        String filename;
        if (!this.filename.contains(".txt")) {
            filename = this.filename + ".txt";
        } else {
            filename = this.filename;
        }
        File file = new File("recordium/notes/" + filename);
        long sizeInBytes = file.length();
        double sizeInKB =  (double) sizeInBytes / 1024;
        String formattedSize = (new DecimalFormat("#.##").format(sizeInKB));

        return String.valueOf(formattedSize);
    }
}

