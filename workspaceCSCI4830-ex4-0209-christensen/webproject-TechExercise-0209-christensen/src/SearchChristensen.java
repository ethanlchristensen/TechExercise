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

@WebServlet("/SearchChristensen")
public class SearchChristensen extends HttpServlet {
   private static final long serialVersionUID = 1L;

   public SearchChristensen() {
      super();
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String nUmber = request.getParameter("number");
      search(nUmber, response);
   }

   void search(String nUmber,  HttpServletResponse response) throws IOException {
      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      String title = "Contact List";
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
         DBConnectionChristensen.getDBConnection(getServletContext());
         connection = DBConnectionChristensen.connection;

         if (nUmber.isEmpty()) {
            String selectSQL = "SELECT * FROM contactListData";
            preparedStatement = connection.prepareStatement(selectSQL);
         } else {
        	String selectSQL = "SELECT * FROM contactListData WHERE number LIKE ?";
     		String theNumber = nUmber + "%";
     		preparedStatement = connection.prepareStatement(selectSQL);
     		preparedStatement.setString(1, theNumber);
         }
         ResultSet rs = preparedStatement.executeQuery();
         
         /*if (rs.getFetchSize() == 0) {
        	 out.println("That Contact does not exist! <br>");
        	 out.println("<a href=search_christensen.html>Search Data</a> <br>");
             out.println("</body></html>");
             rs.close();
             preparedStatement.close();
             connection.close();
         } else {*/
         while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name").trim();
            String address = rs.getString("address").trim();
            String number = rs.getString("number").trim();
            String email = rs.getString("email").trim();
            out.println("Contact: " + id + "<br>");
            out.println("Name: " + name + "<br>");
            out.println("Address: " + address + "<br>");
            out.println("number: " + number + "<br>");
            out.println("Email: " + email + "<br>");
            out.println("<br>");
         }
         out.println("<a href=main.html>Back</a> <br>");
         out.println("</body></html>");
         rs.close();
         preparedStatement.close();
         connection.close();
         //}
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

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doGet(request, response);
   }

}
