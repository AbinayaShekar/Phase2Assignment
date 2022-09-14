

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tickets.DBConnection;

/**
 * Servlet implementation class DemoJDBC
 */
@WebServlet("/DemoJDBC")
public class DemoJDBC extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DemoJDBC() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	try {
		PrintWriter out=response.getWriter();
		out.println("<html><body>");
		
		InputStream in = getServletContext().getResourceAsStream("/WEB-INF/config.properties");
		Properties props=new Properties();
		props.load(in);
		
		DBConnection conn= new DBConnection( props.getProperty("url") ,props.getProperty("userid") , props.getProperty("password"));
		out.println("DB Connection Initialized<br>");
		
		Statement stmt = conn.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
		
		ResultSet rst=stmt.executeQuery("select * from test");
		while(rst.next())
		{
			out.println(rst.getInt("Id") + "   ," + rst.getString("name") +"<br>");
		}
		stmt.close();
		conn.closeConnection();
		out.println("DB connection closed<br>");
		out.println("</body></html>");
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
