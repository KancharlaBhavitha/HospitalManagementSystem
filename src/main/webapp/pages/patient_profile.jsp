<%@ page import="com.hospital.model.PatientProfile" %>

<!DOCTYPE html>
<html>

<head>

<meta charset="UTF-8">

<title>My Profile</title>

<style>

body {
    margin: 0;
    font-family: Arial, sans-serif;
    background-color: #f2f6fa;
}

.container {
    width: 600px;
    margin: 40px auto;
    background-color: white;
    padding: 30px;
    border-radius: 12px;
    box-shadow: 0 0 12px #aaa;
}

h2 {
    text-align: center;
    color: darkblue;
    margin-bottom: 25px;
}

.profile-card {
    background-color: #f8fbff;
    padding: 20px;
    border-radius: 10px;
}

.row {
    display: flex;
    padding: 10px 0;
    border-bottom: 1px solid #ddd;
}

.label {
    width: 40%;
    font-weight: bold;
    color: #333;
}

.value {
    width: 60%;
    color: #555;
}

.back {
    display: block;
    width: 220px;
    margin: 25px auto 0;
    padding: 12px;
    text-align: center;
    background-color: darkblue;
    color: white;
    text-decoration: none;
    border-radius: 6px;
}

.back:hover {
    background-color: blue;
}

</style>

</head>

<body>

<%

PatientProfile patient =
    (PatientProfile) request.getAttribute("patient");

%>

<div class="container">

<h2>My Profile</h2>

<% if (patient != null) { %>

<div class="profile-card">

<div class="row">
    <div class="label">Patient ID</div>
    <div class="value">
        <%= patient.getPatientId() %>
    </div>
</div>

<div class="row">
    <div class="label">Full Name</div>
    <div class="value">
        <%= patient.getFullName() %>
    </div>
</div>

<div class="row">
    <div class="label">Email</div>
    <div class="value">
        <%= patient.getEmail() %>
    </div>
</div>

<div class="row">
    <div class="label">Phone Number</div>
    <div class="value">
        <%= patient.getPhoneNumber() %>
    </div>
</div>

<div class="row">
    <div class="label">Gender</div>
    <div class="value">
        <%= patient.getGender() %>
    </div>
</div>

<div class="row">
    <div class="label">Date of Birth</div>
    <div class="value">
        <%= patient.getDateOfBirth() %>
    </div>
</div>

<div class="row">
    <div class="label">Age</div>
    <div class="value">
        <%= patient.getAge() %>
    </div>
</div>

<div class="row">
    <div class="label">Blood Group</div>
    <div class="value">
        <%= patient.getBloodGroup() %>
    </div>
</div>

<div class="row">
    <div class="label">Address</div>
    <div class="value">
        <%= patient.getAddress() %>
    </div>
</div>

<div class="row">
    <div class="label">Emergency Number</div>
    <div class="value">
        <%= patient.getEmergencyNumber() %>
    </div>
</div>

<div class="row">
    <div class="label">Medical History</div>
    <div class="value">
        <%= patient.getMedicalHistory() %>
    </div>
</div>

<div class="row">
    <div class="label">Documents</div>
    <div class="value">
        <%= patient.getDocumentPath() %>
    </div>
</div>

</div>

<% } else { %>

<h3 style="text-align:center;color:red;">
    Patient profile not found.
</h3>

<% } %>

<a class="back"
   href="<%= request.getContextPath() %>/pages/patient_dashboard.html">
   Back to Patient Dashboard
</a>

</div>

</body>

</html>