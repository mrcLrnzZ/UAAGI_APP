package com.example.uaagi_app.ui.users.ActivityPreEmpForm.Fragments.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * A generic RecyclerView adapter that can be used with any data type and layout.
 * @param <T> The type of data items in the list
 */
public class GenericRecyclerAdapter<T> extends RecyclerView.Adapter<GenericRecyclerAdapter.GenericViewHolder> {

    private List<T> itemList;
    private final int layoutResId;
    private final ViewBinder<T> viewBinder;
    private OnItemClickListener<T> onItemClickListener;
    private OnItemLongClickListener<T> onItemLongClickListener;

    /**
     * Constructor for the generic adapter
     * @param itemList The list of items to display
     * @param layoutResId The layout resource ID for each item
     * @param viewBinder The interface to bind data to views
     */
    public GenericRecyclerAdapter(List<T> itemList, int layoutResId, ViewBinder<T> viewBinder) {
        this.itemList = itemList != null ? itemList : new ArrayList<>();
        this.layoutResId = layoutResId;
        this.viewBinder = viewBinder;
    }

    @NonNull
    @Override
    public GenericViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(layoutResId, parent, false);
        return new GenericViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GenericViewHolder holder, int position) {
        T item = itemList.get(position);
        viewBinder.bind(holder.itemView, item, position);

        // Set click listener if available
        if (onItemClickListener != null) {
            holder.itemView.setOnClickListener(v ->
                onItemClickListener.onItemClick(item, position));
        }

        // Set long click listener if available
        if (onItemLongClickListener != null) {
            holder.itemView.setOnLongClickListener(v -> {
                onItemLongClickListener.onItemLongClick(item, position);
                return true;
            });
        }
    }

    @Override
    public int getItemCount() {
        return itemList != null ? itemList.size() : 0;
    }

    /**
     * Update the entire list and refresh the adapter
     */
    public void updateList(List<T> newList) {
        this.itemList = newList != null ? newList : new ArrayList<>();
        notifyDataSetChanged();
    }

    /**
     * Add a single item to the list
     */
    public void addItem(T item) {
        if (itemList == null) {
            itemList = new ArrayList<>();
        }
        itemList.add(item);
        notifyItemInserted(itemList.size() - 1);
    }

    /**
     * Add a single item at a specific position
     */
    public void addItem(int position, T item) {
        if (itemList == null) {
            itemList = new ArrayList<>();
        }
        itemList.add(position, item);
        notifyItemInserted(position);
    }

    /**
     * Remove an item at a specific position
     */
    public void removeItem(int position) {
        if (itemList != null && position >= 0 && position < itemList.size()) {
            itemList.remove(position);
            notifyItemRemoved(position);
        }
    }

    /**
     * Remove a specific item
     */
    public void removeItem(T item) {
        if (itemList != null) {
            int position = itemList.indexOf(item);
            if (position >= 0) {
                removeItem(position);
            }
        }
    }

    /**
     * Update an item at a specific position
     */
    public void updateItem(int position, T item) {
        if (itemList != null && position >= 0 && position < itemList.size()) {
            itemList.set(position, item);
            notifyItemChanged(position);
        }
    }

    /**
     * Clear all items from the list
     */
    public void clearItems() {
        if (itemList != null) {
            itemList.clear();
            notifyDataSetChanged();
        }
    }

    /**
     * Get the current list
     */
    public List<T> getItemList() {
        return itemList;
    }

    /**
     * Get item at a specific position
     */
    public T getItem(int position) {
        if (itemList != null && position >= 0 && position < itemList.size()) {
            return itemList.get(position);
        }
        return null;
    }

    /**
     * Set item click listener
     */
    public void setOnItemClickListener(OnItemClickListener<T> listener) {
        this.onItemClickListener = listener;
    }

    /**
     * Set item long click listener
     */
    public void setOnItemLongClickListener(OnItemLongClickListener<T> listener) {
        this.onItemLongClickListener = listener;
    }

    /**
     * Generic ViewHolder class
     */
    public static class GenericViewHolder extends RecyclerView.ViewHolder {
        public GenericViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    /**
     * Interface for binding data to views
     * @param <T> The type of data item
     */
    public interface ViewBinder<T> {
        /**
         * Bind data to the view
         * @param view The item view
         * @param item The data item
         * @param position The position in the list
         */
        void bind(View view, T item, int position);
    }

    /**
     * Interface for item click events
     * @param <T> The type of data item
     */
    public interface OnItemClickListener<T> {
        void onItemClick(T item, int position);
    }

    /**
     * Interface for item long click events
     * @param <T> The type of data item
     */
    public interface OnItemLongClickListener<T> {
        void onItemLongClick(T item, int position);
    }
}
