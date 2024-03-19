

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class SearchTimeclock
 */
@WebServlet("/SearchTimeclock")
public class SearchTimeclock extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchTimeclock() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String keyword = request.getParameter("keyword");
	    search(keyword, response);
	}
	
	void search(String keyword, HttpServletResponse response) throws IOException {
	      response.setContentType("text/html");
	      PrintWriter out = response.getWriter();
	      String title = "Timesheet Result";
	      String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + //
	            "transitional//en\">\n"; //
	      out.println(docType + //
	            "<html>\n" + //
	            "<head><title>" + title + "</title></head>\n" + //
	            "<body bgcolor=\"#f0f0f0\">\n" + //
	            "<h1 align=\"center\">" + title + "</h1>\n");

	      Connection connection = null;
	      PreparedStatement preparedStatement = null;
	      try {
	         DBConnectionBokovi.getDBConnection();
	         connection = DBConnectionBokovi.connection;

	         if (keyword.isEmpty()) {
	            String selectSQL = "SELECT * FROM Timesheet";
	            preparedStatement = connection.prepareStatement(selectSQL);
	         } else {
	            String selectSQL = "SELECT * FROM Timesheet WHERE NAME LIKE ?";
	            String name = keyword + "%";
	            preparedStatement = connection.prepareStatement(selectSQL);
	            preparedStatement.setString(1, name);
	         }
	         ResultSet rs = preparedStatement.executeQuery();

	         while (rs.next()) {
	            int id = rs.getInt("id");
	            String name = rs.getString("name").trim();
	            String clockIn = rs.getString("clockIn").trim();
	            String clockOut = rs.getString("clockOut").trim();
	            String date = rs.getString("date").trim();

	            if (keyword.isEmpty() || name.contains(keyword)) {
	               out.println("ID: " + id + ", ");
	               out.println("Name: " + name + ", ");
	               out.println("Clock-In Time: " + clockIn + ", ");
	               out.println("Clock-Out Time: " + clockOut + ", ");
	               out.println("Date: " + date + "<br>");
	            }
	         }
	         out.println("<a href=/Timeclock/search_timesheet.html>Search Timesheet</a> <br>");
	         out.println("</body></html>");
	         rs.close();
	         preparedStatement.close();
	         connection.close();
	      } catch (SQLException se) {
	         se.printStackTrace();
	      } catch (Exception e) {
	         e.printStackTrace();
	      } finally {
	         try {
	            if (preparedStatement != null)
	               preparedStatement.close();
	         } catch (SQLException se2) {
	         }
	         try {
	            if (connection != null)
	               connection.close();
	         } catch (SQLException se) {
	            se.printStackTrace();
	         }
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
