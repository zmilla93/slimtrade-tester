package com.slimtrade.core.managers;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.slimtrade.core.saving.OverlaySaveFile;
import com.slimtrade.core.saving.SaveFile;
import com.slimtrade.core.saving.ScannerSaveFile;
import com.slimtrade.core.saving.StashSaveFile;
import com.slimtrade.gui.options.ISaveable;
import com.slimtrade.gui.panels.ContainerPanel;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

public class SaveManager {

    // Public Info
    public final String savePath;
    public final String stashSavePath;
    public final String overlaySavePath;
    public final String scannerSavePath;
    public final String saveDirectory;
    public SaveFile saveFile = new SaveFile();
    public StashSaveFile stashSaveFile = new StashSaveFile();
    public OverlaySaveFile overlaySaveFile = new OverlaySaveFile();
    public ScannerSaveFile scannerSaveFile = new ScannerSaveFile();

    public ArrayList<String> clientPaths = new ArrayList<>();

    //Internal
    private final String folderWin = "SlimTrade";
    private final String folderOther = ".slimtrade";
    private final String fileName = "settings.json";
    private final String stashFileName = "stash.json";
    private final String overlayFileName = "overlay.json";
    private final String scannerFileName = "scanner.json";

    private boolean validSavePath = false;

    // File Stuff
    private FileReader fr;
    private BufferedReader br;
    private FileWriter fw;
    private Gson gson;

    // TODO : OPTIMIZE :    Combine all saving and loading functions into one using wildcars?
    // TODO :               Also need to add file.exists() check to avoid try/catch

    public SaveManager() {

        // Set save directory
        String os = (System.getProperty("os.name")).toUpperCase();
        if (os.contains("WIN")) {
            saveDirectory = System.getenv("LocalAppData") + File.separator + folderWin;
        } else {
            saveDirectory = System.getProperty("user.home") + File.separator + folderOther;
        }
        savePath = saveDirectory + File.separator + fileName;
        stashSavePath = saveDirectory + File.separator + stashFileName;
        overlaySavePath = saveDirectory + File.separator + overlayFileName;
        scannerSavePath = saveDirectory + File.separator + scannerFileName;
        File saveDir = new File(saveDirectory);
        if (!saveDir.exists()) {
            saveDir.mkdirs();
        }
        if (saveDir.exists()) {
            validSavePath = true;
        }

        gson = new Gson();

    }

    public void loadFromDisk() {
        StringBuilder builder = new StringBuilder();
        try {
            br = new BufferedReader(new FileReader(savePath));
            while (br.ready()) {
                builder.append(br.readLine());
            }
            br.close();
            saveFile = gson.fromJson(builder.toString(), SaveFile.class);
            if (saveFile == null) {
                saveFile = new SaveFile();
            }
        } catch (JsonSyntaxException e1) {
            saveFile = new SaveFile();
            validateClientPath();
            System.out.println("Corrupted save file!");
            return;
        } catch (IOException e2) {
//            System.out.println("Creating new save file.");
            saveFile = new SaveFile();
            validateClientPath();

            return;
        }
        validateClientPath();
    }

    public void saveToDisk() {
        try {
            fw = new FileWriter(savePath);
            fw.write(gson.toJson(saveFile));
            fw.close();
        } catch (IOException e) {
            return;
        }
    }

    public void loadStashFromDisk() {
        StringBuilder builder = new StringBuilder();
        try {
            br = new BufferedReader(new FileReader(stashSavePath));
            while (br.ready()) {
                builder.append(br.readLine());
            }
            br.close();
            stashSaveFile = gson.fromJson(builder.toString(), StashSaveFile.class);
            if (stashSaveFile == null) {
                stashSaveFile = new StashSaveFile();
            }
        } catch (JsonSyntaxException e1) {
            stashSaveFile = new StashSaveFile();
            System.out.println("Corrupted save file!");
            return;
        } catch (IOException e2) {
            stashSaveFile = new StashSaveFile();
//            System.out.println("Creating new save file.");
            return;
        }
    }

    public void saveStashToDisk() {
        try {
            fw = new FileWriter(stashSavePath);
            fw.write(gson.toJson(stashSaveFile));
            fw.close();
        } catch (IOException e) {
            return;
        }
    }

    public void loadOverlayFromDisk() {
        StringBuilder builder = new StringBuilder();
        try {
            br = new BufferedReader(new FileReader(overlaySavePath));
            while (br.ready()) {
                builder.append(br.readLine());
            }
            br.close();
            overlaySaveFile = gson.fromJson(builder.toString(), OverlaySaveFile.class);
            if (overlaySaveFile == null) {
                overlaySaveFile = new OverlaySaveFile();
            }
        } catch (JsonSyntaxException e1) {
            overlaySaveFile = new OverlaySaveFile();
            System.out.println("Corrupted save file!");
            return;
        } catch (IOException e2) {
            overlaySaveFile = new OverlaySaveFile();
//            System.out.println("Creating new save file.");
            return;
        }
    }

    public void saveOverlayToDisk() {
        try {
            fw = new FileWriter(overlaySavePath);
            fw.write(gson.toJson(overlaySaveFile));
            fw.close();
        } catch (IOException e) {
            return;
        }
    }

    public void loadScannerFromDisk() {
        StringBuilder builder = new StringBuilder();
        try {
            br = new BufferedReader(new FileReader(scannerSavePath));
            while (br.ready()) {
                builder.append(br.readLine());
            }
            br.close();
            scannerSaveFile = gson.fromJson(builder.toString(), ScannerSaveFile.class);
            if (scannerSaveFile == null) {
                scannerSaveFile = new ScannerSaveFile();
            }
        } catch (JsonSyntaxException e1) {
            scannerSaveFile = new ScannerSaveFile();
            System.out.println("Corrupted save file!");
            return;
        } catch (IOException e2) {
            scannerSaveFile = new ScannerSaveFile();
            saveScannerToDisk();
//            System.out.println("Creating new save file.");
            return;
        }
    }

    public void saveScannerToDisk() {
        try {
            fw = new FileWriter(scannerSavePath);
            fw.write(gson.toJson(scannerSaveFile));
            fw.close();
        } catch (IOException e) {
            return;
        }
    }

    public int validateClientPath() {
        int clientCount = 0;
        String clientPath = saveFile.clientPath;
        if (clientPath != null) {
            File file = new File(clientPath);
            if (file.exists() && file.isFile()) {
                return 1;
            }
        }
        String[] commonDrives = {"C", "D", "E", "F"};
        ArrayList<String> stubs = new ArrayList<>();
        stubs.add(":/Program Files/Steam/steamapps/common/Path of Exile/logs/Client.txt");
        stubs.add(":/Program Files (x86)/Steam/steamapps/common/Path of Exile/logs/Client.txt");
        stubs.add(":/Program Files/Grinding Gear Games/Path of Exile/logs/Client.txt");
        stubs.add(":/Program Files (x86)/Grinding Gear Games/Path of Exile/logs/Client.txt");
        stubs.add(":/Steam/steamapps/common/Path of Exile/logs/Client.txt");
        stubs.add(":/SteamLibrary/steamapps/common/Path of Exile/logs/Client.txt");
        clientPaths.clear();
        for (String drive : commonDrives) {
            for(String stub : stubs) {
                File clientFile = new File(drive + stub);
                if (clientFile.exists() && clientFile.isFile()) {
//                    System.out.println("Found : " + drive + stub);
                    clientPaths.add(drive + stub);
                    clientCount++;
                }
            }
        }
        if(clientCount == 1) {
            saveFile.clientPath = clientPaths.get(0);
        }
        return clientCount;
    }

    public static void recursiveSave(Component component) {
        if(component instanceof ISaveable) {
            ((ISaveable) component).save();
        }
        if(component instanceof Container) {
            for(Component c : ((Container) component).getComponents()) {
                recursiveSave(c);
            }
        }
    }

    public static void recursiveLoad(Component component) {
        if(component instanceof ISaveable) {
            ((ISaveable) component).load();
        }
        if(component instanceof Container) {
            for(Component c : ((Container) component).getComponents()) {
                recursiveLoad(c);
            }
        }
    }

}
