

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tickets.DBConnection;

/**
 * Servlet implementation class BookConfirm
 */
@WebServlet("/BookConfirm")
public class BookConfirm extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public BookConfirm() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		PrintWriter pw = res.getWriter();
		res.setContentType("text/html");
		int seat=Integer.parseInt(req.getParameter("seats"));
		int flightNumber=Integer.parseInt(req.getParameter("flightNumber"));
		String pname=req.getParameter("name");
		String address=req.getParameter("Address");
		String phone=req.getParameter("Phone");
		InputStream in = getServletContext().getResourceAsStream("/WEB-INF/config.properties");
		Properties props=new Properties();
		String ticket;
		props.load(in);
		RequestDispatcher rd = req.getRequestDispatcher("searchresult.html");
		rd.include(req, res);
		try {
			DBConnection conn = new DBConnection( props.getProperty("url") ,props.getProperty("userid") , props.getProperty("password"));
			PreparedStatement ps = conn.getConnection().prepareStatement("Select * from flight where flightNumber = ?");
			ps.setLong(1, flightNumber);
			ResultSet rs = ps.executeQuery();
			if(rs.next())
			{
				int avail =rs.getInt("seatsAvailable");
				if(seat>avail) {
					pw.println("<div class='tab'><p1 class='menu red'>Only "+avail+" Seats are Available in this Flight!</p1></div>");
				}
				else if(seat<=avail) {
					avail = avail - seat;
					PreparedStatement ps1 = conn.getConnection().prepareStatement("update flight set seatsAvailable=? where flightNumber=?");
					ps1.setInt(1, avail);
					ps1.setInt(2, flightNumber);
					int k= ps1.executeUpdate();
					if(k==1)
					{
						pw.println("<div class='tab'><p1 class='menu green'>"+seat+" Seats Booked Successfully!</p1></div><br><br>");
						
						ticket=req.getParameter("flightNumber").concat(phone);
						pw.println("<div class='tab'><p1 class='menu green'>Flight Details</p1></div><br>"
						+"<table>"
						+ "<tr><td>Ticket No:</td><td>"+ticket+"</td></tr>"
						+ "<tr><td>Flight No:</td><td>"+flightNumber+"</td></tr>"
						+ "<tr><td>Flight Name:</td><td>"+rs.getString("flightName")+"</td></tr>"
						+ "<tr><td>fromAirport:</td><td>"+rs.getString("fromAirport")+"</td></tr>"
						+ "<tr><td>toAirport:</td><td>"+rs.getString("toAirport")+"</td></tr>"
						+ "<tr><td>Date :</td><td>"+rs.getString("Date")+"</td></tr>"
						+ "<tr><td>Time:</td><td>"+rs.getString("Time")+"</td></tr>"
						+ "</table><br>"
						+ "<table>"
						+ "<tr><th>Passenger Details</th></tr>"
						+ "<tr><td>Passenger Name:</td><td>"+pname+"</td></tr>"
						+ "<tr><td>Passenger Address:</td><td>"+address+"</td></tr>"
						+ "<tr><td>Passenger Phone:</td><td>"+phone+"</td></tr>"
						+ "</table>");
						pw.println("<div class='tab'><p1 class='menu green'>THANKYOU!!!</p1></div><br>");
						}
				}
			}
			else {
				pw.println("<div class='tab'><p1 class='menu'>Invalid Flight Number !</p1></div>");
			}
			ps.close();
			ps = conn.getConnection().prepareStatement("insert into passenger (firstname,Address,phone,flightNumber) values (?,?,?,? )");
			ps.setString(1, pname);
			ps.setString(2, address);
			ps.setString(3,phone);
			ps.setInt(4, flightNumber);
			ps.execute();
		}catch(ClassNotFoundException | SQLException e){
			e.printStackTrace();
		}
	}


/**
 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
 */
protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	// TODO Auto-generated method stub
	doGet(request, response);
}

}
