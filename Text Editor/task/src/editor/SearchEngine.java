package editor;

public interface SearchEngine {
    void searchText(String text);

    void searchRegex(String text);

    void previousMatch();

    void nextMatch();
}
