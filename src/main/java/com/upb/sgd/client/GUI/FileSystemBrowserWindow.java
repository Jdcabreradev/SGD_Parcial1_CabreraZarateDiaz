/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.upb.sgd.client.GUI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;

import com.upb.sgd.client.GUI.Interface.AbstractGUIWindow;
import com.upb.sgd.client.Mediator.ClientMediator;

/**
 *
 * @author sebastian
 */
public class FileSystemBrowserWindow extends  AbstractGUIWindow{
    private JFrame viewFrame;
    
    public FileSystemBrowserWindow(ClientMediator mediator) {
        super(mediator);
    }

    @Override
        public void Init() {
 
        viewFrame = new JFrame("File System Browser");
        viewFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        viewFrame.setSize(800, 600);
        viewFrame.setLayout(new BorderLayout());


        JToolBar toolBar = new JToolBar();
        JButton fileButton = new JButton("File");
        JMenu fileMenu = new JMenu("File");
        JMenuItem uploadItem = new JMenuItem("Upload");


        uploadItem.addActionListener(e -> {
            System.out.println("Upload clicked");
        });


        fileMenu.add(uploadItem);
        JPopupMenu popupMenu = new JPopupMenu();
        popupMenu.add(fileMenu);
        fileButton.addActionListener(e -> popupMenu.show(fileButton, 0, fileButton.getHeight()));
        toolBar.add(fileButton);
        viewFrame.add(toolBar, BorderLayout.NORTH);

        // Left panel setup
        JPanel leftPanel = new JPanel(new BorderLayout());
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            // Back button logic here
            System.out.println("Back clicked");
        });
        leftPanel.add(backButton, BorderLayout.NORTH);

        // Middle part (Placeholder for now)
        JLabel leftPanelContent = new JLabel("Left panel content");
        leftPanel.add(leftPanelContent, BorderLayout.CENTER);

        // Bottom search bar with button on left panel
        JPanel leftBottomPanel = new JPanel(new BorderLayout());
        JTextField leftSearchField = new JTextField();
        JButton leftSearchButton = new JButton("Search");
        leftBottomPanel.add(leftSearchField, BorderLayout.CENTER);
        leftBottomPanel.add(leftSearchButton, BorderLayout.EAST);
        leftPanel.add(leftBottomPanel, BorderLayout.SOUTH);

        // Right panel setup (empty for now)
        JPanel rightPanel = new JPanel(new BorderLayout());
        JTextField rightSearchField = new JTextField();
        rightPanel.add(rightSearchField, BorderLayout.NORTH);

        // Split pane to separate left and right panels
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        viewFrame.add(splitPane, BorderLayout.CENTER);

        // Footer setup (Placeholder for notifications)
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel notificationLabel = new JLabel("Notifications will be displayed here.");
        footerPanel.add(notificationLabel);
        viewFrame.add(footerPanel, BorderLayout.SOUTH);

        // Show the frame
        viewFrame.setVisible(true);
    }

    @Override
    public void Close() {
        this.viewFrame.dispose();
    }
}
