package com.example.des;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class DesDecryptionApplication extends Application {
//    @Override
//    public void start(Stage primaryStage) throws Exception {                   //(1)
//        Panel panel = new Panel("This is the title");
//        panel.getStyleClass().add("panel-primary");                            //(2)
//        //Creating a Grid Pane
//        GridPane gridPane = new GridPane();
//        //Setting size for the pane
//        gridPane.setMinSize(800, 400);
//        //Setting the padding
//        gridPane.setPadding(new Insets(20, 20, 20, 20));
//        //Setting the vertical and horizontal gaps between the columns
//        gridPane.setVgap(10);
//        gridPane.setHgap(10);
//        //Setting the Grid alignment
//        gridPane.setAlignment(Pos.CENTER);
//
//        // labels
//        Label Label1 = new Label("Encryption or Decryption");
//
//        // Choices array
//        String ED_Choice_Array[] = { "Encryption", "Decryption" };
//
//        // choiceBox
//        ChoiceBox EDChoiceBox = new ChoiceBox(FXCollections.observableArrayList(ED_Choice_Array));
//
//        // labels
//        Label Label2 = new Label("Way of inputting plain/cipher text");
//        Label Label3 = new Label("Plain/Cipher text");
//        Label Label4 = new Label("Key");
//        Label Label5 = new Label("Result");
//        TextArea resultArea = new TextArea();
//
//        TextArea textArea = new TextArea();
//        TextField textField = new TextField();
//        Button buttonLoad = new Button("Input file");
//        final File[] file = new File[1];
//
//
//        // Choices array
//        String Input_Choice_Array[] = { "From the file system", "Through the interface" };
//
//        // choiceBox
//        ChoiceBox InputChoiceBox = new ChoiceBox(FXCollections.observableArrayList(Input_Choice_Array));
//
//        // adding a listener
//        InputChoiceBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
//
//            // if items of the list are changed
//            public void changed(ObservableValue ov, Number value, Number new_value) {
//
//                if (new_value.intValue() == 1){
//                    if (gridPane.getChildren().contains(buttonLoad)){
//                        gridPane.getChildren().remove(8);
//                    } else {
//                        gridPane.add(Label3, 0, 2);
//                    }
//                    gridPane.add(textArea, 1, 2);
//                } else if (new_value.intValue() == 0) {
//                    if (gridPane.getChildren().contains(textArea)){
//                        gridPane.getChildren().remove(8);
//                    } else {
//                        gridPane.add(Label3, 0, 2);
//                    }
//                    gridPane.add(buttonLoad, 1, 2);
//                    buttonLoad.setOnAction(new EventHandler<ActionEvent>(){
//                        @Override
//                        public void handle(ActionEvent arg0) {
//                            FileChooser fileChooser = new FileChooser();
//                            fileChooser.setTitle("File Chooser");
////                            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
////                            fileChooser.getExtensionFilters().add(extFilter);
//                            file[0] = fileChooser.showOpenDialog(primaryStage);
//                            System.out.println(file[0]);
//                        }
//                    });
//                }
//            }
//        });
//
//        // ChoiceBox
//        //Arranging all the nodes in the grid
//        gridPane.add(Label1, 0, 0);
//        gridPane.add(EDChoiceBox, 1, 0);
//        gridPane.add(Label2, 0, 1);
//        gridPane.add(InputChoiceBox, 1, 1);
//        gridPane.add(Label4, 0, 3);
//        gridPane.add(textField, 1, 3);
//
//        Button submitButton = new Button("Submit");
//        submitButton.getStyleClass().setAll("btn","btn-danger");                     //(2)
//        submitButton.setOnAction((actionEvent -> {
//            String ed = (String) EDChoiceBox.getValue();
//            String key = textField.getText();
//            String way = (String) InputChoiceBox.getValue();
//            if (ed != null && textField != null && way != null){
//                Des desUtil = Des.getInstance(key);//秘钥变成二进制
//                if (way == Input_Choice_Array[0]){
//                    if (file[0] != null){
//                        try {
//                            readfile(file[0].getAbsolutePath(), ed);
//                        } catch (FileNotFoundException e) {
//                            System.out.println("readfile() Exception:" + e.getMessage());
//                        } catch (IOException e) {
//                            throw new RuntimeException(e);
//                        }
//                    } else {
//                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
//                        alert.setTitle("Information Dialog");
//                        alert.setHeaderText(null);
//                        alert.setContentText("Please enter all items!");
//
//                        alert.showAndWait();
//                    }
//                } else if (way == Input_Choice_Array[1]) {
//                    String inputstr = textArea.getText();
//                    String resultstr = null;
//                    if (inputstr != null) {
//                        resultstr = readinput(inputstr, ed);
//                        if (gridPane.getChildren().contains(Label5)){
//
//                        } else {
//                            gridPane.add(Label5, 0, 4);
//                            gridPane.add(resultArea, 1, 4);
//                        }
//                        resultArea.setText(resultstr);
//                    } else {
//                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
//                        alert.setTitle("Information Dialog");
//                        alert.setHeaderText(null);
//                        alert.setContentText("Please enter all items!");
//
//                        alert.showAndWait();
//                    }
//                }
//
//                System.out.println("加解密成功，请到相应目录下查看");
//            } else{
//                Alert alert = new Alert(Alert.AlertType.INFORMATION);
//                alert.setTitle("Information Dialog");
//                alert.setHeaderText(null);
//                alert.setContentText("Please enter all items!");
//
//                alert.showAndWait();
//            }
//
//        }));
//        gridPane.add(submitButton, 1, 5);
//
//        panel.setBody(gridPane);
//
//        Scene scene = new Scene(panel);
//        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());       //(3)
//
//        primaryStage.setTitle("BootstrapFX");
//        primaryStage.setScene(scene);
//        primaryStage.sizeToScene();
//        primaryStage.show();
//    }
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(DesDecryptionApplication.class.getResource("decryption-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 720, 480);
        stage.setTitle("DES---Decryption");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}