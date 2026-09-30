<%@ page language="java"
         contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@ page import="java.util.List" %>

<%
    List<?> medicalRecords =
            (List<?>) request.getAttribute("medicalRecords");
%>

<!DOCTYPE html>

<html>

<head>

    <meta charset="UTF-8">

    <title>Medical Records</title>

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
            width: 92%;
            max-width: 1100px;
            margin: 40px auto;
        }

        .header {
            background: white;
            padding: 25px;
            border-radius: 12px;
            text-align: center;
            box-shadow: 0 3px 10px rgba(0,0,0,0.10);
            margin-bottom: 25px;
        }

        .header h1 {
            margin: 0;
            color: #1e3a5f;
        }

        .header p {
            color: #666;
            margin-bottom: 0;
        }

        .record {
            background: white;
            padding: 22px;
            margin-bottom: 20px;
            border-radius: 12px;
            box-shadow: 0 3px 10px rgba(0,0,0,0.10);
        }

        .record h2 {
            margin-top: 0;
            color: #1e3a5f;
            border-bottom: 1px solid #ddd;
            padding-bottom: 10px;
        }

        .section-title {
            margin-top: 22px;
            margin-bottom: 10px;
            color: #1e3a5f;
            font-size: 18px;
            font-weight: bold;
        }

        .details {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 12px;
        }

        .detail {
            background: #f8fafc;
            padding: 12px;
            border-radius: 7px;
        }

        .label {
            font-weight: bold;
            color: #555;
        }

        .value {
            margin-top: 5px;
            color: #222;
            word-break: break-word;
        }

        .problem {
            margin-top: 15px;
            background: #fff4e6;
            padding: 14px;
            border-radius: 7px;
        }

        .status {
            margin-top: 15px;
            background: #eef5ff;
            padding: 14px;
            border-radius: 7px;
        }

        .medical {
            margin-top: 15px;
            background: #eef8ee;
            padding: 15px;
            border-radius: 7px;
        }

        .prescription {
            margin-top: 15px;
            background: #f5efff;
            padding: 15px;
            border-radius: 7px;
        }

        .empty {
            background: white;
            padding: 40px;
            text-align: center;
            border-radius: 12px;
            color: #777;
            box-shadow: 0 3px 10px rgba(0,0,0,0.10);
        }

        .back {
            display: inline-block;
            margin-top: 10px;
            padding: 11px 20px;
            background: #1e3a5f;
            color: white;
            text-decoration: none;
            border-radius: 6px;
        }

        .back:hover {
            background: #2d527d;
        }

        @media (max-width: 700px) {

            .details {
                grid-template-columns: 1fr;
            }

        }

    </style>

</head>

<body>

<div class="container">

    <div class="header">

        <h1>Medical Records</h1>

        <p>
            View medical information of your patients.
        </p>

    </div>


<%
    if (medicalRecords == null ||
        medicalRecords.isEmpty()) {
%>

    <div class="empty">

        <h3>No Medical Records Found</h3>

        <p>
            No appointments or medical records are available
            for this doctor.
        </p>

    </div>

<%
    } else {

        for (Object obj : medicalRecords) {

            Object[] record =
                    (Object[]) obj;
%>


    <div class="record">

        <h2>
            Patient: <%= record[1] != null
                    ? record[1]
                    : "Unknown" %>
        </h2>


        <!-- ================= PATIENT INFORMATION ================= -->

        <div class="section-title">
            Patient Information
        </div>

        <div class="details">

            <div class="detail">

                <div class="label">
                    Patient ID
                </div>

                <div class="value">
                    <%= record[0] %>
                </div>

            </div>


            <div class="detail">

                <div class="label">
                    Email
                </div>

                <div class="value">
                    <%= record[2] != null
                            ? record[2]
                            : "Not available" %>
                </div>

            </div>


            <div class="detail">

                <div class="label">
                    Phone Number
                </div>

                <div class="value">
                    <%= record[3] != null
                            ? record[3]
                            : "Not available" %>
                </div>

            </div>


            <div class="detail">

                <div class="label">
                    Gender
                </div>

                <div class="value">
                    <%= record[4] != null
                            ? record[4]
                            : "Not available" %>
                </div>

            </div>


            <div class="detail">

                <div class="label">
                    Date of Birth
                </div>

                <div class="value">
                    <%= record[5] != null
                            ? record[5]
                            : "Not available" %>
                </div>

            </div>


            <div class="detail">

                <div class="label">
                    Blood Group
                </div>

                <div class="value">
                    <%= record[7] != null
                            ? record[7]
                            : "Not available" %>
                </div>

            </div>

        </div>


        <div class="detail" style="margin-top:12px;">

            <div class="label">
                Address
            </div>

            <div class="value">
                <%= record[6] != null
                        ? record[6]
                        : "Not available" %>
            </div>

        </div>


        <div class="detail" style="margin-top:12px;">

            <div class="label">
                Medical History
            </div>

            <div class="value">
                <%= record[8] != null &&
                    !record[8].toString().trim().isEmpty()
                        ? record[8]
                        : "No medical history available" %>
            </div>

        </div>


        <!-- ================= APPOINTMENT INFORMATION ================= -->

        <div class="section-title">
            Appointment Information
        </div>

        <div class="details">

            <div class="detail">

                <div class="label">
                    Appointment ID
                </div>

                <div class="value">
                    <%= record[9] %>
                </div>

            </div>


            <div class="detail">

                <div class="label">
                    Appointment Date
                </div>

                <div class="value">
                    <%= record[11] != null
                            ? record[11]
                            : "Not available" %>
                </div>

            </div>

        </div>


        <div class="problem">

            <div class="label">
                Current Problem
            </div>

            <div class="value">

                <%= record[10] != null
                        ? record[10]
                        : "Not available" %>

            </div>

        </div>


        <div class="status">

            <div class="label">
                Appointment Status
            </div>

            <div class="value">

                <%= record[12] != null
                        ? record[12]
                        : "Not available" %>

            </div>

        </div>


        <!-- ================= MEDICAL INFORMATION ================= -->

        <div class="medical">

            <div class="section-title">
                Doctor Medical Information
            </div>


            <div class="detail">

                <div class="label">
                    Diagnosis
                </div>

                <div class="value">

                    <%= record[13] != null &&
                        !record[13].toString().trim().isEmpty()
                            ? record[13]
                            : "Not added yet" %>

                </div>

            </div>


            <div class="detail" style="margin-top:10px;">

                <div class="label">
                    Doctor Notes
                </div>

                <div class="value">

                    <%= record[14] != null &&
                        !record[14].toString().trim().isEmpty()
                            ? record[14]
                            : "No doctor notes available" %>

                </div>

            </div>


            <div class="detail" style="margin-top:10px;">

                <div class="label">
                    Medical Record Prescription
                </div>

                <div class="value">

                    <%= record[15] != null &&
                        !record[15].toString().trim().isEmpty()
                            ? record[15]
                            : "No medical record prescription available" %>

                </div>

            </div>

        </div>


        <!-- ================= PRESCRIPTION ================= -->

        <div class="prescription">

            <div class="section-title">
                Prescription
            </div>


            <div class="detail">

                <div class="label">
                    Medicine Name
                </div>

                <div class="value">

                    <%= record[16] != null &&
                        !record[16].toString().trim().isEmpty()
                            ? record[16]
                            : "No prescription available" %>

                </div>

            </div>


            <div class="detail" style="margin-top:10px;">

                <div class="label">
                    Dosage
                </div>

                <div class="value">

                    <%= record[17] != null &&
                        !record[17].toString().trim().isEmpty()
                            ? record[17]
                            : "Not available" %>

                </div>

            </div>


            <div class="detail" style="margin-top:10px;">

                <div class="label">
                    Duration
                </div>

                <div class="value">

                    <%= record[18] != null &&
                        !record[18].toString().trim().isEmpty()
                            ? record[18]
                            : "Not available" %>

                </div>

            </div>


            <div class="detail" style="margin-top:10px;">

                <div class="label">
                    Instructions
                </div>

                <div class="value">

                    <%= record[19] != null &&
                        !record[19].toString().trim().isEmpty()
                            ? record[19]
                            : "No instructions available" %>

                </div>

            </div>

        </div>

    </div>


<%
        }
    }
%>


    <!-- BACK BUTTON -->

    <a class="back"
       href="<%= request.getContextPath() %>/DoctorDashboardServlet">

        Back to Doctor Dashboard

    </a>


</div>

</body>

</html>