package com.hospital.Servlets;

import java.io.IOException;

import com.hospital.dao.PrescriptionDAO;
import com.hospital.model.Prescription;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/ViewPrescriptionServlet")
public class ViewPrescriptionServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        // Check doctor login
        HttpSession session =
                request.getSession(false);

        if (session == null ||
            session.getAttribute("doctorId") == null) {

            response.sendRedirect(
                    request.getContextPath()
                    + "/pages/login_page.html"
            );

            return;
        }

        try {

            // Get appointment ID
            String appointmentIdParam =
                    request.getParameter("appointmentId");

            if (appointmentIdParam == null ||
                appointmentIdParam.trim().isEmpty()) {

                response.getWriter().println(
                        "<h3>Appointment ID is missing.</h3>"
                );

                return;
            }

            int appointmentId =
                    Integer.parseInt(
                            appointmentIdParam
                    );

            // Get prescription from database
            PrescriptionDAO dao =
                    new PrescriptionDAO();

            Prescription prescription =
                    dao.getPrescriptionByAppointmentId(
                            appointmentId
                    );

            // Check prescription exists
            if (prescription == null) {

                response.getWriter().println(
                        "<h3>"
                        + "No prescription found for this appointment."
                        + "</h3>"
                );

                return;
            }

            // Send prescription to JSP
            request.setAttribute(
                    "prescription",
                    prescription
            );

            // Open View Prescription page
            request.getRequestDispatcher(
                    "/pages/view_prescription.jsp"
            ).forward(
                    request,
                    response
            );

        } catch (Exception e) {

            e.printStackTrace();

            response.getWriter().println(
                    "<h3>"
                    + "Error while loading prescription."
                    + "</h3>"
            );
        }
    }
}