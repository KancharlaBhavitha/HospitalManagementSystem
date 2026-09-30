<%@ page language="java"
contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>

<%@ page import="java.util.List" %>

<%
List<?> todaysPatients =
     (List<?>) request.getAttribute("todaysPatients");
%>

<!DOCTYPE html>

<html>

<head>

<meta charset="UTF-8">

<title>Doctor Dashboard</title>

<style>

* {
    box-sizing: border-box;
}

body {
    margin: 0;
    font-family: Arial, sans-serif;
    background: #f4f7fb;
    color: #333;
}

/* Header */

.header {
    text-align: center;
    padding: 28px 15px 15px;
}

.header h1 {
    margin: 0;
    color: #1e3a5f;
    font-size: 28px;
}

.header p {
    margin-top: 8px;
    color: #666;
    font-size: 15px;
}

/* Container */

.container {
    width: 90%;
    max-width: 1000px;
    margin: auto;
}

/* Cards */

.cards {
    display: grid;
    grid-template-columns: repeat(3, 1fr);
    gap: 18px;
    margin-top: 20px;
}

.card {
    background: white;
    padding: 20px;
    border-radius: 12px;
    text-align: center;
    box-shadow: 0 3px 10px rgba(0,0,0,0.10);
    transition: 0.2s;
}

.card:hover {
    transform: translateY(-3px);
}

.card .icon {
    font-size: 30px;
    margin-bottom: 8px;
}

.card h3 {
    margin: 8px 0;
    color: #1e3a5f;
    font-size: 18px;
}

.card p {
    font-size: 13px;
    color: #666;
    min-height: 35px;
}

.card a {
    display: inline-block;
    margin-top: 8px;
    padding: 9px 16px;
    border-radius: 6px;
    color: white;
    text-decoration: none;
    font-size: 13px;
}

/* Profile */

.profile {
    border-top: 5px solid #6f42c1;
}

.profile a {
    background: #6f42c1;
}

/* Appointments */

.appointments {
    border-top: 5px solid #0d6efd;
}

.appointments a {
    background: #0d6efd;
}

/* Patients */

.patients {
    border-top: 5px solid #198754;
}

.patients a {
    background: #198754;
}

/* Medical Records */

.records {
    border-top: 5px solid #fd7e14;
}

.records a {
    background: #fd7e14;
}

/* Prescriptions */

.prescriptions {
    border-top: 5px solid #dc3545;
}

.prescriptions a {
    background: #dc3545;
}

/* Today's Appointments */

.today-section {
    background: white;
    margin-top: 25px;
    padding: 20px;
    border-radius: 12px;
    box-shadow: 0 3px 10px rgba(0,0,0,0.10);
}

.today-section h2 {
    margin-top: 0;
    text-align: center;
    color: #1e3a5f;
    font-size: 21px;
}

.patient-item {
    background: #eef5ff;
    padding: 12px;
    margin: 8px 0;
    border-radius: 7px;
    color: #333;
}

.no-patient {
    text-align: center;
    color: #777;
    padding: 12px;
}

/* Logout */

.logout {
    text-align: center;
    margin: 25px 0 35px;
}

.logout a {
    display: inline-block;
    padding: 10px 25px;
    background: #dc3545;
    color: white;
    text-decoration: none;
    border-radius: 6px;
    font-size: 14px;
}

.logout a:hover {
    background: #bb2d3b;
}

/* Responsive */

@media (max-width: 700px) {

    .cards {
        grid-template-columns: 1fr;
    }

}

</style>

</head>

<body>

<div class="header">


<h1>Welcome, Doctor</h1>

<p>
    Manage your patients, appointments and prescriptions.
</p>


</div>

<div class="container">

<div class="cards">

<!-- My Profile -->

<div class="card profile">


<div class="icon">Doctor</div>

<h3>My Profile</h3>

<p>
    View your doctor profile.
</p>

<a href="<%=request.getContextPath()%>/DoctorProfileServlet">
    View Profile
</a>


</div>

<!-- My Appointments -->

<div class="card appointments">


<div class="icon">Calendar</div>

<h3>My Appointments</h3>

<p>
    View all appointments.
</p>

<a href="<%=request.getContextPath()%>/AppointmentListServlet">
    View Appointments
</a>


</div>

<!-- My Patients -->

<div class="card patients">


<div class="icon">Patients</div>

<h3>My Patients</h3>

<p>
    View your patients.
</p>

<a href="<%=request.getContextPath()%>/DoctorPatientsServlet">
    View Patients
</a>


</div>

<!-- Medical Records -->

<div class="card records">


<div class="icon">Records</div>

<h3>Medical Records</h3>

<p>
    View patient medical records.
</p>

<a href="<%=request.getContextPath()%>/DoctorMedicalRecordsServlet">
    View Records
</a>


</div>

<!-- Prescriptions -->

<div class="card prescriptions">


<div class="icon">Medicine</div>

<h3>Prescriptions</h3>

<p>
    View and manage prescriptions.
</p>

<a href="<%=request.getContextPath()%>/PrescriptionListServlet">
    Manage
</a>


</div>

</div>

<!-- Today's Appointments -->

<div class="today-section">


<h2>Today's Appointments</h2>

<%

    if (todaysPatients != null &&
        !todaysPatients.isEmpty()) {

        for (Object patient : todaysPatients) {

%>

            <div class="patient-item">
                <%= patient %>
            </div>

<%

        }

    } else {

%>

        <div class="no-patient">
            No appointments scheduled for today.
        </div>

<%

    }

%>


</div>

<!-- Logout -->

<div class="logout">


<a href="<%=request.getContextPath()%>/pages/login_page.html">
    Logout
</a>


</div>

</div>

</body>

</html>
