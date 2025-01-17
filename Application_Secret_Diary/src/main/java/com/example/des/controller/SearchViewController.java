package com.example.des.controller;

import com.example.des.Des;
import com.example.des.DesApplication;
import com.example.des.model.EnumMode;
import com.example.des.model.Diary;
import com.example.des.tools.DialogTool;
import com.example.des.tools.DatabaseTool;
import com.example.des.tools.SingleValueTool;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.web.HTMLEditor;
import org.apache.commons.codec.digest.DigestUtils;

import java.time.LocalDate;
import java.util.ArrayList;

public class SearchViewController {
	@FXML
	private TableView<Diary> diaryTable;
	
	@FXML
	private TableColumn<Diary, String> titleColumn;
	
	@FXML
	private TableColumn<Diary, LocalDate> dateColumn;
	
	@FXML
	private TextField titleField;
	
	@FXML
	private TextField keyField;

	@FXML
	private TextField password;
	
	@FXML
	private ChoiceBox<String> weatherCBox;
	
	@FXML
	private DatePicker datePicker;
	
	@FXML
	private HTMLEditor htmlEditor;
	
	@FXML
	private ImageView backImg;
	
	@FXML
	private ImageView searchImg;
	
	@FXML
	private TextField searchField;

	@FXML
	private Button lockBtn;
	
	private DesApplication mainApp;
	
	private ObservableList<Diary> diaryData = FXCollections.observableArrayList();

	private int selected_id;
	private String content_en;
	@FXML
	private void initialize() {

		// set icons
		searchImg.setImage(new Image("file:images/search.png"));
		backImg.setImage(new Image("file:images/back.png"));
		
		// set weather
		weatherCBox.getItems().addAll(SingleValueTool.getWeaStrs());
//		weatherCBox.getSelectionModel().selectFirst();
		
		titleColumn.setCellValueFactory(cellData->cellData.getValue().getTitle());
		dateColumn.setCellValueFactory(cellData->cellData.getValue().getDate());
		
		// add diary list
		diaryTable.setItems(diaryData);
		
		// clear all information
		showDiaryDetails(null);
		setEditable(false);
		// listener to show the detailed diary at the right-hand side
		diaryTable.getSelectionModel().selectedItemProperty().addListener(
					(Observable, oldValue, newValue)->{
						showDiaryDetails(newValue);
						setEditable(false);});

	}
	// if you select one cell, the right table will show the information
	private void showDiaryDetails(Diary diary) {
		// selected
		if(diary != null) {
			titleField.setText(diary.getTitle().getValue());
			keyField.setText(diary.getKey().getValue());
			weatherCBox.getSelectionModel().select(diary.getWeather().get());
			datePicker.setValue(diary.getDate().getValue());
			content_en = diary.getContent().getValue();
			htmlEditor.setHtmlText(content_en);
			selected_id = diary.getId();
		}else { //not selected
			titleField.setText("");
			keyField.setText("");
			weatherCBox.getSelectionModel().selectFirst();
			datePicker.setValue(LocalDate.now());
			htmlEditor.setHtmlText("");
		}
	}
	// if the diary is editable, you can choose to save your modification
	@FXML
	private void handleUpdateBtnAction() {
		if(keyField.isEditable()) {
			boolean isUpdate = DialogTool.confirmDialog("Update", "Are you sure to save your latest modification?");
			if(isUpdate) {
				String title = titleField.getText();
				String key = keyField.getText();
				int weaStrIndex = weatherCBox.getSelectionModel().getSelectedIndex();
				LocalDate date = datePicker.getValue();
				String content = htmlEditor.getHtmlText();

				if("".equals(title) || "".equals(key) || date==null || "".equals(content)) {
					DialogTool.confirmDialog("Something Missing!", "Please fill all items");
				}else {
					Des desUtil = Des.getInstance();
					desUtil.setKey(key);
					// get the result using the key to encrypt
					String result = Des.DesText(content, EnumMode.ENCRYPTION);

					String key_en = DigestUtils.sha1Hex(key); // the hashed key
					String sql = "update diary set title = ?, key_en = ?, weather = ?, date = ?, content = ? where did = ?";
					boolean isUpdateOk = DatabaseTool.executeInsertDeleteUpdate(sql, title, key_en, weaStrIndex,
							date.toString(), result, selected_id);
					if(isUpdateOk) {
						Diary oldDiary = diaryTable.getSelectionModel().getSelectedItem();
						oldDiary.setTitle(title);
						oldDiary.setKey(key_en);
						oldDiary.setWeather(weaStrIndex);
						oldDiary.setDate(date);
						oldDiary.setContent(result);
						showDiaryDetails(oldDiary);
						DialogTool.informationDialog("Update Successfully!", "You have updated it.");
						setEditable(false);

					}else{
						DialogTool.informationDialog("Update Unsuccessfully", "Something wrong occurs.");
					}
				}
			}
		}else {
			DialogTool.warningDialog("Not Editable", "Please unlock this diary first!");
		}
	}
	// unlock/lock the diary. if you want to update or read the diary, you should unlock it.
	@FXML
	private void handleUnlockBtnAction() {
		if(!keyField.isEditable()) {
			// If Correct key, unlock
			boolean isUnlock = DialogTool.confirmDialog("Unlock", "Are you sure to unlock this current diary？");
			if(isUnlock) {
				String key = password.getText();
				boolean isCorrect = DigestUtils.sha1Hex(key).equals(keyField.getText()); // check whether the hashed value is equal

				if (isCorrect){
					setEditable(true);
					keyField.setText(key); // it should be the un-hashed value
					// get the Des Instance
					Des desUtil = Des.getInstance();
					desUtil.setKey(key);
					// get the result by decryption
					String result = Des.DesText(content_en, EnumMode.DECRYPTION);
					htmlEditor.setHtmlText(result);
					DialogTool.informationDialog("Information", "This diary have been unlocked, please save it after updating.");
				}else {
					DialogTool.warningDialog("Wrong password!", "Incorrect password. Please enter a correct one");
				}
			}
		}else {
			// if lock and not save, the old version will show
			showDiaryDetails(diaryTable.getSelectionModel().getSelectedItem());
			setEditable(false);
		}
	}
	// the right table is set whether be edited
	private void setEditable(boolean isEditable) {
		titleField.setEditable(isEditable);
		keyField.setVisible(isEditable);
		keyField.setEditable(isEditable);
		datePicker.setDisable(!isEditable);
		weatherCBox.setDisable(!isEditable);
		htmlEditor.setDisable(!isEditable);
		if (isEditable){
			lockBtn.setText("Lock but not save");
		}else {
			lockBtn.setText("Unlock");
		}
	}
	// search for diaries by title
	@FXML
	private void handleSearchImgClickedAction() {
		String sql = "select * from diary where title like ?";
		ArrayList<Diary> queryList = DatabaseTool.getDiaryList(sql, '%'+searchField.getText()+'%');
		if(!queryList.isEmpty()) {
			diaryData.clear();
			diaryData.addAll(queryList);
			// add diary list
			diaryTable.setItems(diaryData);

			// clear all information
			showDiaryDetails(null);
			setEditable(false);
		}else {
			DialogTool.informationDialog("No diary Found.", "This diary doesn't exists.");
		}
	}
	// back to the main interface
	@FXML
	private void handleBackImgClickedAction() {
		mainApp.showSystemView();
	}
	// get the main system reference
	public void setMainApp(DesApplication mainApp) {
		this.mainApp  = mainApp;
		// when it is initialized
		String sql = "select * from diary";
		ArrayList<Diary> diaryList = DatabaseTool.getDiaryList(sql, null);
		diaryData.addAll(diaryList);
	}
}
