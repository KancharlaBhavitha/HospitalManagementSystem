package com.hospital.Servlets;

import java.io.IOException;
import java.io.PrintWriter;

import com.hospital.dao.InvoiceDAO;
import com.hospital.model.Invoice;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/InvoiceServlet")
public class InvoiceServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        // Check patient session
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

        // Get patient ID from session
        int patientId =
                (Integer) session.getAttribute(
                        "patientId"
                );

        // Get bill number
        String billParam =
                request.getParameter("billNumber");

        if (billParam == null ||
            billParam.trim().isEmpty()) {

            response.setContentType(
                    "text/html;charset=UTF-8"
            );

            PrintWriter out =
                    response.getWriter();

            out.println(
                    "<h3 style='color:red;"
                    + "text-align:center;'>"
                    + "Bill Number is required."
                    + "</h3>"
            );

            return;
        }

        try {

            int billNumber =
                    Integer.parseInt(billParam);

            // Get invoice details
            InvoiceDAO dao =
                    new InvoiceDAO();

            Invoice invoice =
                    dao.getInvoiceByBillNumber(
                            billNumber,
                            patientId
                    );

            response.setContentType(
                    "text/html;charset=UTF-8"
            );

            PrintWriter out =
                    response.getWriter();

            // Invoice not found
            if (invoice == null) {

                out.println(
                        "<html>"
                        + "<head>"
                        + "<title>Invoice Not Found</title>"
                        + "</head>"
                        + "<body>"
                        + "<h2 style='color:red;"
                        + "text-align:center;"
                        + "margin-top:100px;'>"
                        + "Invoice not found."
                        + "</h2>"
                        + "</body>"
                        + "</html>"
                );

                return;
            }

            // =========================
            // HTML START
            // =========================

            out.println("<!DOCTYPE html>");
            out.println("<html>");

            out.println("<head>");

            out.println(
                    "<meta charset='UTF-8'>"
            );

            out.println(
                    "<title>Hospital Invoice</title>"
            );

            // =========================
            // CSS
            // =========================

            out.println("<style>");

            out.println(
                    "body{"
                    + "font-family:Arial,sans-serif;"
                    + "background:#f2f2f2;"
                    + "margin:0;"
                    + "padding:30px;"
                    + "}"
            );

            out.println(
                    ".invoice{"
                    + "width:700px;"
                    + "margin:auto;"
                    + "background:white;"
                    + "padding:30px;"
                    + "border-radius:10px;"
                    + "box-shadow:0 0 10px gray;"
                    + "}"
            );

            out.println(
                    ".header{"
                    + "text-align:center;"
                    + "border-bottom:2px solid darkblue;"
                    + "padding-bottom:15px;"
                    + "}"
            );

            out.println(
                    ".header h1{"
                    + "color:darkblue;"
                    + "margin:0;"
                    + "}"
            );

            out.println(
                    ".header h3{"
                    + "color:gray;"
                    + "margin-top:8px;"
                    + "}"
            );

            out.println(
                    "table{"
                    + "width:100%;"
                    + "border-collapse:collapse;"
                    + "margin-top:25px;"
                    + "}"
            );

            out.println(
                    "td{"
                    + "padding:12px;"
                    + "border:1px solid #ddd;"
                    + "}"
            );

            out.println(
                    "td:first-child{"
                    + "font-weight:bold;"
                    + "width:40%;"
                    + "background:#f5f8ff;"
                    + "}"
            );

            out.println(
                    ".total{"
                    + "font-size:20px;"
                    + "font-weight:bold;"
                    + "}"
            );

            out.println(
                    ".paid{"
                    + "color:green;"
                    + "font-weight:bold;"
                    + "}"
            );

            // Buttons section
            out.println(
                    ".buttons{"
                    + "text-align:center;"
                    + "margin-top:25px;"
                    + "}"
            );

            // Print button
            out.println(
                    ".print-btn{"
                    + "padding:12px 25px;"
                    + "background:green;"
                    + "color:white;"
                    + "border:none;"
                    + "border-radius:5px;"
                    + "font-size:16px;"
                    + "cursor:pointer;"
                    + "margin:5px;"
                    + "}"
            );

            out.println(
                    ".print-btn:hover{"
                    + "background:darkgreen;"
                    + "}"
            );

            // Logout button
            out.println(
                    ".logout-btn{"
                    + "display:inline-block;"
                    + "padding:12px 30px;"
                    + "background:red;"
                    + "color:white;"
                    + "text-decoration:none;"
                    + "border-radius:5px;"
                    + "font-size:16px;"
                    + "margin:5px;"
                    + "}"
            );

            out.println(
                    ".logout-btn:hover{"
                    + "background:darkred;"
                    + "}"
            );

            // =========================
            // PRINT CSS
            // =========================

            out.println(
                    "@media print{"
                    + ".buttons{display:none;}"
                    + "body{"
                    + "background:white;"
                    + "padding:0;"
                    + "}"
                    + ".invoice{"
                    + "width:100%;"
                    + "box-shadow:none;"
                    + "}"
                    + "}"
            );

            out.println("</style>");

            out.println("</head>");

            // =========================
            // BODY
            // =========================

            out.println("<body>");

            out.println("<div class='invoice'>");

            // Header
            out.println("<div class='header'>");

            out.println(
                    "<h1>HOSPITAL INVOICE</h1>"
            );

            out.println(
                    "<h3>Hospital Management System</h3>"
            );

            out.println("</div>");

            // =========================
            // INVOICE DETAILS
            // =========================

            out.println("<table>");

            out.println(
                    "<tr>"
                    + "<td>Invoice ID</td>"
                    + "<td>"
                    + invoice.getInvoiceId()
                    + "</td>"
                    + "</tr>"
            );

            out.println(
                    "<tr>"
                    + "<td>Bill Number</td>"
                    + "<td>"
                    + invoice.getBillNumber()
                    + "</td>"
                    + "</tr>"
            );

            out.println(
                    "<tr>"
                    + "<td>Appointment ID</td>"
                    + "<td>"
                    + invoice.getAppointmentId()
                    + "</td>"
                    + "</tr>"
            );

            out.println(
                    "<tr>"
                    + "<td>Patient Name</td>"
                    + "<td>"
                    + invoice.getPatientName()
                    + "</td>"
                    + "</tr>"
            );

            out.println(
                    "<tr>"
                    + "<td>Doctor Name</td>"
                    + "<td>"
                    + invoice.getDoctorName()
                    + "</td>"
                    + "</tr>"
            );

            out.println(
                    "<tr>"
                    + "<td>Consultation Fee</td>"
                    + "<td>"
                    + "&#8377;"
                    + invoice.getConsultationFee()
                    + "</td>"
                    + "</tr>"
            );

            // =====================================================
            // MEDICINE COST / ADDITIONAL CHARGES
            // =====================================================

            if (invoice.getConsultationFee() == 0) {

                out.println(
                        "<tr>"
                        + "<td>Medicine Cost</td>"
                        + "<td>"
                        + "&#8377;"
                        + invoice.getAdditionalCharges()
                        + "</td>"
                        + "</tr>"
                );

            } else {

                out.println(
                        "<tr>"
                        + "<td>Additional Charges</td>"
                        + "<td>"
                        + "&#8377;"
                        + invoice.getAdditionalCharges()
                        + "</td>"
                        + "</tr>"
                );
            }

            out.println(
                    "<tr class='total'>"
                    + "<td>Total Amount</td>"
                    + "<td>"
                    + "&#8377;"
                    + invoice.getTotalAmount()
                    + "</td>"
                    + "</tr>"
            );

            out.println(
                    "<tr>"
                    + "<td>Payment Method</td>"
                    + "<td>"
                    + (
                        invoice.getPaymentMethod() == null
                        ? "Not Paid"
                        : invoice.getPaymentMethod()
                      )
                    + "</td>"
                    + "</tr>"
            );

            String paymentStatus =
                    invoice.getPaymentStatus();

            out.println(
                    "<tr>"
                    + "<td>Payment Status</td>"
                    + "<td class='paid'>"
                    + (
                        paymentStatus == null
                        ? "Pending"
                        : paymentStatus
                      )
                    + "</td>"
                    + "</tr>"
            );

            out.println(
                    "<tr>"
                    + "<td>Payment Date</td>"
                    + "<td>"
                    + (
                        invoice.getPaymentDate() == null
                        ? "-"
                        : invoice.getPaymentDate()
                      )
                    + "</td>"
                    + "</tr>"
            );

            out.println("</table>");

            // =========================
            // BUTTONS
            // =========================

            out.println(
                    "<div class='buttons'>"
            );

            // Print Invoice
            out.println(
                    "<button class='print-btn' "
                    + "onclick='window.print()'>"
                    + "Print Invoice"
                    + "</button>"
            );

            // Logout
            out.println(
                    "<a class='logout-btn' "
                    + "href='"
                    + request.getContextPath()
                    + "/LogoutServlet'>"
                    + "Logout"
                    + "</a>"
            );

            out.println("</div>");

            out.println("</div>");

            out.println("</body>");

            out.println("</html>");

        } catch (NumberFormatException e) {

            response.setContentType(
                    "text/html;charset=UTF-8"
            );

            response.getWriter().println(
                    "<h3 style='color:red;"
                    + "text-align:center;'>"
                    + "Invalid Bill Number."
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
                    + "Error while displaying invoice."
                    + "</h3>"
            );
        }
    }
}