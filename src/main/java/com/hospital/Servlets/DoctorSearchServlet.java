package com.hospital.Servlets;

import java.io.IOException;
import java.util.List;

import com.hospital.dao.DoctorDAO;
import com.hospital.model.Doctor;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/DoctorSearchServlet")
public class DoctorSearchServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String disease =
                request.getParameter("disease");

        System.out.println(
                "========== DOCTOR SEARCH =========="
        );

        System.out.println(
                "Disease received: " + disease
        );

        if (disease == null ||
            disease.trim().isEmpty()) {

            response.sendRedirect(
                    request.getContextPath()
                    + "/pages/find_doctor.html"
            );

            return;
        }

        disease = disease.trim();

        DoctorDAO dao =
                new DoctorDAO();

        List<Doctor> doctorList =
                dao.searchDoctorsByDisease(
                        disease
                );

        System.out.println(
                "Doctors found: "
                + doctorList.size()
        );

        request.setAttribute(
                "doctorList",
                doctorList
        );

        // IMPORTANT:
        // JSP is expecting "disease"

        request.setAttribute(
                "disease",
                disease
        );

        request.getRequestDispatcher(
                "/pages/doctor_search_results.jsp"
        ).forward(
                request,
                response
        );
    }
}