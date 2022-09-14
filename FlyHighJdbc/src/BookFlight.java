

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class BookFlight
 */
@WebServlet("/BookFlight")
public class BookFlight extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BookFlight() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		PrintWriter pw = res.getWriter();
		res.setContentType("text/html");
		int flightNumber=Integer.parseInt(req.getParameter("flightNumber"));
		String fromAirport=req.getParameter("fromAirport");
		String toAirport=req.getParameter("toAirport");
		String Date=req.getParameter("Date");
		String Time=req.getParameter("Time");
		int seat=1;
		RequestDispatcher rd = req.getRequestDispatcher("searchresult.html");
		rd.include(req, res);
		pw.println("<div class='main'><p1 class='menu'>Your Ticket Booking Information</p1></div>");
		pw.println("<div class='tab'><form action='bookconfirm' method='post'>"
				+ "<table>"
				+ "<tr><td>Flight No:</td><td>"+flightNumber+"</td></tr>"
				+ "<tr><td>fromAirport:</td><td>"+fromAirport+"</td></tr>"
				+ "<tr><td>toAirport:</td><td>"+toAirport+"</td></tr>"
				+ "<tr><td>Date :</td><td>"+Date+"</td></tr>"
				+ "<tr><td>Time:</td><td>"+Time+"</td></tr>"
				+ "<tr><td>No of Seats:</td><td><input type='text' name='seats' value='"+seat+"'</td></tr>"
				+ "</table><br>"
				+ "<table>"
				+ "<tr><th>Enter Passenger Details</th></tr>"
				+ "<tr><td>Passenger Name:</td><td><input type='text' name='name' ></td></tr>"
				+ "<tr><td>Passenger Address:</td><td><input type='text' name='Address' ></td></tr>"
				+ "<tr><td>Passenger Phone:</td><td><input type='text' name='Phone' ></td></tr>"
				+ "<input type='hidden' name='flightNumber' value='"+flightNumber+"'>"
				+ "</table></div>"
				+ "<div class='tab'><p1 class='menu'><input type='submit' value='Pay And Book'></p1></div>"			
				+ "</form>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
