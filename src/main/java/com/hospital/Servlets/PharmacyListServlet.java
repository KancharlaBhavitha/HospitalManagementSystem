package com.hospital.Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import com.hospital.dao.PharmacyListDAO;
import com.hospital.model.PharmacyList;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/PharmacyListServlet")
public class PharmacyListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        PharmacyListDAO dao = new PharmacyListDAO();

        ArrayList<PharmacyList> list = dao.getAllMedicines();

        response.setContentType("text/html");

        PrintWriter out = response.getWriter();

        out.println("<html><body>");
        out.println("<h2 align='center'>Medicine List</h2>");

        out.println("<table border='1' cellpadding='10' align='center'>");

        out.println("<tr>");
        out.println("<th>ID</th>");
        out.println("<th>Medicine Name</th>");
        out.println("<th>Category</th>");
        out.println("<th>Manufacturer</th>");
        out.println("<th>Quantity</th>");
        out.println("<th>Price</th>");
        out.println("<th>Expiry Date</th>");
        out.println("</tr>");

        for(PharmacyList medicine : list){

            out.println("<tr>");

            out.println("<td>"+medicine.getMedicineId()+"</td>");
            out.println("<td>"+medicine.getMedicineName()+"</td>");
            out.println("<td>"+medicine.getCategory()+"</td>");
            out.println("<td>"+medicine.getManufacturer()+"</td>");
            out.println("<td>"+medicine.getQuantity()+"</td>");
            out.println("<td>"+medicine.getPrice()+"</td>");
            out.println("<td>"+medicine.getExpiryDate()+"</td>");

            out.println("</tr>");

        }

        out.println("</table>");

        out.println("</body></html>");
    }

}