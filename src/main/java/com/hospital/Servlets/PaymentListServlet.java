package com.hospital.Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import com.hospital.dao.PaymentListDAO;
import com.hospital.model.PaymentList;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/PaymentListServlet")
public class PaymentListServlet extends HttpServlet {

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
        // Get Patient ID from Session
        // -----------------------------------------

        int patientId =
                (Integer) session.getAttribute("patientId");

        // -----------------------------------------
        // Get Payments of Logged-in Patient
        // -----------------------------------------

        PaymentListDAO dao =
                new PaymentListDAO();

        ArrayList<PaymentList> list =
                dao.getPaymentsByPatientId(patientId);

        // -----------------------------------------
        // Display
        // -----------------------------------------

        response.setContentType(
                "text/html;charset=UTF-8"
        );

        PrintWriter out =
                response.getWriter();

        out.println("<html>");

        out.println("<head>");

        out.println("<title>Payment List</title>");

        out.println("<style>");

        out.println(
            "body{"
            + "font-family:Arial;"
            + "background:#f2f2f2;"
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
            + "box-shadow:0 0 10px gray;"
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
            + "margin-top:20px;"
            + "}"
        );

        out.println(
            "th,td{"
            + "border:1px solid #ccc;"
            + "padding:12px;"
            + "text-align:center;"
            + "}"
        );

        out.println(
            "th{"
            + "background:darkblue;"
            + "color:white;"
            + "}"
        );

        out.println(
            "tr:nth-child(even){"
            + "background:#f5f5f5;"
            + "}"
        );

        out.println(
            ".paid{"
            + "color:green;"
            + "font-weight:bold;"
            + "}"
        );

        out.println(
            ".back{"
            + "display:block;"
            + "width:220px;"
            + "margin:25px auto 0;"
            + "padding:10px;"
            + "background:darkblue;"
            + "color:white;"
            + "text-align:center;"
            + "text-decoration:none;"
            + "border-radius:5px;"
            + "}"
        );

        out.println("</style>");

        out.println("</head>");

        out.println("<body>");

        out.println("<div class='container'>");

        out.println("<h2>My Payment History</h2>");

        // -----------------------------------------
        // No Payments
        // -----------------------------------------

        if (list.isEmpty()) {

            out.println(
                "<h3 style='text-align:center;"
                + "color:red;'>"
                + "No payments found."
                + "</h3>"
            );

        } else {

            // -----------------------------------------
            // Payment Table
            // -----------------------------------------

            out.println("<table>");

            out.println("<tr>");

            out.println("<th>Payment ID</th>");

            out.println("<th>Bill ID</th>");

            out.println("<th>Patient Name</th>");

            out.println("<th>Bill Amount</th>");

            out.println("<th>Payment Method</th>");

            out.println("<th>Payment Date</th>");

            out.println("<th>Payment Status</th>");

            out.println("</tr>");

            // -----------------------------------------
            // Display Each Payment
            // -----------------------------------------

            for (PaymentList payment : list) {

                out.println("<tr>");

                // Payment ID
                out.println(
                    "<td>"
                    + payment.getPaymentId()
                    + "</td>"
                );

                // Bill ID
                out.println(
                    "<td>"
                    + payment.getBillId()
                    + "</td>"
                );

                // Patient Name
                out.println(
                    "<td>"
                    + payment.getPatientName()
                    + "</td>"
                );

                // Bill Amount
                out.println(
                    "<td>&#8377;"
                    + payment.getBillAmount()
                    + "</td>"
                );

                // Payment Method
                out.println(
                    "<td>"
                    + payment.getPaymentMethod()
                    + "</td>"
                );

                // Payment Date
                out.println(
                    "<td>"
                    + payment.getPaymentDate()
                    + "</td>"
                );

                // Payment Status
                out.println(
                    "<td class='paid'>"
                    + payment.getPaymentStatus()
                    + "</td>"
                );

                out.println("</tr>");
            }

            out.println("</table>");
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