package com.example.uaagi_app.ui.users.FragmentsCareers;

import android.graphics.Bitmap;
import android.graphics.pdf.PdfRenderer;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uaagi_app.R;
import com.example.uaagi_app.data.viewmodel.JobViewModel;
import com.example.uaagi_app.ui.users.ActivityPreEmpForm.Fragments.Adapter.GenericRecyclerAdapter;
import com.example.uaagi_app.ui.utils.PdfPickerHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ResumePreview extends Fragment {

    private int jobId;
    private Uri pdfUri;
    private RecyclerView rvPdfPages;
    private Button btnSubmitResume;
    private ImageButton btnBack;
    private TextView jobTitle, jobCompanyLocation, tvFileName;
    private JobViewModel jobViewModel;
    private PdfPickerHelper pdfPickerHelper;

    public static ResumePreview newInstance(int jobId, Uri pdfUri) {
        ResumePreview fragment = new ResumePreview();
        Bundle args = new Bundle();
        args.putInt("jobId", jobId);
        args.putParcelable("pdfUri", pdfUri);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            jobId = getArguments().getInt("jobId");
            pdfUri = getArguments().getParcelable("pdfUri");
        }
        pdfPickerHelper = new PdfPickerHelper(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_careers_resume_preview, container, false);
        initializeViews(view);
        
        jobViewModel = new ViewModelProvider(requireActivity()).get(JobViewModel.class);
        observeJobData();
        
        if (jobId != -1) {
            jobViewModel.fetchJobById(jobId, requireContext());
        }

        renderPdf();
        setupButtons();

        return view;
    }

    private void initializeViews(View view) {
        rvPdfPages = view.findViewById(R.id.rvPdfPages);
        btnSubmitResume = view.findViewById(R.id.btnSubmitResume);
        btnBack = view.findViewById(R.id.btnBack);
        jobTitle = view.findViewById(R.id.jobTitle);
        jobCompanyLocation = view.findViewById(R.id.jobCompany_jobLocation);
        tvFileName = view.findViewById(R.id.tvFileName);

        rvPdfPages.setLayoutManager(new LinearLayoutManager(requireContext()));
        
        String fileName = PdfPickerHelper.getFileName(requireContext(), pdfUri);
        tvFileName.setText("RESUME: " + fileName);
    }

    private void observeJobData() {
        jobViewModel.getJobData().observe(getViewLifecycleOwner(), job -> {
            if (job != null) {
                jobTitle.setText(job.getJobTitle());
                jobCompanyLocation.setText(job.getCompany().getDisplayName() + " • " + job.getLocation());
            }
        });
    }

    private void renderPdf() {
        try {
            ParcelFileDescriptor pfd = requireContext().getContentResolver().openFileDescriptor(pdfUri, "r");
            if (pfd != null) {
                PdfRenderer renderer = new PdfRenderer(pfd);
                List<Bitmap> bitmaps = new ArrayList<>();
                
                for (int i = 0; i < renderer.getPageCount(); i++) {
                    PdfRenderer.Page page = renderer.openPage(i);
                    Bitmap bitmap = Bitmap.createBitmap(page.getWidth() * 2, page.getHeight() * 2, Bitmap.Config.ARGB_8888);
                    page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);
                    bitmaps.add(bitmap);
                    page.close();
                }
                
                renderer.close();
                pfd.close();

                GenericRecyclerAdapter<Bitmap> adapter = new GenericRecyclerAdapter<>(
                        bitmaps,
                        R.layout.item_pdf_page,
                        (v, bitmap, position) -> {
                            ImageView iv = v.findViewById(R.id.ivPdfPage);
                            iv.setImageBitmap(bitmap);
                        }
                );
                rvPdfPages.setAdapter(adapter);
            }
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(requireContext(), "Error loading PDF preview", Toast.LENGTH_SHORT).show();
        }
    }

    private void setupButtons() {
        btnBack.setOnClickListener(v -> getParentFragmentManager().popBackStack());
        
        btnSubmitResume.setOnClickListener(v -> {
            btnSubmitResume.setEnabled(false);
            pdfPickerHelper.uploadPdfFile(pdfUri, jobId, new PdfPickerHelper.PdfUploadCallback() {
                @Override
                public void onSuccess() {
                    navigateToResult("success");
                }

                @Override
                public void onError(String errorMessage) {
                    btnSubmitResume.setEnabled(true);
                    handleError(errorMessage);
                }
            });
        });
    }

    private void handleError(String errorMessage) {
        String result = (errorMessage != null && errorMessage.contains("Already sent"))
                ? "alreadysent" : "error";
        navigateToResult(result);
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
