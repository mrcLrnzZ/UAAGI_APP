package com.example.uaagi_app.ui.users.ActivityPreEmpForm.Fragments;

import androidx.fragment.app.Fragment;

public abstract class BaseFormStepFragment extends Fragment implements FormStepFragment {

    @Override
    public void onPause() {
        super.onPause();
        saveFormData();
    }
}