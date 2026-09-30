<%@ page import="com.hospital.model.Prescription" %>

<%
    Prescription prescription =
        (Prescription) request.getAttribute("prescription");
%>

<!DOCTYPE html>
<html>
<head>

    <meta charset="UTF-8">

    <title>View Prescription</title>

    <style>

        body {
            font-family: Arial, sans-serif;
            background: #f4f6f9;
            margin: 0;
            padding: 30px;
        }

        .container {
            width: 650px;
            margin: auto;
            background: white;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 4px 12px rgba(0,0,0,0.15);
        }

        h2 {
            text-align: center;
            color: #333;
            margin-bottom: 25px;
        }

        .details {
            margin-top: 10px;
        }

        .row {
            display: flex;
            padding: 12px;
            border-bottom: 1px solid #ddd;
        }

        .label {
            width: 180px;
            font-weight: bold;
            color: #555;
        }

        .value {
            flex: 1;
            color: #333;
        }

        .buttons {
            margin-top: 30px;
            text-align: center;
        }

        .btn {
            display: inline-block;
            padding: 10px 18px;
            margin: 5px;
            border: none;
            border-radius: 6px;
            text-decoration: none;
            color: white;
            cursor: pointer;
            font-size: 15px;
        }

        .print {
            background: #007bff;
        }

        .print:hover {
            background: #0056b3;
        }

        .store {
            background: #28a745;
        }

        .store:hover {
            background: #218838;
        }

        .back {
            background: #6c757d;
        }

        .back:hover {
            background: #545b62;
        }

        @media print {

            body {
                background: white;
                padding: 0;
            }

            .container {
                width: 100%;
                box-shadow: none;
            }

            .buttons {
                display: none;
            }
        }

    </style>

</head>

<body>

<div class="container">

<%
    if (prescription == null) {
%>

    <h2>Prescription not found</h2>

    <div class="buttons">

        <a class="btn back"
           href="<%= request.getContextPath() %>/PrescriptionListServlet">
            Back to Prescriptions
        </a>

    </div>

<%
    } else {
%>

    <h2>💊 Prescription Details</h2>

    <div class="details">

        <div class="row">
            <div class="label">Prescription ID</div>
            <div class="value">
                <%= prescription.getPrescriptionId() %>
            </div>
        </div>

        <div class="row">
            <div class="label">Appointment ID</div>
            <div class="value">
                <%= prescription.getAppointmentId() %>
            </div>
        </div>

        <div class="row">
            <div class="label">Patient ID</div>
            <div class="value">
                <%= prescription.getPatientId() %>
            </div>
        </div>

        <div class="row">
            <div class="label">Medicine</div>
            <div class="value">
                <%= prescription.getMedicineName() %>
            </div>
        </div>

        <div class="row">
            <div class="label">Dosage</div>
            <div class="value">
                <%= prescription.getDosage() %>
            </div>
        </div>

        <div class="row">
            <div class="label">Duration</div>
            <div class="value">
                <%= prescription.getDuration() %>
            </div>
        </div>

        <div class="row">
            <div class="label">Instructions</div>
            <div class="value">
                <%= prescription.getInstructions() %>
            </div>
        </div>

        <div class="row">
            <div class="label">Prescription Date</div>
            <div class="value">
                <%= prescription.getPrescriptionDate() %>
            </div>
        </div>

    </div>


    <!-- BUTTONS -->

    <div class="buttons">

        <button class="btn print"
                onclick="window.print()">
            Print
        </button>


        <a class="btn store"
           href="<%= request.getContextPath() %>/MedicineStoreServlet?appointmentId=<%= prescription.getAppointmentId() %>">
             Medical Store
        </a>


        <a class="btn back"
           href="<%= request.getContextPath() %>/PrescriptionListServlet">
            Back to Prescriptions
        </a>

    </div>

<%
    }
%>

</div>

</body>
</html>