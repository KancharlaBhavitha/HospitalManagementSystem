<%@ page import="java.util.List" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Medicine Prescriptions</title>

    <style>
        body {
            margin: 0;
            font-family: Arial, sans-serif;
            background: #f4f7fb;
        }

        .container {
            width: 80%;
            margin: 50px auto;
            background: white;
            padding: 30px;
            border-radius: 12px;
            box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
        }

        h1 {
            text-align: center;
            color: #333;
            margin-bottom: 30px;
        }

        table {
            width: 100%;
            border-collapse: collapse;
        }

        th,
        td {
            padding: 14px;
            border-bottom: 1px solid #ddd;
            text-align: left;
        }

        th {
            background: #f0f3f7;
            color: #333;
        }

        .view-btn {
            display: inline-block;
            padding: 8px 18px;
            background: #007bff;
            color: white;
            text-decoration: none;
            border-radius: 6px;
        }

        .view-btn:hover {
            background: #0056b3;
        }

        .empty {
            text-align: center;
            padding: 30px;
            color: #777;
        }

        .back-btn {
            display: inline-block;
            margin-top: 25px;
            padding: 10px 20px;
            background: #555;
            color: white;
            text-decoration: none;
            border-radius: 6px;
        }

        .back-btn:hover {
            background: #333;
        }
    </style>
</head>

<body>

<div class="container">

    <h1>Medicine Prescriptions</h1>

    <%
    @SuppressWarnings("unchecked")
    List<Object[]> prescriptions =
        (List<Object[]>) request.getAttribute("prescriptions");
    %>

    <% if (prescriptions != null && !prescriptions.isEmpty()) { %>

        <table>

            <tr>
                <th>Patient Name</th>
                <th>Action</th>
            </tr>

            <%
                for (Object[] row : prescriptions) {

                    int appointmentId = (Integer) row[1];
                    String patientName = (String) row[3];
            %>

            <tr>

                <td>
                    <%= patientName %>
                </td>

                <td>
                    <a class="view-btn"
                       href="<%= request.getContextPath() %>/ViewPrescriptionServlet?appointmentId=<%= appointmentId %>">
                        View
                    </a>
                </td>

            </tr>

            <%
                }
            %>

        </table>

    <% } else { %>

        <div class="empty">
            No medicine prescriptions available.
        </div>

    <% } %>

    <a class="back-btn"
       href="<%= request.getContextPath() %>/DoctorDashboardServlet">
        Back to Doctor Dashboard
    </a>

</div>

</body>
</html>