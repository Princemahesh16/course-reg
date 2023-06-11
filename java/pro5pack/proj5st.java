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

@WebServlet("/proj5st")
public class proj5st extends HttpServlet {
    private static final long serialVersionUID = 1L;

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
        String course = null;
        String gmailid = null;
        String mobilenumber = null;

        try {
            // Load the JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            // Establish database connection
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mahesh", "root", "tiger");

            // Prepare SQL statement
            String sql = "SELECT * FROM admin WHERE username = ? AND password = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);

            // Execute the query
            rs = stmt.executeQuery();

            // Check if the admin is valid
            if (rs.next()) {
                isValidAdmin = true;
                course = rs.getString("course");
                gmailid = rs.getString("gmailid");
                mobilenumber = rs.getString("mobilenumber");
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
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<meta charset=\"UTF-8\">");
            out.println("<title>Student Details</title>");
            out.println("<style>");
            out.println("body {");
            out.println("    font-family: Arial, sans-serif;");
            out.println("    background-color: #f1f1f1;");
            out.println("}");

            out.println("h1 {");
            out.println("    text-align: center;");
            out.println("    color: #333;");
            out.println("}");

            out.println("p {");
            out.println("    margin: 10px;");
            out.println("    color: #666;");
            out.println("}");

            out.println(".container {");
            out.println("    max-width: 400px;");
            out.println("    margin: 0 auto;");
            out.println("    padding: 20px;");
            out.println("    border: 1px solid tomato;");
            out.println("    background-color: rgba(255, 255, 255, 0.9);");
            out.println("    border-radius: 5px;");
            out.println("    top: 60%");
            out.println("}");

            out.println("</style>");
            out.println("</head>");
            out.println("<body>");
            out.println("<div class=\"container\">");

            if (isValidAdmin) {
                out.println("<h1>Student Details</h1>");
                out.println("<p><strong>Course:</strong> " + course + "</p>");
                out.println("<p><strong>Gmail Id:</strong> " + gmailid + "</p>");
                out.println("<p><strong>Mobile Number:</strong> " + mobilenumber + "</p>");
            } else {
                out.println("<script>alert('Invalid login credentials, please try again'); window.location.href='proj5stad.html';</script>");
            }

            out.println("</div>");
            out.println("</body>");
            out.println("</html>");
            out.close();
        }
    }
}
