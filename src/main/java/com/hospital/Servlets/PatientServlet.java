package com.hospital.Servlets;

import java.io.IOException;

import com.hospital.dao.PatientDAO;
import com.hospital.model.Patient;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/PatientServlet")
public class PatientServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String fullName =
                request.getParameter("fullName");

        String email =
                request.getParameter("email");

        String phoneNumber =
                request.getParameter("phoneNumber");

        String password =
                request.getParameter("password");

        String confirmPassword =
                request.getParameter("confirmPassword");

        String gender =
                request.getParameter("gender");

        String dateOfBirth =
                request.getParameter("dateOfBirth");

        String address =
                request.getParameter("address");


        // -----------------------------------------
        // Required fields
        // -----------------------------------------

        if (fullName == null || fullName.trim().isEmpty()
                || email == null || email.trim().isEmpty()
                || phoneNumber == null || phoneNumber.trim().isEmpty()
                || password == null || password.trim().isEmpty()
                || confirmPassword == null || confirmPassword.trim().isEmpty()) {

            response.sendRedirect(
                    request.getContextPath()
                    + "/pages/patient_registration.html"
            );

            return;
        }


        // -----------------------------------------
        // Password check
        // -----------------------------------------

        if (!password.equals(confirmPassword)) {

            response.setContentType("text/html;charset=UTF-8");

            response.getWriter().println(
                    "<h3 style='color:red;text-align:center;'>"
                    + "Passwords do not match!"
                    + "</h3>"
            );

            response.getWriter().println(
                    "<p style='text-align:center;'>"
                    + "<a href='"
                    + request.getContextPath()
                    + "/pages/patient_registration.html'>"
                    + "Back to Registration"
                    + "</a>"
                    + "</p>"
            );

            return;
        }


        // -----------------------------------------
        // Create Patient object
        // -----------------------------------------

        Patient patient =
                new Patient();

        patient.setFullName(fullName);
        patient.setEmail(email);
        patient.setPhoneNumber(phoneNumber);
        patient.setPassword(password);
        patient.setGender(gender);
        patient.setDateOfBirth(dateOfBirth);
        patient.setAddress(address);


        // -----------------------------------------
        // DAO
        // -----------------------------------------

        PatientDAO dao =
                new PatientDAO();

        boolean status =
                dao.registerPatient(patient);


        // -----------------------------------------
        // Success
        // -----------------------------------------

        if (status) {

            response.setContentType("text/html;charset=UTF-8");

            response.getWriter().println(
                    "<h2 style='color:green;text-align:center;'>"
                    + "Patient Registration Successful!"
                    + "</h2>"
            );

            response.getWriter().println(
                    "<p style='text-align:center;'>"
                    + "Your account has been created successfully."
                    + "</p>"
            );

            response.getWriter().println(
                    "<p style='text-align:center;'>"
                    + "<a href='"
                    + request.getContextPath()
                    + "/pages/login_page.html'>"
                    + "Go to Login"
                    + "</a>"
                    + "</p>"
            );

        } else {

            response.setContentType("text/html;charset=UTF-8");

            response.getWriter().println(
                    "<h3 style='color:red;text-align:center;'>"
                    + "Registration failed!"
                    + "</h3>"
            );

            response.getWriter().println(
                    "<p style='text-align:center;'>"
                    + "Email may already be registered."
                    + "</p>"
            );

            response.getWriter().println(
                    "<p style='text-align:center;'>"
                    + "<a href='"
                    + request.getContextPath()
                    + "/pages/patient_registration.html'>"
                    + "Back to Registration"
                    + "</a>"
                    + "</p>"
            );
        }
    }


    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        response.sendRedirect(
                request.getContextPath()
                + "/pages/patient_registration.html"
        );
    }
}