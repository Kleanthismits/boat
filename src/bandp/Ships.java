package bandp;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;


public class Ships {
	private String shipName;
	private int shipLength;
	private int lanes;
	private int numOfMotos;
	private int numOfPassengers;

	ArrayList<Double> lane1 = new ArrayList<>();
	ArrayList<Double> lane2 = new ArrayList<>();
	ArrayList<Double> lane3 = new ArrayList<>();
	ArrayList<Double> lane4 = new ArrayList<>();
	ArrayList<Moto> moto = new ArrayList<>();
	ArrayList<Passengers> pas = new ArrayList<>();

	public Ships() {
	}

	public Ships(String shipName,int shipLength, int lanes, int numOfMotos, int numOfPassengers) {
		this.shipName = shipName;
		this.shipLength = shipLength;
		this.lanes = lanes;
		this.numOfMotos = numOfMotos;
		this.numOfPassengers = numOfPassengers;
	}

	public int getShipLength() {
		return shipLength;
	}

	public void setShipLength(int shipLength) {
		this.shipLength = shipLength;
	}

	public int getLanes() {
		return lanes;
	}

	public void setLanes(int lanes) {
		this.lanes = lanes;
	}

	public int getNumOfMotos() {
		return numOfMotos;
	}

	public void setNumOfMotos(int numOfMotos) {
		this.numOfMotos = numOfMotos;
	}

	public int getNumOfPassengers() {
		return numOfPassengers;
	}

	public void setNumOfPassengers(int numOfPassengers) {
		this.numOfPassengers = numOfPassengers;
	}
	
	public static void menu(Ships s) throws SQLException, IOException {
		
		Main main = new Main();
		String url = "jdbc:mysql://localhost:3306";
		main.connect(url, "root", "password");
		boolean flag=false;
		boolean full=false; 
		int numberOfPassengers = 0;
		double lengthLeft1 = s.getShipLength();
		double lengthLeft2 = s.getShipLength();
		double lengthLeft3 = s.getShipLength();
		double lengthLeft4 = s.getShipLength();
		String t1 = "Regular";
		String t2 = "Elder";
		String t3 = "Student";
		String t4 = "AMEA";
		String t5 = "Baby";
		String totalPasamount = "";
		String totalVamount = "";
		
		long currentTime = System.currentTimeMillis();
		Date time = new Date(currentTime);
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		String cTime = sdf.format(time);

		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		Date today = Calendar.getInstance().getTime();
		String cDate = df.format(today);
		
		main.executeStatement("truncate ships.passengers;");
		main.executeStatement("truncate ships.vehicles;");
		
		PrintWriter writer2 = new PrintWriter(new FileWriter("shipReport.txt",false));
		writer2.write("");
		writer2.close();
		
//		String query = ("SELECT MAX(vehicles.vehicle_id) as maxId FROM ships.vehicles;");
//		String query2 = ("SELECT MAX(passengers.passenger_id) as maxId2 FROM ships.passengers;");
//		ResultSet rs = main.resultSet(query);
//		ResultSet rs2 = main.resultSet(query2);
//		System.out.println();
//		
//			try {
//				while(rs.next()) {
//				String maxvId = rs.getString("maxId");
//				System.out.println(maxvId);
//				}
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			
//			try {
//				while(rs2.next()) {
//				String maxpId = rs2.getString("maxId2");
//				System.out.println(maxpId);
//				}
//			} catch (SQLException ex) {
//				// TODO Auto-generated catch block
//				ex.printStackTrace();
//			}
//		
		do {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Select the number of passengers: ");
		numberOfPassengers = scanner.nextInt();
		for (int i = 0; i < numberOfPassengers; i++) {
			boolean b = false;
			do {
			System.out.println("Enter type of " + (i + 1) + " passenger: ");
			System.out.println("1. Regular");
			System.out.println("2. Elder");
			System.out.println("3. Student");
			System.out.println("4. AMEA");
			System.out.println("5. Baby");
			String type = scanner.next();
			if (type.equals("1")) {
				type = t1;
			}else if(type.equals("2")) {
				type = t2;
			}else if(type.equals("3")) {
				type = t3;
			}else if(type.equals("4")) {
				type = t4;
			}else if(type.equals("5")) {
				type = t5;
			}
			else {
				System.out.println("Invalid Choice");
				b = true;
			}
			try {
			main.executeStatement("insert into ships.passengers (passenger_type) values ('"+type+"');");
			}catch(NullPointerException e) {
				//e.getMessage();
			}
			s.pas.add(new Passengers(type));
			if(s.pas.size()==s.getNumOfPassengers()) {
				System.out.println("Passengers limit reached");
			}
			
			}while(b);
		}
		System.out.println("Space left per lane :");
		System.out.println();
		System.out.print("\t" + "Lane 1: " + (int)lengthLeft1 + "m ");
		System.out.print("\t" + "Lane 2: " + (int)lengthLeft2 + "m ");
		System.out.print("\t" + "Lane 3: " + (int)lengthLeft3 + "m ");
		if (s.getLanes()>3) {
			System.out.print("\t" + "Lane 4: " + (int)lengthLeft4 + "m ");
		}
		System.out.println();
		System.out.println("Select vehicle type:\n1.Moto\n2.Car\n3.Truck\n4.No Vehicle ");
		String vehicleChoice = scanner.next();

		if (vehicleChoice.equals("1")) {
			s.moto.add(new Moto());
			if (s.moto.size()==s.getNumOfMotos()) {
				System.out.println("Moto limit reached");
			}
			main.executeStatement("insert into ships.vehicles (vehicle_type) values ('Moto');");
		} else if (vehicleChoice.equals("2") || vehicleChoice.equals("3")) {
			System.out.println("Enter vehicle length : ");
			Double vehicleLength = scanner.nextDouble();
				
			
			
								if (laneLength(s.lane1)<=laneLength(s.lane2) && (laneLength(s.lane1)+vehicleLength) <= s.getShipLength()) {
									
									System.out.println("lane1");
									s.lane1.add(vehicleLength);
									lengthLeft1 = (s.getShipLength() - (laneLength(s.lane1)));
									
								}else if (laneLength(s.lane2)<=laneLength(s.lane3) && (laneLength(s.lane2)+vehicleLength) <= s.getShipLength()) {
									
									System.out.println("lane2");
									s.lane2.add(vehicleLength);
									lengthLeft2 = (s.getShipLength() - (laneLength(s.lane2)));
								}else if (laneLength(s.lane3)<=laneLength(s.lane4) && (laneLength(s.lane3)+vehicleLength) <= s.getShipLength()) {
									
									System.out.println("lane3");
									s.lane3.add(vehicleLength);
									lengthLeft3 = (s.getShipLength() - (laneLength(s.lane3)));
								}else if(s.getLanes()>3) {
									if(laneLength(s.lane4)<=laneLength(s.lane1) && (laneLength(s.lane4)+vehicleLength) <= s.getShipLength()) {
										
										System.out.println("lane4");
										s.lane4.add(vehicleLength);
										lengthLeft4 = (s.getShipLength() - (laneLength(s.lane4)));
									}else {
										int p = (int) Math.round(max(lengthLeft1,lengthLeft2,lengthLeft3,lengthLeft4));
										System.out.println("No more vehicles allowed");
										if (p>0) {
										System.out.println("Only vehicles with size smaller than " + p + " allowed");
										}
									}
								}else {
									int p = (int) Math.round(max(lengthLeft1,lengthLeft2,lengthLeft3,lengthLeft4));
									System.out.println("No more vehicles allowed");
									if (p>0) {
									System.out.println("Only vehicles with size smaller than " + p + "allowed");
									}
								}
								
						if (vehicleChoice.equals("2")) {
							main.executeStatement("insert into ships.vehicles (vehicle_type,vehicle_length) values ('Car','"+vehicleLength+"');");
						}else if (vehicleChoice.equals("3")) {
							main.executeStatement("insert into ships.vehicles (vehicle_type,vehicle_length) values ('Truck','"+vehicleLength+"');");
						}
					
		}else if (vehicleChoice.equals("4")){
		flag=true;
		}
		System.out.println("Print report?");
		System.out.println("Y for Yes");
		System.out.println("N for No");
		String ticket = scanner.next();
		if (ticket.equalsIgnoreCase("y")) {
			ResultSet rs3 = main.resultSet("SELECT COUNT(ships.vehicles.vehicle_type) as Quantity,ships.vehicles.vehicle_type as Type,  sum(ships.vehicles.ticket_price) as Total from ships.vehicles\r\n" + 
					"group by vehicles.vehicle_type;");
			try {
				PrintWriter writer = new PrintWriter(new FileWriter("shipReport.txt",true));
				writer.append(String.format("%-10s %-10s", "Ship :", s.getShipName()));
				writer.append(System.getProperty( "line.separator" ));
				writer.append(String.format(""));
				writer.append(System.getProperty( "line.separator" ));
				writer.append(String.format("%-10s %-15s %-10s %-10s", "Date: ", cDate, "Time: ", cTime));
				writer.append(System.getProperty( "line.separator" ));
				writer.append(String.format(""));
				writer.append(System.getProperty( "line.separator" ));
				writer.append(String.format("%25s", "Vehicles Report"));
				writer.append(System.getProperty( "line.separator" ));
				writer.append(String.format(""));
				writer.append(System.getProperty( "line.separator" ));
				writer.append(String.format("%-12s %-15s %-10s ", "Quantity", "Vehicle Type", "Total amount"));
				writer.append(System.getProperty( "line.separator" ));
				writer.append(String.format(""));
				writer.append(System.getProperty( "line.separator" ));
				while (rs3.next()) {
					String count = rs3.getString("Quantity");
					String vtype = rs3.getString("Type");
					String sum = rs3.getString("Total");
					
					
					writer.append(String.format("%-12s %-15s %-10s ", count, vtype, sum));
					writer.append(System.getProperty( "line.separator" ));
					
					} 
				writer.append(System.getProperty( "line.separator" ));
				writer.append(String.format(""));
				writer.close();
			}catch (SQLException | IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
			ResultSet rs4 = main.resultSet("SELECT COUNT(passengers.passenger_type) as Quantity,passengers.passenger_type as Type,  sum(ticket_price) as Total from ships.passengers\r\n" + 
					"group by passengers.passenger_type;");
			try {
				PrintWriter writer = new PrintWriter(new FileWriter("shipReport.txt",true));
				writer.append(String.format("%27s", "Passengers Report"));
				writer.append(System.getProperty( "line.separator" ));
				writer.append(String.format(""));
				writer.append(System.getProperty( "line.separator" ));
				writer.append(String.format("%-12s %-15s %-10s ", "Quantity", "Passenger Type", "Total amount"));
				writer.append(System.getProperty( "line.separator" ));
				writer.append(String.format(""));
				writer.append(System.getProperty( "line.separator" ));
				while (rs4.next()) {
					String count = rs4.getString("Quantity");
					String vtype = rs4.getString("Type");
					String sum = rs4.getString("Total");
						
					writer.append(String.format("%-12s %-15s %-10s ", count, vtype, sum));
					writer.append(System.getProperty( "line.separator" ));
					
					} 
				
				
				writer.close();
			}catch (SQLException | IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			ResultSet rs5 = main.resultSet("SELECT SUM(passengers.ticket_price) as sum from ships.passengers;");
			while(rs5.next()) {
				totalPasamount = rs5.getString("sum");
			}
			rs5 = main.resultSet("SELECT SUM(vehicles.ticket_price) as sum from ships.vehicles;");
			while(rs5.next()) {
				totalVamount = rs5.getString("sum");
			}	
			PrintWriter writer = new PrintWriter(new FileWriter("shipReport.txt",true));
			writer.append(System.getProperty( "line.separator" ));
			writer.append(String.format(""));
			writer.append(System.getProperty( "line.separator" ));
			writer.append(String.format("%-20s %-10s", "Total income from Passengers: ",totalPasamount));
			writer.append(System.getProperty( "line.separator" ));
			writer.append(String.format("%-20s %-10s", "Total income from Vehicles: ",totalVamount));
			writer.append(System.getProperty( "line.separator" ));
			writer.append(String.format(""));
			writer.append(System.getProperty( "line.separator" ));
			writer.append(String.format("%-15s %-10.2f", "Total income: ",(Double.parseDouble(totalPasamount) + Double.parseDouble(totalVamount))));
			
			writer.close();
		}
		System.out.println("Do you want another action? (Y/N)");
		String answer=scanner.next();
		if (answer.equalsIgnoreCase("Y")) {
			flag=true;
		}
		else {flag=false;}
		
		}while(flag);

	}
	
	public static double max(double a, double b, double c, double d) {

	    double max = a;

	    if (b > max)
	        max = b;
	    if (c > max)
	        max = c;
	    if (d > max)
	        max = d;

	     return max;
	}
public static int laneLength(ArrayList<Double> al) {
	double count=0;
	int i;
	for(i=0;i<al.size();i++) {
		count=count+al.get(i);
	}
	return (int) Math.round(count);
}

public String getShipName() {
	return shipName;
}

public void setShipName(String shipName) {
	this.shipName = shipName;
}
}
