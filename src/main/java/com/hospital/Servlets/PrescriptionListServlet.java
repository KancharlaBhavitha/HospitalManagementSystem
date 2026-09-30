package com.hospital.Servlets;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.hospital.dao.PrescriptionListDAO;

@WebServlet("/PrescriptionListServlet")
public class PrescriptionListServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null ||
            session.getAttribute("doctorId") == null) {

            response.sendRedirect(
                request.getContextPath() + "/pages/login_page.html"
            );
            return;
        }

        int doctorId =
                (Integer) session.getAttribute("doctorId");

        PrescriptionListDAO dao = new PrescriptionListDAO();

        List<Object[]> prescriptions =
                dao.getPrescriptionsByDoctorId(doctorId);

        request.setAttribute("prescriptions", prescriptions);

        request.getRequestDispatcher(
                "/pages/prescription_list.jsp"
        ).forward(request, response);
    }
}