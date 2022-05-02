package com.benj4.gcgm;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.plugin.Plugin;
import emu.grasscutter.utils.Utils;
import express.Express;
import io.javalin.http.staticfiles.Location;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class GCGMPlugin extends Plugin {

    File webData;

    @Override
    public void onLoad() {
        File pluginDataDir = getDataFolder();
        webData = new File(Utils.toFilePath(getDataFolder().getPath() + "/www"));
        String zipFileLoc = Utils.toFilePath(getDataFolder().getPath() + "/DefaultWebApp.zip");

        if(!pluginDataDir.exists() && !pluginDataDir.mkdirs()) {
            Grasscutter.getLogger().error("[GCGM] Failed to create plugin data directory directory: " + pluginDataDir.getAbsolutePath());
            return;
        }

        if(!webData.exists()) {
            Grasscutter.getLogger().warn("[GCGM] The './plugins/GCGM/www' folder does not exist.");

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
                    Grasscutter.getLogger().info("[GCGM] Copying 'DefaultWebApp.zip' to './plugins/GCGM'");
                    Files.copy(defaultWebAppZip, Paths.get(new File(zipFileLoc).toURI()), StandardCopyOption.REPLACE_EXISTING);
                }

                Grasscutter.getLogger().warn("[GCGM] Please extract the contents of 'DefaultWebApp.zip' from within './plugins/GCGM' to './plugins/GCGM/www");
                Grasscutter.getLogger().warn("[GCGM] Your server will now exit to allow this process to be completed");

                System.exit(0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            if(new File(zipFileLoc).exists()) {
                Grasscutter.getLogger().info("[GCGM] Note: You can now safely delete 'DefaultWebApp.zip' from within './plugins/GCGM'");
            }
        }

        Grasscutter.getLogger().info("[GCGM] GCGM has now loaded...");
    }

    @Override
    public void onEnable() {
        Express app = Grasscutter.getDispatchServer().getServer();

        app.raw().config.precompressStaticFiles = false;
        app.raw().config.addStaticFiles("/gm", webData.getAbsolutePath(), Location.EXTERNAL);
        app.raw().config.addSinglePageRoot("/gm", Utils.toFilePath(getDataFolder().getPath() + "/www/index.html"), Location.EXTERNAL);

        Grasscutter.getLogger().info("[GCGM] GCGM Enabled");
        Grasscutter.getLogger().info("[GCGM] You can access your GM panel by navigating to http" + (Grasscutter.getConfig().getDispatchOptions().FrontHTTPS ? "s" : "") + "://" +
                (Grasscutter.getConfig().getDispatchOptions().PublicIp.isEmpty() ? Grasscutter.getConfig().getDispatchOptions().Ip : Grasscutter.getConfig().getDispatchOptions().PublicIp) +
                ":" + (Grasscutter.getConfig().getDispatchOptions().PublicPort != 0 ? Grasscutter.getConfig().getDispatchOptions().PublicPort : Grasscutter.getConfig().getDispatchOptions().Port) +
                "/gm"
        );
    }

    @Override
    public void onDisable() {
        Grasscutter.getLogger().info("[GCGM] GCGM Disabled");
    }
}
