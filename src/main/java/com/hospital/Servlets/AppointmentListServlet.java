package com.hospital.Servlets;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

import com.hospital.dao.AppointmentListDAO;
import com.hospital.model.AppointmentList;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/AppointmentListServlet")
public class AppointmentListServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        HttpSession session =
                request.getSession(false);

        // Check login session
        if (session == null ||
            session.getAttribute("role") == null) {

            response.sendRedirect(
                    request.getContextPath()
                    + "/pages/login_page.html"
            );

            return;
        }

        String role =
                (String) session.getAttribute("role");

        AppointmentListDAO dao =
                new AppointmentListDAO();

        List<AppointmentList> appointments;


        // =====================================================
        // PATIENT
        // =====================================================

        if ("PATIENT".equalsIgnoreCase(role)) {

            Integer patientId =
                    (Integer) session.getAttribute("patientId");

            if (patientId == null) {

                response.sendRedirect(
                        request.getContextPath()
                        + "/pages/login_page.html"
                );

                return;
            }

            appointments =
                    dao.getAppointmentsByPatientId(
                            patientId
                    );

            response.getWriter().println(

                    "<!DOCTYPE html>"
                    + "<html>"
                    + "<head>"
                    + "<meta charset='UTF-8'>"
                    + "<title>My Appointments</title>"

                    + "<style>"

                    + "*{box-sizing:border-box;}"

                    + "body{"
                    + "margin:0;"
                    + "font-family:Arial,sans-serif;"
                    + "background:#f4f7fb;"
                    + "}"

                    + ".container{"
                    + "width:90%;"
                    + "margin:40px auto;"
                    + "background:white;"
                    + "padding:30px;"
                    + "border-radius:12px;"
                    + "box-shadow:0 3px 12px rgba(0,0,0,0.10);"
                    + "}"

                    + "h1{"
                    + "text-align:center;"
                    + "color:#1e3a5f;"
                    + "margin-bottom:30px;"
                    + "}"

                    + "table{"
                    + "width:100%;"
                    + "border-collapse:collapse;"
                    + "}"

                    + "th{"
                    + "background:#1e3a5f;"
                    + "color:white;"
                    + "padding:14px;"
                    + "text-align:left;"
                    + "}"

                    + "td{"
                    + "padding:14px;"
                    + "border-bottom:1px solid #ddd;"
                    + "}"

                    + "tr:hover{"
                    + "background:#f8fafc;"
                    + "}"

                    + ".status{"
                    + "font-weight:bold;"
                    + "}"

                    + ".completed{"
                    + "color:green;"
                    + "}"

                    + ".pending{"
                    + "color:#d97706;"
                    + "}"

                    + ".back{"
                    + "display:inline-block;"
                    + "margin-top:25px;"
                    + "padding:11px 20px;"
                    + "background:#1e3a5f;"
                    + "color:white;"
                    + "text-decoration:none;"
                    + "border-radius:6px;"
                    + "}"

                    + ".back:hover{"
                    + "background:#2d527d;"
                    + "}"

                    + ".empty{"
                    + "text-align:center;"
                    + "padding:30px;"
                    + "color:#777;"
                    + "}"

                    + "</style>"

                    + "</head>"

                    + "<body>"

                    + "<div class='container'>"

                    + "<h1>My Appointments</h1>"
            );


            if (appointments == null ||
                appointments.isEmpty()) {

                response.getWriter().println(

                        "<div class='empty'>"
                        + "No appointments found."
                        + "</div>"
                );

            } else {

                response.getWriter().println(

                        "<table>"

                        + "<tr>"
                        + "<th>Appointment ID</th>"
                        + "<th>Doctor</th>"
                        + "<th>Date</th>"
                        + "<th>Time</th>"
                        + "<th>Problem</th>"
                        + "<th>Status</th>"
                        + "</tr>"
                );


                for (AppointmentList appointment :
                        appointments) {

                    String status =
                            appointment.getStatus();

                    String statusClass =
                            "Completed".equalsIgnoreCase(status)
                            ? "completed"
                            : "pending";


                    response.getWriter().println(

                            "<tr>"

                            + "<td>"
                            + appointment.getAppointmentId()
                            + "</td>"

                            + "<td>"
                            + appointment.getDoctorName()
                            + "</td>"

                            + "<td>"
                            + appointment.getAppointmentDate()
                            + "</td>"

                            + "<td>"
                            + appointment.getAppointmentTime()
                            + "</td>"

                            + "<td>"
                            + appointment.getProblem()
                            + "</td>"

                            + "<td class='status "
                            + statusClass
                            + "'>"
                            + status
                            + "</td>"

                            + "</tr>"
                    );
                }


                response.getWriter().println(
                        "</table>"
                );
            }


            response.getWriter().println(

                    "<a class='back' href='"
                    + request.getContextPath()
                    + "/pages/patient_dashboard.html'>"

                    + "Back to Patient Dashboard"

                    + "</a>"

                    + "</div>"
                    + "</body>"
                    + "</html>"
            );

            return;
        }


        // =====================================================
        // DOCTOR
        // =====================================================

        if ("DOCTOR".equalsIgnoreCase(role)) {

            Integer doctorId =
                    (Integer) session.getAttribute("doctorId");

            if (doctorId == null) {

                response.sendRedirect(
                        request.getContextPath()
                        + "/pages/login_page.html"
                );

                return;
            }


            appointments =
                    dao.getAppointmentsByDoctorId(
                            doctorId
                    );


            response.getWriter().println(

                    "<!DOCTYPE html>"
                    + "<html>"
                    + "<head>"
                    + "<meta charset='UTF-8'>"
                    + "<title>Doctor Appointments</title>"

                    + "<style>"

                    + "*{box-sizing:border-box;}"

                    + "body{"
                    + "margin:0;"
                    + "font-family:Arial,sans-serif;"
                    + "background:#f4f7fb;"
                    + "}"

                    + ".container{"
                    + "width:95%;"
                    + "margin:40px auto;"
                    + "background:white;"
                    + "padding:30px;"
                    + "border-radius:12px;"
                    + "box-shadow:0 3px 12px rgba(0,0,0,0.10);"
                    + "}"

                    + "h1{"
                    + "text-align:center;"
                    + "color:#1e3a5f;"
                    + "margin-bottom:30px;"
                    + "}"

                    + "table{"
                    + "width:100%;"
                    + "border-collapse:collapse;"
                    + "}"

                    + "th{"
                    + "background:#1e3a5f;"
                    + "color:white;"
                    + "padding:14px;"
                    + "text-align:left;"
                    + "}"

                    + "td{"
                    + "padding:14px;"
                    + "border-bottom:1px solid #ddd;"
                    + "vertical-align:middle;"
                    + "}"

                    + "tr:hover{"
                    + "background:#f8fafc;"
                    + "}"

                    + ".status{"
                    + "font-weight:bold;"
                    + "}"

                    + ".completed{"
                    + "color:green;"
                    + "}"

                    + ".pending{"
                    + "color:#d97706;"
                    + "}"

                    + ".prescription{"
                    + "display:inline-block;"
                    + "padding:8px 12px;"
                    + "background:#1e3a5f;"
                    + "color:white;"
                    + "text-decoration:none;"
                    + "border-radius:5px;"
                    + "font-size:14px;"
                    + "}"

                    + ".prescription:hover{"
                    + "background:#2d527d;"
                    + "}"

                    + ".view-prescription{"
                    + "display:inline-block;"
                    + "padding:8px 12px;"
                    + "background:#198754;"
                    + "color:white;"
                    + "text-decoration:none;"
                    + "border-radius:5px;"
                    + "font-size:14px;"
                    + "}"

                    + ".view-prescription:hover{"
                    + "background:#157347;"
                    + "}"

                    + ".medical-record{"
                    + "display:inline-block;"
                    + "padding:8px 12px;"
                    + "background:#fd7e14;"
                    + "color:white;"
                    + "text-decoration:none;"
                    + "border-radius:5px;"
                    + "font-size:14px;"
                    + "}"

                    + ".medical-record:hover{"
                    + "background:#dc6502;"
                    + "}"

                    + ".back{"
                    + "display:inline-block;"
                    + "margin-top:25px;"
                    + "padding:11px 20px;"
                    + "background:#1e3a5f;"
                    + "color:white;"
                    + "text-decoration:none;"
                    + "border-radius:6px;"
                    + "}"

                    + ".back:hover{"
                    + "background:#2d527d;"
                    + "}"

                    + ".empty{"
                    + "text-align:center;"
                    + "padding:30px;"
                    + "color:#777;"
                    + "}"

                    + "</style>"

                    + "</head>"

                    + "<body>"

                    + "<div class='container'>"

                    + "<h1>Doctor Appointments</h1>"
            );


            if (appointments == null ||
                appointments.isEmpty()) {

                response.getWriter().println(

                        "<div class='empty'>"
                        + "No appointments found."
                        + "</div>"
                );

            } else {

                response.getWriter().println(

                        "<table>"

                        + "<tr>"
                        + "<th>Appointment ID</th>"
                        + "<th>Patient</th>"
                        + "<th>Date</th>"
                        + "<th>Time</th>"
                        + "<th>Problem</th>"
                        + "<th>Status</th>"
                        + "<th>Action</th>"
                        + "</tr>"
                );


                for (AppointmentList appointment :
                        appointments) {

                    String status =
                            appointment.getStatus();

                    String statusClass =
                            "Completed".equalsIgnoreCase(status)
                            ? "completed"
                            : "pending";


                    String patientName =
                            appointment.getPatientName();

                    String problem =
                            appointment.getProblem();


                    if (patientName == null) {
                        patientName = "";
                    }

                    if (problem == null) {
                        problem = "";
                    }


                    response.getWriter().println(

                            "<tr>"

                            + "<td>"
                            + appointment.getAppointmentId()
                            + "</td>"

                            + "<td>"
                            + patientName
                            + "</td>"

                            + "<td>"
                            + appointment.getAppointmentDate()
                            + "</td>"

                            + "<td>"
                            + appointment.getAppointmentTime()
                            + "</td>"

                            + "<td>"
                            + problem
                            + "</td>"

                            + "<td class='status "
                            + statusClass
                            + "'>"
                            + status
                            + "</td>"


                            + "<td>"

                            // =================================
                            // CREATE PRESCRIPTION
                            // =================================

                            + "<a class='prescription' href='"
                            + request.getContextPath()
                            + "/pages/prescription_page.jsp"
                            + "?appointmentId="
                            + appointment.getAppointmentId()
                            + "&patientId="
                            + appointment.getPatientId()
                            + "&patientName="
                            + URLEncoder.encode(
                                    patientName,
                                    "UTF-8"
                              )
                            + "&problem="
                            + URLEncoder.encode(
                                    problem,
                                    "UTF-8"
                              )
                            + "'>"

                            + "Prescription"

                            + "</a>"


                            + "<br><br>"


                            // =================================
                            // VIEW PRESCRIPTION
                            // =================================

                            + "<a class='view-prescription' href='"
                            + request.getContextPath()
                            + "/ViewPrescriptionServlet"
                            + "?appointmentId="
                            + appointment.getAppointmentId()
                            + "'>"

                            + "View Prescription"

                            + "</a>"


                            + "<br><br>"


                            // =================================
                            // ADD MEDICAL RECORD
                            // =================================

                            + "<a class='medical-record' href='"
                            + request.getContextPath()
                            + "/pages/add_medical_record.jsp"
                            + "?appointmentId="
                            + appointment.getAppointmentId()
                            + "&patientId="
                            + appointment.getPatientId()
                            + "&patientName="
                            + URLEncoder.encode(
                                    patientName,
                                    "UTF-8"
                              )
                            + "&problem="
                            + URLEncoder.encode(
                                    problem,
                                    "UTF-8"
                              )
                            + "'>"

                            + "Add Medical Record"

                            + "</a>"


                            + "</td>"

                            + "</tr>"
                    );
                }


                response.getWriter().println(
                        "</table>"
                );
            }


            response.getWriter().println(

                    "<a class='back' href='"
                    + request.getContextPath()
                    + "/DoctorDashboardServlet'>"

                    + "Back to Doctor Dashboard"

                    + "</a>"

                    + "</div>"

                    + "</body>"

                    + "</html>"
            );

            return;
        }


        // =====================================================
        // INVALID ROLE
        // =====================================================

        response.sendRedirect(
                request.getContextPath()
                + "/pages/login_page.html"
        );
    }
}