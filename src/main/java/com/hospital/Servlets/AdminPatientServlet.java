package com.hospital.Servlets;

import java.io.IOException;
import java.util.List;

import com.hospital.dao.AdminPatientDAO;
import com.hospital.model.PatientDoctorActivity;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/AdminPatientServlet")
public class AdminPatientServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        AdminPatientDAO dao = new AdminPatientDAO();

        List<PatientDoctorActivity> patientList =
                dao.getPatientDoctorDetails();

        request.setAttribute("patientList", patientList);

        request.getRequestDispatcher(
                "/pages/admin_patients.jsp"
        ).forward(request, response);
    }
}