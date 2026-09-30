<%@ page import="java.util.List" %>
<%@ page import="com.hospital.model.Doctor" %>
<%@ page import="java.net.URLEncoder" %>

<!DOCTYPE html>

<html>

<head>

    <meta charset="UTF-8">

    <title>Available Doctors</title>

    <style>

        body {
            margin: 0;
            font-family: Arial, sans-serif;
            background-color: #f2f6fa;
        }

        .container {
            width: 90%;
            margin: 40px auto;
        }

        h2 {
            text-align: center;
            color: darkblue;
        }

        .search-info {
            text-align: center;
            margin: 20px;
            font-size: 18px;
        }

        .doctor-card {
            background-color: white;
            width: 350px;
            margin: 20px auto;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 0 10px #aaa;
        }

        .doctor-card h3 {
            color: darkblue;
            margin-top: 0;
        }

        .doctor-card p {
            margin: 8px 0;
        }

        .book-btn {
            display: block;
            text-align: center;
            margin-top: 15px;
            padding: 10px;
            background-color: green;
            color: white;
            text-decoration: none;
            border-radius: 5px;
        }

        .book-btn:hover {
            background-color: darkgreen;
        }

        .back {
            display: block;
            width: 220px;
            margin: 20px auto;
            text-align: center;
            padding: 10px;
            background-color: darkblue;
            color: white;
            text-decoration: none;
            border-radius: 5px;
        }

        .back:hover {
            background-color: navy;
        }

    </style>

</head>

<body>

<div class="container">

    <h2>Available Doctors</h2>

    <%

        String disease =
            (String) request.getAttribute("disease");

        @SuppressWarnings("unchecked")
        List<Doctor> doctorList =
            (List<Doctor>) request.getAttribute("doctorList");

    %>

    <div class="search-info">

        Doctors available for:

        <strong>

            <%= disease != null
                ? disease
                : "Unknown Disease" %>

        </strong>

    </div>


    <%

        if (doctorList != null &&
            !doctorList.isEmpty()) {

            for (Doctor doctor : doctorList) {

    %>

    <div class="doctor-card">

        <h3>

            <%= doctor.getFullName() %>

        </h3>

        <p>

            <strong>Doctor ID:</strong>

            <%= doctor.getDoctorId() %>

        </p>

        <p>

            <strong>Specialization:</strong>

            <%= doctor.getSpecialization() %>

        </p>

        <p>

            <strong>Diseases:</strong>

            <%= doctor.getDisease() %>

        </p>

        <p>

            <strong>Experience:</strong>

            <%= doctor.getExperience() %> years

        </p>

        <p>

            <strong>Phone:</strong>

            <%= doctor.getPhoneNumber() %>

        </p>

        <p>

            <strong>Email:</strong>

            <%= doctor.getEmail() %>

        </p>

        <p>

            <strong>Consultation Fee:</strong>

            &#8377;<%= doctor.getConsultationFee() %>

        </p>


        <!-- Book Appointment -->

        <a
            class="book-btn"
            href="<%= request.getContextPath() %>/pages/appointment_page.html?doctorId=<%= doctor.getDoctorId() %>&disease=<%= URLEncoder.encode(disease != null ? disease : "", "UTF-8") %>"
        >

            Book Appointment

        </a>

    </div>

    <%

            }

        } else {

    %>

    <div class="search-info">

        <h3>No Doctors Found</h3>

        <p>

            No doctor is available for the entered disease.

        </p>

    </div>

    <%

        }

    %>


    <!-- Search Again -->

    <a
        class="back"
        href="<%= request.getContextPath() %>/pages/find_doctor.html"
    >

        Search Again

    </a>


    <!-- Patient Dashboard -->

    <a
        class="back"
        href="<%= request.getContextPath() %>/pages/patient_dashboard.html"
    >

        Back to Patient Dashboard

    </a>

</div>

</body>

</html>