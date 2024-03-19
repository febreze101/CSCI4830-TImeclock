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
 * Servlet implementation class Timeclock
 */
@WebServlet("/InsertTimeclock")
public class InsertTimeclock extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public InsertTimeclock() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String clockIn = request.getParameter("clockIn");
        String clockOut = request.getParameter("clockOut");
        String date = request.getParameter("date");

        Connection connection = null;
        String insertSql = " INSERT INTO Timesheet (id, NAME, CLOCKIN, CLOCKOUT, DATE) values (default, ?, ?, ?, ?)";

        try {
           DBConnectionBokovi.getDBConnection();
           connection = DBConnectionBokovi.connection;
           PreparedStatement preparedStmt = connection.prepareStatement(insertSql);
           preparedStmt.setString(1, name);
           preparedStmt.setString(2, clockIn);
           preparedStmt.setString(3, clockOut);
           preparedStmt.setString(4, date);
           preparedStmt.execute();
           connection.close();
        } catch (Exception e) {
           e.printStackTrace();
        }

        // Set response content type
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String title = "Insert Data to DB table";
        String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";
        out.println(docType + //
              "<html>\n" + //
              "<head><title>" + title + "</title></head>\n" + //
              "<body bgcolor=\"#f0f0f0\">\n" + //
              "<h2 align=\"center\">" + title + "</h2>\n" + //
              "<ul>\n" + //

              "  <li><b>Name</b>: " + name + "\n" + //
              "  <li><b>Clock-In</b>: " + clockIn + "\n" + //
              "  <li><b>Clock-Out</b>: " + clockOut + "\n" + //
              "  <li><b>Date</b>: " + date + "\n" + //

              "</ul>\n");

        out.println("<a href=/Timeclock/search_timesheet.html>Search Timesheet</a> <br>");
        out.println("</body></html>");
     }
	
	


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
