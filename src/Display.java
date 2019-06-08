import javafx.scene.control.Label;

public class Display extends Label {
    private String defaultValue = "0";

    Display() {
        super.setText(defaultValue);
    }

    void updateText(String text) {
        if (text.trim().equals("")) {
            setText(defaultValue);
        } else {
            setText(text);
        }
    }
}
