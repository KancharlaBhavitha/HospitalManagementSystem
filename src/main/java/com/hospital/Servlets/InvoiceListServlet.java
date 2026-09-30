package com.hospital.Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import com.hospital.dao.InvoiceListDAO;
import com.hospital.model.InvoiceList;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/InvoiceListServlet")
public class InvoiceListServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

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

        int patientId =
                (Integer) session.getAttribute("patientId");

        InvoiceListDAO dao =
                new InvoiceListDAO();

        ArrayList<InvoiceList> list =
                dao.getAllInvoices(patientId);

        response.setContentType(
                "text/html;charset=UTF-8"
        );

        PrintWriter out =
                response.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");

        out.println("<meta charset='UTF-8'>");
        out.println("<title>Invoice List</title>");

        out.println("<style>");

        out.println("body {");
        out.println("font-family: Arial, sans-serif;");
        out.println("background: #f2f2f2;");
        out.println("margin: 0;");
        out.println("padding: 30px;");
        out.println("}");

        out.println("h2 {");
        out.println("text-align: center;");
        out.println("color: darkblue;");
        out.println("}");

        out.println(".container {");
        out.println("width: 95%;");
        out.println("margin: auto;");
        out.println("}");

        out.println("table {");
        out.println("width: 100%;");
        out.println("border-collapse: collapse;");
        out.println("background: white;");
        out.println("}");

        out.println("th, td {");
        out.println("padding: 12px;");
        out.println("border: 1px solid #ccc;");
        out.println("text-align: center;");
        out.println("}");

        out.println("th {");
        out.println("background: darkblue;");
        out.println("color: white;");
        out.println("}");

        out.println(".view {");
        out.println("background: green;");
        out.println("color: white;");
        out.println("padding: 7px 12px;");
        out.println("text-decoration: none;");
        out.println("border-radius: 5px;");
        out.println("}");

        out.println(".view:hover {");
        out.println("background: darkgreen;");
        out.println("}");

        out.println(".back {");
        out.println("display: block;");
        out.println("width: 240px;");
        out.println("margin: 30px auto 10px;");
        out.println("padding: 12px;");
        out.println("background: darkblue;");
        out.println("color: white;");
        out.println("text-align: center;");
        out.println("text-decoration: none;");
        out.println("border-radius: 6px;");
        out.println("font-weight: bold;");
        out.println("}");

        out.println(".back:hover {");
        out.println("background: #00008b;");
        out.println("}");

        out.println("</style>");

        out.println("</head>");
        out.println("<body>");

        out.println("<div class='container'>");

        out.println("<h2>My Invoice List</h2>");

        if (list.isEmpty()) {

            out.println(
                    "<h3 style='text-align:center;"
                    + "color:red;'>"
                    + "No invoices found."
                    + "</h3>"
            );

        } else {

            out.println("<table>");

            out.println("<tr>");

            out.println("<th>Bill ID</th>");
            out.println("<th>Appointment ID</th>");
            out.println("<th>Patient Name</th>");
            out.println("<th>Doctor Name</th>");
            out.println("<th>Consultation Fee</th>");
            out.println("<th>Additional Charges</th>");
            out.println("<th>Total Amount</th>");
            out.println("<th>Payment Method</th>");
            out.println("<th>Payment Status</th>");
            out.println("<th>Invoice</th>");

            out.println("</tr>");

            for (InvoiceList invoice : list) {

                out.println("<tr>");

                out.println(
                        "<td>"
                        + invoice.getBillNumber()
                        + "</td>"
                );

                out.println(
                        "<td>"
                        + invoice.getAppointmentId()
                        + "</td>"
                );

                out.println(
                        "<td>"
                        + invoice.getPatientName()
                        + "</td>"
                );

                out.println(
                        "<td>"
                        + invoice.getDoctorName()
                        + "</td>"
                );

                out.println(
                        "<td>&#8377;"
                        + invoice.getConsultationFee()
                        + "</td>"
                );

                out.println(
                        "<td>&#8377;"
                        + invoice.getAdditionalCharges()
                        + "</td>"
                );

                out.println(
                        "<td><b>&#8377;"
                        + invoice.getTotalAmount()
                        + "</b></td>"
                );

                out.println(
                        "<td>"
                        + (
                            invoice.getPaymentMethod() == null
                            ? "Not Paid"
                            : invoice.getPaymentMethod()
                          )
                        + "</td>"
                );

                out.println(
                        "<td>"
                        + (
                            invoice.getPaymentStatus() == null
                            ? "Pending"
                            : invoice.getPaymentStatus()
                          )
                        + "</td>"
                );

                out.println(
                        "<td>"
                        + "<a class='view' href='"
                        + request.getContextPath()
                        + "/InvoiceServlet?billNumber="
                        + invoice.getBillNumber()
                        + "'>"
                        + "View Invoice"
                        + "</a>"
                        + "</td>"
                );

                out.println("</tr>");
            }

            out.println("</table>");
        }

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