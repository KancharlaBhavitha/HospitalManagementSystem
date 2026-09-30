package com.hospital.Servlets;

import java.io.IOException;

import com.hospital.dao.LoginDAO;
import com.hospital.model.Login;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        try {

            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String role = request.getParameter("role");

            if (email == null || email.trim().isEmpty()
                    || password == null || password.trim().isEmpty()
                    || role == null || role.trim().isEmpty()) {

                response.setContentType("text/html;charset=UTF-8");

                response.getWriter().println(
                        "<h3 style='color:red;text-align:center;'>"
                        + "Please enter Email, Password and Role."
                        + "</h3>"
                );

                return;
            }

            email = email.trim();
            role = role.trim();

            Login login = new Login();

            login.setEmail(email);
            login.setPassword(password);
            login.setRole(role);

            LoginDAO dao = new LoginDAO();

            boolean valid = dao.validate(login);

            if (!valid) {

                response.setContentType("text/html;charset=UTF-8");

                response.getWriter().println(
                        "<h3 style='color:red;text-align:center;'>"
                        + "Invalid Email, Password or Role."
                        + "</h3>"
                );

                return;
            }

            HttpSession session = request.getSession(true);

            session.setAttribute("loginId", email);
            session.setAttribute("email", email);
            session.setAttribute("role", role);


            // =========================
            // PATIENT LOGIN
            // =========================

            if (role.equalsIgnoreCase("PATIENT")) {

                int patientId = dao.getPatientIdByEmail(email);

                if (patientId <= 0) {

                    response.setContentType("text/html;charset=UTF-8");

                    response.getWriter().println(
                            "<h3 style='color:red;text-align:center;'>"
                            + "Patient profile not found."
                            + "</h3>"
                    );

                    return;
                }

                session.setAttribute("patientId", patientId);

                System.out.println("Patient Login Successful");
                System.out.println("Patient ID: " + patientId);

                response.sendRedirect(
                        request.getContextPath()
                        + "/pages/patient_dashboard.html"
                );

                return;
            }


            // =========================
            // DOCTOR LOGIN
            // =========================

            if (role.equalsIgnoreCase("DOCTOR")) {

                int doctorId = dao.getDoctorIdByEmail(email);

                if (doctorId <= 0) {

                    response.setContentType("text/html;charset=UTF-8");

                    response.getWriter().println(
                            "<h3 style='color:red;text-align:center;'>"
                            + "Doctor profile not found."
                            + "</h3>"
                    );

                    return;
                }

                session.setAttribute("doctorId", doctorId);

                System.out.println("Doctor Login Successful");
                System.out.println("Doctor ID: " + doctorId);

                // Doctor Dashboard Servlet will load patient count
                response.sendRedirect(
                        request.getContextPath()
                        + "/DoctorDashboardServlet"
                );

                return;
            }


            // =========================
            // ADMIN LOGIN
            // =========================

            if (role.equalsIgnoreCase("ADMIN")) {

                System.out.println("Admin Login Successful");

                // AdminServlet will load dashboard data
                response.sendRedirect(
                        request.getContextPath()
                        + "/AdminServlet"
                );

                return;
            }


            // =========================
            // INVALID ROLE
            // =========================

            response.setContentType("text/html;charset=UTF-8");

            response.getWriter().println(
                    "<h3 style='color:red;text-align:center;'>"
                    + "Invalid Role."
                    + "</h3>"
            );

        } catch (Exception e) {

            e.printStackTrace();

            response.setContentType("text/html;charset=UTF-8");

            response.getWriter().println(
                    "<h3 style='color:red;text-align:center;'>"
                    + "Error while processing login."
                    + "</h3>"
            );
        }
    }
}