import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Servlet implementation class HelloServlet
 */
@WebServlet("/HelloServlet")
public class HelloServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	 @Override
	    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        // Preprocess request: we actually don't need to do any business stuff, so just display JSP.
	        request.getRequestDispatcher("/output.jsp").forward(request, response);
	    }

	    @Override
	    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        // Postprocess request: gather and validate submitted data and display the result in the same JSP.

	        // Prepare messages.
	        Map<String, String> messages = new HashMap<String, String>();
	        request.setAttribute("messages", messages);

	        // Get and validate name
	        String sku = request.getParameter("sku");
	        String title=findBook(sku);
	        
	        if (sku == null || sku.trim().isEmpty()||title=="") {
	            messages.put("sku", "Please enter a valid sku");
	        } else if (!sku.matches("\\p{Alnum}+")) {
	            messages.put("sku", "Please enter alphanumeric characters only");
	        }

	        // No validation errors? Do the business job!
	        if (messages.isEmpty()) {
	          
	            messages.put("success", String.format("Hello, the sku you entered is %s and the book is %s!", sku, title));
	        }

	        request.getRequestDispatcher("/output.jsp").forward(request, response);
	    }
	    private Connection con = null;
		private Statement stmt = null;
		private ResultSet rs = null;
		public void Connect(){
			try{
				Class.forName("oracle.jdbc.driver.OracleDriver");
				con = DriverManager.getConnection("jdbc:oracle:thin:ora1/ora1@localhost:1521:orcl");
			}catch(SQLException e) {
				e.printStackTrace();
			}catch (ClassNotFoundException e) {
				e.printStackTrace();
			} finally {
			}
		}	
		public String findBook(String sku){
			String title="";
			String sql = "select title from books where sku='"+sku+"'";
			try{
				Connect();
				stmt = con.createStatement();
				rs = stmt.executeQuery(sql);
				while(rs.next()){
					title=rs.getString("title");
                 
					//System.out.println(rs.getString(2));
				}
			}catch (SQLException e) {
				e.printStackTrace();
			}
			finally {
				try {
					rs.close();
					stmt.close();
					con.close();
				}catch(SQLException e){
					e.printStackTrace();
				}
			}
			return title;
		}
}
