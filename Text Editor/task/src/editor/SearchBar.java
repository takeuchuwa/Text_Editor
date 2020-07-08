package editor;

import javax.swing.*;
import java.awt.*;

public class SearchBar implements ForceSize {
    private JTextField searchField;
    private JScrollPane scrollableSearchField;

    private SearchEngine searchEngine;

    private JButton searchButton;
    private JButton previousMatchButton;
    private JButton nextMatchButton;
    private JCheckBox isRegex;
    SearchBar(TextEditor editor) {
        searchEngine = new SearchProcessor(editor);
        createMatchButtons(new ImageIcon("previous.png"), new ImageIcon("next.png"));
        createRegexCheckBox();
        createSearchButton(new ImageIcon("search.png"));
        scrollableSearchField = createSearchField();
    }

    private void createRegexCheckBox() {
        isRegex = new JCheckBox("Use regex");
        isRegex.setName("UseRegExCheckbox");
    }

    private void createMatchButtons(ImageIcon iconPrevious, ImageIcon iconNext) {
        Image img = iconPrevious.getImage();
        Image newimg = img.getScaledInstance(15, 15, Image.SCALE_SMOOTH);
        iconPrevious = new ImageIcon(newimg);
        img = iconNext.getImage();
        newimg = img.getScaledInstance(15, 15, Image.SCALE_SMOOTH);
        iconNext = new ImageIcon(newimg);

        previousMatchButton = new JButton(iconPrevious);
        forceSize(previousMatchButton, 20, 20);
        previousMatchButton.setName("PreviousMatchButton");
        previousMatchButton.addActionListener(e -> {
            searchEngine.previousMatch();
        });

        nextMatchButton = new JButton(iconNext);
        forceSize(nextMatchButton, 20, 20);
        nextMatchButton.setName("NextMatchButton");
        nextMatchButton.addActionListener(e -> {
            searchEngine.nextMatch();
        });
    }

    private void createSearchButton(ImageIcon icon) {
        Image img = icon.getImage();
        Image newimg = img.getScaledInstance(15, 15, Image.SCALE_SMOOTH);
        icon = new ImageIcon(newimg);
        searchButton = new JButton(icon);
        forceSize(searchButton, 20, 20);
        searchButton.setName("StartSearchButton");
        searchButton.addActionListener(e -> {
            if (isRegex.isSelected() && !searchField.getText().equals("")) {
                searchEngine.searchRegex(searchField.getText());
            } else {
                searchEngine.searchText(searchField.getText());
            }
        });
    }

    private JScrollPane createSearchField() {
        searchField = new JTextField();
        searchField.setName("SearchField");
        forceSize(searchField, 250, 20);
        JScrollPane scrollFilenameField = new JScrollPane(searchField);
        scrollFilenameField.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollFilenameField.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        return scrollFilenameField;
    }

    public void addComponentToPane(JPanel fileLayout) {
        fileLayout.add(scrollableSearchField);
        fileLayout.add(searchButton);
        fileLayout.add(previousMatchButton);
        fileLayout.add(nextMatchButton);
        fileLayout.add(isRegex);
    }


    @Override
    public void forceSize(JComponent component, int width, int height) {
        Dimension d = new Dimension(width, height);
        component.setPreferredSize(d);
    }

    public void setIsRegex() {
        isRegex.setSelected(!isRegex.isSelected());
    }

    public String getText() {
        return searchField.getText();
    }

    public JTextField getSearchField() {
        return searchField;
    }

    public JCheckBox getIsRegex() {
        return isRegex;
    }

    public SearchEngine getSearchEngine() {
        return searchEngine;
    }
}
