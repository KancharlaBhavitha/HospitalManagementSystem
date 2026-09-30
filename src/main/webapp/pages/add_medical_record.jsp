<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
    String patientId = request.getParameter("patientId");
    String appointmentId = request.getParameter("appointmentId");
    String patientName = request.getParameter("patientName");
    String problem = request.getParameter("problem");
%>

<!DOCTYPE html>
<html>

<head>

<meta charset="UTF-8">

<title>Add Medical Record</title>

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

.container {
    width: 90%;
    max-width: 650px;
    margin: 40px auto;
}

.card {
    background: white;
    padding: 30px;
    border-radius: 12px;
    box-shadow: 0 3px 12px rgba(0,0,0,0.10);
}

h1 {
    text-align: center;
    color: #1e3a5f;
    margin-bottom: 25px;
}

.patient-info {
    background: #eef5ff;
    padding: 15px;
    border-radius: 8px;
    margin-bottom: 20px;
}

.patient-info p {
    margin: 7px 0;
}

label {
    display: block;
    margin-top: 15px;
    margin-bottom: 6px;
    font-weight: bold;
    color: #1e3a5f;
}

textarea {
    width: 100%;
    padding: 11px;
    border: 1px solid #ccc;
    border-radius: 6px;
    font-family: Arial, sans-serif;
    font-size: 14px;
    resize: vertical;
}

textarea:focus {
    outline: none;
    border-color: #0d6efd;
}

button {
    width: 100%;
    margin-top: 25px;
    padding: 12px;
    border: none;
    border-radius: 6px;
    background: #198754;
    color: white;
    font-size: 15px;
    cursor: pointer;
}

button:hover {
    background: #157347;
}

.back {
    display: block;
    text-align: center;
    margin-top: 15px;
    color: #1e3a5f;
    text-decoration: none;
    font-size: 14px;
}

</style>

</head>

<body>

<div class="container">

<div class="card">

<h1>Add Medical Record</h1>

<div class="patient-info">

    <p>
        <strong>Patient Name:</strong>
        <%= patientName != null ? patientName : "Unknown" %>
    </p>

    <p>
        <strong>Patient ID:</strong>
        <%= patientId != null ? patientId : "Unknown" %>
    </p>

    <p>
        <strong>Appointment ID:</strong>
        <%= appointmentId != null ? appointmentId : "Unknown" %>
    </p>

    <p>
        <strong>Current Problem:</strong>
        <%= problem != null ? problem : "Not available" %>
    </p>

</div>

<form action="<%=request.getContextPath()%>/AddMedicalRecordServlet"
      method="post">

    <input type="hidden"
           name="patientId"
           value="<%= patientId %>">

    <input type="hidden"
           name="appointmentId"
           value="<%= appointmentId %>">

    <label>Diagnosis</label>

    <textarea name="diagnosis"
              rows="3"
              placeholder="Enter diagnosis"
              required></textarea>


    <label>Doctor Notes</label>

    <textarea name="doctorNotes"
              rows="5"
              placeholder="Enter doctor's notes"
              required></textarea>


    <label>Prescription</label>

    <textarea name="prescription"
              rows="5"
              placeholder="Enter prescribed medicines and instructions"
              required></textarea>


    <button type="submit">
        Save Medical Record
    </button>

</form>

<a class="back"
   href="<%=request.getContextPath()%>/AppointmentListServlet">
    Back to Appointments
</a>

</div>

</div>

</body>

</html>