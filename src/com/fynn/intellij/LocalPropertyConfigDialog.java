package com.fynn.intellij;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;

import javax.swing.*;
import java.awt.event.*;

public class LocalPropertyConfigDialog extends JDialog {

    private JPanel contentPane;
    private JButton btnSave;
    private JButton btnCancel;
    private JButton btnSaveSync;

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
                onCancel();
                Messages.showInfoMessage("hhhww", "title");
            }
        });

        btnSaveSync.requestFocusInWindow();
    }

    public static void main(Project project, AnActionEvent event, LocalPropertySet set) {
        LocalPropertyConfigDialog dialog = new LocalPropertyConfigDialog(project, event, set);
        dialog.pack();
        dialog.setLocationRelativeTo(SwingUtilities.getWindowAncestor(event.getInputEvent().getComponent()));
        dialog.setVisible(true);
    }

    private void onOK() {
        // add your code here
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
}
