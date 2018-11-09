package bandp;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;


public class Main {
	Connection connection;
	DriverManager dm;
	//Statement stm;
	
	public java.sql.Connection connect(String DB_URL, String username, String password) {
		try {
			connection = DriverManager.getConnection(DB_URL, username, password);
			
			return connection;
		}catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Problems with server / database");
			return null;
		}catch (NullPointerException e) {
			e.printStackTrace();
			System.out.println("Null");
			return null;
		}
	}
	
	public int executeStatement(String sql) {
		try {
			Statement stm = connection.createStatement();
			return stm.executeUpdate(sql);
		}catch (SQLException | NullPointerException e) {
			e.printStackTrace();
			System.out.println("You did something wrong!!!");
			return -22;
		}
		
	}
	
	public ResultSet resultSet(String query) {
		ResultSet rs = null;
		try {
			Statement stm = connection.createStatement();
		rs = stm.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}

	public static void main(String[] args) throws SQLException, IOException {
		
		System.out.println("Select the current ship: ");
		Scanner scanner=new Scanner(System.in);
		
		String shipChoice="";
		System.out.println("1.Myrtw\n2.Artemis\n3.Eirini");
		do {
			shipChoice=scanner.next();
		
		if(shipChoice.equals("1")) {
			Ships Myrtw=new Ships("Myrtw",50,4,30,300);
			Ships.menu(Myrtw);
		}else if(shipChoice.equals("2")) {
			Ships Artemis=new Ships("Artemis",40,4,20,220);
			Ships.menu(Artemis);
		}else if(shipChoice.equals("3")) {
			Ships Eirini=new Ships("Eirini",37,3,14,170);
			Ships.menu(Eirini);
		}else {
			System.out.println("Enter a valid number: ");
		}
		}while (Integer.parseInt(shipChoice)>3 || Integer.parseInt(shipChoice)<1);
		
	}

}
