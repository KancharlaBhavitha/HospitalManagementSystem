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

@WebServlet("/DoctorListServlet")
public class DoctorListServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println(
            "========== DoctorListServlet Called =========="
        );

        DoctorDAO dao = new DoctorDAO();

        List<Doctor> doctorList = dao.getAllDoctors();

        request.setAttribute("doctorList", doctorList);

        request.getRequestDispatcher(
            "/pages/doctor_list.jsp"
        ).forward(request, response);
    }
}