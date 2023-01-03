package com.managment;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.jdi.connect.spi.Connection;

public class RegisterServlet extends HttpServlet{

	public void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException {
		
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		
		//fetch the data
		String name=request.getParameter("username");
		String mail=request.getParameter("usermail");
		String password=request.getParameter("password");	
		//print the data
		//out.println("<h2> Name:"+name+"</h2>");
		//out.println("<h2> Mail:"+mail+"</h2>");
		//out.println("<h2> Password:"+password+"</h2>");
		
		//add menu bar.....

		
		//create a connection
		try {
			//load the driver
			Class.forName("com.mysql.jdbc.Driver");
			
			//create the connection
			String url="jdbc:mysql://localhost:3307/hotel";
			String username="root";
			String password1="2000";
			java.sql.Connection con=DriverManager.getConnection(url,username,password1);
			
			PreparedStatement ps=con.prepareStatement("select Mail from userdata");
			ResultSet r=ps.executeQuery();
			r.next();
			
			if (mail.equals(r.getString(1))) {
				out.println("<h2>you have already register</h2>");
				
			}
			else {
				//write a query
				String query="insert into userdata(name,mail,password) values (?,?,?)";
				PreparedStatement ps1=con.prepareStatement(query);

				ps1.setString(1, name);
				ps1.setString(2, mail);
				ps1.setString(3, password);
			
			   int i =  ps1.executeUpdate();
				if(i==1) {
					out.println("you are a new user");
					RequestDispatcher rd=request.getRequestDispatcher("menu.jsp");	
					rd.include(request, response);
				}
				con.close();
			}
			
			
		}
		
		catch (Exception e) {
			System.out.println(e);
		}
	}
}
