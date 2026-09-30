package com.hospital.Servlets;

import java.io.IOException;
import java.util.List;

import com.hospital.dao.DoctorPatientsDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/DoctorPatientsServlet")
public class DoctorPatientsServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session =
                request.getSession(false);

        // Check doctor login
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

        DoctorPatientsDAO dao =
                new DoctorPatientsDAO();

        List<String> patients =
                dao.getPatientsByDoctorId(doctorId);

        request.setAttribute(
                "patients",
                patients
        );

        request.getRequestDispatcher(
                "/pages/doctor_patients.jsp"
        ).forward(request, response);
    }
}