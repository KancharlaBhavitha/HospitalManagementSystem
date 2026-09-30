package com.hospital.Servlets;

import java.io.IOException;

import com.hospital.dao.DoctorDAO;
import com.hospital.model.Doctor;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/DoctorServlet")
public class DoctorServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request,
                           HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("========== DoctorServlet Called ==========");

        String fullName = request.getParameter("fullName");
        String specialization = request.getParameter("specialization");
        String disease = request.getParameter("disease");
        String phoneNumber = request.getParameter("phoneNumber");
        String email = request.getParameter("email");
        String experience = request.getParameter("experience");
        String consultationFee = request.getParameter("consultationFee");

        if (fullName == null || fullName.trim().isEmpty()
                || specialization == null || specialization.trim().isEmpty()
                || disease == null || disease.trim().isEmpty()
                || phoneNumber == null || phoneNumber.trim().isEmpty()
                || email == null || email.trim().isEmpty()
                || experience == null || experience.trim().isEmpty()
                || consultationFee == null || consultationFee.trim().isEmpty()) {

            response.setContentType("text/html");

            response.getWriter().println(
                "<h3 style='color:red;'>Please fill all fields.</h3>"
            );

            response.getWriter().println(
                "<a href='" +
                request.getContextPath() +
                "/pages/doctor_registration_page.html'>" +
                "Back to Registration</a>"
            );

            return;
        }

        Doctor doctor = new Doctor();

        doctor.setFullName(fullName);
        doctor.setSpecialization(specialization);
        doctor.setDisease(disease);
        doctor.setPhoneNumber(phoneNumber);
        doctor.setEmail(email);

        try {

            doctor.setExperience(
                Integer.parseInt(experience)
            );

        } catch (NumberFormatException e) {

            response.setContentType("text/html");

            response.getWriter().println(
                "<h3 style='color:red;'>Experience must be a number.</h3>"
            );

            return;
        }

        try {

            doctor.setConsultationFee(
                Double.parseDouble(consultationFee)
            );

        } catch (NumberFormatException e) {

            response.setContentType("text/html");

            response.getWriter().println(
                "<h3 style='color:red;'>Consultation Fee must be a number.</h3>"
            );

            return;
        }

        DoctorDAO dao = new DoctorDAO();

        boolean status = dao.registerDoctor(doctor);

        if (status) {

            System.out.println(
                "========== DOCTOR REGISTRATION SUCCESS =========="
            );

            response.sendRedirect(
                request.getContextPath()
                + "/pages/doctor_dashboard.html"
            );

        } else {

            System.out.println(
                "========== DOCTOR REGISTRATION FAILED =========="
            );

            response.setContentType("text/html");

            response.getWriter().println(
                "<h3 style='color:red;'>Doctor Registration Failed</h3>"
            );

            response.getWriter().println(
                "<a href='" +
                request.getContextPath() +
                "/pages/doctor_registration_page.html'>" +
                "Back to Registration</a>"
            );
        }
    }
}