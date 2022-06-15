package editor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class TextEditor extends JFrame {
    private static File currentFile;
    private boolean regex = false;
    private JTextArea textArea;
    String foundText = " ";

    public TextEditor() {
        super("The first stage");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 300);
        setVisible(true);

        initComponents();
    }

    private void initComponents() {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        fileMenu.setName("MenuFile");
        fileMenu.setMnemonic(KeyEvent.VK_F);

        JMenu searchMenu = new JMenu("Search");
        searchMenu.setName("MenuSearch");

        JMenuItem loadMenuItem = new JMenuItem("Open");
        loadMenuItem.setName("MenuOpen");
        JMenuItem saveMenuItem = new JMenuItem("Save");
        saveMenuItem.setName("MenuSave");
        JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.setName("MenuExit");

        JMenuItem startSearchItem = new JMenuItem("Start search");
        startSearchItem.setName("MenuStartSearch");
        JMenuItem prevMatchItem = new JMenuItem("Previous search");
        prevMatchItem.setName("MenuPreviousMatch");
        JMenuItem nextMatchItem = new JMenuItem("Next match");
        nextMatchItem.setName("MenuNextMatch");
        JMenuItem useRegExpItem = new JMenuItem("Use regular expressions");
        useRegExpItem.setName("MenuUseRegExp");

        fileMenu.add(loadMenuItem);
        fileMenu.add(saveMenuItem);
        fileMenu.add(exitMenuItem);

        searchMenu.add(startSearchItem);
        searchMenu.add(prevMatchItem);
        searchMenu.add(nextMatchItem);
        searchMenu.add(useRegExpItem);

        menuBar.add(fileMenu);
        menuBar.add(searchMenu);
        setJMenuBar(menuBar);

        JPanel topPanel = new JPanel();
        topPanel.setBounds(0, 0, 250, 30);
        topPanel.setLayout(new FlowLayout());
        topPanel.setVisible(true);
        add(topPanel, BorderLayout.NORTH);

        ImageIcon save = new ImageIcon("Resources/save-icon.png");
        JButton saveButton = new JButton(save);
        saveButton.setName("SaveButton");
        saveButton.setBounds(100, 70, 100, 30);
        saveButton.setVisible(true);
        topPanel.add(saveButton);

        ImageIcon load = new ImageIcon("Resources/open-icon.png");
        JButton loadButton = new JButton(load);
        loadButton.setName("OpenButton");
        loadButton.setBounds(100, 70, 100, 30);
        loadButton.setVisible(true);
        topPanel.add(loadButton);

        JTextField searchField = new JTextField();
        searchField.setName("SearchField");
        searchField.setBounds(100, 70, 100, 30);
        add(searchField);

        ImageIcon search = new ImageIcon("Resources/search-icon.png");
        JButton searchButton = new JButton(search);
        searchButton.setName("StartSearchButton");
        searchButton.setBounds(100, 70, 100, 30);
        searchButton.setVisible(true);
        topPanel.add(searchButton);

        ImageIcon prevMatch = new ImageIcon("Resources/prev-search.png");
        JButton prevMatchButton = new JButton(prevMatch);
        prevMatchButton.setName("PreviousMatchButton");
        prevMatchButton.setBounds(100, 70, 100, 30);
        prevMatchButton.setVisible(true);
        topPanel.add(prevMatchButton);

        ImageIcon nextMatch = new ImageIcon("Resources/next-search.png");
        JButton nextMatchButton = new JButton(nextMatch);
        nextMatchButton.setName("NextMatchButton");
        nextMatchButton.setBounds(100, 70, 100, 30);
        nextMatchButton.setVisible(true);
        topPanel.add(nextMatchButton);

        JCheckBox regExCheckBox = new JCheckBox("Use regular expressions", false);
        regExCheckBox.setName("UseRegExCheckbox");
        regExCheckBox.setVisible(true);
        topPanel.add(regExCheckBox);

        JFileChooser jfc = new JFileChooser();
        jfc.setName("FileChooser");
        jfc.setVisible(false);
        topPanel.add(jfc);

        textArea = new JTextArea();
        textArea.setName("TextArea");
        textArea.setVisible(true);

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBounds(10, 10, 250, 250);
        scrollPane.setName("ScrollPane");
        scrollPane.setVisible(true);
        add(scrollPane, BorderLayout.CENTER);

        loadMenuItem.addActionListener(e -> {
            jfc.setVisible(true);
            int returnValue = jfc.showOpenDialog(null);

            if (returnValue == JFileChooser.APPROVE_OPTION) {
                currentFile = jfc.getSelectedFile();
                textArea.setText(loadFile(currentFile));
            }
            jfc.setVisible(false);
        });
        saveMenuItem.addActionListener(e -> {
            jfc.setVisible(true);
            int returnValue = jfc.showOpenDialog(null);

            if (returnValue == JFileChooser.APPROVE_OPTION) {
                currentFile = jfc.getSelectedFile();
                saveFile(currentFile, textArea.getText());
            }
            jfc.setVisible(false);
        });
        exitMenuItem.addActionListener(e -> System.exit(0));

        startSearchItem.addActionListener(e -> {
            foundText = searchField.getText();
            if (regex) {
                Pattern pattern = Pattern.compile(foundText);
                Matcher matcher = pattern.matcher(textArea.getText());

                if (matcher.find()) {
                    String text = matcher.group();
                    int index = matcher.end();
                    textArea.setCaretPosition(index);
                    textArea.select(index - text.length(), index);
                }
            } else {
                int index = textArea.getText().indexOf(foundText);

                textArea.setCaretPosition(index + foundText.length());
                textArea.select(index, index + foundText.length());
            }
            textArea.grabFocus();
        });
        prevMatchItem.addActionListener(e -> {
            if (regex) {
                Pattern pattern = Pattern.compile(foundText);
                Matcher matcher = pattern.matcher(textArea.getText());
                int index = textArea.getCaretPosition();
                matcher.region(0,index);
                String text = "";
                int tempIndex = 0;

                while (matcher.find()) {
                    if (matcher.end() < index) {
                        text = matcher.group();
                        tempIndex = matcher.end();
                    }
                }

                index = tempIndex;
                textArea.setCaretPosition(index);
                textArea.select(index - text.length(), index);
            }else {
                int oldIndex = textArea.getCaretPosition() - foundText.length() - 1;
                int newIndex = textArea.getText().lastIndexOf(foundText, oldIndex);

                if (newIndex < 0) {
                    newIndex = textArea.getText().lastIndexOf(foundText);
                }

                textArea.setCaretPosition(newIndex + foundText.length());
                textArea.select(newIndex, newIndex + foundText.length());
            }
            textArea.grabFocus();
        });
        nextMatchItem.addActionListener(e -> {
            if (regex) {
                Pattern pattern = Pattern.compile(foundText);
                Matcher matcher = pattern.matcher(textArea.getText());
                int index = textArea.getCaretPosition();

                if (matcher.find(index)) {
                    String text = matcher.group();
                    index = matcher.end();
                    textArea.setCaretPosition(index);
                    textArea.select(index - text.length(), index);
                }
            }else {
                int oldIndex = textArea.getCaretPosition();
                int newIndex = textArea.getText().indexOf(foundText, oldIndex);

                textArea.setCaretPosition(newIndex - 1);
                textArea.select(newIndex, newIndex + foundText.length());
            }
            textArea.grabFocus();
        });

        useRegExpItem.addActionListener(e -> {
            regex = !regex;
            regExCheckBox.setSelected(regex);
        });

        loadButton.addActionListener(e -> {
            jfc.setVisible(true);
            int returnValue = jfc.showOpenDialog(null);

            if (returnValue == JFileChooser.APPROVE_OPTION) {
                currentFile = jfc.getSelectedFile();
                textArea.setText(loadFile(currentFile));
            }
            jfc.setVisible(false);
        });
        saveButton.addActionListener(e -> {
            jfc.setVisible(true);
            int returnValue = jfc.showOpenDialog(null);

            if (returnValue == JFileChooser.APPROVE_OPTION) {
                currentFile = jfc.getSelectedFile();
                saveFile(currentFile, textArea.getText());
            }
            jfc.setVisible(false);
        });

        searchButton.addActionListener(e -> {
            foundText = searchField.getText();
            if (regex) {
                Pattern pattern = Pattern.compile(foundText);
                Matcher matcher = pattern.matcher(textArea.getText());

                if (matcher.find()) {
                    String text = matcher.group();
                    int index = matcher.end();
                    textArea.setCaretPosition(index);
                    textArea.select(index - text.length(), index);
                }
            } else {
                int index = textArea.getText().indexOf(foundText);

                textArea.setCaretPosition(index + foundText.length());
                textArea.select(index, index + foundText.length());
            }
            textArea.grabFocus();
        });
        prevMatchButton.addActionListener(e -> {
            if (regex) {
                Pattern pattern = Pattern.compile(foundText);
                Matcher matcher = pattern.matcher(textArea.getText());
                int index = textArea.getCaretPosition();

                matcher.region(0,index);
                String text = "";
                int tempIndex = 0;

                while (matcher.find()) {
                    if (matcher.end() < index) {
                        text = matcher.group();
                        tempIndex = matcher.end();
                    }
                }

                if ( tempIndex < 1 && text.equals("")) {
                    matcher.reset();
                    while (matcher.find()) {
                        if (matcher.end() < textArea.getText().length()) {
                            text = matcher.group();
                            tempIndex = matcher.end();
                        }
                    }
                }
                index = tempIndex;
                textArea.setCaretPosition(index + text.length());
                textArea.select(index - text.length(), index);
            }else {
                int oldIndex = textArea.getCaretPosition() - foundText.length() - 1;
                int newIndex = textArea.getText().lastIndexOf(foundText, oldIndex);

                if (newIndex < 0) {
                    newIndex = textArea.getText().lastIndexOf(foundText);
                }

                textArea.setCaretPosition(newIndex + foundText.length());
                textArea.select(newIndex, newIndex + foundText.length());
            }
            textArea.grabFocus();
        });
        nextMatchButton.addActionListener(e -> {
            if (regex) {
                Pattern pattern = Pattern.compile(foundText);
                Matcher matcher = pattern.matcher(textArea.getText());
                int index = textArea.getCaretPosition();

                if (matcher.find(index)) {
                    String text = matcher.group();
                    index = matcher.end();
                    textArea.setCaretPosition(index - 1);
                    textArea.select(index - text.length(), index);
                }
            }else {
                int oldIndex = textArea.getCaretPosition();
                int newIndex = textArea.getText().indexOf(foundText, oldIndex);

                textArea.setCaretPosition(newIndex);
                textArea.select(newIndex, newIndex + foundText.length());
            }
            textArea.grabFocus();
        });
        regExCheckBox.addActionListener(e -> regex = regExCheckBox.isSelected());
    }

    static void saveFile(File file, String text) {
        if (file != null && file.getName().trim().length() > 0) {
            try (FileWriter writer = new FileWriter(currentFile.getName())){
                writer.write(text);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    static String loadFile(File file) {
        if (file != null && file.getName().trim().length() > 0) {
            try {
                if (!file.exists()) {
                    file.createNewFile();
                }
                return readFileAsString(file.getName());
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return "";
    }

    public static String readFileAsString(String fileName) throws IOException {
        return new String(Files.readAllBytes(Paths.get(fileName)));
    }
}