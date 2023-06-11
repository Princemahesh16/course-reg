package pro5pack;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/proj5adminval")
public class proj5adminval extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Database connection parameters
   // private static final String DB_URL = "jdbc:mysql://localhost:3306/mahesh";
   // private static final String DB_USERNAME = "root";
  //  private static final String DB_PASSWORD = "tiger";
    //private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Get input parameters
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Database connection and validation
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        boolean isValidAdmin = false;

try {   // Load the JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            // Establish database connection
            conn = DriverManager.getConnection( "jdbc:mysql://localhost:3306/mahesh", "root", "tiger");

            // Prepare SQL statement
            String sql = "SELECT * FROM newadmin WHERE username = ? AND password = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);

            // Execute the query
            rs = stmt.executeQuery();

            // Check if the admin is valid
    if (rs.next()) {
      isValidAdmin = true;
       }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close database resources
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // Send the response to the client
   
        	response.setContentType("text/html");
        	PrintWriter out = response.getWriter();

        	if (isValidAdmin) {
        	    response.sendRedirect("proj5stad.html");
        	} else {
        	    out.println("<script>alert('Invalid login credentials please try again'); window.location.href='proj5reg.html';</script>");
        	}
        out.close();
    }
}
