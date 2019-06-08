import javafx.event.EventTarget;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

class MemoryBar extends HBox {
    MemoryBar(ActionHandler handler) {
        String[] buttons = new String[]{"MC", "M+", "M-", "MR"};
        for (String text : buttons) {
            Button button = new Button(text);
            button.setPrefSize(80, 25);
            getChildren().add(button);
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
