package com.example.uaagi_app.data.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.uaagi_app.data.model.PreEmploymentForm.PreEmpFormDataModel;

public class PreEmpFormViewModel extends ViewModel {

    private final MutableLiveData<PreEmpFormDataModel> form =
            new MutableLiveData<>(new PreEmpFormDataModel());

    public LiveData<PreEmpFormDataModel> getForm() {
        return form;
    }

    public PreEmpFormDataModel getValue() {
        return form.getValue();
    }

    public void update(PreEmpFormUpdater updater) {
        PreEmpFormDataModel current = form.getValue();
        if (current != null) {
            updater.update(current);
            form.setValue(current);
        }
    }

    public interface PreEmpFormUpdater {
        void update(PreEmpFormDataModel form);
    }
}
