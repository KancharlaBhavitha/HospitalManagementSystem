<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>

<!DOCTYPE html>

<html>

<head>

<meta charset="UTF-8">

<title>My Patients</title>

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
    width: 80%;
    margin: 50px auto;
    background: white;
    padding: 30px;
    border-radius: 12px;
    box-shadow: 0 3px 12px rgba(0,0,0,0.10);
}

h1 {
    text-align: center;
    color: #1e3a5f;
    margin-bottom: 30px;
}

.patient-card {
    padding: 18px;
    margin-bottom: 12px;
    background: #f8fafc;
    border-radius: 8px;
    border: 1px solid #e2e8f0;
}

.patient-name {
    font-size: 18px;
    font-weight: bold;
    color: #1e3a5f;
}

.empty {
    text-align: center;
    padding: 30px;
    color: #777;
}

.back {
    display: inline-block;
    margin-top: 25px;
    padding: 11px 20px;
    background: #1e3a5f;
    color: white;
    text-decoration: none;
    border-radius: 6px;
}

.back:hover {
    background: #2d527d;
}

</style>

</head>

<body>

<div class="container">

<h1>My Patients</h1>

<%

List<?> patients =
        (List<?>) request.getAttribute("patients");

if (patients == null || patients.isEmpty()) {

%>

<div class="empty">

    No patients found.

</div>

<%

} else {

    int count = 1;

    for (Object patient : patients) {

%>

<div class="patient-card">

    <div class="patient-name">

        <%= count %>. <%= patient %>

    </div>

</div>

<%

        count++;
    }

}

%>

<a class="back"
   href="<%=request.getContextPath()%>/DoctorDashboardServlet">

    Back to Doctor Dashboard

</a>

</div>

</body>

</html>