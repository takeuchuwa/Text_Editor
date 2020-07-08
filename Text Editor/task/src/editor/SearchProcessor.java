package editor;

import javax.swing.*;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SearchProcessor extends SwingWorker<Deque<Integer>, String> implements SearchEngine {

    private TextArea textArea;
    private boolean isRegex;
    private String foundText;
    private Deque<Integer> indexes;
    private Deque<Integer> length;

    private int currentIndex;
    private int lengthOfWord;

    SearchProcessor(TextEditor editor) {
        textArea = editor.getTextArea();
    }

    @Override
    public void searchText(String text) {
        isRegex = false;
        foundText = text;
        try {
            doInBackground();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void searchRegex(String text) {
        isRegex = true;
        foundText = text;
        try {
            doInBackground();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void previousMatch() {
        int indexBuffer = currentIndex;
        int lengthBuffer = lengthOfWord;
        currentIndex = indexes.pollLast();
        indexes.offerFirst(indexBuffer);
        lengthOfWord = length.pollLast();
        length.offerFirst(lengthBuffer);
        textArea.setCaretPosition(currentIndex + lengthOfWord);
        textArea.select(currentIndex, currentIndex + lengthOfWord);
        textArea.grabFocus();
    }

    @Override
    public void nextMatch() {
        int indexBuffer = currentIndex;
        int lengthBuffer = lengthOfWord;
        currentIndex = indexes.pollFirst();
        indexes.offerLast(indexBuffer);
        lengthOfWord = isRegex ? length.pollFirst() : foundText.length();
        length.offerLast(lengthBuffer);
        textArea.setCaretPosition(currentIndex + lengthOfWord);
        textArea.select(currentIndex, currentIndex + lengthOfWord);
        textArea.grabFocus();
    }

    @Override
    protected Deque<Integer> doInBackground() throws Exception {
        indexes = new ArrayDeque<>();
        length = new ArrayDeque<>();
        Pattern pattern = Pattern.compile(foundText);
        Matcher matcher = pattern.matcher(textArea.getText());

        while (matcher.find()) {
            indexes.offerLast(matcher.start());
            length.offerLast(matcher.group().length());
        }
        currentIndex = indexes.pollFirst();
        lengthOfWord = length.pollFirst();
        textArea.setCaretPosition(currentIndex + lengthOfWord);
        textArea.select(currentIndex, currentIndex + lengthOfWord);
        textArea.grabFocus();
        return indexes;
    }
}
