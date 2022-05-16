package editor;

import javax.swing.*;

public class TextEditor extends JFrame {
    public TextEditor() {
        super("The first stage");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 300);
        setLocationRelativeTo(null);
        setVisible(true);
        setLayout(null);

        JTextArea textArea = new JTextArea();
        textArea.setName("TextArea");
        textArea.setBounds(10,10,265,200);
        add(textArea);
    }
}
