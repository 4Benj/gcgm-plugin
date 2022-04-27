package com.benj4.gcgm;

import com.sun.net.httpserver.HttpExchange;
import emu.grasscutter.Grasscutter;
import emu.grasscutter.plugin.Plugin;
import emu.grasscutter.server.dispatch.DispatchServer;
import emu.grasscutter.utils.Utils;
import net.lingala.zip4j.ZipFile;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static com.benj4.gcgm.util.WebUtil.getFileExtensionImproved;
import static com.benj4.gcgm.util.WebUtil.responseStream;

public class GCGMPlugin extends Plugin {


    File webData;

    @Override
    public void onLoad() {
        Grasscutter.getLogger().info("Loading GCGM...");

        File pluginDataDir = new File(Utils.toFilePath(Grasscutter.getConfig().PLUGINS_FOLDER + "/gcgm"));
        webData = new File(Utils.toFilePath(Grasscutter.getConfig().PLUGINS_FOLDER + "/gcgm/www"));
        String zipFileLoc = Utils.toFilePath(Grasscutter.getConfig().PLUGINS_FOLDER + "/gcgm/DefaultWebApp.zip");

        if(!pluginDataDir.exists() && !pluginDataDir.mkdirs()) {
            Grasscutter.getLogger().error("Failed to create plugin data directory directory: " + pluginDataDir.getAbsolutePath());
            return;
        }

        if(!webData.exists()) {
            Grasscutter.getLogger().warn("The './plugins/gcgm/www' folder does not exist.");

            // Get the ZIP
            URL url = null;
            try {
                url = new File(Utils.toFilePath(Grasscutter.getConfig().PLUGINS_FOLDER + "/gcgm-plugin.jar")).toURI().toURL();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            //URLClassLoader loader = new URLClassLoader(new URL[]{url});

            InputStream defaultWebAppZip = getResource("DefaultWebApp.zip");
            try {
                // Copy the the zip from resources to the plugin's data directory
                if(!new File(zipFileLoc).exists()) {
                    Grasscutter.getLogger().info("Copying 'DefaultWebApp.zip' to './plugins/gcgm'");
                    Files.copy(defaultWebAppZip, Paths.get(new File(zipFileLoc).toURI()), StandardCopyOption.REPLACE_EXISTING);
                }

                Grasscutter.getLogger().warn("Please extract the contents of 'DefaultWebApp.zip' from within './plugins/gcgm' to './plugins/gcgm/www");
                Grasscutter.getLogger().warn("Your server will now exit to allow this process to be completed");

                System.exit(0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            if(new File(zipFileLoc).exists()) {
                Grasscutter.getLogger().info("Note: You can now safely delete 'DefaultWebApp.zip' from within './plugins/gcgm'");
            }
        }

        Grasscutter.getLogger().info("GCGM has now loaded...");
    }

    @Override
    public void onEnable() {
        Grasscutter.getLogger().info("GCGM Enabled");

        List<File> wwwFiles = listf(webData.getAbsolutePath());

        Grasscutter.getDispatchServer().getServer().createContext("/gm", t -> {
            File file = new File(Utils.toFilePath(webData.getAbsolutePath() + "/index.html"));

            responseStream(t, file, "text/html");
        });

        for (File file : wwwFiles) {
            String fromWebRoot = file.getAbsolutePath().replace(webData.getAbsolutePath(), "");
            fromWebRoot = fromWebRoot.replace("\\", "/");

            Grasscutter.getDispatchServer().getServer().createContext("/gm" + fromWebRoot, t -> {
                String fileExtension = getFileExtensionImproved(file.getAbsolutePath());
                String contentType = "";
                switch(fileExtension.toLowerCase()) {
                    case "png" -> {
                        contentType = "image/png";
                    }
                    case "js" -> {
                        contentType = "text/javascript";
                    }
                    case "css" -> {
                        contentType = "text/css";
                    }
                    case "html" -> {
                        contentType = "text/html";
                    }
                    case "json", "map" -> {
                        // A .map file is probably .css.map or .js.map file. So i'll return the save as .json
                        contentType = "application/json";
                    }
                    case "ico" -> {
                        contentType = "image/x-icon";

                    }
                    case "svg" -> {
                        contentType = "image/svg+xml";

                    }
                    default ->{
                        contentType = "unknown";
                        Grasscutter.getLogger().error("Unknown content type for extension: " + fileExtension);
                        Grasscutter.getLogger().error("For file: " + file.getAbsolutePath());
                    }
                }
                responseStream(t, file, contentType);
            });
        }
    }



    @Override
    public void onDisable() {
        Grasscutter.getLogger().info("GCGM Disabled");
    }

    private List<File> listf(String directoryName) {
        File directory = new File(directoryName);
        List<File> files = new ArrayList<File>();

        // Get all files from a directory.
        File[] fList = directory.listFiles();
        if (fList != null) {
            for (File file : fList) {
                if (file.isFile()) {
                    files.add(file);
                } else if (file.isDirectory()) {
                    files.addAll(listf(file.getAbsolutePath()));
                }
            }
        }
        return files;
    }
}
