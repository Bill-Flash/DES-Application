package com.example.des.tools;

import com.example.des.model.Diary;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Properties;

public class DatabaseTool {

	// connect to the dataBase
	public static Connection getConnection() {
		Connection conn = null;
		try {
			// load the driver
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			Properties properties = new Properties();
			FileInputStream fis = null;
			try {
				// read the property file that contains my Local database: password
				fis = new FileInputStream("mysql.properties");
				properties.load(fis);
				String url = "jdbc:mysql://"+
						properties.getProperty("host")+
						":"+properties.getProperty("port")+
						"/"+properties.getProperty("database");

				// create connection
				conn = DriverManager.getConnection(url, properties);
			} catch(IOException e) {
				e.printStackTrace();
			} finally {
				if(fis != null) {
					try {
						fis.close();
					}catch(IOException e) {
						e.printStackTrace();
					}
				}
			}
		}catch(ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
	// release the resource in case the waste
	public static void release(Connection conn, Statement pstmt, ResultSet res) {
		if(conn != null) {
			try {
				conn.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		if(pstmt != null) {
			try {
				pstmt.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		if(res != null) {
			try {
				res.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
	// CUD, the basic operations
	public static boolean executeInsertDeleteUpdate(String sql, Object...args) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		boolean isSuccessful = false;
		
		try {
			// get Connection
			conn = DatabaseTool.getConnection();
			pstmt = conn.prepareStatement(sql);
			// as a CURD function, it receives the variable length of args
			if(args != null) {
				for(int i=0; i<args.length; i++) {
					pstmt.setObject(i+1, args[i]);
				}
			}

			// get the result
			int result = pstmt.executeUpdate();
			if(result > 0) {
				isSuccessful = true;
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			// release
			DatabaseTool.release(conn, pstmt, null);
		}
		return isSuccessful;
	}
	

	// Read, get the request of the diary list
	public static ArrayList<Diary> getDiaryList(String sql, Object...args){
		ArrayList<Diary> dList = new ArrayList<>();
		
		ResultSet res = null;
		PreparedStatement pstmt = null;
		Connection conn = DatabaseTool.getConnection();
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			if(args != null) {
				for(int i=0; i<args.length; i++) {
					pstmt.setObject(i+1, args[i]);
				}
			}
			res = pstmt.executeQuery();

			// iterator to get all items
			while(res.next()) {
				int id = res.getInt(1);
				String title = res.getString(2);
				String key = res.getString(3);
				int weaIndex = res.getInt(4);
				LocalDate date = res.getDate(5).toLocalDate();// become the date, rather than the time
				String content = res.getString(6);
				Diary diary = new Diary(id, title, key, weaIndex, date, content);
				dList.add(diary);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			DatabaseTool.release(conn, pstmt, res);
		}
		return dList;
	}

	
}
