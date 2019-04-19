package com.fynn.intellij;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;

import java.io.IOException;

public class Utils {

    /**
     * Read File 'local.properties'
     */
    public static final LocalPropertySet readProperties(Project project) {
        VirtualFile propertiesFile = project.getBaseDir().findFileByRelativePath("./local.properties");

        if (propertiesFile == null || !propertiesFile.exists()) {
            return null;
        }

        LinkedProperties properties = new LinkedProperties();

        try {
            properties.load(propertiesFile.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        LocalPropertySet set = new LocalPropertySet();
        set.properties = properties;
        return set;
    }
}
