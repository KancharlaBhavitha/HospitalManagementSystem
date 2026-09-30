package com.hospital.Servlets;

import java.io.IOException;
import java.util.List;

import com.hospital.dao.DoctorDashboardDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/DoctorDashboardServlet")
public class DoctorDashboardServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

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

        Integer doctorId =
                (Integer) session.getAttribute("doctorId");

        DoctorDashboardDAO dao =
                new DoctorDashboardDAO();


        // Patient Count
        int patientCount =
                dao.getPatientCount(doctorId);

        request.setAttribute(
                "patientCount",
                patientCount
        );


        // Upcoming Appointments
        int upcomingAppointments =
                dao.getUpcomingAppointmentCount(doctorId);

        request.setAttribute(
                "upcomingAppointments",
                upcomingAppointments
        );


        // Today's Patients
        List<String> todaysPatients =
                dao.getTodaysPatients(doctorId);

        request.setAttribute(
                "todaysPatients",
                todaysPatients
        );


        // Open Doctor Dashboard
        request.getRequestDispatcher(
                "/pages/doctor_dashboard.jsp"
        ).forward(request, response);
    }
}