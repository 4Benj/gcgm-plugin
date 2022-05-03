package com.benj4.gcgm.utils;

import com.benj4.gcgm.GCGMPlugin;
import emu.grasscutter.Grasscutter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class GCGMUtils {

    public static String GetDispatchAddress() {
        return "http" + (Grasscutter.getConfig().getDispatchOptions().FrontHTTPS ? "s" : "") + "://" +
                (Grasscutter.getConfig().getDispatchOptions().PublicIp.isEmpty() ? Grasscutter.getConfig().getDispatchOptions().Ip : Grasscutter.getConfig().getDispatchOptions().PublicIp) +
                ":" + (Grasscutter.getConfig().getDispatchOptions().PublicPort != 0 ? Grasscutter.getConfig().getDispatchOptions().PublicPort : Grasscutter.getConfig().getDispatchOptions().Port);
    }

    public static boolean CopyFile(String resourceName, String copyLocation) {
        try {
            Grasscutter.getLogger().info("[GCGM] Copying 'DefaultWebApp.zip' to './plugins/GCGM'");
            Files.copy(GCGMPlugin.GetInstance().getResource(resourceName), Paths.get(new File(copyLocation).toURI()), StandardCopyOption.REPLACE_EXISTING);
            return true;
        } catch (IOException e) {
            Grasscutter.getLogger().error(String.format("[GCGM] An error occurred while trying to copy '%s' to '%s'", resourceName, copyLocation));
            e.printStackTrace();
            return false;
        }
    }
}
