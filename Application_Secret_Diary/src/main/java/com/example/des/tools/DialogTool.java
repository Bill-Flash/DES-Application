package com.example.des.tools;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class DialogTool {

	public static void warningDialog(String header, String content) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setGraphic(new ImageView(new Image("file:images/warn.png")));
		alert.setTitle("Warning Dialog");
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.showAndWait();
	}

	public static boolean confirmDialog(String header, String content) {

		// array to solve the problem caused by lambda expression
		boolean[] isClickedOk = new boolean[] {false};
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setGraphic(new ImageView(new Image("file:images/confirm.png")));
		alert.setTitle("Confirm");
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.showAndWait().filter(response->response==ButtonType.OK)
		.ifPresent(response->isClickedOk[0] = true);
		
		return isClickedOk[0];
	}

	public static void informationDialog(String header, String content) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setGraphic(new ImageView(new Image("file:images/message.png")));
		alert.setTitle("Information Dialog");
		alert.setHeaderText(header);
		alert.setContentText(content);
		//pending
		alert.showAndWait();
	}

}


