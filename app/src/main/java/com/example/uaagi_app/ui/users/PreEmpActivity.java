package com.example.uaagi_app.ui.users;



import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spannable;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.uaagi_app.utils.Helpers;
import com.example.uaagi_app.ui.utils.UiHelpers;
import com.example.uaagi_app.utils.Helpers;
import com.example.uaagi_app.ui.users.HomePage;
import com.example.uaagi_app.R;
import com.example.uaagi_app.utils.InputValidator;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;

public class PreEmpActivity extends AppCompatActivity {
    private static final String TAG = "PreEmpActLifecycle";
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
    private final int TOTAL_STEPS = 7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "PreEmpAct onCreate()");
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
        String[] genders = {
                "Male", "Female", "Non-binary", "Other", "Prefer not to say"
        };
        UiHelpers.dropDownMaker(genders, genderSpinner, this);
        String[] religions = {
                "Roman Catholic", "Christian", "Iglesia ni Cristo", "Evangelical",
                "Jehovah's Witness", "Seventh-day Adventist", "Islam",
                "Other", "Prefer not to say"
        };
        UiHelpers.dropDownMaker(religions, religionSpinner, this);
        String[] civilStatus = {
                "Single", "Married", "Widowed", "Separated", "Divorced"
        };
        UiHelpers.dropDownMaker(civilStatus, civilStatusSpinner, this);
        String[] nationalities = {
                "Filipino", "American", "Chinese", "Japanese", "Korean",
                "British", "Australian", "Canadian", "Other"
        };
        UiHelpers.dropDownMaker(nationalities, nationalitySpinner, this);
        String[] bloodTypes = {
                "A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"
        };
        UiHelpers.dropDownMaker(bloodTypes, bloodTypeSpinner, this);
        String[] regions = {
                "NCR", "Region I", "Region II", "Region III", "Region IV-A",
                "Region IV-B", "Region V", "Region VI", "Region VII", "Region VIII",
                "Region IX", "Region X", "Region XI", "Region XII", "CAR", "BARMM"
        };
        UiHelpers.dropDownMaker(regions, regionSpinner, this);
        String[] cities = {
                "Manila", "Quezon City", "Caloocan", "Makati",
                "Pasig", "Taguig", "Mandaluyong",
                "Marikina", "Parañaque", "Las Piñas"
        };
        UiHelpers.dropDownMaker(cities, citySpinner, this);
        String[] barangay = {
                "Bagong Pag-asa", "Batasan Hills", "Commonwealth", "Diliman",
                "Fairview", "Holy Spirit", "Novaliches", "Payatas",
                "Project 6", "Tandang Sora"
        };

        UiHelpers.dropDownMaker(barangay, barangaySpinner, this);
    }
    private void setupHintsWithAsterisk() {
        UiHelpers.setRequiredHint(PreEmpActivity.this, firstNameLayout, "First Name");
        UiHelpers.setRequiredHint(PreEmpActivity.this, middleNameLayout, "Middle Initial");
        UiHelpers.setRequiredHint(PreEmpActivity.this, lastNameLayout, "Last Name");
    }
    private void setupListeners() {
        dobInput.setOnClickListener(v -> showDatePicker());

        btnNext.setOnClickListener(v -> {
            if (validateCurrentStep()) {
                changeStep(1);
            }
        });

        btnPrevious.setOnClickListener(v -> changeStep(-1));

        btnSubmit.setOnClickListener(v -> submitForm());

        regionSpinner.setOnItemClickListener((parent, view, position, id) ->
                Helpers.generateCurrentAddress(streetInput, barangaySpinner, citySpinner, regionSpinner, currentAddressInput)
                );
        citySpinner.setOnItemClickListener((parent, view, position, id) ->
                Helpers.generateCurrentAddress(streetInput, barangaySpinner, citySpinner, regionSpinner, currentAddressInput)
                );
        barangaySpinner.setOnItemClickListener((parent, view, position, id) ->
                Helpers.generateCurrentAddress(streetInput, barangaySpinner, citySpinner, regionSpinner, currentAddressInput)
                );
        streetInput.addTextChangedListener(new android.text.TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Helpers.generateCurrentAddress(streetInput, barangaySpinner, citySpinner, regionSpinner, currentAddressInput);
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
                    ageInput.setText(Helpers.calculateAge(selectedYear, selectedMonth, selectedDay));
                },
                year, month, day
        );
        datePickerDialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());
        datePickerDialog.show();
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

        isValid &= InputValidator.isValid(
                firstNameInput.getText().toString(),
                firstNameLayout,
                "First name is required"
        );

        isValid &= InputValidator.isValid(
                middleNameInput.getText().toString(),
                middleNameLayout,
                "Middle initial is required"
        );

        isValid &= InputValidator.isValid(
                lastNameInput.getText().toString(),
                lastNameLayout,
                "Last name is required"
        );

        isValid &= InputValidator.isValid(
                dobInput.getText().toString(),
                dobLayout,
                "Date of birth is required"
        );

        isValid &= InputValidator.isValid(
                genderSpinner.getText().toString(),
                genderLayout,
                "Gender is required"
        );

        isValid &= InputValidator.isValid(
                civilStatusSpinner.getText().toString(),
                civilStatusLayout,
                "Civil status is required"
        );

        if (!isValid) {
            Toast.makeText(this, "Please fill all required fields", Toast.LENGTH_SHORT).show();
        }

        return isValid;
    }
    private boolean validateContactInfo() {
        boolean isValid = true;

        isValid &= InputValidator.isValid(
                phoneInput.getText().toString(),
                phoneLayout,
                "Phone number is required"
        );

        isValid &= InputValidator.isValid(
                otherPhoneInput.getText().toString(),
                otherPhoneLayout,
                "Other contact is required"
        );

        isValid &= InputValidator.isValid(
                regionSpinner.getText().toString(),
                regionLayout,
                "Region is required"
        );

        isValid &= InputValidator.isValid(
                citySpinner.getText().toString(),
                cityLayout,
                "City/Municipality is required"
        );

        isValid &= InputValidator.isValid(
                barangaySpinner.getText().toString(),
                barangayLayout,
                "Barangay is required"
        );

        isValid &= InputValidator.isValid(
                streetInput.getText().toString(),
                streetLayout,
                "Street address is required"
        );

        isValid &= InputValidator.isValid(
                permanentAddressInput.getText().toString(),
                permanentAddressLayout,
                "Permanent address is required"
        );

        if (!isValid) {
            Toast.makeText(this, "Please fill all required fields", Toast.LENGTH_SHORT).show();
        }

        return isValid;
    }
    private void changeStep(int direction) {
        currentStep += direction;
        currentStep = Math.max(1, Math.min(TOTAL_STEPS, currentStep));

        updateButtonsVisibility();

        // Here you would show/hide the appropriate sections
        // This is a simplified version - you'll need to implement ViewPager or similar
        Toast.makeText(this, "Step " + currentStep + " of " + TOTAL_STEPS, Toast.LENGTH_SHORT).show();
    }
    private void updateButtonsVisibility() {
        btnPrevious.setVisibility(currentStep == 1 ? View.GONE : View.VISIBLE);

        if (currentStep == TOTAL_STEPS) {
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
        Intent intent = new Intent(PreEmpActivity.this, HomePage.class);

        // Optional: Pass data to MainActivity
        intent.putExtra("firstName", firstNameInput.getText().toString());
        intent.putExtra("lastName", lastNameInput.getText().toString());
        intent.putExtra("phone", phoneInput.getText().toString());

        startActivity(intent);
        finish(); // Prevent back navigation to PreEmpActivity
    }
}