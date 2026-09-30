package com.hospital.Servlets;

import java.io.IOException;
import java.util.List;

import com.hospital.dao.DoctorMedicalRecordsDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/DoctorMedicalRecordsServlet")
public class DoctorMedicalRecordsServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        // Doctor login check
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

        DoctorMedicalRecordsDAO dao =
                new DoctorMedicalRecordsDAO();

        List<Object[]> records =
                dao.getMedicalRecordsByDoctorId(doctorId);

        request.setAttribute(
                "medicalRecords",
                records
        );

        request.getRequestDispatcher(
                "/pages/doctor_medical_records.jsp"
        ).forward(request, response);
    }
}