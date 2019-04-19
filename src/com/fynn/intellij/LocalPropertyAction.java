package com.fynn.intellij;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.util.IconLoader;

import javax.swing.*;

public class LocalPropertyAction extends AnAction {

    public static Icon ICON_LOGO = IconLoader.getIcon("/logo.png");

    public LocalPropertyAction() {
        super(ICON_LOGO);
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        LocalPropertySet set = Utils.readProperties(e.getProject());

        if (set == null) {
            Messages.showInfoMessage("没有找到 local.properties 文件", "LocalProperty Tools");
        } else {
            LocalPropertyConfigDialog.main(e.getProject(), e, set);
        }
    }
}
