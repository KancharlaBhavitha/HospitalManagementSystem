package com.hospital.Servlets;

import java.io.IOException;
import java.time.LocalDate;

import com.hospital.dao.PaymentDAO;
import com.hospital.model.Payment;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/PaymentServlet")
public class PaymentServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        try {

            // -----------------------------------------
            // Check Patient Session
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
            // Get Patient ID
            // -----------------------------------------

            int patientId =
                    (Integer) session.getAttribute(
                            "patientId"
                    );

            // -----------------------------------------
            // Get Bill ID
            // -----------------------------------------

            String billIdValue =
                    request.getParameter("billId");

            // -----------------------------------------
            // Get Bill Amount
            // -----------------------------------------

            String billAmountValue =
                    request.getParameter("billAmount");

            // -----------------------------------------
            // Get Payment Method
            // -----------------------------------------

            String paymentMethod =
                    request.getParameter(
                            "paymentMethod"
                    );

            if (billIdValue == null ||
                billIdValue.trim().isEmpty() ||

                billAmountValue == null ||
                billAmountValue.trim().isEmpty()) {

                response.setContentType(
                        "text/html;charset=UTF-8"
                );

                response.getWriter().println(
                        "<h3 style='color:red;"
                        + "text-align:center;'>"
                        + "Bill details are missing."
                        + "</h3>"
                );

                return;
            }

            if (paymentMethod == null ||
                paymentMethod.trim().isEmpty()) {

                response.setContentType(
                        "text/html;charset=UTF-8"
                );

                response.getWriter().println(
                        "<h3 style='color:red;"
                        + "text-align:center;'>"
                        + "Please select a payment method."
                        + "</h3>"
                );

                return;
            }

            // -----------------------------------------
            // Convert Values
            // -----------------------------------------

            int billId =
                    Integer.parseInt(billIdValue);

            double billAmount =
                    Double.parseDouble(billAmountValue);

            // -----------------------------------------
            // Payment Date
            // -----------------------------------------

            String paymentDate =
                    LocalDate.now().toString();

            // -----------------------------------------
            // Create Payment Object
            // -----------------------------------------

            Payment payment =
                    new Payment();

            payment.setBillId(
                    billId
            );

            payment.setPatientId(
                    patientId
            );

            payment.setBillAmount(
                    billAmount
            );

            payment.setPaymentMethod(
                    paymentMethod
            );

            payment.setPaymentDate(
                    paymentDate
            );

            payment.setPaymentStatus(
                    "Paid"
            );

            // -----------------------------------------
            // Make Payment
            // -----------------------------------------

            PaymentDAO dao =
                    new PaymentDAO();

            boolean status =
                    dao.makePayment(payment);

            // -----------------------------------------
            // Payment Successful
            // Directly Open Invoice
            // -----------------------------------------

            if (status) {

                response.sendRedirect(
                        request.getContextPath()
                        + "/InvoiceServlet?billNumber="
                        + billId
                );

            } else {

                response.setContentType(
                        "text/html;charset=UTF-8"
                );

                response.getWriter().println(
                        "<h3 style='color:red;"
                        + "text-align:center;'>"
                        + "Payment Failed."
                        + "</h3>"
                );
            }

        } catch (NumberFormatException e) {

            e.printStackTrace();

            response.setContentType(
                    "text/html;charset=UTF-8"
            );

            response.getWriter().println(
                    "<h3 style='color:red;"
                    + "text-align:center;'>"
                    + "Invalid Bill ID or Amount."
                    + "</h3>"
            );

        } catch (Exception e) {

            e.printStackTrace();

            response.setContentType(
                    "text/html;charset=UTF-8"
            );

            response.getWriter().println(
                    "<h3 style='color:red;"
                    + "text-align:center;'>"
                    + "Error while processing payment."
                    + "</h3>"
            );
        }
    }
}