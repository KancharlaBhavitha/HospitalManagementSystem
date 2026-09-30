
<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>

<head>

    <meta charset="UTF-8">

    <title>Admin Dashboard</title>

    <style>

        body {
            font-family: Arial;
            background: #f2f2f2;
        }

        .container {
            width: 90%;
            margin: auto;
            margin-top: 30px;
        }

        h1 {
            text-align: center;
            color: darkblue;
        }

        .card {
            width: 220px;
            height: 130px;
            background: white;
            display: inline-block;
            margin: 20px;
            border-radius: 10px;
            box-shadow: 0 0 10px gray;
            text-align: center;
            padding-top: 25px;
        }

        a {
            text-decoration: none;
        }

        button {
            width: 170px;
            padding: 10px;
            background: green;
            color: white;
            border: none;
            cursor: pointer;
            font-size: 16px;
        }

        button:hover {
            background: darkgreen;
        }

        /* =========================
           DASHBOARD STATISTICS
           ========================= */

        .stat-card {
            width: 220px;
            height: 110px;
            background: white;
            display: inline-block;
            margin: 20px;
            border-radius: 10px;
            box-shadow: 0 0 10px gray;
            text-align: center;
            padding-top: 20px;
        }

        .stat-card h3 {
            margin: 5px;
            color: darkblue;
        }

        .stat-card p {
            font-size: 25px;
            font-weight: bold;
            margin: 15px;
            color: green;
        }

        .section-title {
            text-align: center;
            color: darkblue;
            margin-top: 30px;
        }

        .stats-container {
            text-align: center;
        }

        .cards-container {
            text-align: center;
        }

    </style>

</head>

<body>

<div class="container">

    <h1>Hospital Management System</h1>

    <!-- =========================
         DASHBOARD STATISTICS
         ========================= -->

    <h2 class="section-title">Dashboard Summary</h2>

    <div class="stats-container">

        <!-- TOTAL PATIENTS -->

        <div class="stat-card">

            <h3>Total Patients</h3>

            <p>
                <%= request.getAttribute("totalPatients") %>
            </p>

        </div>


        <!-- TOTAL DOCTORS -->

        <div class="stat-card">

            <h3>Total Doctors</h3>

            <p>
                <%= request.getAttribute("totalDoctors") %>
            </p>

        </div>


        <!-- TOTAL APPOINTMENTS -->

        <div class="stat-card">

            <h3>Total Appointments</h3>

            <p>
                <%= request.getAttribute("totalAppointments") %>
            </p>

        </div>


        <!-- THIS MONTH REVENUE -->

        <div class="stat-card">

            <h3>This Month Revenue</h3>

            <p>
                ₹<%= String.format("%.2f",
                    request.getAttribute("monthlyRevenue")) %>
            </p>

        </div>

    </div>


    <!-- =========================
         PATIENTS
         ========================= -->

    <div class="cards-container">

        <div class="card">

            <h3>Patients</h3>

            <a href="<%= request.getContextPath() %>/AdminPatientServlet">
                <button>View Patients</button>
            </a>

        </div>


        <!-- =========================
             LOGOUT
             ========================= -->

        <div class="card">

            <h3>Logout</h3>

            <a href="<%= request.getContextPath() %>/pages/login_page.html">
                <button>Logout</button>
            </a>

        </div>

    </div>

</div>

</body>

</html>
