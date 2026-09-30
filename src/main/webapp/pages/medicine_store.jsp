<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="com.hospital.model.MedicineStore" %>

<!DOCTYPE html>

<html>

<head>

    <meta charset="UTF-8">

    <title>Medical Store</title>

    <style>

        body {
            font-family: Arial, sans-serif;
            background: #f4f7fb;
            margin: 0;
            padding: 30px;
        }

        .container {
            max-width: 1100px;
            margin: auto;
            background: white;
            padding: 30px;
            border-radius: 12px;
            box-shadow: 0 4px 15px rgba(0,0,0,0.1);
        }

        h1 {
            text-align: center;
            color: #333;
            margin-bottom: 25px;
        }

        table {
            width: 100%;
            border-collapse: collapse;
        }

        th {
            background: #673ab7;
            color: white;
            padding: 14px;
        }

        td {
            padding: 12px;
            border-bottom: 1px solid #ddd;
            text-align: center;
        }

        input[type="number"] {
            width: 100px;
            padding: 8px;
            border: 1px solid #aaa;
            border-radius: 5px;
        }

        .total-box {
            text-align: right;
            margin-top: 25px;
            font-size: 20px;
            font-weight: bold;
        }

        .pay-btn {
            display: block;
            margin: 25px auto 0;
            padding: 12px 35px;
            background: #673ab7;
            color: white;
            border: none;
            border-radius: 6px;
            font-size: 16px;
            cursor: pointer;
        }

        .pay-btn:hover {
            background: #512da8;
        }

        .empty {
            text-align: center;
            color: red;
            padding: 30px;
        }

    </style>

</head>

<body>

<div class="container">

    <h1>Medical Store</h1>

    <%
        MedicineStore[] medicines =
                (MedicineStore[])
                request.getAttribute("medicines");

        Integer appointmentId =
                (Integer)
                request.getAttribute("appointmentId");
    %>


    <% if (medicines != null && medicines.length > 0) { %>

    <form action="<%= request.getContextPath() %>/MedicineStoreServlet"
          method="post">

        <input type="hidden"
               name="appointmentId"
               value="<%= appointmentId %>">


        <table>

            <tr>

                <th>Medicine Name</th>

                <th>Dosage</th>

                <th>Duration</th>

                <th>Instructions</th>

                <th>Amount (₹)</th>

            </tr>


            <%

                for (MedicineStore medicine : medicines) {

            %>

            <tr>

                <td>

                    <%= medicine.getMedicineName() %>

                    <input type="hidden"
                           name="prescriptionId"
                           value="<%= medicine.getPrescriptionId() %>">

                    <input type="hidden"
                           name="medicineName"
                           value="<%= medicine.getMedicineName() %>">

                </td>


                <td>

                    <%= medicine.getDosage() %>

                    <input type="hidden"
                           name="dosage"
                           value="<%= medicine.getDosage() %>">

                </td>


                <td>

                    <%= medicine.getDuration() %>

                    <input type="hidden"
                           name="duration"
                           value="<%= medicine.getDuration() %>">

                </td>


                <td>

                    <%= medicine.getInstructions() %>

                    <input type="hidden"
                           name="instructions"
                           value="<%= medicine.getInstructions() %>">

                </td>


                <td>

                    <input type="number"
                           name="amount"
                           min="0"
                           step="0.01"
                           placeholder="₹ Amount"
                           required>

                </td>

            </tr>


            <%

                }

            %>

        </table>


        <div class="total-box">

            Amounts will be calculated at checkout.

        </div>


        <button type="submit"
                class="pay-btn">

            Continue to Payment

        </button>

    </form>


    <% } else { %>


        <div class="empty">

            No prescription medicines found.

        </div>


    <% } %>

</div>

</body>

</html>