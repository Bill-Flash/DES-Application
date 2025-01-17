package com.example.des.tools;

public class SingleValueTool {
	static String[] weaStrs = new String[] {"Sunny","Raining","Cloudy","Windy","Clear","Foggy","Snowy"};

	// get the list of str
	public static String[] getWeaStrs() {
		return weaStrs;
	}
	// get the str of selected str
	public static String getWeaStrSelected(int index) {
		return weaStrs[index];
	}
}
