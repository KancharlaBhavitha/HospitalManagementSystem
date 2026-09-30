package com.hospital.Servlets;

import java.io.IOException;

import com.hospital.dao.RegisterDAO;
import com.hospital.model.Register;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        Register patient = new Register();

        patient.setFullName(request.getParameter("fullName"));
        patient.setAge(Integer.parseInt(request.getParameter("age")));
        patient.setGender(request.getParameter("gender"));
        patient.setBloodGroup(request.getParameter("bloodGroup"));
        patient.setPhoneNumber(request.getParameter("phoneNumber"));
        patient.setAddress(request.getParameter("address"));
        patient.setEmergencyNumber(request.getParameter("emergencyNumber"));
        patient.setMedicalHistory(request.getParameter("medicalHistory"));

        RegisterDAO dao = new RegisterDAO();

        if (dao.registerPatient(patient)) {
            response.sendRedirect("pages/dashboard.html");
        } else {
            response.getWriter().println("Patient Registration Failed");
        }
    }
}