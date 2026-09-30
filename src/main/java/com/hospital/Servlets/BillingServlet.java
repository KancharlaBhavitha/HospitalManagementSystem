package com.hospital.Servlets;

import java.io.IOException;

import com.hospital.dao.BillingDAO;
import com.hospital.model.Billing;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/BillingServlet")
public class BillingServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        try {

            // -----------------------------------------
            // Get Patient Session
            // -----------------------------------------

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


            // -----------------------------------------
            // Patient ID
            // -----------------------------------------

            int patientId =
                    (Integer) session.getAttribute(
                            "patientId"
                    );


            // -----------------------------------------
            // Appointment ID
            // -----------------------------------------

            int appointmentId =
                    Integer.parseInt(
                            request.getParameter(
                                    "appointmentId"
                            )
                    );


            // -----------------------------------------
            // Doctor ID
            // -----------------------------------------

            int doctorId =
                    Integer.parseInt(
                            request.getParameter(
                                    "doctorId"
                            )
                    );


            // -----------------------------------------
            // Additional Charges
            // -----------------------------------------

            String additionalChargesValue =
                    request.getParameter(
                            "additionalCharges"
                    );

            double additionalCharges = 0;

            if (additionalChargesValue != null &&
                !additionalChargesValue.trim().isEmpty()) {

                additionalCharges =
                        Double.parseDouble(
                                additionalChargesValue
                        );
            }


            // -----------------------------------------
            // Get Consultation Fee
            // -----------------------------------------

            BillingDAO dao =
                    new BillingDAO();

            double consultationFee =
                    dao.getDoctorConsultationFee(
                            doctorId
                    );


            // -----------------------------------------
            // Calculate Total
            // -----------------------------------------

            double totalAmount =
                    consultationFee
                    + additionalCharges;


            // -----------------------------------------
            // Create Billing Object
            // -----------------------------------------

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
                    additionalCharges
            );

            billing.setTotalAmount(
                    totalAmount
            );

            billing.setBillStatus(
                    "Pending"
            );


            // -----------------------------------------
            // Save Bill and Get Bill ID
            // -----------------------------------------

            int billId =
                    dao.createBillAndGetId(
                            billing
                    );


            // -----------------------------------------
            // Open Payment Page Automatically
            // -----------------------------------------

            if (billId > 0) {

                response.sendRedirect(
                        request.getContextPath()
                        + "/pages/payment.html"
                        + "?billId=" + billId
                        + "&amount=" + totalAmount
                );

            } else {

                response.setContentType(
                        "text/html;charset=UTF-8"
                );

                response.getWriter().println(
                        "<h3 style='color:red;text-align:center;'>"
                        + "Billing creation failed."
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
                    + "Invalid appointment or doctor ID."
                    + "</h3>"
            );


        } catch (Exception e) {

            e.printStackTrace();

            response.setContentType(
                    "text/html;charset=UTF-8"
            );

            response.getWriter().println(
                    "<h3 style='color:red;text-align:center;'>"
                    + "Error while creating bill."
                    + "</h3>"
            );
        }
    }
}