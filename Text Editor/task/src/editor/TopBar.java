package editor;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TopBar implements ForceSize {
    private SearchBar searchBar;
    private JPanel fileLayout;
    private JButton saveButton;
    private JButton openButton;

    TopBar(TextEditor editor) {
        fileLayout = new JPanel();
        searchBar = new SearchBar(editor);
        createOpenButton(editor, new ImageIcon("folder.png"));
        createSaveButton(editor, new ImageIcon("save.png"));
        addComponentToPane();
    }

    private void addComponentToPane() {
        fileLayout.setLayout(new FlowLayout(FlowLayout.LEFT));
        fileLayout.add(Box.createHorizontalStrut(5));
        fileLayout.add(openButton);
        fileLayout.add(saveButton);
        searchBar.addComponentToPane(fileLayout);
    }

    private void createOpenButton(TextEditor editor, ImageIcon icon) {
        Image img = icon.getImage();
        Image newimg = img.getScaledInstance(15, 15, Image.SCALE_SMOOTH);
        icon = new ImageIcon(newimg);
        openButton = new JButton(icon);
        forceSize(openButton, 20, 20);
        openButton.setName("OpenButton");
        openButton.setPreferredSize(new Dimension(20, 20));
        openButton.addActionListener(e -> {
            File file = new File("");
            int returnValue = editor.getFileChooser().showOpenDialog(null);

            if (returnValue == JFileChooser.APPROVE_OPTION) {
                file = editor.getFileChooser().getSelectedFile();
            }
            try {
                if (file.exists()) {
                    String fileContent = new String(Files.readAllBytes(Paths.get(file.getAbsolutePath())));
                    editor.getTextArea().setText(fileContent);
                    searchBar.getSearchField().setText(file.getAbsolutePath());
                } else {
                    editor.getTextArea().setText("");
                }
            } catch (IOException ioException) {
                System.out.println("File not found");
            }
        });
    }

    private void createSaveButton(TextEditor editor, ImageIcon icon) {
        Image img = icon.getImage();
        Image newimg = img.getScaledInstance(15, 15, Image.SCALE_SMOOTH);
        icon = new ImageIcon(newimg);
        saveButton = new JButton(icon);
        forceSize(saveButton, 20, 20);
        saveButton.setName("SaveButton");
        saveButton.addActionListener(e -> {
            File file = new File("");
            int returnValue = editor.getFileChooser().showSaveDialog(null);

            if (returnValue == JFileChooser.APPROVE_OPTION) {
                file = editor.getFileChooser().getSelectedFile();
            }
            try (FileWriter writer = new FileWriter(file)) {
                writer.write(editor.getTextArea().getText());
            } catch (IOException ioException) {
                System.out.println("File not found");
            }
        });
    }


    public JPanel getFileLayout() {
        return fileLayout;
    }

    @Override
    public void forceSize(JComponent component, int width, int height) {
        Dimension d = new Dimension(width, height);
        component.setPreferredSize(d);
    }

    public SearchBar getSearchBar() {
        return searchBar;
    }
}
