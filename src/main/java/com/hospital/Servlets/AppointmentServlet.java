package com.hospital.Servlets;

import java.io.IOException;

import com.hospital.dao.AppointmentDAO;
import com.hospital.dao.BillingDAO;
import com.hospital.model.Appointment;
import com.hospital.model.Billing;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/AppointmentServlet")
public class AppointmentServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        try {

            // ==========================================
            // GET PATIENT SESSION
            // ==========================================

            HttpSession session =
                    request.getSession(false);

            if (session == null ||
                session.getAttribute("patientId") == null) {

                response.sendRedirect(
                        request.getContextPath()
                        + "/pages/login_page.html"
                );

                return;
            }


            // ==========================================
            // PATIENT ID
            // ==========================================

            int patientId =
                    (Integer) session.getAttribute(
                            "patientId"
                    );


            // ==========================================
            // GET FORM VALUES
            // ==========================================

            String doctorIdValue =
                    request.getParameter("doctorId");

            String appointmentDate =
                    request.getParameter("appointmentDate");

            String appointmentTime =
                    request.getParameter("appointmentTime");

            String reason =
                    request.getParameter("reason");


            // ==========================================
            // VALIDATION
            // ==========================================

            if (doctorIdValue == null ||
                doctorIdValue.trim().isEmpty() ||

                appointmentDate == null ||
                appointmentDate.trim().isEmpty() ||

                appointmentTime == null ||
                appointmentTime.trim().isEmpty() ||

                reason == null ||
                reason.trim().isEmpty()) {

                response.setContentType(
                        "text/html;charset=UTF-8"
                );

                response.getWriter().println(
                        "<h3 style='color:red;text-align:center;'>"
                        + "Please provide all appointment details."
                        + "</h3>"
                );

                return;
            }


            // ==========================================
            // CONVERT DOCTOR ID
            // ==========================================

            int doctorId =
                    Integer.parseInt(
                            doctorIdValue
                    );


            // ==========================================
            // CREATE APPOINTMENT OBJECT
            // ==========================================

            Appointment appointment =
                    new Appointment();

            appointment.setPatientId(
                    patientId
            );

            appointment.setDoctorId(
                    doctorId
            );

            appointment.setAppointmentDate(
                    appointmentDate
            );

            appointment.setAppointmentTime(
                    appointmentTime
            );

            appointment.setReason(
                    reason
            );

            appointment.setStatus(
                    "Pending"
            );


            // ==========================================
            // SAVE APPOINTMENT
            // ==========================================

            AppointmentDAO appointmentDAO =
                    new AppointmentDAO();

            int appointmentId =
                    appointmentDAO.bookAppointment(
                            appointment
                    );


            // ==========================================
            // APPOINTMENT SUCCESS
            // ==========================================

            if (appointmentId > 0) {

                // ======================================
                // GET DOCTOR CONSULTATION FEE
                // ======================================

                BillingDAO billingDAO =
                        new BillingDAO();

                double consultationFee =
                        billingDAO.getDoctorConsultationFee(
                                doctorId
                        );


                // ======================================
                // CREATE BILL AUTOMATICALLY
                // ======================================

                Billing billing =
                        new Billing();

                billing.setAppointmentId(
                        appointmentId
                );

                billing.setPatientId(
                        patientId
                );

                billing.setDoctorId(
                        doctorId
                );

                billing.setConsultationFee(
                        consultationFee
                );

                billing.setAdditionalCharges(
                        0
                );

                billing.setTotalAmount(
                        consultationFee
                );

                billing.setBillStatus(
                        "Pending"
                );


                // ======================================
                // SAVE BILL
                // ======================================

                int billId =
                        billingDAO.createBillAndGetId(
                                billing
                        );


                // ======================================
                // CHECK BILL CREATION
                // ======================================

                if (billId > 0) {

                    // Store details in session

                    session.setAttribute(
                            "appointmentId",
                            appointmentId
                    );

                    session.setAttribute(
                            "appointmentDoctorId",
                            doctorId
                    );

                    session.setAttribute(
                            "billId",
                            billId
                    );


                    // ==================================
                    // SUCCESS MESSAGE
                    // ==================================

                    response.setContentType(
                            "text/html;charset=UTF-8"
                    );

                    response.getWriter().println(
                            "<html>"
                            + "<head>"
                            + "<title>Appointment Successful</title>"
                            + "</head>"
                            + "<body style='font-family:Arial;text-align:center;"
                            + "background:#f2f2f2;padding-top:100px;'>"

                            + "<div style='background:white;width:450px;"
                            + "margin:auto;padding:30px;border-radius:10px;"
                            + "box-shadow:0 0 10px gray;'>"

                            + "<h2 style='color:green;'>"
                            + "Appointment Booked Successfully!"
                            + "</h2>"

                            + "<p>Your bill has been generated automatically.</p>"

                            + "<p><strong>Bill ID:</strong> "
                            + billId
                            + "</p>"

                            + "<p><strong>Consultation Fee:</strong> ₹"
                            + consultationFee
                            + "</p>"

                            + "<p><strong>Bill Status:</strong> "
                            + "<span style='color:orange;'>Pending</span>"
                            + "</p>"

                            + "<br>"

                            + "<a href='"
                            + request.getContextPath()
                            + "/BillingListServlet'"
                            + " style='display:inline-block;"
                            + "padding:12px 20px;background:darkblue;"
                            + "color:white;text-decoration:none;"
                            + "border-radius:5px;'>"
                            + "View My Bill"
                            + "</a>"

                            + "</div>"
                            + "</body>"
                            + "</html>"
                    );

                } else {

                    response.setContentType(
                            "text/html;charset=UTF-8"
                    );

                    response.getWriter().println(
                            "<h3 style='color:red;text-align:center;'>"
                            + "Appointment booked, but bill creation failed."
                            + "</h3>"
                    );
                }


            } else {

                response.setContentType(
                        "text/html;charset=UTF-8"
                );

                response.getWriter().println(
                        "<h3 style='color:red;text-align:center;'>"
                        + "Appointment booking failed."
                        + "</h3>"
                );
            }


        } catch (NumberFormatException e) {

            e.printStackTrace();

            response.setContentType(
                    "text/html;charset=UTF-8"
            );

            response.getWriter().println(
                    "<h3 style='color:red;text-align:center;'>"
                    + "Invalid Doctor ID."
                    + "</h3>"
            );


        } catch (Exception e) {

            e.printStackTrace();

            response.setContentType(
                    "text/html;charset=UTF-8"
            );

            response.getWriter().println(
                    "<h3 style='color:red;text-align:center;'>"
                    + "Error while booking appointment."
                    + "</h3>"
            );
        }
    }
}