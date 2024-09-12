/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.upb.sgd.client.GUI.Utils;

/**
 *
 * @author sebastian
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.text.SimpleDateFormat;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import com.upb.sgd.shared.domain.Document;

public class DocumentCellRenderer extends JPanel implements ListCellRenderer<Document> {
    private final JLabel nameLabel;
    private final JLabel dateLabel;
    private final JLabel sizeLabel;
    private final JPanel container;

    public DocumentCellRenderer() {
        setLayout(new BorderLayout());
        container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        add(container, BorderLayout.CENTER);

        nameLabel = new JLabel();
        dateLabel = new JLabel();
        sizeLabel = new JLabel();

        container.add(nameLabel);
        container.add(dateLabel);
        container.add(sizeLabel);

        setBorder(BorderFactory.createLineBorder(Color.GRAY));
        setPreferredSize(new Dimension(200, 60));
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends Document> list, Document value, int index, boolean isSelected, boolean cellHasFocus) {
        nameLabel.setText("Name: " + value.name);
        dateLabel.setText("Created: " + new SimpleDateFormat("yyyy-MM-dd").format(value.createdAt));
        sizeLabel.setText("Size: " + value.size);

        if (isSelected) {
            setBackground(Color.BLUE);
            nameLabel.setForeground(Color.WHITE);
            dateLabel.setForeground(Color.WHITE);
            sizeLabel.setForeground(Color.WHITE);
        } else {
            setBackground(Color.WHITE);
            nameLabel.setForeground(Color.BLACK);
            dateLabel.setForeground(Color.BLACK);
            sizeLabel.setForeground(Color.BLACK);
        }
        return this;
    }
}

