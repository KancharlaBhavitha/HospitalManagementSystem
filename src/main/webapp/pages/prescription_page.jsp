<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
    String appointmentId =
            request.getParameter("appointmentId");

    String patientId =
            request.getParameter("patientId");

    String patientName =
            request.getParameter("patientName");

    String problem =
            request.getParameter("problem");
%>

<!DOCTYPE html>

<html>

<head>

<meta charset="UTF-8">

<title>Create Prescription</title>

<style>

* {
    box-sizing: border-box;
}

body {

    margin: 0;

    font-family: Arial, sans-serif;

    background: #f4f7fb;
}


.container {

    width: 650px;

    margin: 40px auto;

    background: white;

    padding: 30px;

    border-radius: 12px;

    box-shadow:
        0 3px 12px
        rgba(0,0,0,0.12);
}


h1 {

    text-align: center;

    color: #1e3a5f;

    margin-bottom: 30px;
}


.patient-info {

    background: #eef4fa;

    padding: 18px;

    border-radius: 8px;

    margin-bottom: 25px;
}


.patient-info p {

    margin: 8px 0;

    font-size: 16px;
}


label {

    display: block;

    margin-top: 16px;

    margin-bottom: 7px;

    font-weight: bold;

    color: #333;
}


input,
textarea {

    width: 100%;

    padding: 12px;

    border: 1px solid #ccc;

    border-radius: 6px;

    font-size: 15px;
}


textarea {

    height: 90px;

    resize: vertical;
}


button {

    width: 100%;

    margin-top: 25px;

    padding: 13px;

    background: #1e3a5f;

    color: white;

    border: none;

    border-radius: 6px;

    font-size: 16px;

    cursor: pointer;
}


button:hover {

    background: #2d527d;
}


.back {

    display: block;

    text-align: center;

    margin-top: 18px;

    color: #1e3a5f;

    text-decoration: none;
}


.back:hover {

    text-decoration: underline;
}


.template-box {

    background: #fff8e6;

    border: 1px solid #f0d98c;

    padding: 15px;

    border-radius: 8px;

    margin-bottom: 20px;
}


.template-box h3 {

    margin-top: 0;

    color: #8a6500;
}


.template-box p {

    margin: 6px 0;

    color: #555;
}

</style>

</head>


<body>


<div class="container">


    <h1>💊 Create Prescription</h1>


    <!-- Patient Information -->

    <div class="patient-info">

        <p>
            <strong>Patient:</strong>
            <%= patientName != null ? patientName : "" %>
        </p>

        <p>
            <strong>Problem:</strong>
            <%= problem != null ? problem : "" %>
        </p>

        <p>
            <strong>Appointment ID:</strong>
            <%= appointmentId != null ? appointmentId : "" %>
        </p>

    </div>


    <!-- Suggestion -->

    <div class="template-box">

        <h3>💡 Prescription Template</h3>

        <p>
            Problem:
            <strong>
                <%= problem != null ? problem : "Not specified" %>
            </strong>
        </p>

        <p>
            Doctor should verify and enter the appropriate
            medicine, dosage and duration.
        </p>

    </div>


    <!-- Prescription Form -->

    <form
        action="<%=request.getContextPath()%>/PrescriptionServlet"
        method="post">


        <!-- Hidden Appointment ID -->

        <input
            type="hidden"
            name="appointmentId"
            value="<%= appointmentId %>">


        <!-- Hidden Patient ID -->

        <input
            type="hidden"
            name="patientId"
            value="<%= patientId %>">


        <!-- Medicine -->

        <label>
            Medicine Name
        </label>

        <input
            type="text"
            name="medicineName"
            placeholder="Enter medicine name"
            required>


        <!-- Dosage -->

        <label>
            Dosage
        </label>

        <input
            type="text"
            name="dosage"
            placeholder="Example: 1 tablet">


        <!-- Duration -->

        <label>
            Duration
        </label>

        <input
            type="text"
            name="duration"
            placeholder="Example: 3 days">


        <!-- Instructions -->

        <label>
            Instructions
        </label>

        <textarea
            name="instructions"
            placeholder="Example: Take after food"></textarea>


        <!-- Save -->

        <button type="submit">

            💾 Save Prescription

        </button>


    </form>


    <!-- Back -->

    <a
        class="back"
        href="<%=request.getContextPath()%>/AppointmentListServlet">

        ← Back to Appointments

    </a>


</div>


</body>

</html>