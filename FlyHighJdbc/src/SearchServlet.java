

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
 * Servlet implementation class SearchServlet
 */
@WebServlet("/SearchServlet")
public class SearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SearchServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		res.setContentType("text/html");
		PrintWriter pw = res.getWriter();

		InputStream in = getServletContext().getResourceAsStream("/WEB-INF/config.properties");
		Properties props=new Properties();
		props.load(in);

		try {
			DBConnection conn= new DBConnection( props.getProperty("url") ,props.getProperty("userid") , props.getProperty("password"));
			PreparedStatement ps = conn.getConnection().prepareStatement("Select * from flight where fromAirport=? AND toAirport=? AND Date=?");
			ps.setString(1, req.getParameter("fromAirport"));
			ps.setString(2, req.getParameter("toAirport"));
			ps.setString(3, req.getParameter("Date"));
			ResultSet rs = ps.executeQuery();
			if(rs.next())  {
				RequestDispatcher rd = req.getRequestDispatcher("searchresult.html");
				rd.include(req, res);
				pw.println("<div class='hd'><h1>Available Flight Detail</h1></div><br>");
				int flightNumber;
				String fromAirport;
				String toAirport;
				String Date;
				String Time;
				pw.println("<div class='tab'><table><tr><th>Flight Number</th><th>Flight Name</th>"
						+ "<th>From Airport</th><th>To Airport</th><th>Seats Available</th><th>Fare (INR)</th><th>Time</th><th>Booking</th></tr>");
				do {
					
					flightNumber = rs.getInt("flightNumber");
					fromAirport = rs.getString("fromAirport");
					 toAirport = rs.getString("toAirport");
					 Date=rs.getString("Date");
					 Time=rs.getString("Time");
				pw.println(""
						+ "<tr> "
						+ ""
						+ "<td>"+flightNumber+"</td>"
						+ "<td>"+rs.getString("flightName")+"</td>"
						+ "<td>"+fromAirport+"</td>"
						+ "<td>"+toAirport+"</td>"
						+ "<td>"+rs.getInt("seatsAvailable")+"</td>"
						+ "<td>"+rs.getInt("Fare")+" RS</td>"
								+ "<td>"+rs.getString("Date")+"</td>"
								+ "<td>"+rs.getString("Time")+"</td>"
						+ "<td><a href='bookflight?flightNumber="+flightNumber+"&fromAirport="+fromAirport+"&toAirport="+toAirport+"&Date="+Date+"&Time="+Time+"'><div class='red'>Book Now</div></a></td></tr>"
					);
				}while(rs.next());
				pw.println("</table></div>");
			}else {
				RequestDispatcher rd = req.getRequestDispatcher("searchresult.html");
				rd.include(req, res);
				pw.println("<div class='main'><p1 class='menu red'> Sorry!!No Flights Available </p1></div>");
			}
		}catch (ClassNotFoundException | SQLException e)
		{
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
