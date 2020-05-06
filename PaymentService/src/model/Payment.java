package model;

import java.sql.*;
//qqqqq
public class Payment {
	// A common method to connect to the DB
	private Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");

			// Provide the correct details: DBServer/DBName, username, password
			con = DriverManager.getConnection(
					"jdbc:mysql://localhost/helthcare?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	public String insertPayments(String Appoinments, String Pharmacy, String Services, String Amount) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for inserting.";
			}
			// create a prepared statement
			String query = " insert into payments(`PaymentId`,`ChargeOfAppoinments`,`ChargeOfPharmacy`,`ChargeOfServices`,`TotalAmount`)"
					+ " values (?, ?, ?, ?, ?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setDouble(2, Double.parseDouble(Appoinments));
			preparedStmt.setDouble(3, Double.parseDouble(Pharmacy));
			preparedStmt.setDouble(4, Double.parseDouble(Services));
			preparedStmt.setDouble(5, Double.parseDouble(Amount));
			// execute the statement
			preparedStmt.execute();
			con.close();
			//output = "Inserted successfully";
			
			String newPayments = readPayments();
			output = "{\"status\":\"success\", \"data\": \"" + newPayments + "\"}";
			
		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\": \"Error while inserting the payment.\"}";
			 System.err.println(e.getMessage()); 
		}
		return output;
	}

	public String readPayments() {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for reading.";
			}
			// Prepare the html table to be displayed
			 output = "<table border='1'><tr><th>Charge Of Appoinments</th><th>Charge Of Pharmacy</th><th>Charge Of Services</th>"+ "<th>Total Amount</th><th>Update</th><th>Remove</th></tr>";
			String query = "select * from payments";
			Statement stmt = (Statement) con.createStatement();
			ResultSet rs = ((java.sql.Statement) stmt).executeQuery(query);
			// iterate through the rows in the result set
			while (rs.next()) {
				String PaymentId = Integer.toString(rs.getInt("PaymentId"));
				String ChargeOfAppoinments = Double.toString(rs.getDouble("ChargeOfAppoinments"));
				String ChargeOfPharmacy = Double.toString(rs.getDouble("ChargeOfPharmacy"));
				String ChargeOfServices = Double.toString(rs.getDouble("ChargeOfServices"));
				String TotalAmount = Double.toString(rs.getDouble("TotalAmount"));
				// Add into the html table
				output += "<tr><td><input id='hidPaymentIDUpdate'name='hidPaymentIDUpdate' type='hidden'value='" + PaymentId + "'>" + ChargeOfAppoinments + "</td>";
				output += "<td>" + ChargeOfPharmacy + "</td>";
				output += "<td>" + ChargeOfServices + "</td>";
				output += "<td>" + TotalAmount + "</td>";
				// buttons
				output += "<td><input name='btnUpdate' type='button' value='Update' class='btnUpdate btn btn-secondary'></td><td><input name='btnRemove' type='button' value='Remove'class='btnRemove btn btn-danger' data-paymentid='"+ PaymentId + "'>" + "</td></tr>"; 			}
			con.close();
			// Complete the html table
			output += "</table>";
		} catch (Exception e) {
			output = "Error while reading the payments.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String updatePayments(String Id, String Appoinments, String Pharmacy, String Services, String Amount) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for updating.";
			}
			// create a prepared statement
			String query = "UPDATE payment SET ChargeOfAppoinments=?,ChargeOfPharmacy=?,ChargeOfServices=?,TotalAmount=? WHERE PaymentId=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setDouble(1, Double.parseDouble(Appoinments));
			preparedStmt.setDouble(2, Double.parseDouble(Pharmacy));
			preparedStmt.setDouble(3, Double.parseDouble(Services));
			preparedStmt.setDouble(4, Double.parseDouble(Amount));
			preparedStmt.setInt(5, Integer.parseInt(Id));
			// execute the statement
			preparedStmt.execute();
			con.close();
			//output = "Updated successfully";
			
			String newPayments = readPayments();
			 output = "{\"status\":\"success\", \"data\": \"" +
			 newPayments + "\"}"; 
			
		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\": \"Error while updating the payments.\"}";
			 System.err.println(e.getMessage()); 
		}
		return output;
	}

	public String deletePayment(String PaymentId) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}
			// create a prepared statement
			String query = "delete from payment where PaymentId=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, Integer.parseInt(PaymentId));
			// execute the statement
			preparedStmt.execute();
			con.close();
			//output = "Deleted successfully";
			
			String newPayments = readPayments();
			 output = "{\"status\":\"success\", \"data\": \"" +
			 newPayments + "\"}"; 
			
		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\": \"Error while deleting the payment.\"}";
			 System.err.println(e.getMessage());
		}
		return output;
	}

}
