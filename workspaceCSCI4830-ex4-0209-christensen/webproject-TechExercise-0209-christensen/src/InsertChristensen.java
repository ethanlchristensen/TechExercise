
/**
 * @file InsertChristensen.java
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/InsertChristensen")
public class InsertChristensen extends HttpServlet {
   private static final long serialVersionUID = 1L;

   public InsertChristensen() {
      super();
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String name = request.getParameter("name");
      String address = request.getParameter("address");
      String number = request.getParameter("number");
      String email = request.getParameter("email");
      
      if(name.isEmpty() || address.isEmpty() || number.isEmpty() || email.isEmpty()) {
    	  response.setContentType("text/html");
          PrintWriter out = response.getWriter();
          String title = "Error";
          String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";
          out.println(docType + //
                "<html>\n" + //
                "<head><title>" + title + "</title></head>\n" + //
                "<body bgcolor=\"#f0f0f0\">\n" + //
                "<h2 align=\"center\">" + title + "</h2>\n" + //
                "<ul>\n" + //

                "  <li><b>You must fill out each field for the contact . . .</b>: \n" + //
                "</ul>\n");

          out.println("<a href=insert_christensen.html>Insert Data</a> <br>");
          out.println("</body></html>");
      }
      Connection connection = null;
      String insertSql = " INSERT INTO contactListData (id, name, address, number, email) values (default, ?, ?, ?, ?)";

      try {
         DBConnectionChristensen.getDBConnection(getServletContext());
         connection = DBConnectionChristensen.connection;
         PreparedStatement preparedStmt = connection.prepareStatement(insertSql);
         preparedStmt.setString(1, name);
         preparedStmt.setString(2, address);
         preparedStmt.setString(3, number);
         preparedStmt.setString(4, email);
         preparedStmt.execute();
         connection.close();
      } catch (Exception e) {
         e.printStackTrace();
      }

      // Set response content type
      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      String title = "Inserted Contact";
      String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";
      out.println(docType + //
            "<html>\n" + //
            "<head><title>" + title + "</title></head>\n" + //
            "<body bgcolor=\"#f0f0f0\">\n" + //
            "<h2 align=\"center\">" + title + "</h2>\n" + //
            "<ul>\n" + //

            "  <li><b>Name</b>: " + name + " \n" + //
            "  <li><b>Address</b>: " + address + "\n" + //
            "  <li><b>Number</b>: " + number + "\n" + //
            "  <li><b>Email</b>: " + email + "\n" + //

            "</ul>\n");

      out.println("<a href=main.html>Back</a> <br>");
      out.println("</body></html>");
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doGet(request, response);
   }

}
