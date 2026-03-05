package com.example.uaagi_app.ui.users.FragmentsCareers;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uaagi_app.R;
import com.example.uaagi_app.network.Services.JobFetchService;
import com.example.uaagi_app.network.dto.JobFetchResponse;
import com.example.uaagi_app.ui.users.FragmentError;
import com.example.uaagi_app.ui.users.FragmentLoading;
import com.example.uaagi_app.ui.utils.PdfPickerHelper;
import com.example.uaagi_app.ui.utils.UiHelpers;

import java.util.List;

public class ApplyOption extends Fragment {

    private TextView jobTitleTV, jobLocCom;
    private RadioGroup radioGroupOptions;
    private RadioButton radioPreEmployment, radioUploadResume;
    private Button btnContinuePreEmployment;
    private View contentContainer, loadingContainer, errorContainer;
    private PdfPickerHelper pdfPickerHelper;
    private View btnBackToDesc;

    public ApplyOption() {}
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pdfPickerHelper = new PdfPickerHelper(this);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_careers_apply_options, container, false);
        String jobID = getArgumentsFromBundle();

        setupUiStates(view);
        if (jobID != null) {
            fetchJobs(Integer.parseInt(jobID));
        }
        jobTitleTV = view.findViewById(R.id.jobTitle);
        jobLocCom = view.findViewById(R.id.jobCompany_jobLocation);

        radioGroupOptions = view.findViewById(R.id.radioGroupOptions);
        radioPreEmployment = view.findViewById(R.id.radioPreEmployment);
        radioUploadResume = view.findViewById(R.id.radioUploadResume);

        btnContinuePreEmployment = view.findViewById(R.id.btnContinuePreEmployment);

        btnBackToDesc = view.findViewById(R.id.btnBackToDesc);
        LinearLayout option1 = view.findViewById(R.id.option1);
        LinearLayout option2 = view.findViewById(R.id.option2);

        final RadioButton[] selectedRadio = {null};

        option1.setOnClickListener(v -> {
            radioPreEmployment.setChecked(true);
            radioUploadResume.setChecked(false);
            selectedRadio[0] = radioPreEmployment;
        });

        option2.setOnClickListener(v -> {
            radioUploadResume.setChecked(true);
            radioPreEmployment.setChecked(false);
            selectedRadio[0] = radioUploadResume;
        });

        btnContinuePreEmployment.setOnClickListener(v -> {
            if (selectedRadio[0] == null) {
                Toast.makeText(requireContext(), "Please select an option", Toast.LENGTH_SHORT).show();
            } else if (selectedRadio[0] == radioPreEmployment) {
                unsetRadioButtons();
                UiHelpers.switchFragment(
                        requireActivity().getSupportFragmentManager(),
                        ApplyPreEmp.newInstance(Integer.parseInt(jobID))
                );
            } else if (selectedRadio[0] == radioUploadResume) {
                unsetRadioButtons();
                Toast.makeText(requireContext(), "You selected Upload Resume", Toast.LENGTH_SHORT).show();
                pdfPickerHelper.openPdfPicker(Integer.parseInt(jobID), new PdfPickerHelper.PdfUploadCallback() {
                    @Override
                    public void onSuccess() {
                        navigateToResult("success");
                    }

                    @Override
                    public void onError(String errorMessage) {
                        if ("Already sent".contains(errorMessage)) {
                            navigateToResult("alreadysent");
                        } else {
                            navigateToResult("error");
                        }
                    }
                });
            }
        });
        return view;
    }
    private void fetchJobs(int jobID) {
        showLoading();
        JobFetchService service = new JobFetchService(requireContext());
        service.fetchJobById(jobID, new JobFetchService.JobFetchCallback() {
            @Override
            public void onResponse(List<JobFetchResponse> jobs) {
                showContent(jobs);
            }

            @Override
            public void onError(String errorMessage) {
                showError(errorMessage);
            }
        });
    }

    private void showContent(List<JobFetchResponse> job) {
        loadingContainer.setVisibility(View.GONE);
        errorContainer.setVisibility(View.GONE);
        contentContainer.setVisibility(View.VISIBLE);

        if (job != null && !job.isEmpty()) {
            JobFetchResponse jobData = job.get(0);
            jobTitleTV.setText(jobData.getJobTitle());
            jobLocCom.setText(String.format("%s • %s",
                    jobData.getCompany() != null ? jobData.getCompany().getDisplayName() : "N/A",
                    jobData.getLocation()));

            btnBackToDesc.setOnClickListener(v -> {
                JobDesc fragment = new JobDesc();
                Bundle bundle = new Bundle();
                bundle.putString("jobId", String.valueOf(jobData.getId()));
                bundle.putString("Department", jobData.getDepartment());
                fragment.setArguments(bundle);
                UiHelpers.switchFragment(requireActivity().getSupportFragmentManager(), fragment);
            });
        }
    }

    private void setupUiStates(View view) {
        contentContainer = view.findViewById(R.id.apply_option_container);
        loadingContainer = view.findViewById(R.id.loading_container);
        errorContainer = view.findViewById(R.id.error_container);
    }

    private void showLoading() {
        loadingContainer.setVisibility(View.VISIBLE);
        errorContainer.setVisibility(View.GONE);
        contentContainer.setVisibility(View.GONE);
        UiHelpers.replaceChildFragment(
                getChildFragmentManager(),
                R.id.loading_container,
                FragmentLoading.newInstance()
        );
    }

    private void showError(String message) {
        loadingContainer.setVisibility(View.GONE);
        contentContainer.setVisibility(View.GONE);
        errorContainer.setVisibility(View.VISIBLE);
        UiHelpers.replaceChildFragment(
                getChildFragmentManager(),
                R.id.error_container,
                FragmentError.newInstance(message)
        );
    }

    private String getArgumentsFromBundle() {
        if (getArguments() != null) {
            return getArguments().getString("jobId");
        }
        return null;
    }
    private void unsetRadioButtons() {
        radioPreEmployment.setChecked(false);
        radioUploadResume.setChecked(false);
    }
    private void navigateToResult(String result) {
        ApplyResult fragment = ApplyResult.newInstance(result);

        getParentFragmentManager()
                .beginTransaction()
                .replace(R.id.container, fragment)
                .addToBackStack(null)
                .commit();
    }
}