import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.HashMap;

public class Calculator extends Application {
    private VBox root;
    private Display display;
    private MathExpression mathExpression;
    private MathExpression memory;

    @Override
    public void start(Stage stage) {
        mathExpression = new MathExpression();
        memory = new MathExpression();

        stage.setTitle("Calculator");
        stage.setResizable(false);

        root = new VBox();
        root.setAlignment(Pos.TOP_RIGHT);
        Scene scene = new Scene(root, 300, 300);
        stage.setScene(scene);
        stage.show();

        createMenuBar();
        root.getChildren().add(new MemoryBar(this::handleButtons));
        createDisplay();
        root.getChildren().add(new NumPad(this::handleButtons));

        attachEvent();
    }

    private void refreshDisplay() {
        display.updateText(mathExpression.toString());
    }

    private void handleButtons(String action) {
        switch (action) {
            case "C":
                mathExpression.clear();
                break;
            case "AC":
                mathExpression.deleteLast();
                break;
            case "=":
                try {
                    mathExpression.evaluate();
                } catch (Exception e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(e.getClass().getSimpleName());
                    alert.setContentText(e.getLocalizedMessage());

                    alert.showAndWait();
                }
                break;
            case "MC":
                memory.clear();
                break;
            case "M+":
                memory.append("+(" + mathExpression.toString() + ")");
                break;
            case "M-":
                memory.append("-(" + mathExpression.toString() + ")");
                break;
            case "MR":
                mathExpression.append(memory.toString());
                break;
            default:
                mathExpression.append(action);
                break;
        }

        refreshDisplay();
    }

    private void createMenuBar() {
        Menu fileMenu = new Menu("File");
        MenuItem exitItem = new MenuItem("exit");
        exitItem.setOnAction(event -> {
            System.exit(0);
        });
        fileMenu.getItems().addAll(exitItem);

        Menu helpMenu = new Menu("Help");
        MenuItem helpItem = new MenuItem("help");
        helpItem.setOnAction(event -> {

        });
        MenuItem aboutItem = new MenuItem("about");
        aboutItem.setOnAction(event -> {

        });
        helpMenu.getItems().addAll(helpItem, aboutItem);

        MenuBar menuBar = new MenuBar(fileMenu, helpMenu);
        root.getChildren().add(menuBar);
    }

    private void attachEvent() {
        root.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            HashMap<KeyCodeCombination, String> keyCombinations = new HashMap<KeyCodeCombination, String>() {{
                put(new KeyCodeCombination(KeyCode.DIGIT8, KeyCombination.SHIFT_DOWN), "(");
                put(new KeyCodeCombination(KeyCode.DIGIT9, KeyCombination.SHIFT_DOWN), ")");
                put(new KeyCodeCombination(KeyCode.DIGIT7, KeyCombination.SHIFT_DOWN), "/");
                put(new KeyCodeCombination(KeyCode.PERIOD, KeyCombination.SHIFT_DOWN), "/");
                put(new KeyCodeCombination(KeyCode.PLUS, KeyCombination.SHIFT_DOWN), "*");
            }};
            for (KeyCodeCombination keyCom : keyCombinations.keySet()) {
                if (keyCom.match(event)) {
                    handleButtons(keyCombinations.get(keyCom));
                    return;
                }
            }

            String[] bypass = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "+", "-", ".", "^"};
            HashMap<KeyCode, String> keyTranslator = new HashMap<KeyCode, String>() {{
                put(KeyCode.ENTER, "=");
                put(KeyCode.BACK_SPACE, "AC");
                put(KeyCode.C, "C");
                put(KeyCode.COLON, "/");
                put(KeyCode.A, "tan(");
                put(KeyCode.O, "cos(");
                put(KeyCode.I, "sin(");
            }};
            if (Arrays.asList(bypass).contains(event.getText())) {
                handleButtons(event.getText());
            } else if (keyTranslator.containsKey(event.getCode())) {
                handleButtons(keyTranslator.get(event.getCode()));
            }
        });
    }

    private void createDisplay() {
        display = new Display();
        display.setPrefHeight(60);
        display.setFont(new Font(25));
        root.getChildren().add(display);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
