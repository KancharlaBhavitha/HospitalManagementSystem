package com.hospital.Servlets;

import java.io.IOException;
import java.util.List;

import com.hospital.dao.BillingDAO;
import com.hospital.dao.MedicineStoreDAO;
import com.hospital.dao.PrescriptionDAO;
import com.hospital.model.MedicineStore;
import com.hospital.model.Prescription;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/MedicineStoreServlet")
public class MedicineStoreServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private MedicineStoreDAO medicineStoreDAO;
    private PrescriptionDAO prescriptionDAO;
    private BillingDAO billingDAO;

    @Override
    public void init() throws ServletException {

        medicineStoreDAO = new MedicineStoreDAO();
        prescriptionDAO = new PrescriptionDAO();
        billingDAO = new BillingDAO();
    }


    // =====================================================
    // OPEN MEDICAL STORE
    // =====================================================
    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session =
                request.getSession(false);

        if (session == null) {

            response.sendRedirect(
                    request.getContextPath()
                    + "/pages/login_page.html"
            );

            return;
        }

        Integer patientId =
                (Integer) session.getAttribute("patientId");

        Integer doctorId =
                (Integer) session.getAttribute("doctorId");


        // Patient OR Doctor can open Medical Store
        if (patientId == null && doctorId == null) {

            response.sendRedirect(
                    request.getContextPath()
                    + "/pages/login_page.html"
            );

            return;
        }


        String appointmentIdParam =
                request.getParameter("appointmentId");

        if (appointmentIdParam == null
                || appointmentIdParam.trim().isEmpty()) {

            response.setContentType(
                    "text/html;charset=UTF-8"
            );

            response.getWriter().println(
                    "<h3 style='color:red;text-align:center;'>"
                    + "Appointment ID is missing."
                    + "</h3>"
            );

            return;
        }


        try {

            int appointmentId =
                    Integer.parseInt(
                            appointmentIdParam
                    );


            int finalPatientId = 0;


            // ---------------------------------------------
            // Patient login
            // ---------------------------------------------
            if (patientId != null) {

                finalPatientId = patientId;

            }

            // ---------------------------------------------
            // Doctor login
            // ---------------------------------------------
            else {

                Prescription prescription =
                        prescriptionDAO
                        .getPrescriptionByAppointmentId(
                                appointmentId
                        );

                if (prescription != null) {

                    finalPatientId =
                            prescription.getPatientId();
                }
            }


            if (finalPatientId == 0) {

                response.setContentType(
                        "text/html;charset=UTF-8"
                );

                response.getWriter().println(
                        "<h3 style='color:red;text-align:center;'>"
                        + "Patient information not found."
                        + "</h3>"
                );

                return;
            }


            // ---------------------------------------------
            // Get prescription medicines
            // ---------------------------------------------
            List<MedicineStore> medicineList =
                    medicineStoreDAO
                    .getPrescriptionMedicines(
                            appointmentId,
                            finalPatientId
                    );


            if (medicineList == null
                    || medicineList.isEmpty()) {

                response.setContentType(
                        "text/html;charset=UTF-8"
                );

                response.getWriter().println(
                        "<h3 style='color:red;text-align:center;'>"
                        + "No prescription medicines found."
                        + "</h3>"
                );

                return;
            }


            MedicineStore[] medicines =
                    medicineList.toArray(
                            new MedicineStore[0]
                    );


            request.setAttribute(
                    "medicines",
                    medicines
            );

            request.setAttribute(
                    "appointmentId",
                    appointmentId
            );


            request.getRequestDispatcher(
                    "/pages/medicine_store.jsp"
            ).forward(
                    request,
                    response
            );


        } catch (NumberFormatException e) {

            response.setContentType(
                    "text/html;charset=UTF-8"
            );

            response.getWriter().println(
                    "<h3 style='color:red;text-align:center;'>"
                    + "Invalid Appointment ID."
                    + "</h3>"
            );

        } catch (Exception e) {

            e.printStackTrace();

            response.setContentType(
                    "text/html;charset=UTF-8"
            );

            response.getWriter().println(
                    "<h3 style='color:red;text-align:center;'>"
                    + "Error opening Medical Store."
                    + "</h3>"
            );
        }
    }


    // =====================================================
    // SAVE MEDICINE AMOUNTS + CREATE MEDICINE BILL
    // =====================================================
    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {


        HttpSession session =
                request.getSession(false);


        if (session == null) {

            response.sendRedirect(
                    request.getContextPath()
                    + "/pages/login_page.html"
            );

            return;
        }


        // =================================================
        // IMPORTANT:
        // Medicine payment must be done by PATIENT
        // =================================================
        Integer patientId =
                (Integer) session.getAttribute("patientId");


        Integer doctorId =
                (Integer) session.getAttribute("doctorId");


        System.out.println(
                "=========================================="
        );

        System.out.println(
                "MEDICINE STORE PAYMENT REQUEST"
        );

        System.out.println(
                "patientId = " + patientId
        );

        System.out.println(
                "doctorId = " + doctorId
        );

        System.out.println(
                "=========================================="
        );


        // -------------------------------------------------
        // Patient session missing
        // -------------------------------------------------
        if (patientId == null) {

            response.setContentType(
                    "text/html;charset=UTF-8"
            );

            response.getWriter().println(
                    "<html>"
                    + "<head>"
                    + "<title>Patient Login Required</title>"
                    + "</head>"
                    + "<body style='font-family:Arial;text-align:center;"
                    + "padding-top:100px;'>"

                    + "<h2 style='color:red;'>"
                    + "Patient Login Required"
                    + "</h2>"

                    + "<p>"
                    + "Please login as patient before making medicine payment."
                    + "</p>"

                    + "<a href='"
                    + request.getContextPath()
                    + "/pages/login_page.html'"
                    + " style='display:inline-block;"
                    + "padding:10px 20px;"
                    + "background:#673ab7;"
                    + "color:white;"
                    + "text-decoration:none;"
                    + "border-radius:5px;'>"
                    + "Go to Login"
                    + "</a>"

                    + "</body>"
                    + "</html>"
            );

            return;
        }


        try {

            // =================================================
            // Appointment ID
            // =================================================
            String appointmentIdValue =
                    request.getParameter(
                            "appointmentId"
                    );


            if (appointmentIdValue == null
                    || appointmentIdValue.trim().isEmpty()) {

                response.setContentType(
                        "text/html;charset=UTF-8"
                );

                response.getWriter().println(
                        "<h3 style='color:red;text-align:center;'>"
                        + "Appointment ID is missing."
                        + "</h3>"
                );

                return;
            }


            int appointmentId =
                    Integer.parseInt(
                            appointmentIdValue
                    );


            // =================================================
            // Get form arrays
            // =================================================
            String[] prescriptionIds =
                    request.getParameterValues(
                            "prescriptionId"
                    );

            String[] medicineNames =
                    request.getParameterValues(
                            "medicineName"
                    );

            String[] dosages =
                    request.getParameterValues(
                            "dosage"
                    );

            String[] durations =
                    request.getParameterValues(
                            "duration"
                    );

            String[] instructions =
                    request.getParameterValues(
                            "instructions"
                    );

            String[] amounts =
                    request.getParameterValues(
                            "amount"
                    );


            if (prescriptionIds == null
                    || medicineNames == null
                    || dosages == null
                    || durations == null
                    || instructions == null
                    || amounts == null) {

                response.setContentType(
                        "text/html;charset=UTF-8"
                );

                response.getWriter().println(
                        "<h3 style='color:red;text-align:center;'>"
                        + "Medicine details are missing."
                        + "</h3>"
                );

                return;
            }


            // =================================================
            // Get prescription
            // =================================================
            Prescription prescription =
                    prescriptionDAO
                    .getPrescriptionByAppointmentId(
                            appointmentId
                    );


            if (prescription == null) {

                response.setContentType(
                        "text/html;charset=UTF-8"
                );

                response.getWriter().println(
                        "<h3 style='color:red;text-align:center;'>"
                        + "Prescription not found."
                        + "</h3>"
                );

                return;
            }


            // Doctor who prescribed medicine
            int prescriptionDoctorId =
                    prescription.getDoctorId();


            // =================================================
            // Save medicine amounts
            // =================================================
            double totalAmount = 0.0;

            int savedMedicineCount = 0;


            for (int i = 0;
                    i < prescriptionIds.length;
                    i++) {


                if (i >= medicineNames.length
                        || i >= dosages.length
                        || i >= durations.length
                        || i >= instructions.length
                        || i >= amounts.length) {

                    break;
                }


                if (amounts[i] == null
                        || amounts[i].trim().isEmpty()) {

                    continue;
                }


                double amount =
                        Double.parseDouble(
                                amounts[i]
                        );


                if (amount <= 0) {

                    response.setContentType(
                            "text/html;charset=UTF-8"
                    );

                    response.getWriter().println(
                            "<h3 style='color:red;text-align:center;'>"
                            + "Medicine amount must be greater than 0."
                            + "</h3>"
                    );

                    return;
                }


                MedicineStore medicine =
                        new MedicineStore();


                medicine.setPrescriptionId(
                        Integer.parseInt(
                                prescriptionIds[i]
                        )
                );


                medicine.setAppointmentId(
                        appointmentId
                );


                medicine.setPatientId(
                        patientId
                );


                medicine.setMedicineName(
                        medicineNames[i]
                );


                medicine.setDosage(
                        dosages[i]
                );


                medicine.setDuration(
                        durations[i]
                );


                medicine.setInstructions(
                        instructions[i]
                );


                medicine.setAmount(
                        amount
                );


                int medicineId =
                        medicineStoreDAO
                        .saveMedicineAmount(
                                medicine
                        );


                if (medicineId > 0) {

                    totalAmount =
                            totalAmount + amount;

                    savedMedicineCount++;
                }
            }


            // =================================================
            // Validate medicine amount
            // =================================================
            if (savedMedicineCount == 0
                    || totalAmount <= 0) {

                response.setContentType(
                        "text/html;charset=UTF-8"
                );

                response.getWriter().println(
                        "<h3 style='color:red;text-align:center;'>"
                        + "Please enter a valid medicine amount."
                        + "</h3>"
                );

                return;
            }


            // =================================================
            // Create Billing record
            // =================================================
            int billId =
                    billingDAO.createMedicineBill(
                            appointmentId,
                            patientId,
                            prescriptionDoctorId,
                            totalAmount
                    );


            if (billId <= 0) {

                response.setContentType(
                        "text/html;charset=UTF-8"
                );

                response.getWriter().println(
                        "<h3 style='color:red;text-align:center;'>"
                        + "Medicine bill creation failed."
                        + "</h3>"
                );

                return;
            }


            System.out.println(
                    "Medicine Bill Created"
            );

            System.out.println(
                    "Bill ID = " + billId
            );

            System.out.println(
                    "Medicine Amount = ₹"
                    + totalAmount
            );


            // =================================================
            // Go to EXISTING payment page
            // =================================================
            response.sendRedirect(
                    request.getContextPath()
                    + "/pages/payment_page.html"
                    + "?billId="
                    + billId
                    + "&amount="
                    + totalAmount
            );


        } catch (NumberFormatException e) {

            e.printStackTrace();

            response.setContentType(
                    "text/html;charset=UTF-8"
            );

            response.getWriter().println(
                    "<h3 style='color:red;text-align:center;'>"
                    + "Invalid appointment ID or medicine amount."
                    + "</h3>"
            );


        } catch (Exception e) {

            e.printStackTrace();

            response.setContentType(
                    "text/html;charset=UTF-8"
            );

            response.getWriter().println(
                    "<h3 style='color:red;text-align:center;'>"
                    + "Error while processing medicine payment."
                    + "</h3>"
            );
        }
    }
}