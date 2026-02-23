package com.example.uaagi_app.ui.users.ActivityPreEmpForm.Fragments.EntryHandler;

import android.content.Context;
import android.util.Log;

import androidx.recyclerview.widget.RecyclerView;

import com.example.uaagi_app.data.model.PreEmploymentForm.PreEmpFormDataModel;
import com.example.uaagi_app.data.viewmodel.PreEmpFormViewModel;
import com.example.uaagi_app.ui.utils.UiHelpers;

import java.util.List;
import java.util.function.Supplier;

/**
 * Utility class for handling common entry operations in RecyclerViews
 * Provides generalized methods for adding, removing, loading, and saving data
 */
public class EntryHandler {

    private static final String TAG = "EntryHandler";

    /**
     * Generalized method to add a new entry to a list and update the RecyclerView
     * @param list The list to add the entry to
     * @param newItem The new item instance to add
     * @param container The RecyclerView container
     * @param adapter The adapter for the RecyclerView
     * @param <T> The type of the list items
     */
    public static <T> void addEntry(List<T> list, T newItem, RecyclerView container, RecyclerView.Adapter adapter, int maxEntries) {
        Log.d(TAG, "Adding new entry: " + newItem.getClass().getSimpleName());
        if (list.size() < maxEntries) {
            list.add(newItem);
            adapter.notifyItemInserted(list.size() - 1);
            container.scrollToPosition(list.size() - 1);
        } else {
            UiHelpers.showToast("Cannot add more entries", container.getContext());
        }
    }

    /**
     * Generalized method to remove the last entry from a list and update the RecyclerView
     * @param list The list to remove the entry from
     * @param container The RecyclerView container
     * @param adapter The adapter for the RecyclerView
     * @param context The context for showing toast messages
     * @param minEntries The minimum number of entries allowed in the list
     * @param <T> The type of the list items
     */
    public static <T> void removeEntry(List<T> list, RecyclerView container, RecyclerView.Adapter adapter, Context context, int minEntries) {
        if (list.size() > minEntries) {
            int removeIndex = list.size() - 1;
            list.remove(removeIndex);
            adapter.notifyItemRemoved(removeIndex);
            Log.d(TAG, "Removed entry at index: " + removeIndex);
        } else {
            UiHelpers.showToast("Cannot remove more entries", context);
        }
    }

    /**
     * Generalized method to load data from ViewModel into a list
     * @param targetList The list to load data into
     * @param savedData The saved data from ViewModel
     * @param defaultItemSupplier A default item to add if no saved data exists
     * @param <T> The type of the list items
     */
    public static <T> void loadData(List<T> targetList, List<T> savedData, Supplier<T> defaultItemSupplier, int minEntries) {
        targetList.clear();
        if (savedData != null && !savedData.isEmpty()) {
            targetList.addAll(savedData);
            Log.d(TAG, "Loaded " + savedData.size() + " entries of type: " + defaultItemSupplier.get().getClass().getSimpleName());
        } else {
            for (int i = 0; i < minEntries; i++) {
                targetList.add(defaultItemSupplier.get()); // call supplier each time
            }
            Log.d(TAG, "No saved data, added " + minEntries + " default entries of type: " + defaultItemSupplier.get().getClass().getSimpleName());
        }
    }

    /**
     * Generalized method to save data to ViewModel
     * @param viewModel The ViewModel to save data to
     * @param dataSetter A functional interface to set the data in the ViewModel
     */
    public static void saveData(PreEmpFormViewModel viewModel, DataSetter dataSetter) {
        viewModel.update(dataSetter::setData);
        Log.d(TAG, "saveData: " + viewModel.getValue().getEducations());
    }

    /**
     * Functional interface for setting data in the ViewModel
     */
    @FunctionalInterface
    public interface DataSetter {
        void setData(PreEmpFormDataModel form);
    }
}
