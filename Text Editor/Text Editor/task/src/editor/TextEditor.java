package editor;

import javax.swing.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TextEditor extends JFrame {
    public TextEditor() {
        super("Text Editor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setVisible(true);

        JTextField fileNameField = new JTextField();
        fileNameField.setName("FilenameField");
        fileNameField.setBounds(10, 20, 200, 30);
        add(fileNameField);

        JTextArea textArea = new JTextArea();
        textArea.setName("TextArea");
        textArea.setBounds(10, 100, 550, 200);
        add(textArea);

        JButton saveButton = new JButton("Save");
        saveButton.setName("SaveButton");
        saveButton.setBounds(230, 20, 100, 30);
        add(saveButton);

        saveButton.addActionListener(e -> {
            String fileName = fileNameField.getText();
            String text = textArea.getText();
            try {
                Files.write(Paths.get(fileName), text.getBytes());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        JButton loadButton = new JButton("Load");
        loadButton.setName("LoadButton");
        loadButton.setBounds(340, 20, 100, 30);
        add(loadButton);

        loadButton.addActionListener(e -> {
            textArea.setText(null);
            String textFromFile;
            try {
                textFromFile = Files.readString(Paths.get(fileNameField.getText()));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            textArea.setText(textFromFile);
        });

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setName("ScrollPane");
        add(scrollPane);
    }
}
