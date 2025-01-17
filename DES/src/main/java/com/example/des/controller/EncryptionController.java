package com.example.des.controller;

import com.example.des.Des;
import com.example.des.en.EnumMedia;
import com.example.des.en.EnumMode;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;


public class EncryptionController {
    @FXML
    private ChoiceBox<EnumMedia> media;
    @FXML
    private Label inputLabel;
    @FXML
    private Button fileChooser;
    private final File[] file = new File[1];
    @FXML
    private Text hint;
    @FXML
    private TextArea inputText;
    @FXML
    private TextArea resultText;
    @FXML
    private Label resultLabel;
    @FXML
    private TextField key;
    @FXML
    private GridPane root;
    private Stage stage;


    public void initialize(){
        // set Media box, and the default value is FILE System
        media.getItems().addAll(EnumMedia.values());
        media.setValue(EnumMedia.FILE);

        // a listener to change UI since the way of input is different
        addMediaListener();
    }

    // automatically launched for getting the stage
    private void initConnect(){
        stage = (Stage) root.getScene().getWindow();
    }

    // media box listener
    private void addMediaListener(){
        // if items of the list are changed
        media.getSelectionModel().selectedIndexProperty().addListener((ov, value, new_value) -> {
            if (new_value.intValue() == 1) {
                // set the text mode
                fileChooser.setVisible(false);
                hint.setText("");
                // Text and the input area to be visible
                inputText.setVisible(true);
                inputLabel.setText("Enter your plain text:");
            } else if (new_value.intValue() == 0) {
                // set the file mode
                fileChooser.setVisible(true);
                inputLabel.setText("File");
                // Text and the input area to be invisible
                inputText.setVisible(false);
                inputText.clear();
                resultText.setVisible(false);
                resultLabel.setVisible(false);
            }
        });

    }

    // the onSubmit event for choose file button
    public void chooseFile(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("File Chooser");

        File temp = fileChooser.showOpenDialog(stage);
        // in case no file is selected
        if (temp == null) return;
        file[0] = temp;
        hint.setText(file[0].getName());
    }

    // the onAction event for decrytion
    public void encryption() {
        // get know which method the user choose
        EnumMedia method = media.getValue();
        if (method.equals(EnumMedia.FILE)) {
            if (file[0] != null && key.getText() != null) {
                // get the Des Instance
                Des desUtil = Des.getInstance();
                desUtil.setKey(key.getText());
                // Decrypt the file
                Des.DesFile(file[0].getAbsolutePath(), EnumMode.ENCRYPTION);
                // Tell user where it is generated
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Successfully Encryption.");
                alert.setHeaderText(null);
                alert.setContentText("Please check the 'Encryption' fold!");
                alert.showAndWait();
            } else {
                alertItems();
            }
        }else if (method.equals(EnumMedia.INTERFACE)) {
            String input = inputText.getText();
            String result = null;
            if (input != null && key.getText() != null) {
                // get the Des Instance
                Des desUtil = Des.getInstance();
                desUtil.setKey(key.getText());
                // get the result
                result = Des.DesText(input, EnumMode.ENCRYPTION);

                resultText.setText(result);
                resultText.setVisible(true);
                resultLabel.setVisible(true);
            } else {
                alertItems();
            }
        }
    }

    // alert some fields not filled
    private void alertItems(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Please enter all items!");
        alert.showAndWait();
    }
}