package com.example.uaagi_app.ui.users.PreEmpActvityForm;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.uaagi_app.R;
import com.example.uaagi_app.data.viewmodel.PreEmpFormViewModel;
import com.example.uaagi_app.ui.utils.UiHelpers;
import com.example.uaagi_app.utils.Helpers;
import com.example.uaagi_app.utils.InputValidator;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;

public class PreEmpFormStep1 extends Fragment {

    private PreEmpFormViewModel viewModel;
    private static final String TAG = "PreEmpStep1Lifecycle";
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
    private final int TOTAL_STEPS = 6;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_preemp_step_1, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity())
                .get(PreEmpFormViewModel.class);

        firstNameInput = view.findViewById(R.id.firstNameInput);
        lastNameInput = view.findViewById(R.id.lastNameInput);
        initializeViews(view);
        setupSpinners();
        //setupHintsWithAsterisk();
        setupListeners();
    }
    public void next() {
        viewModel.update(form -> {
            form.setFirstName(firstNameInput.getText().toString());
            form.setLastName(lastNameInput.getText().toString());
        });
        ((PreEmpStepperActivity) requireActivity()).nextStep(new PreEmpFormStep2());
    }
    private void initializeViews(View view) {
        // Personal Information
        firstNameLayout = view.findViewById(R.id.firstNameLayout);
        middleNameLayout = view.findViewById(R.id.middleNameLayout);
        lastNameLayout = view.findViewById(R.id.lastNameLayout);
        dobLayout = view.findViewById(R.id.dobLayout);
        ageLayout = view.findViewById(R.id.ageLayout);
        genderLayout = view.findViewById(R.id.genderLayout);
        religionLayout = view.findViewById(R.id.religionLayout);
        civilStatusLayout = view.findViewById(R.id.civilStatusLayout);
        nationalityLayout = view.findViewById(R.id.nationalityLayout);
        heightLayout = view.findViewById(R.id.heightLayout);
        weightLayout = view.findViewById(R.id.weightLayout);
        bloodTypeLayout = view.findViewById(R.id.bloodTypeLayout);

        firstNameInput = view.findViewById(R.id.firstNameInput);
        middleNameInput = view.findViewById(R.id.middleNameInput);
        lastNameInput = view.findViewById(R.id.lastNameInput);
        dobInput = view.findViewById(R.id.dobInput);
        ageInput = view.findViewById(R.id.ageInput);
        heightInput = view.findViewById(R.id.heightInput);
        weightInput = view.findViewById(R.id.weightInput);

        genderSpinner = view.findViewById(R.id.genderSpinner);
        religionSpinner = view.findViewById(R.id.religionSpinner);
        civilStatusSpinner = view.findViewById(R.id.civilStatusSpinner);
        nationalitySpinner = view.findViewById(R.id.nationalitySpinner);
        bloodTypeSpinner = view.findViewById(R.id.bloodTypeSpinner);

        // Contact Information
        phoneLayout = view.findViewById(R.id.phoneLayout);
        otherPhoneLayout = view.findViewById(R.id.otherPhoneLayout);
        telephoneLayout = view.findViewById(R.id.telephoneLayout);
        regionLayout = view.findViewById(R.id.regionLayout);
        cityLayout = view.findViewById(R.id.cityLayout);
        barangayLayout = view.findViewById(R.id.barangayLayout);
        streetLayout = view.findViewById(R.id.streetLayout);
        currentAddressLayout = view.findViewById(R.id.currentAddressLayout);
        permanentAddressLayout = view.findViewById(R.id.permanentAddressLayout);

        phoneInput = view.findViewById(R.id.phoneInput);
        otherPhoneInput = view.findViewById(R.id.otherPhoneInput);
        telephoneInput = view.findViewById(R.id.telephoneInput);
        streetInput = view.findViewById(R.id.streetInput);
        currentAddressInput = view.findViewById(R.id.currentAddressInput);
        permanentAddressInput = view.findViewById(R.id.permanentAddressInput);

        regionSpinner = view.findViewById(R.id.regionSpinner);
        citySpinner = view.findViewById(R.id.citySpinner);
        barangaySpinner = view.findViewById(R.id.barangaySpinner);

        // Buttons
        btnPrevious = view.findViewById(R.id.btnPrevious);
        btnNext = view.findViewById(R.id.btnNext);
        btnSubmit = view.findViewById(R.id.btnSubmit);
    }
    private void setupSpinners() {
        String[] genders = {
                "Male", "Female", "Non-binary", "Other", "Prefer not to say"
        };
        UiHelpers.dropDownMaker(genders, genderSpinner, requireContext());
        String[] religions = {
                "Roman Catholic", "Christian", "Iglesia ni Cristo", "Evangelical",
                "Jehovah's Witness", "Seventh-day Adventist", "Islam",
                "Other", "Prefer not to say"
        };
        UiHelpers.dropDownMaker(religions, religionSpinner, requireContext());
        String[] civilStatus = {
                "Single", "Married", "Widowed", "Separated", "Divorced"
        };
        UiHelpers.dropDownMaker(civilStatus, civilStatusSpinner, requireContext());
        String[] nationalities = {
                "Filipino", "American", "Chinese", "Japanese", "Korean",
                "British", "Australian", "Canadian", "Other"
        };
        UiHelpers.dropDownMaker(nationalities, nationalitySpinner, requireContext());
        String[] bloodTypes = {
                "A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"
        };
        UiHelpers.dropDownMaker(bloodTypes, bloodTypeSpinner, requireContext());
        String[] regions = {
                "NCR", "Region I", "Region II", "Region III", "Region IV-A",
                "Region IV-B", "Region V", "Region VI", "Region VII", "Region VIII",
                "Region IX", "Region X", "Region XI", "Region XII", "CAR", "BARMM"
        };
        UiHelpers.dropDownMaker(regions, regionSpinner, requireContext());
        String[] cities = {
                "Manila", "Quezon City", "Caloocan", "Makati",
                "Pasig", "Taguig", "Mandaluyong",
                "Marikina", "Parañaque", "Las Piñas"
        };
        UiHelpers.dropDownMaker(cities, citySpinner, requireContext());
        String[] barangay = {
                "Bagong Pag-asa", "Batasan Hills", "Commonwealth", "Diliman",
                "Fairview", "Holy Spirit", "Novaliches", "Payatas",
                "Project 6", "Tandang Sora"
        };

        UiHelpers.dropDownMaker(barangay, barangaySpinner, requireContext());
    }
    private void setupHintsWithAsterisk()  {
        UiHelpers.setRequiredHint(requireContext(), firstNameLayout, "First Name");
        UiHelpers.setRequiredHint(requireContext(), middleNameLayout, "Middle Initial");
        UiHelpers.setRequiredHint(requireContext(), lastNameLayout, "Last Name");
    }
    private void setupListeners() {
        dobInput.setOnClickListener(v -> showDatePicker());

        btnNext.setOnClickListener(v -> next());

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
                requireContext(),
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
//    private boolean validateCurrentStep() {
//        switch (currentStep) {
//            case 1: // Personal Information
//                return validatePersonalInfo();
//            case 2: // Contact Information
//                return validateContactInfo();
//            default:
//                return true;
//        }
//    }
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
            Toast.makeText(requireContext(), "Please fill all required fields", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(requireContext(), "Please fill all required fields", Toast.LENGTH_SHORT).show();
        }

        return isValid;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

}
