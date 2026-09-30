
package com.hospital.Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import com.hospital.dao.BillingListDAO;
import com.hospital.model.BillingList;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/BillingListServlet")
public class BillingListServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

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
        // Get Logged-in Patient ID
        // -----------------------------------------

        int patientId =
                (Integer) session.getAttribute("patientId");

        // -----------------------------------------
        // Get Patient Bills
        // -----------------------------------------

        BillingListDAO dao =
                new BillingListDAO();

        ArrayList<BillingList> list =
                dao.getBillsByPatientId(patientId);

        // -----------------------------------------
        // Display
        // -----------------------------------------

        response.setContentType(
                "text/html;charset=UTF-8"
        );

        PrintWriter out =
                response.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");

        out.println("<meta charset='UTF-8'>");
        out.println("<title>My Bills</title>");

        out.println("<style>");

        out.println(
                "body{"
                + "font-family:Arial,sans-serif;"
                + "background-color:#f2f6fa;"
                + "margin:0;"
                + "padding:30px;"
                + "}"
        );

        out.println(
                ".container{"
                + "width:95%;"
                + "margin:auto;"
                + "background:white;"
                + "padding:25px;"
                + "border-radius:10px;"
                + "box-shadow:0 0 10px #aaa;"
                + "}"
        );

        out.println(
                "h2{"
                + "text-align:center;"
                + "color:darkblue;"
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
                "th{"
                + "background-color:darkblue;"
                + "color:white;"
                + "padding:12px;"
                + "}"
        );

        out.println(
                "td{"
                + "padding:10px;"
                + "text-align:center;"
                + "border:1px solid #ddd;"
                + "}"
        );

        out.println(
                ".pending{"
                + "color:orange;"
                + "font-weight:bold;"
                + "}"
        );

        out.println(
                ".paid{"
                + "color:green;"
                + "font-weight:bold;"
                + "}"
        );

        out.println(
                ".pay{"
                + "display:inline-block;"
                + "padding:7px 12px;"
                + "background:green;"
                + "color:white;"
                + "text-decoration:none;"
                + "border-radius:5px;"
                + "}"
        );

        out.println(
                ".pay:hover{"
                + "background:darkgreen;"
                + "}"
        );

        out.println(
                ".back{"
                + "display:block;"
                + "width:220px;"
                + "margin:25px auto;"
                + "padding:12px;"
                + "text-align:center;"
                + "background:darkblue;"
                + "color:white;"
                + "text-decoration:none;"
                + "border-radius:6px;"
                + "}"
        );

        out.println("</style>");
        out.println("</head>");
        out.println("<body>");

        out.println("<div class='container'>");

        out.println("<h2>My Bills</h2>");

        // -----------------------------------------
        // Check Bills
        // -----------------------------------------

        if (list != null && !list.isEmpty()) {

            out.println("<table>");

            out.println("<tr>");

            out.println("<th>Bill ID</th>");
            out.println("<th>Appointment ID</th>");
            out.println("<th>Doctor ID</th>");
            out.println("<th>Consultation Fee</th>");

            // General heading because both
            // consultation and medicine bills exist
            out.println("<th>Charges</th>");

            out.println("<th>Total Amount</th>");
            out.println("<th>Status</th>");
            out.println("<th>Action</th>");

            out.println("</tr>");

            // -----------------------------------------
            // Display Bills
            // -----------------------------------------

            for (BillingList bill : list) {

                out.println("<tr>");

                // Bill ID
                out.println(
                        "<td>"
                        + bill.getBillId()
                        + "</td>"
                );

                // Appointment ID
                out.println(
                        "<td>"
                        + bill.getAppointmentId()
                        + "</td>"
                );

                // Doctor ID
                out.println(
                        "<td>"
                        + bill.getDoctorId()
                        + "</td>"
                );

                // Consultation Fee
                out.println(
                        "<td>₹"
                        + bill.getConsultationFee()
                        + "</td>"
                );

                // -----------------------------------------
                // Medicine Cost / Additional Charges
                // -----------------------------------------

                if (bill.getConsultationFee() == 0) {

                    // Medicine Bill
                    out.println(
                            "<td>"
                            + "Medicine Cost: ₹"
                            + bill.getAdditionalCharges()
                            + "</td>"
                    );

                } else {

                    // Consultation Bill
                    out.println(
                            "<td>"
                            + "Additional Charges: ₹"
                            + bill.getAdditionalCharges()
                            + "</td>"
                    );
                }

                // -----------------------------------------
                // Total Amount
                // -----------------------------------------

                out.println(
                        "<td>₹"
                        + bill.getTotalAmount()
                        + "</td>"
                );

                String status =
                        bill.getBillStatus();

                // -----------------------------------------
                // Paid Bill
                // -----------------------------------------

                if ("Paid".equalsIgnoreCase(status)) {

                    out.println(
                            "<td class='paid'>"
                            + status
                            + "</td>"
                    );

                    out.println(
                            "<td>"
                            + "Payment Completed"
                            + "</td>"
                    );

                } else {

                    // -----------------------------------------
                    // Pending Bill
                    // -----------------------------------------

                    out.println(
                            "<td class='pending'>"
                            + status
                            + "</td>"
                    );

                    // -----------------------------------------
                    // Pay Now
                    // -----------------------------------------

                    out.println(
                            "<td>"
                            + "<a class='pay' href='"
                            + request.getContextPath()
                            + "/pages/payment_page.html"
                            + "?billId="
                            + bill.getBillId()
                            + "&amount="
                            + bill.getTotalAmount()
                            + "'>"
                            + "Pay Now"
                            + "</a>"
                            + "</td>"
                    );
                }

                out.println("</tr>");
            }

            out.println("</table>");

        } else {

            // -----------------------------------------
            // No Bills
            // -----------------------------------------

            out.println(
                    "<h3 style='text-align:center;"
                    + "color:red;'>"
                    + "No Bills Found"
                    + "</h3>"
            );
        }

        // -----------------------------------------
        // Back to Patient Dashboard
        // -----------------------------------------

        out.println(
                "<a class='back' href='"
                + request.getContextPath()
                + "/pages/patient_dashboard.html'>"
                + "Back to Patient Dashboard"
                + "</a>"
        );

        out.println("</div>");
        out.println("</body>");
        out.println("</html>");
    }
}

