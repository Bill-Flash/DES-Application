package com.example.des.controller;

import com.example.des.DesApplication;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class SystemViewController {
	private DesApplication mainApp;

	@FXML
	private ImageView homeImg;

	@FXML
	private void initialize() {
		// set the homepage picture
		homeImg.setImage(new Image("file:images/homepage-image.jpg"));

	}
	// Search diary
	@FXML
	private void handleSearchBtnAction() {
		mainApp.showSearchView();
	}
	// write diary
	@FXML
	private void handleWriteBtnAction() {
		mainApp.showWriteView();
	}
	// initialization to get the APP
	public void setMainApp(DesApplication mainApp) {
		this.mainApp = mainApp;
	}
	
}
