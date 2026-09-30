package com.hospital.Servlets;

import java.io.IOException;

import com.hospital.dao.PatientProfileDAO;
import com.hospital.model.PatientProfile;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/PatientProfileServlet")
public class PatientProfileServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        try {

            HttpSession session =
                    request.getSession(false);

            // Check login session
            if (session == null ||
                session.getAttribute("patientId") == null) {

                response.sendRedirect(
                        request.getContextPath()
                        + "/pages/login_page.html"
                );

                return;
            }

            // Get patient ID from session
            int patientId =
                    (Integer) session.getAttribute("patientId");

            System.out.println(
                    "Patient Profile - Patient ID: "
                    + patientId
            );

            // Get patient details
            PatientProfileDAO dao =
                    new PatientProfileDAO();

            PatientProfile patient =
                    dao.getPatientProfile(patientId);

            if (patient != null) {

                request.setAttribute(
                        "patient",
                        patient
                );

                request.getRequestDispatcher(
                        "/pages/patient_profile.jsp"
                ).forward(request, response);

            } else {

                response.setContentType(
                        "text/html;charset=UTF-8"
                );

                response.getWriter().println(
                        "<h3 style='color:red;"
                        + "text-align:center;'>"
                        + "Patient profile not found."
                        + "</h3>"
                );
            }

        } catch (Exception e) {

            e.printStackTrace();

            response.setContentType(
                    "text/html;charset=UTF-8"
            );

            response.getWriter().println(
                    "<h3 style='color:red;"
                    + "text-align:center;'>"
                    + "Error while loading patient profile."
                    + "</h3>"
            );
        }
    }
}