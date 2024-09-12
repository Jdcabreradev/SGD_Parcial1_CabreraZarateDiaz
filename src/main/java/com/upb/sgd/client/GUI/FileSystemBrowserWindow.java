/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.upb.sgd.client.GUI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import com.upb.sgd.client.GUI.Interface.AbstractGUIWindow;
import com.upb.sgd.client.GUI.Utils.DocumentCellRenderer;
import com.upb.sgd.client.Mediator.ClientMediator;
import com.upb.sgd.shared.domain.Directory;
import com.upb.sgd.shared.domain.Document;
import com.upb.sgd.shared.domain.Folder;
import com.upb.sgd.utils.FileUtils;

/**
 *
 * @author sebastian
 */
public class FileSystemBrowserWindow extends AbstractGUIWindow {

    private JFrame viewFrame;
    private JTree folderTree;
    private JList<Document> documentList;

    public FileSystemBrowserWindow(ClientMediator mediator) {
        super(mediator);
    }

    @Override
    public void Init() {
        viewFrame = new JFrame("SGD - Browser");
        viewFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        viewFrame.setSize(800, 600);
        viewFrame.setLayout(new BorderLayout());

        JToolBar toolBar = new JToolBar();

        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> {
            RefreshFileSystemView();  // Method to refresh the file system view
        });
        toolBar.add(refreshButton);

        // Toolbar
        JButton fileButton = new JButton("File");
        JMenu fileMenu = new JMenu("File");
        JMenuItem uploadItem = new JMenuItem("Upload");
        uploadItem.addActionListener(e -> {
            UploadDocumentDialog();
        });
        fileMenu.add(uploadItem);

        JPopupMenu filePopupMenu = new JPopupMenu();
        filePopupMenu.add(fileMenu);
        fileButton.addActionListener(e -> filePopupMenu.show(fileButton, 0, fileButton.getHeight()));
        toolBar.add(fileButton);

        JButton folderButton = new JButton("Folder");
        JMenu folderMenu = new JMenu("Folder");
        JMenuItem newFolderItem = new JMenuItem("New Folder");
        newFolderItem.addActionListener(e -> {
            CreateNewFolder();
        });
        folderMenu.add(newFolderItem);

        JPopupMenu folderPopupMenu = new JPopupMenu();
        folderPopupMenu.add(folderMenu);
        folderButton.addActionListener(e -> folderPopupMenu.show(folderButton, 0, folderButton.getHeight()));
        toolBar.add(folderButton);
        viewFrame.add(toolBar, BorderLayout.NORTH);

        // Folder tree
        JPanel leftPanel = new JPanel(new BorderLayout());
        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(this.mediator.dataService.rootFolder);
        folderTree = new JTree(rootNode);
        folderTree.addTreeSelectionListener(e -> {
            DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) folderTree.getLastSelectedPathComponent();
            if (selectedNode != null) {
                this.mediator.dataService.currentFolder = (Folder) selectedNode.getUserObject();
                UpdateDocumentList(this.mediator.dataService.currentFolder);
                System.out.println(this.mediator.dataService.currentFolder.name);
            }
        });
        JScrollPane leftScrollPane = new JScrollPane(folderTree);
        leftPanel.add(leftScrollPane, BorderLayout.CENTER);

        // Search Bar
        JPanel leftBottomPanel = new JPanel(new BorderLayout());
        JTextField leftSearchField = new JTextField();
        JButton leftSearchButton = new JButton("Search");

        leftSearchButton.addActionListener(e->{
            var result = this.mediator.dataService.SearchForDocuments(
                leftSearchField.getText()
            );

            UpdateFolderTree(
                result
            );
            UpdateDocumentList(result);
        } );

        leftBottomPanel.add(leftSearchField, BorderLayout.CENTER);
        leftBottomPanel.add(leftSearchButton, BorderLayout.EAST);
        leftPanel.add(leftBottomPanel, BorderLayout.SOUTH);

        // Document List
        JPanel rightPanel = new JPanel(new BorderLayout());
        documentList = new JList<>();
        documentList.setCellRenderer(new DocumentCellRenderer());

        // Document Interaction Menu
        documentList.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    int index = documentList.locationToIndex(e.getPoint());
                    if (index != -1) {
                        documentList.setSelectedIndex(index);
                        JPopupMenu filePopupMenu = new JPopupMenu();

                        // Download option
                        JMenuItem downloadItem = new JMenuItem("Download");
                        downloadItem.addActionListener(ev -> {
                            Document selectedDocument = documentList.getSelectedValue();
                            DownloadDocument(selectedDocument);
                        });
                        filePopupMenu.add(downloadItem);

                        // Rename option
                        JMenuItem renameItem = new JMenuItem("Rename");
                        renameItem.addActionListener(ev -> {
                            Document selectedDocument = documentList.getSelectedValue();
                            RenameDirectoryDialog(selectedDocument);
                        });
                        filePopupMenu.add(renameItem);

                        // Properties option
                        JMenuItem propertiesItem = new JMenuItem("Properties");
                        propertiesItem.addActionListener(ev -> {
                            Document selectedDocument = documentList.getSelectedValue();
                            ShowDirectoryProperties(selectedDocument);
                        });
                        filePopupMenu.add(propertiesItem);
                        filePopupMenu.show(documentList, e.getX(), e.getY());
                    }
                }
            }
        });

        JScrollPane rightScrollPane = new JScrollPane(documentList);
        rightPanel.add(rightScrollPane, BorderLayout.CENTER);
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        viewFrame.add(splitPane, BorderLayout.CENTER);

        // Notifications
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel notificationLabel = new JLabel("Notifications will be displayed here.");
        footerPanel.add(notificationLabel);
        viewFrame.add(footerPanel, BorderLayout.SOUTH);
        viewFrame.setVisible(true);

        // Load file system data into the view
        this.mediator.dataService.GetRoot();
        this.UpdateFolderTree(this.mediator.dataService.rootFolder);
        this.UpdateDocumentList(this.mediator.dataService.rootFolder);
    }

    private void UploadDocumentDialog() {
        JDialog uploadDialog = new JDialog(viewFrame, "Upload Document", true);
        uploadDialog.setSize(400, 300);
        uploadDialog.setLayout(new BorderLayout());

        // Document picker
        JButton chooseFileButton = new JButton("Choose File");
        JTextField selectedFilePath = new JTextField(20);
        chooseFileButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int returnValue = fileChooser.showOpenDialog(viewFrame);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                selectedFilePath.setText(selectedFile.getAbsolutePath());
                Document uploadDoc = new Document();
                uploadDoc.name = selectedFile.getName();
                uploadDoc.path = selectedFilePath.getName();
                try {
                    uploadDoc.fileData = FileUtils.readFileToByteArray(Paths.get(selectedFile.getAbsolutePath()));
                } catch (IOException ex) {
                    System.out.println("unable to find uploadFile path");
                }
                this.mediator.dataService.UploadFile(uploadDoc,this.mediator.dataService.currentFolder.getPath());
            }
        });

        JPanel filePanel = new JPanel();
        filePanel.add(chooseFileButton);
        filePanel.add(selectedFilePath);

        JTextField documentNameField = new JTextField(20);
        JTextField permissionsField = new JTextField(20);
        JTextField tagsField = new JTextField(20);

        JPanel attributesPanel = new JPanel(new GridLayout(3, 2));
        attributesPanel.add(new JLabel("Document Name:"));
        attributesPanel.add(documentNameField);
        attributesPanel.add(new JLabel("Permissions:"));
        attributesPanel.add(permissionsField);
        attributesPanel.add(new JLabel("Tags:"));
        attributesPanel.add(tagsField);

        JButton uploadButton = new JButton("Upload");
        uploadButton.addActionListener(e -> {
            String documentName = documentNameField.getText();
            String permissions = permissionsField.getText();
            String tags = tagsField.getText();
            String filePath = selectedFilePath.getText();

            if (!filePath.isEmpty()) {
                // Logic
                System.out.println("Uploading document: " + documentName + " | Permissions: " + permissions + " | Tags: " + tags + " | File: " + filePath);
                uploadDialog.dispose();
            } else {
                JOptionPane.showMessageDialog(uploadDialog, "Please choose a file to upload.");
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(uploadButton);

        // Add panels to the dialog
        uploadDialog.add(filePanel, BorderLayout.NORTH);
        uploadDialog.add(attributesPanel, BorderLayout.CENTER);
        uploadDialog.add(buttonPanel, BorderLayout.SOUTH);

        // Show the dialog
        uploadDialog.setVisible(true);
    }

    @Override
    public void Close() {
        this.viewFrame.dispose();
    }

    private void ShowDirectoryProperties(Directory directory) {
        if (directory != null) {
            var tagString = "";
            for (String tag : directory.tags) {
                if("".equals(tagString)) tagString += tag;
                else tagString += ", " + tag;
            }

            JOptionPane.showMessageDialog(viewFrame,
                    "Name: " + directory.name + "\n"
                    + "Size: " + directory.size + "\n"
                    + "Created: " + directory.createdAt.toString() + "\n"
                    + "Permissions: " + directory.permissions + "\n"
                    + "Tags: " + tagString,
                    "Properties", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void RenameDirectoryDialog(Directory directory) {
        if (directory != null) {
            String newName = JOptionPane.showInputDialog(viewFrame, "Enter new name:", directory.name);
            if (newName != null && !newName.trim().isEmpty()) {
                //Logic
            }
        }
    }

    private void UpdateFolderTree(Folder folder) {
        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(folder);
        PopulateFolderTree(rootNode, folder);
        folderTree.setModel(new DefaultTreeModel(rootNode));
    }

    private void PopulateFolderTree(DefaultMutableTreeNode parentNode, Folder folder) {
        for (Directory child : folder.children) {
            DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(child);
            parentNode.add(childNode);
            if (child instanceof Folder tmpFolder) {
                PopulateFolderTree(childNode, tmpFolder);
            }
        }
    }

    private void UpdateDocumentList(Folder folder) {
        DefaultListModel<Document> listModel = new DefaultListModel<>();
        if (folder != null) {
            for (Directory dir : folder.children) {
                if (dir instanceof Document document) {
                    listModel.addElement(document);
                }
            }
        }
        documentList.setModel(listModel);
    }

    private void DownloadDocument(Document document) {
        System.out.println(document.name);
    }

    private void CreateNewFolder() {
        JDialog newFolderDialog = new JDialog(viewFrame, "Create Folder", true);
        newFolderDialog.setSize(400, 300);
        newFolderDialog.setLayout(new BorderLayout());

        JPanel folderPanel = new JPanel();
        JTextField foldername = new JTextField(20);

        folderPanel.add(foldername, BorderLayout.CENTER);

        JButton uploadButton = new JButton("Create");
        uploadButton.addActionListener(e -> {
            
        });

        newFolderDialog.add(folderPanel);
        newFolderDialog.setVisible(true);
    }

    private void RefreshFileSystemView(){
        //TODO: Call remote update to rootFolder
        UpdateFolderTree(mediator.dataService.rootFolder);
        UpdateDocumentList(mediator.dataService.rootFolder);
    }
}
