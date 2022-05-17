package editor;

import javax.swing.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TextEditor extends JFrame {
    JTextField searchField = new JTextField();
    JTextArea textArea = new JTextArea();
    JButton saveButton = new JButton("Save");
    JButton openButton = new JButton("Open");
    JButton searchButton = new JButton("Search");
    JButton previousMatchButton = new JButton("Previous");
    JButton nextMatchButton = new JButton("Next");
    JScrollPane scrollPane = new JScrollPane();
    JMenuBar menuBar = new JMenuBar();
    JMenu fileMenu = new JMenu("File");
    JMenu searchMenu = new JMenu("Search");
    JMenuItem openItem = new JMenuItem("Open");
    JMenuItem saveItem = new JMenuItem("Save");
    JMenuItem exitItem = new JMenuItem("Exit");
    JMenuItem menuStartSearchItem = new JMenuItem("Search");
    JMenuItem menuPreviousMatchItem = new JMenuItem("Previous");
    JMenuItem menuNextMatchItem = new JMenuItem("Next");
    JMenuItem menuUseRegExpItem = new JMenuItem("Use regular expressions");
    JFileChooser jFileChooser = new JFileChooser();
    JCheckBox regExpCheckBox = new JCheckBox();


    public TextEditor() {
        super("Text Editor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
        setVisible(true);

        regExpCheckBox.setName("UseRegExCheckbox");
        regExpCheckBox.setText("Use regex");
        regExpCheckBox.setBounds(530,25,100,20);
        add(regExpCheckBox);

        searchField.setName("SearchField");
        searchField.setBounds(10, 20, 200, 30);
        add(searchField);

        textArea.setName("TextArea");
        textArea.setBounds(10, 100, 750, 400);
        add(textArea);

        searchButton.setName("StartSearchButton");
        searchButton.setBounds(240, 20, 80, 30);
        add(searchButton);

        previousMatchButton.setName("PreviousMatchButton");
        previousMatchButton.setBounds(340, 20, 80, 30);
        add(previousMatchButton);

        nextMatchButton.setName("NextMatchButton");
        nextMatchButton.setBounds(440, 20, 80, 30);
        add(nextMatchButton);

        saveButton.setName("SaveButton");
        saveButton.setBounds(730, 20, 80, 30);
        add(saveButton);

        openButton.setName("OpenButton");
        openButton.setBounds(640, 20, 80, 30);
        add(openButton);

        scrollPane.setName("ScrollPane");
        add(scrollPane);

        setJMenuBar(menuBar);

        fileMenu.setName("MenuFile");
        menuBar.add(fileMenu);

        openItem.setName("MenuOpen");
        saveItem.setName("MenuSave");
        exitItem.setName("MenuExit");
        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);

        searchMenu.setName("MenuSearch");
        menuBar.add(searchMenu);

        menuStartSearchItem.setName("MenuStartSearch");
        menuPreviousMatchItem.setName("MenuPreviousMatch");
        menuNextMatchItem.setName("MenuNextMatch");
        menuUseRegExpItem.setName("MenuUseRegExp");
        searchMenu.add(menuStartSearchItem);
        searchMenu.add(menuPreviousMatchItem);
        searchMenu.add(menuNextMatchItem);
        searchMenu.add(menuUseRegExpItem);


        saveItem.addActionListener(e -> {
            String fileName = searchField.getText();
            String text = textArea.getText();
            try {
                Files.write(Paths.get(fileName), text.getBytes());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        openItem.addActionListener(e -> {
            textArea.setText(null);
            String textFromFile;
            try {
                textFromFile = Files.readString(Paths.get(searchField.getText()));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            textArea.setText(textFromFile);
        });
        exitItem.addActionListener(e -> dispose());

        openButton.addActionListener(e -> {
            textArea.setText(null);
            String textFromFile;
            try {
                textFromFile = Files.readString(Paths.get(searchField.getText()));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            textArea.setText(textFromFile);
        });

        saveButton.addActionListener(e -> {
            String fileName = searchField.getText();
            String text = textArea.getText();
            try {
                Files.write(Paths.get(fileName), text.getBytes());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }
}
