package com.example.uaagi_app.data.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.uaagi_app.data.model.PreEmploymentForm.PreEmpFormDataModel;
import com.example.uaagi_app.network.Services.PreEmpFetchService;
import com.example.uaagi_app.network.Services.PreEmpSendService;

public class PreEmpFormViewModel extends ViewModel {

    private final MutableLiveData<PreEmpFormDataModel> form =
            new MutableLiveData<>(new PreEmpFormDataModel());

    private final MutableLiveData<Boolean> isSubmitting = new MutableLiveData<>(false);
    private final MutableLiveData<String> submissionError = new MutableLiveData<>();
    private final MutableLiveData<Boolean> submissionSuccess = new MutableLiveData<>(false);

    public LiveData<PreEmpFormDataModel> getForm() {
        return form;
    }

    public PreEmpFormDataModel getValue() {
        return form.getValue();
    }

    public LiveData<Boolean> getIsSubmitting() {
        return isSubmitting;
    }

    public LiveData<String> getSubmissionError() {
        return submissionError;
    }

    public LiveData<Boolean> getSubmissionSuccess() {
        return submissionSuccess;
    }

    public void update(PreEmpFormUpdater updater) {
        PreEmpFormDataModel current = form.getValue();
        if (current != null) {
            updater.update(current);
            form.setValue(current);
        }
    }

    public void submitForm(Context context) {
        isSubmitting.setValue(true);
        submissionError.setValue(null);
        submissionSuccess.setValue(false);

        PreEmpSendService service = new PreEmpSendService(context);
        service.sendPreEmploymentForm(this, new PreEmpSendService.SendPreEmploymentCallback() {
            @Override
            public void onSuccess(String message) {
                isSubmitting.postValue(false);
                submissionSuccess.postValue(true);
            }

            @Override
            public void onError(String error) {
                isSubmitting.postValue(false);
                submissionError.postValue(error);
            }
        });
    }
//    public void getForm(Context context) {
//        PreEmpFetchService service = new PreEmpFetchService(context);
//        service.fetchPreEmpForm(this, new PreEmpFetchService.PreEmpFetchCallback() {
//            @Override
//            public void onSuccess(PreEmpFetchResponse data) {
//                form.setValue(new PreEmpFormDataModel(data));
//            }
//
//            @Override
//            public void onError(String message) {
//                // Handle error
//            }
//        }
//    }

    public interface PreEmpFormUpdater {
        void update(PreEmpFormDataModel form);
    }
}
