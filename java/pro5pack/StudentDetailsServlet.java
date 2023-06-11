package pro5pack;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class StudentDetailsServlet extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Get input parameter
        String username = request.getParameter("username");

        // Database connection and query
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        boolean isValidStudent = false;
        String studentName = null;
        String studentCourse = null;

        try {
            // Load the JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            // Establish database connection
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mahesh", "root", "tiger");

            // Prepare SQL statement
            String sql = "SELECT * FROM admin WHERE username = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);

            // Execute the query
            rs = stmt.executeQuery();

            // Check if the student exists
            if (rs.next()) {
                isValidStudent = true;
                studentName = rs.getString("fullname");
                studentCourse = rs.getString("course");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            // Handle the exception and display an error message
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("Internal error: JDBC driver not found");
            out.close();
            return; // Stop further execution
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception and display an error message
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("Database error: " + e.getMessage());
            out.close();
            return; // Stop further execution
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

        if (isValidStudent) {
            out.println("<h1>Student Details</h1>");
            out.println("<p><strong>username:</strong> " + username + "</p>");
            String fullname = null;
			out.println("<p><strong>fullname:</strong> " + fullname + "</p>");
            String course = null;
			out.println("<p><strong>Course:</strong> " + course + "</p>");
        } else {
            out.println("<p>No student found with ID: " + username + "</p>");
        }
        out.close();
    }
}

