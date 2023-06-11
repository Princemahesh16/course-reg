package pro5pack;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/proj5reg")
public class proj5reg extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public proj5reg() {
        super();
    }

    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter pw = response.getWriter();
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String fullname = request.getParameter("fullname");
        String dateofbirth = request.getParameter("Birthday_day") + " " +
                             request.getParameter("Birthday_Month") + " " +
                             request.getParameter("Birthday_Year");
        String gmailid = request.getParameter("gmailid");
        String gender = request.getParameter("Gender");
        String address = request.getParameter("Address");
        String course = request.getParameter("Course");
        String mobilenumber = request.getParameter("Mobilenumber");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mahesh", "root", "tiger");
            PreparedStatement st = con.prepareStatement("INSERT INTO admin (username, password, fullname, dateofbirth, gmailid, gender, address, course, mobilenumber) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");

            st.setString(1, username);
            st.setString(2, password);
            st.setString(3, fullname);
            st.setString(4, dateofbirth);
            st.setString(5, gmailid);
            st.setString(6, gender);
            st.setString(7, address);
            st.setString(8, course);
            st.setString(9, mobilenumber);

            st.executeUpdate();
            con.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        pw.println("Successfully stored the data");
    }
}
