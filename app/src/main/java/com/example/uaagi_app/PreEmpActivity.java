package com.example.uaagi_app;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spannable;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;

public class PreEmpActivity extends AppCompatActivity {

    // UI Components
    private TextInputLayout firstNameLayout, middleNameLayout, lastNameLayout;
    private TextInputLayout dobLayout, ageLayout, genderLayout, religionLayout;
    private TextInputLayout civilStatusLayout, nationalityLayout, heightLayout, weightLayout, bloodTypeLayout;
    private TextInputLayout phoneLayout, otherPhoneLayout, telephoneLayout;
    private TextInputLayout regionLayout, cityLayout, barangayLayout, streetLayout;
    private TextInputLayout currentAddressLayout, permanentAddressLayout;

    private TextInputEditText firstNameInput, middleNameInput, lastNameInput;
    private TextInputEditText dobInput, ageInput;
    private TextInputEditText heightInput, weightInput;
    private TextInputEditText phoneInput, otherPhoneInput, telephoneInput, streetInput;
    private TextInputEditText currentAddressInput, permanentAddressInput;

    private AutoCompleteTextView genderSpinner, religionSpinner, civilStatusSpinner;
    private AutoCompleteTextView nationalitySpinner, bloodTypeSpinner;
    private AutoCompleteTextView regionSpinner, citySpinner, barangaySpinner;

    private Button btnPrevious, btnNext, btnSubmit;

    private int currentStep = 1;
    private final int totalSteps = 7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pre_emp);

        initializeViews();
        setupSpinners();
        setupHintsWithAsterisk();
        setupListeners();
    }

    private void initializeViews() {
        // Personal Information
        firstNameLayout = findViewById(R.id.firstNameLayout);
        middleNameLayout = findViewById(R.id.middleNameLayout);
        lastNameLayout = findViewById(R.id.lastNameLayout);
        dobLayout = findViewById(R.id.dobLayout);
        ageLayout = findViewById(R.id.ageLayout);
        genderLayout = findViewById(R.id.genderLayout);
        religionLayout = findViewById(R.id.religionLayout);
        civilStatusLayout = findViewById(R.id.civilStatusLayout);
        nationalityLayout = findViewById(R.id.nationalityLayout);
        heightLayout = findViewById(R.id.heightLayout);
        weightLayout = findViewById(R.id.weightLayout);
        bloodTypeLayout = findViewById(R.id.bloodTypeLayout);

        firstNameInput = findViewById(R.id.firstNameInput);
        middleNameInput = findViewById(R.id.middleNameInput);
        lastNameInput = findViewById(R.id.lastNameInput);
        dobInput = findViewById(R.id.dobInput);
        ageInput = findViewById(R.id.ageInput);
        heightInput = findViewById(R.id.heightInput);
        weightInput = findViewById(R.id.weightInput);

        genderSpinner = findViewById(R.id.genderSpinner);
        religionSpinner = findViewById(R.id.religionSpinner);
        civilStatusSpinner = findViewById(R.id.civilStatusSpinner);
        nationalitySpinner = findViewById(R.id.nationalitySpinner);
        bloodTypeSpinner = findViewById(R.id.bloodTypeSpinner);

        // Contact Information
        phoneLayout = findViewById(R.id.phoneLayout);
        otherPhoneLayout = findViewById(R.id.otherPhoneLayout);
        telephoneLayout = findViewById(R.id.telephoneLayout);
        regionLayout = findViewById(R.id.regionLayout);
        cityLayout = findViewById(R.id.cityLayout);
        barangayLayout = findViewById(R.id.barangayLayout);
        streetLayout = findViewById(R.id.streetLayout);
        currentAddressLayout = findViewById(R.id.currentAddressLayout);
        permanentAddressLayout = findViewById(R.id.permanentAddressLayout);

        phoneInput = findViewById(R.id.phoneInput);
        otherPhoneInput = findViewById(R.id.otherPhoneInput);
        telephoneInput = findViewById(R.id.telephoneInput);
        streetInput = findViewById(R.id.streetInput);
        currentAddressInput = findViewById(R.id.currentAddressInput);
        permanentAddressInput = findViewById(R.id.permanentAddressInput);

        regionSpinner = findViewById(R.id.regionSpinner);
        citySpinner = findViewById(R.id.citySpinner);
        barangaySpinner = findViewById(R.id.barangaySpinner);

        // Buttons
        btnPrevious = findViewById(R.id.btnPrevious);
        btnNext = findViewById(R.id.btnNext);
        btnSubmit = findViewById(R.id.btnSubmit);
    }

    private void setupSpinners() {
        // Gender
        String[] genders = {"Male", "Female", "Non-binary", "Other", "Prefer not to say"};
        ArrayAdapter<String> genderAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, genders);
        genderSpinner.setAdapter(genderAdapter);

        // Religion
        String[] religions = {"Roman Catholic", "Christian", "Iglesia ni Cristo", "Evangelical",
                "Jehovah's Witness", "Seventh-day Adventist", "Islam", "Other", "Prefer not to say"};
        ArrayAdapter<String> religionAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, religions);
        religionSpinner.setAdapter(religionAdapter);

        // Civil Status
        String[] civilStatus = {"Single", "Married", "Widowed", "Separated", "Divorced"};
        ArrayAdapter<String> civilAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, civilStatus);
        civilStatusSpinner.setAdapter(civilAdapter);

        // Nationality
        String[] nationalities = {"Filipino", "American", "Chinese", "Japanese", "Korean",
                "British", "Australian", "Canadian", "Other"};
        ArrayAdapter<String> nationalityAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, nationalities);
        nationalitySpinner.setAdapter(nationalityAdapter);

        // Blood Type
        String[] bloodTypes = {"A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"};
        ArrayAdapter<String> bloodAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, bloodTypes);
        bloodTypeSpinner.setAdapter(bloodAdapter);

        // Region
        String[] regions = {"NCR", "Region I", "Region II", "Region III", "Region IV-A",
                "Region IV-B", "Region V", "Region VI", "Region VII", "Region VIII",
                "Region IX", "Region X", "Region XI", "Region XII", "CAR", "BARMM"};
        ArrayAdapter<String> regionAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, regions);
        regionSpinner.setAdapter(regionAdapter);
    }

    private void setupHintsWithAsterisk() {
        setRequiredHint(firstNameLayout, "First Name");
        setRequiredHint(middleNameLayout, "Middle Initial");
        setRequiredHint(lastNameLayout, "Last Name");
    }

    private void setRequiredHint(TextInputLayout layout, String text) {
        String hint = text + " *";
        SpannableString spannable = new SpannableString(hint);
        spannable.setSpan(
                new ForegroundColorSpan(getResources().getColor(android.R.color.holo_red_dark)),
                hint.length() - 1,
                hint.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        );
        layout.setHint(spannable);
    }

    private void setupListeners() {
        // Date of Birth picker
        dobInput.setOnClickListener(v -> showDatePicker());

        // Navigation buttons
        btnNext.setOnClickListener(v -> {
            if (validateCurrentStep()) {
                changeStep(1);
            }
        });

        btnPrevious.setOnClickListener(v -> changeStep(-1));

        btnSubmit.setOnClickListener(v -> submitForm());

        // Auto-generate current address
        regionSpinner.setOnItemClickListener((parent, view, position, id) -> generateCurrentAddress());
        citySpinner.setOnItemClickListener((parent, view, position, id) -> generateCurrentAddress());
        barangaySpinner.setOnItemClickListener((parent, view, position, id) -> generateCurrentAddress());
        streetInput.addTextChangedListener(new android.text.TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                generateCurrentAddress();
            }

            @Override
            public void afterTextChanged(android.text.Editable s) {}
        });
    }

    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    String date = selectedYear + "-" + String.format("%02d", (selectedMonth + 1)) + "-" + String.format("%02d", selectedDay);
                    dobInput.setText(date);
                    calculateAge(selectedYear, selectedMonth, selectedDay);
                },
                year, month, day
        );

        // Set max date to today
        datePickerDialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());
        datePickerDialog.show();
    }

    private void calculateAge(int year, int month, int day) {
        Calendar dob = Calendar.getInstance();
        dob.set(year, month, day);

        Calendar today = Calendar.getInstance();

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.MONTH) < dob.get(Calendar.MONTH) ||
                (today.get(Calendar.MONTH) == dob.get(Calendar.MONTH) &&
                        today.get(Calendar.DAY_OF_MONTH) < dob.get(Calendar.DAY_OF_MONTH))) {
            age--;
        }

        ageInput.setText(String.valueOf(age));
    }

    private void generateCurrentAddress() {
        String street = streetInput.getText().toString().trim();
        String barangay = barangaySpinner.getText().toString().trim();
        String city = citySpinner.getText().toString().trim();
        String region = regionSpinner.getText().toString().trim();

        if (!street.isEmpty() && !barangay.isEmpty() && !city.isEmpty() && !region.isEmpty()) {
            String fullAddress = street + ", " + barangay + ", " + city + ", " + region;
            currentAddressInput.setText(fullAddress);
        }
    }

    private boolean validateCurrentStep() {
        switch (currentStep) {
            case 1: // Personal Information
                return validatePersonalInfo();
            case 2: // Contact Information
                return validateContactInfo();
            default:
                return true;
        }
    }

    private boolean validatePersonalInfo() {
        boolean isValid = true;

        if (firstNameInput.getText().toString().trim().isEmpty()) {
            firstNameLayout.setError("First name is required");
            isValid = false;
        } else {
            firstNameLayout.setError(null);
        }

        if (middleNameInput.getText().toString().trim().isEmpty()) {
            middleNameLayout.setError("Middle initial is required");
            isValid = false;
        } else {
            middleNameLayout.setError(null);
        }

        if (lastNameInput.getText().toString().trim().isEmpty()) {
            lastNameLayout.setError("Last name is required");
            isValid = false;
        } else {
            lastNameLayout.setError(null);
        }

        if (dobInput.getText().toString().trim().isEmpty()) {
            dobLayout.setError("Date of birth is required");
            isValid = false;
        } else {
            dobLayout.setError(null);
        }

        if (genderSpinner.getText().toString().trim().isEmpty()) {
            genderLayout.setError("Gender is required");
            isValid = false;
        } else {
            genderLayout.setError(null);
        }

        if (civilStatusSpinner.getText().toString().trim().isEmpty()) {
            civilStatusLayout.setError("Civil status is required");
            isValid = false;
        } else {
            civilStatusLayout.setError(null);
        }

        if (!isValid) {
            Toast.makeText(this, "Please fill all required fields", Toast.LENGTH_SHORT).show();
        }

        return isValid;
    }

    private boolean validateContactInfo() {
        boolean isValid = true;

        if (phoneInput.getText().toString().trim().isEmpty()) {
            phoneLayout.setError("Phone number is required");
            isValid = false;
        } else {
            phoneLayout.setError(null);
        }

        if (otherPhoneInput.getText().toString().trim().isEmpty()) {
            otherPhoneLayout.setError("Other contact is required");
            isValid = false;
        } else {
            otherPhoneLayout.setError(null);
        }

        if (regionSpinner.getText().toString().trim().isEmpty()) {
            regionLayout.setError("Region is required");
            isValid = false;
        } else {
            regionLayout.setError(null);
        }

        if (citySpinner.getText().toString().trim().isEmpty()) {
            cityLayout.setError("City/Municipality is required");
            isValid = false;
        } else {
            cityLayout.setError(null);
        }

        if (barangaySpinner.getText().toString().trim().isEmpty()) {
            barangayLayout.setError("Barangay is required");
            isValid = false;
        } else {
            barangayLayout.setError(null);
        }

        if (streetInput.getText().toString().trim().isEmpty()) {
            streetLayout.setError("Street address is required");
            isValid = false;
        } else {
            streetLayout.setError(null);
        }

        if (permanentAddressInput.getText().toString().trim().isEmpty()) {
            permanentAddressLayout.setError("Permanent address is required");
            isValid = false;
        } else {
            permanentAddressLayout.setError(null);
        }

        if (!isValid) {
            Toast.makeText(this, "Please fill all required fields", Toast.LENGTH_SHORT).show();
        }

        return isValid;
    }

    private void changeStep(int direction) {
        currentStep += direction;
        currentStep = Math.max(1, Math.min(totalSteps, currentStep));

        updateButtonsVisibility();

        // Here you would show/hide the appropriate sections
        // This is a simplified version - you'll need to implement ViewPager or similar
        Toast.makeText(this, "Step " + currentStep + " of " + totalSteps, Toast.LENGTH_SHORT).show();
    }

    private void updateButtonsVisibility() {
        btnPrevious.setVisibility(currentStep == 1 ? View.GONE : View.VISIBLE);

        if (currentStep == totalSteps) {
            btnNext.setVisibility(View.GONE);
            btnSubmit.setVisibility(View.VISIBLE);
        } else {
            btnNext.setVisibility(View.VISIBLE);
            btnSubmit.setVisibility(View.GONE);
        }
    }

    private void submitForm() {
        // Validate all steps before submission
        if (!validatePersonalInfo() || !validateContactInfo()) {
            Toast.makeText(this, "Please complete all required fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Show success message
        Toast.makeText(this, "Form submitted successfully!", Toast.LENGTH_LONG).show();

        // Navigate to MainActivity
        Intent intent = new Intent(PreEmpActivity.this, MainActivity.class);

        // Optional: Pass data to MainActivity
        intent.putExtra("firstName", firstNameInput.getText().toString());
        intent.putExtra("lastName", lastNameInput.getText().toString());
        intent.putExtra("phone", phoneInput.getText().toString());

        startActivity(intent);
        finish(); // Prevent back navigation to PreEmpActivity
    }
}