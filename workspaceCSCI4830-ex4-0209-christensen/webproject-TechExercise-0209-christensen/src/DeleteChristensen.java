
/**
 * @file DeleteChristensen.java
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

@WebServlet("/DeleteChristensen")
public class DeleteChristensen extends HttpServlet {
   private static final long serialVersionUID = 1L;

   public DeleteChristensen() {
      super();
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String number = request.getParameter("number");
      
      Connection connection = null;
      String deleteSql = " DELETE FROM contactListData WHERE number LIKE ?";
      String resetSql = " ALTER TABLE contactListData AUTO_INCREMENT = AUTO_INCREMENT - 1";
      String theNumber = number + "%";

      try {
         DBConnectionChristensen.getDBConnection(getServletContext());
         connection = DBConnectionChristensen.connection;
         PreparedStatement preparedStmt = connection.prepareStatement(deleteSql);
         preparedStmt.setString(1, theNumber);
         preparedStmt.execute();
         preparedStmt.setString(1, resetSql);
         preparedStmt.execute();
         
         connection.close();
      } catch (Exception e) {
         e.printStackTrace();
      }

      // Set response content type
      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      String title = "Contact Deleted";
      String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";
      out.println(docType + //
            "<html>\n" + //
            "<head><title>" + title + "</title></head>\n" + //
            "<body bgcolor=\"#f0f0f0\">\n" + //
            "<h2 align=\"center\">" + title + "</h2>\n");

      out.println("<a href=main.html>Back</a> <br>");
      out.println("</body></html>");
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doGet(request, response);
   }

}
