package com.example.des.model;



import javafx.beans.property.*;

import java.time.LocalDate;

// the diary model used in tableView
public class Diary {
	private IntegerProperty id;
	private StringProperty title;
	private StringProperty key;
	private IntegerProperty weatherIndex;
	private ObjectProperty<LocalDate> date;
	private StringProperty content;

	public Diary(int id, String title, String key, int weatherIndex, LocalDate date, String content) {
		this.id = new SimpleIntegerProperty(id);
		this.title = new SimpleStringProperty(title);
		this.key = new SimpleStringProperty(key);
		this.weatherIndex = new SimpleIntegerProperty(weatherIndex);
		this.date = new SimpleObjectProperty<>(date);
		this.content = new SimpleStringProperty(content);
	}

	public int getId(){return id.getValue();}

	public StringProperty getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title.set(title);
	}
	
	public StringProperty getKey() {
		return key;
	}
	
	public void setKey(String key) {
		this.key.set(key);
	}

	public IntegerProperty getWeather() {
		return weatherIndex;
	}
	
	public void setWeather(int weather) {
		this.weatherIndex.set(weather);
	}

	public ObjectProperty<LocalDate> getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date.set(date);
	}
	
	public StringProperty getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content.set(content);
	}

}
