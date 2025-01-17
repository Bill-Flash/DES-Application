package com.example.des.controller;

import com.example.des.Des;
import com.example.des.DesApplication;
import com.example.des.model.EnumMode;
import com.example.des.tools.DialogTool;
import com.example.des.tools.DatabaseTool;
import com.example.des.tools.SingleValueTool;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.web.HTMLEditor;
import org.apache.commons.codec.digest.DigestUtils;

import java.time.LocalDate;

public class WriteViewController {
	@FXML
	private TextField titleField;
	
	@FXML
	private TextField keyField;
	
	@FXML
	private ChoiceBox<String> weather;
	
	@FXML
	private DatePicker datePicker;
	
	@FXML
	private HTMLEditor htmlEditor;
	
	private DesApplication mainApp;
	
	@FXML
	private void initialize() {
		weather.getItems().addAll(SingleValueTool.getWeaStrs());
		weather.getSelectionModel().selectFirst();
		// default time is today
		datePicker.setValue(LocalDate.now());
	}
	// cancel and return
	@FXML
	private void handleCancelAndReturnAction() {
		boolean isCancel = DialogTool.confirmDialog("Cancel", "Your content will miss if you don't save.");
		if(isCancel) {
			// back to the main interface
			mainApp.showSystemView();
		}
	}
	// Save the diary you are writing
	@FXML
	private void handleSaveAction() {
		boolean isSave = DialogTool.confirmDialog("Save", "Are you sure to save this diary");
		if(isSave) {
			String title = titleField.getText();
			String key = keyField.getText();
			int weaStrIndex = weather.getSelectionModel().getSelectedIndex();
			LocalDate date = datePicker.getValue();
			String content = htmlEditor.getHtmlText();
			
			if("".equals(title) || "".equals(key) || date==null || "".equals(content)) {
				DialogTool.confirmDialog("Something missing", "Please enter all items in the diary");
			}else {
				Des desUtil = Des.getInstance();
				desUtil.setKey(key);
				// get the result
				String result = Des.DesText(content, EnumMode.ENCRYPTION);

				String key_en = DigestUtils.sha1Hex(key);
				String sql1 = "insert into diary(title, key_en, weather, date, content) values(?,?,?,?,?) ";
				boolean isInsertOk1 = DatabaseTool.executeInsertDeleteUpdate(sql1, title, key_en, weaStrIndex, date.toString(), result);
				
				if(isInsertOk1) {
					DialogTool.informationDialog("Save successfully", "About to go to the main interface.");
					mainApp.showSystemView();
				}else {
					DialogTool.informationDialog("Save unsuccessfully", "Something wrong happened.");
				}
			}
		}	
	}
	
	// initialization to get the APP
	public void setMainApp(DesApplication mainApp) {
		this.mainApp = mainApp;
	}
}
