<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="com.hospital.model.Doctor" %>

<%
    Doctor doctor =
        (Doctor) request.getAttribute("doctor");
%>

<!DOCTYPE html>
<html>

<head>

    <meta charset="UTF-8">

    <title>Doctor Profile</title>

    <style>

        body {
            margin: 0;
            font-family: Arial, sans-serif;
            background: #f4f7fb;
        }

        .header {
            background: #2563eb;
            color: white;
            padding: 20px;
            text-align: center;
        }

        .container {
            width: 600px;
            margin: 40px auto;
            background: white;
            padding: 30px;
            border-radius: 12px;
            box-shadow: 0 4px 15px rgba(0,0,0,0.1);
        }

        .profile-icon {
            width: 90px;
            height: 90px;
            background: #e0e7ff;
            border-radius: 50%;
            margin: 0 auto 20px;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 45px;
        }

        h2 {
            text-align: center;
            margin-bottom: 30px;
        }

        .row {
            display: flex;
            justify-content: space-between;
            padding: 14px 0;
            border-bottom: 1px solid #eee;
        }

        .label {
            font-weight: bold;
            color: #555;
        }

        .value {
            color: #222;
        }

        .back-btn {
            display: block;
            width: fit-content;
            margin: 25px auto 0;
            padding: 12px 25px;
            background: #2563eb;
            color: white;
            text-decoration: none;
            border-radius: 6px;
        }

        .back-btn:hover {
            background: #1d4ed8;
        }

    </style>

</head>

<body>

    <div class="header">

        <h1>Doctor Profile</h1>

    </div>


    <div class="container">

        <div class="profile-icon">
            👨‍⚕️
        </div>

        <h2>
            <%= doctor.getFullName() %>
        </h2>


        <div class="row">

            <span class="label">
                Doctor ID
            </span>

            <span class="value">
                <%= doctor.getDoctorId() %>
            </span>

        </div>


        <div class="row">

            <span class="label">
                Full Name
            </span>

            <span class="value">
                <%= doctor.getFullName() %>
            </span>

        </div>


        <div class="row">

            <span class="label">
                Specialization
            </span>

            <span class="value">
                <%= doctor.getSpecialization() %>
            </span>

        </div>


        <div class="row">

            <span class="label">
                Disease
            </span>

            <span class="value">
                <%= doctor.getDisease() %>
            </span>

        </div>


        <div class="row">

            <span class="label">
                Phone Number
            </span>

            <span class="value">
                <%= doctor.getPhoneNumber() %>
            </span>

        </div>


        <div class="row">

            <span class="label">
                Email
            </span>

            <span class="value">
                <%= doctor.getEmail() %>
            </span>

        </div>


        <div class="row">

            <span class="label">
                Experience
            </span>

            <span class="value">
                <%= doctor.getExperience() %> years
            </span>

        </div>


        <div class="row">

            <span class="label">
                Consultation Fee
            </span>

            <span class="value">
                ₹<%= doctor.getConsultationFee() %>
            </span>

        </div>


        <a
            class="back-btn"
            href="${pageContext.request.contextPath}/DoctorDashboardServlet">

            ← Back to Dashboard

        </a>

    </div>

</body>

</html>