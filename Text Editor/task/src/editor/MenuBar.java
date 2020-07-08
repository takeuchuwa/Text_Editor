package editor;

import org.w3c.dom.Text;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class MenuBar extends JMenuBar {

    private JMenuItem openMenuItem;
    private JMenuItem saveMenuItem;
    private JMenuItem exitMenuItem;
    private JMenu fileMenu;
    private JMenu searchMenu;
    private JMenuItem startSearchMenuItem;
    private JMenuItem previousMatchMenuItem;
    private JMenuItem nextMatchMenuItem;
    private JMenuItem useRegexMenuItem;
    private SearchEngine searchEngine;

    MenuBar(TextEditor editor) {
        searchEngine = editor.getTopBar().getSearchBar().getSearchEngine();
        fileMenu = new JMenu("File");
        fileMenu.setName("MenuFile");
        searchMenu = new JMenu("Search");
        searchMenu.setName("MenuSearch");
        createOpenMenu(editor);
        createSaveMenu(editor);
        createExitMenu();
        createStartSearch(editor);
        createMatchMenu();
        createUseRegex(editor);
        addComponentToPane();
    }

    private void addComponentToPane() {
        fileMenu.add(openMenuItem);
        fileMenu.add(saveMenuItem);
        fileMenu.addSeparator();
        fileMenu.add(exitMenuItem);
        searchMenu.add(startSearchMenuItem);
        searchMenu.add(previousMatchMenuItem);
        searchMenu.add(nextMatchMenuItem);
        searchMenu.add(useRegexMenuItem);
        add(fileMenu);
        add(searchMenu);
    }

    private void createExitMenu() {
        exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.setName("MenuExit");
        exitMenuItem.addActionListener(e -> {
            System.exit(0);
        });
    }

    private void createStartSearch(TextEditor editor) {
        startSearchMenuItem = new JMenuItem("Start search");
        startSearchMenuItem.setName("MenuStartSearch");
        startSearchMenuItem.addActionListener(e -> {
            if (editor.getTopBar().getSearchBar().getIsRegex().isSelected() && !editor.getTopBar().getSearchBar().getSearchField().getText().equals("")) {
                searchEngine.searchRegex(editor.getTopBar().getSearchBar().getSearchField().getText());
            } else {
                searchEngine.searchText(editor.getTopBar().getSearchBar().getSearchField().getText());
            }
        });
    }

    private void createMatchMenu() {
        previousMatchMenuItem = new JMenuItem("Previous Match");
        previousMatchMenuItem.setName("MenuPreviousMatch");
        previousMatchMenuItem.addActionListener(e -> {
            searchEngine.previousMatch();
        });

        nextMatchMenuItem = new JMenuItem("Next Match");
        nextMatchMenuItem.setName("MenuNextMatch");
        nextMatchMenuItem.addActionListener(e -> {
            searchEngine.nextMatch();
        });
    }

    private void createUseRegex(TextEditor editor) {
        useRegexMenuItem = new JMenuItem("Use regular expressions");
        useRegexMenuItem.setName("MenuUseRegExp");
        useRegexMenuItem.addActionListener(e -> {
            editor.getTopBar().getSearchBar().setIsRegex();
        });
    }

    private void createSaveMenu(TextEditor editor) {
        saveMenuItem = new JMenuItem("Save");
        saveMenuItem.setName("MenuSave");
        saveMenuItem.addActionListener(e -> {
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

    private void createOpenMenu(TextEditor editor) {
        openMenuItem = new JMenuItem("Open");
        openMenuItem.setName("MenuOpen");
        openMenuItem.addActionListener(e -> {
            File file = new File("");
            int returnValue = editor.getFileChooser().showOpenDialog(null);

            if (returnValue == JFileChooser.APPROVE_OPTION) {
                file = editor.getFileChooser().getSelectedFile();
            }
            try {
                if (file.exists()) {
                    String fileContent = new String(Files.readAllBytes(Paths.get(file.getAbsolutePath())));
                    editor.getTextArea().setText(fileContent);
                    editor.getTopBar().getSearchBar().getSearchField().setText(file.getAbsolutePath());
                } else {
                    editor.getTextArea().setText("");
                }
            } catch (IOException ioException) {
                System.out.println("File not found");
            }
        });
    }
}
