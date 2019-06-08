import javafx.event.EventTarget;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

class NumPad extends GridPane {

    NumPad(ActionHandler handler) {
        String[][] buttons = new String[][]{
                {"7", "8", "9", "+", "C", "^("},
                {"4", "5", "6", "-", "(", ")"},
                {"1", "2", "3", "*", "sin(", "cos("},
                {".", "0", "AC", "/", "=", "tan("}
        };

        for (int y = 0; y < buttons.length; y++) {
            String[] buttonRow = buttons[y];
            for (int x = 0; x < buttonRow.length; x++) {
                Button button = new Button(buttonRow[x]);
                button.setPrefSize(55, 55);
                add(button, x, y);
            }
        }

        attachHandler(handler);
    }

    private void attachHandler(ActionHandler handler) {
        addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
            EventTarget target = event.getTarget();
            if (target instanceof Button) {
                Button button = (Button) target;
                handler.handle(button.getText());
            } else if (target instanceof Text && ((Text) target).getParent() instanceof Button) {
                Text text = (Text) target;
                handler.handle(text.getText());
            }
        });
    }
}
