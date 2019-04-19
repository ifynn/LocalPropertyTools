package com.fynn.intellij;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.util.Iterator;
import java.util.Properties;

public class LocalPropertyConfigDialog extends JDialog {

    private JPanel contentPane;
    private JButton btnSave;
    private JButton btnCancel;
    private JButton btnSaveSync;
    private JTextPane textData;

    private Project project;
    private AnActionEvent event;
    private LocalPropertySet propertySet;

    public LocalPropertyConfigDialog(Project project, AnActionEvent event, LocalPropertySet propertySet) {
        this.project = project;
        this.event = event;
        this.propertySet = propertySet;

        initContentPane();
        initOthers();
    }

    private void initContentPane() {
        setContentPane(contentPane);
        setModal(true);

        getRootPane().setDefaultButton(btnSaveSync);

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        setMaximumSize(new Dimension(1600,1200));
    }

    private void initOthers() {
        btnSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        btnCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        btnSaveSync.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onOKAndSync();
            }
        });

        btnSaveSync.requestFocusInWindow();
        loadLocalProperty();
    }

    public static void main(Project project, AnActionEvent event, LocalPropertySet set) {
        LocalPropertyConfigDialog dialog = new LocalPropertyConfigDialog(project, event, set);
        dialog.pack();
        dialog.setLocationRelativeTo(SwingUtilities.getWindowAncestor(event.getInputEvent().getComponent()));
        dialog.setVisible(true);
    }

    private void loadLocalProperty() {
        if (propertySet.properties == null) {
            return;
        }

        Iterator iterator = propertySet.properties.keySet().iterator();
        textData.setText("");

        StyledDocument doc = textData.getStyledDocument();
        SimpleAttributeSet attr = new SimpleAttributeSet();

        while (iterator.hasNext()) {
            String key = (String) iterator.next();
            String value = propertySet.properties.getProperty(key);

            try {
                StyleConstants.setForeground(attr, new Color(0x000085));
                StyleConstants.setBold(attr, true);
                doc.insertString(doc.getLength(), key, attr);

                doc.insertString(doc.getLength(), "=", null);

                StyleConstants.setForeground(attr, new Color(0x008400));
                doc.insertString(doc.getLength(), value, attr);

                doc.insertString(doc.getLength(), "\n", null);

            } catch (Exception e) {
                Messages.showInfoMessage("An internal exception occurred", "LocalPropertyTools");
            }
        }
    }

    private void onOK() {
        Properties properties = new LinkedProperties();
        try {
            properties.load(new StringReader(textData.getText()));

            VirtualFile file = project.getBaseDir().findFileByRelativePath("./local.properties");
            properties.store(new FileWriter(file.getPath()), "");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    private void onOKAndSync() {
        dispose();
        onOK();

        AnAction syncProjectAction = event.getActionManager().getAction("Android.SyncProject");
        if (syncProjectAction != null) {
            syncProjectAction.actionPerformed(event);
        }
    }
}
