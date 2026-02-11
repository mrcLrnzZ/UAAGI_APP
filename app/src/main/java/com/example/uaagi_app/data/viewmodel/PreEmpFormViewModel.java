package com.example.uaagi_app.data.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.uaagi_app.data.model.PreEmpForm;

public class PreEmpFormViewModel extends ViewModel {

    private final MutableLiveData<PreEmpForm> form =
            new MutableLiveData<>(new PreEmpForm());

    public LiveData<PreEmpForm> getForm() {
        return form;
    }

    public PreEmpForm getValue() {
        return form.getValue();
    }

    public void update(PreEmpFormUpdater updater) {
        PreEmpForm current = form.getValue();
        if (current != null) {
            updater.update(current);
            form.setValue(current);
        }
    }

    public interface PreEmpFormUpdater {
        void update(PreEmpForm form);
    }
}
