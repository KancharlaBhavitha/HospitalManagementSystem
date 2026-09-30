package com.hospital.Servlets;

import java.io.IOException;

import com.hospital.dao.AdminDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/AdminServlet")
public class AdminServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;


    // ==========================================
    // ADMIN DASHBOARD
    // ==========================================

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        AdminDAO dao = new AdminDAO();

        // Get dashboard statistics
        int totalPatients = dao.getTotalPatients();

        int totalDoctors = dao.getTotalDoctors();

        int totalAppointments = dao.getTotalAppointments();

        double monthlyRevenue = dao.getThisMonthRevenue();


        // Get doctor activity
        request.setAttribute(
                "doctorActivity",
                dao.getDoctorActivity()
        );


        // Send values to JSP
        request.setAttribute(
                "totalPatients",
                totalPatients
        );

        request.setAttribute(
                "totalDoctors",
                totalDoctors
        );

        request.setAttribute(
                "totalAppointments",
                totalAppointments
        );

        request.setAttribute(
                "monthlyRevenue",
                monthlyRevenue
        );


        // Open Admin Dashboard
        request.getRequestDispatcher(
                "/pages/admin_dashboard.jsp"
        ).forward(request, response);
    }


    // ==========================================
    // ADMIN LOGIN
    // ==========================================

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        com.hospital.model.Admin admin =
                new com.hospital.model.Admin();

        admin.setUsername(
                request.getParameter("username")
        );

        admin.setPassword(
                request.getParameter("password")
        );


        AdminDAO dao = new AdminDAO();


        if (dao.login(admin)) {

            // After login open dashboard through doGet()
            response.sendRedirect(
                    request.getContextPath()
                    + "/AdminServlet"
            );

        } else {

            response.setContentType("text/html;charset=UTF-8");

            response.getWriter().println(
                    "<h2>Invalid Admin Login</h2>"
            );
        }
    }
}