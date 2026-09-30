package com.hospital.Servlets;

import java.io.IOException;

import com.hospital.dao.DoctorProfileDAO;
import com.hospital.model.Doctor;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/DoctorProfileServlet")
public class DoctorProfileServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session =
                request.getSession(false);

        // Doctor login check
        if (session == null ||
            session.getAttribute("doctorId") == null) {

            response.sendRedirect(
                request.getContextPath()
                + "/pages/login_page.html"
            );

            return;
        }

        // Get logged-in doctor ID
        Integer doctorId =
                (Integer) session.getAttribute("doctorId");

        DoctorProfileDAO dao =
                new DoctorProfileDAO();

        Doctor doctor =
                dao.getDoctorById(doctorId);

        // Profile not found
        if (doctor == null) {

            response.setContentType(
                "text/html;charset=UTF-8"
            );

            response.getWriter().println(
                "<h3 style='color:red;text-align:center;'>"
                + "Doctor profile not found."
                + "</h3>"
            );

            return;
        }

        // Send doctor object to JSP
        request.setAttribute(
            "doctor",
            doctor
        );

        // Open profile page
        request.getRequestDispatcher(
            "/pages/doctor_profile.jsp"
        ).forward(request, response);
    }
}