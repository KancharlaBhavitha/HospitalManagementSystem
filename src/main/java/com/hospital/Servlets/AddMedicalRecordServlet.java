package com.hospital.Servlets;

import java.io.IOException;

import com.hospital.dao.MedicalRecordDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/AddMedicalRecordServlet")
public class AddMedicalRecordServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

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

        try {

            int patientId =
                Integer.parseInt(
                    request.getParameter("patientId")
                );

            int appointmentId =
                Integer.parseInt(
                    request.getParameter("appointmentId")
                );

            String diagnosis =
                request.getParameter("diagnosis");

            String doctorNotes =
                request.getParameter("doctorNotes");

            String prescription =
                request.getParameter("prescription");

            MedicalRecordDAO dao =
                new MedicalRecordDAO();

            boolean status =
                dao.addMedicalRecord(
                    patientId,
                    doctorId,
                    appointmentId,
                    diagnosis,
                    doctorNotes,
                    prescription
                );

            if (status) {

                response.sendRedirect(
                    request.getContextPath()
                    + "/DoctorMedicalRecordsServlet"
                );

            } else {

                response.getWriter().println(
                    "Failed to save medical record."
                );
            }

        } catch (Exception e) {

            e.printStackTrace();

            response.getWriter().println(
                "Error while saving medical record."
            );
        }
    }
}