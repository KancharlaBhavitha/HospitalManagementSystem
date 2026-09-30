<%@ page import="java.util.List" %>
<%@ page import="com.hospital.model.Doctor" %>

<!DOCTYPE html>
<html>

<head>

    <meta charset="UTF-8">

    <title>Hospital Doctors</title>

    <style>

        body {
            margin: 0;
            font-family: Arial, sans-serif;
            background-color: #f2f6fa;
        }

        .container {
            width: 95%;
            max-width: 1200px;
            margin: 40px auto;
            background-color: white;
            padding: 25px;
            border-radius: 10px;
            box-shadow: 0 0 10px #aaa;
        }

        h2 {
            text-align: center;
            color: darkblue;
            margin-bottom: 25px;
        }

        table {
            width: 100%;
            border-collapse: collapse;
        }

        table, th, td {
            border: 1px solid #999;
        }

        th {
            background-color: darkblue;
            color: white;
            padding: 12px;
        }

        td {
            padding: 10px;
            text-align: center;
        }

        tr:nth-child(even) {
            background-color: #f2f2f2;
        }

        .back-btn {
            display: block;
            width: 220px;
            margin: 25px auto 0;
            padding: 10px;
            background-color: darkblue;
            color: white;
            text-align: center;
            text-decoration: none;
            border-radius: 5px;
        }

        .back-btn:hover {
            background-color: navy;
        }

        .no-doctors {
            text-align: center;
            color: red;
            font-size: 18px;
            padding: 20px;
        }

    </style>

</head>

<body>

<div class="container">

    <h2>Hospital Doctors</h2>

    <%
        @SuppressWarnings("unchecked")
        List<Doctor> doctorList =
            (List<Doctor>) request.getAttribute("doctorList");
    %>

    <%
        if (doctorList != null && !doctorList.isEmpty()) {
    %>

    <table>

        <tr>
            <th>Doctor ID</th>
            <th>Doctor Name</th>
            <th>Specialization</th>
            <th>Diseases</th>
            <th>Phone</th>
            <th>Email</th>
            <th>Experience</th>
            <th>Consultation Fee</th>
        </tr>

        <%
            for (Doctor doctor : doctorList) {
        %>

        <tr>

            <td>
                <%= doctor.getDoctorId() %>
            </td>

            <td>
                <%= doctor.getFullName() %>
            </td>

            <td>
                <%= doctor.getSpecialization() %>
            </td>

            <td>
                <%= doctor.getDisease() %>
            </td>

            <td>
                <%= doctor.getPhoneNumber() %>
            </td>

            <td>
                <%= doctor.getEmail() %>
            </td>

            <td>
                <%= doctor.getExperience() %> years
            </td>

            <td>
                &#8377;<%= doctor.getConsultationFee() %>
            </td>

        </tr>

        <%
            }
        %>

    </table>

    <%
        } else {
    %>

    <div class="no-doctors">
        No doctors available.
    </div>

    <%
        }
    %>

    <a class="back-btn"
       href="<%= request.getContextPath() %>/pages/patient_dashboard.html">

        Back to Patient Dashboard

    </a>

</div>

</body>

</html>