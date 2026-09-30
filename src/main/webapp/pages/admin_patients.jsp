
<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="java.util.List" %>
<%@ page import="com.hospital.model.PatientDoctorActivity" %>

<!DOCTYPE html>
<html>

<head>

    <meta charset="UTF-8">

    <title>Patient Details</title>

    <style>

        body {
            font-family: Arial, sans-serif;
            background: #f4f7fb;
            margin: 0;
            padding: 30px;
        }

        .container {
            width: 90%;
            margin: auto;
            background: white;
            padding: 25px;
            border-radius: 10px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }

        h2 {
            text-align: center;
            margin-bottom: 25px;
        }

        table {
            width: 100%;
            border-collapse: collapse;
        }

        th, td {
            padding: 12px;
            border: 1px solid #ddd;
            text-align: center;
        }

        th {
            background: #4a4a8a;
            color: white;
        }

        tr:nth-child(even) {
            background: #f8f8f8;
        }

        .completed {
            color: green;
            font-weight: bold;
        }

        .pending {
            color: orange;
            font-weight: bold;
        }

        .back-btn {
            display: inline-block;
            margin-top: 20px;
            padding: 10px 18px;
            background: #4a4a8a;
            color: white;
            text-decoration: none;
            border-radius: 5px;
        }

        .back-btn:hover {
            opacity: 0.9;
        }

    </style>

</head>

<body>

<div class="container">

    <h2>Patient &amp; Doctor Details</h2>

    <table>

        <tr>
            <th>Patient ID</th>
            <th>Patient Name</th>
            <th>Doctor</th>
            <th>Problem</th>
            <th>Status</th>
        </tr>

        <%
            List<?> patientList =
                (List<?>) request.getAttribute("patientList");

            if (patientList != null && !patientList.isEmpty()) {

                for (Object obj : patientList) {

                    PatientDoctorActivity patient =
                        (PatientDoctorActivity) obj;
        %>

        <tr>

            <td>
                <%= patient.getPatientId() %>
            </td>

            <td>
                <%= patient.getPatientName() %>
            </td>

            <td>
                <%= patient.getDoctorName() %>
            </td>

            <td>
                <%= patient.getReason() %>
            </td>

            <td>

                <% if ("Completed".equalsIgnoreCase(patient.getStatus())) { %>

                    <span class="completed">
                        <%= patient.getStatus() %>
                    </span>

                <% } else { %>

                    <span class="pending">
                        <%= patient.getStatus() %>
                    </span>

                <% } %>

            </td>

        </tr>

        <%
                }

            } else {
        %>

        <tr>
            <td colspan="5">
                No patient records found.
            </td>
        </tr>

        <%
            }
        %>

    </table>

    <a class="back-btn"
       href="<%= request.getContextPath() %>/AdminServlet">
        &#8592; Back to Admin Dashboard
    </a>

</div>

</body>

</html>

