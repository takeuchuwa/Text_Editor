package editor;

import javax.swing.*;

public class TextArea extends JTextArea {

    private JScrollPane scrollPane;

    TextArea() {
        setName("TextArea");
        scrollPane = new JScrollPane(this);
        scrollPane.setName("ScrollPane");
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    }

    public JScrollPane getScrollPane() {
        return scrollPane;
    }

}
