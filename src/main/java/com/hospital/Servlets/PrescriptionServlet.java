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

@WebServlet("/PrescriptionServlet")
public class PrescriptionServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doPost(
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


        try {

            // Doctor ID from session
            int doctorId =
                    (Integer) session.getAttribute("doctorId");


            // Appointment ID
            int appointmentId =
                    Integer.parseInt(
                            request.getParameter(
                                    "appointmentId"
                            )
                    );


            // Patient ID
            int patientId =
                    Integer.parseInt(
                            request.getParameter(
                                    "patientId"
                            )
                    );


            // Prescription details
            String medicineName =
                    request.getParameter(
                            "medicineName"
                    );

            String dosage =
                    request.getParameter(
                            "dosage"
                    );

            String duration =
                    request.getParameter(
                            "duration"
                    );

            String instructions =
                    request.getParameter(
                            "instructions"
                    );


            // Validation
            if (medicineName == null ||
                medicineName.trim().isEmpty()) {

                response.getWriter().println(
                        "<h3>Medicine name is required.</h3>"
                );

                return;
            }


            // Create Prescription object
            Prescription prescription =
                    new Prescription();


            prescription.setAppointmentId(
                    appointmentId
            );

            prescription.setPatientId(
                    patientId
            );

            prescription.setDoctorId(
                    doctorId
            );

            prescription.setMedicineName(
                    medicineName
            );

            prescription.setDosage(
                    dosage
            );

            prescription.setDuration(
                    duration
            );

            prescription.setInstructions(
                    instructions
            );


            // DAO
            PrescriptionDAO dao =
                    new PrescriptionDAO();


            boolean result =
                    dao.savePrescription(
                            prescription
                    );


            if (result) {

                response.sendRedirect(
                        request.getContextPath()
                        + "/AppointmentListServlet"
                );

            } else {

                response.getWriter().println(
                        "<h3>"
                        + "Failed to save prescription."
                        + "</h3>"
                );
            }


        } catch (Exception e) {

            e.printStackTrace();

            response.getWriter().println(
                    "<h3>"
                    + "Error while saving prescription."
                    + "</h3>"
            );
        }
    }
}