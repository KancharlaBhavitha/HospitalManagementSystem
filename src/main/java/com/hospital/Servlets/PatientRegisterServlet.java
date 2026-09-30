package com.hospital.Servlets;

import java.io.IOException;

import com.hospital.dao.PatientRegisterDAO;
import com.hospital.model.PatientRegister;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/PatientRegisterServlet")
public class PatientRegisterServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String phoneNumber = request.getParameter("phoneNumber");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        String gender = request.getParameter("gender");
        String dateOfBirth = request.getParameter("dateOfBirth");
        String address = request.getParameter("address");

        // Check required fields
        if (fullName == null || fullName.trim().isEmpty() ||
            email == null || email.trim().isEmpty() ||
            phoneNumber == null || phoneNumber.trim().isEmpty() ||
            password == null || password.trim().isEmpty() ||
            confirmPassword == null || confirmPassword.trim().isEmpty() ||
            gender == null || gender.trim().isEmpty() ||
            dateOfBirth == null || dateOfBirth.trim().isEmpty() ||
            address == null || address.trim().isEmpty()) {

            response.setContentType("text/html");

            response.getWriter().println(
                "<h3 style='color:red;'>Please fill all required fields.</h3>"
            );

            response.getWriter().println(
                "<a href='" + request.getContextPath()
                + "/pages/patient_register_page.html'>"
                + "Back to Registration</a>"
            );

            return;
        }

        // Check password and confirm password
        if (!password.equals(confirmPassword)) {

            response.setContentType("text/html");

            response.getWriter().println(
                "<h3 style='color:red;'>"
                + "Password and Confirm Password do not match."
                + "</h3>"
            );

            response.getWriter().println(
                "<a href='" + request.getContextPath()
                + "/pages/patient_register_page.html'>"
                + "Back to Registration</a>"
            );

            return;
        }

        // Create Patient object
        PatientRegister patient = new PatientRegister();

        patient.setFullName(fullName.trim());
        patient.setEmail(email.trim());
        patient.setPhoneNumber(phoneNumber.trim());
        patient.setPassword(password);
        patient.setConfirmPassword(confirmPassword);
        patient.setGender(gender.trim());
        patient.setDateOfBirth(dateOfBirth.trim());
        patient.setAddress(address.trim());

        // DAO
        PatientRegisterDAO dao = new PatientRegisterDAO();

        // Register patient
        if (dao.registerPatient(patient)) {

            // Registration successful → Patient Dashboard
            response.sendRedirect(
                request.getContextPath()
                + "/pages/patient_dashboard.html"
            );

        } else {

            response.setContentType("text/html");

            response.getWriter().println(
                "<h3 style='color:red;'>Registration Failed.</h3>"
            );

            response.getWriter().println(
                "<a href='" + request.getContextPath()
                + "/pages/patient_register_page.html'>"
                + "Back to Registration</a>"
            );
        }
    }
}