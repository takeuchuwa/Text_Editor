package editor;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;


public class TextEditor extends JFrame {

    private TextArea textArea;
    private TopBar topBar;
    private MenuBar menuBar;

    private JFileChooser fileChooser;

    public TextEditor() {
        setTitle("TextEditor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        createComponents();
        setVisible(true);
    }

    private void createComponents() {
        textArea = new TextArea();
        topBar = new TopBar(this);
        menuBar = new MenuBar(this);
        fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        fileChooser.setName("FileChooser");
        fileChooser.setDialogTitle("Select a text file");
        fileChooser.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files", "txt");
        fileChooser.addChoosableFileFilter(filter);
        addComponentToPane(getContentPane());
    }

    private void addComponentToPane(final Container pane) {
        setJMenuBar(menuBar);
        pane.add(topBar.getFileLayout(), BorderLayout.NORTH);
        pane.add(textArea.getScrollPane(), BorderLayout.CENTER);
        pane.add(new JLabel(" "), BorderLayout.SOUTH);
        pane.add(new JLabel("    "), BorderLayout.WEST);
        pane.add(new JLabel("    "), BorderLayout.EAST);

    }

    public TextArea getTextArea() {
        return textArea;
    }

    public TopBar getTopBar() {
        return topBar;
    }

    public JFileChooser getFileChooser() {
        return fileChooser;
    }



}



