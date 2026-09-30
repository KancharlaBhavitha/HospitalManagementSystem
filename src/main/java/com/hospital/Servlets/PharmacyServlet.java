package com.hospital.Servlets;

import java.io.IOException;

import com.hospital.dao.PharmacyDAO;
import com.hospital.model.Pharmacy;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/PharmacyServlet")
public class PharmacyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        Pharmacy medicine = new Pharmacy();

        medicine.setMedicineName(request.getParameter("medicineName"));
        medicine.setCategory(request.getParameter("category"));
        medicine.setManufacturer(request.getParameter("manufacturer"));
        medicine.setQuantity(Integer.parseInt(request.getParameter("quantity")));
        medicine.setPrice(Double.parseDouble(request.getParameter("price")));
        medicine.setExpiryDate(request.getParameter("expiryDate"));

        PharmacyDAO dao = new PharmacyDAO();

        if(dao.addMedicine(medicine)){

            response.getWriter().println(
                "<h2>Medicine Added Successfully</h2>"
            );

        }else{

            response.getWriter().println(
                "<h2>Failed to Add Medicine</h2>"
            );

        }

    }

}