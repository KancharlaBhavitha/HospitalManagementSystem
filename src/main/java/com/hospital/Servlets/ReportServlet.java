package com.hospital.Servlets;

import java.io.IOException;

import com.hospital.dao.ReportDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/ReportServlet")
public class ReportServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String type = request.getParameter("type");

        ReportDAO dao = new ReportDAO();

        int count = dao.getCount(type);

        response.setContentType("text/html");

        response.getWriter().println(

            "<h2>" + type.toUpperCase() + " REPORT</h2>" +

            "<h3>Total Records : " + count + "</h3>"

        );

    }

}