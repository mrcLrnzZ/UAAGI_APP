package com.example.uaagi_app.ui.users.ActivityPreEmpForm.Fragments.Adapter;

import android.widget.TextView;

import com.example.uaagi_app.R;

import java.util.ArrayList;
import java.util.List;

/**
 * This class demonstrates various ways to use the GenericRecyclerAdapter.
 * These are example implementations - adapt them to your specific needs.
 */
public class GenericAdapterUsageExample {

    /**
     * Example 1: Simple String List Adapter
     * Usage: Display a list of strings in a RecyclerView
     */
    public static GenericRecyclerAdapter<String> createSimpleStringAdapter(List<String> items) {
        return new GenericRecyclerAdapter<>(
                items,
                android.R.layout.simple_list_item_1,
                (view, item, position) -> {
                    TextView textView = view.findViewById(android.R.id.text1);
                    textView.setText(item);
                }
        );
    }

    /**
     * Example 2: Custom Object Adapter with Click Listener
     * Usage: Display custom objects with click handling
     */
    public static class CustomObjectAdapter<T> extends GenericRecyclerAdapter<T> {
        public CustomObjectAdapter(List<T> items, int layoutResId, ViewBinder<T> binder) {
            super(items, layoutResId, binder);
        }
    }

    /**
     * Example 3: Inline Adapter Creation with Lambda
     * This shows how to create an adapter inline in your Fragment/Activity
     */
    public void exampleInlineUsage() {
        List<String> sampleData = new ArrayList<>();
        sampleData.add("Item 1");
        sampleData.add("Item 2");
        sampleData.add("Item 3");

        GenericRecyclerAdapter<String> adapter = new GenericRecyclerAdapter<>(
                sampleData,
                R.layout.item_character_reference_preview, // Replace with your layout
                (view, item, position) -> {
                    // Bind your data here
                    TextView textView = view.findViewById(R.id.tvPreviewRefName);
                    textView.setText(item);
                }
        );

        // Set click listener
        adapter.setOnItemClickListener((item, position) -> {
            // Handle item click
            // Example: Toast.makeText(context, "Clicked: " + item, Toast.LENGTH_SHORT).show();
        });

        // Set long click listener
        adapter.setOnItemLongClickListener((item, position) -> {
            // Handle long click
            // Example: Show delete dialog
        });

        // Use the adapter with RecyclerView
        // recyclerView.setAdapter(adapter);
    }

    /**
     * Example 4: Extending GenericRecyclerAdapter for Specific Use Case
     * This is similar to how CharacterReferencePreviewAdapter is implemented
     */
    public static class EmployeeAdapter extends GenericRecyclerAdapter<Employee> {
        public EmployeeAdapter(List<Employee> employees) {
            super(employees, R.layout.item_character_reference_preview, (view, employee, position) -> {
                TextView nameView = view.findViewById(R.id.tvPreviewRefName);
                TextView occupationView = view.findViewById(R.id.tvPreviewRefOccupation);
                TextView companyView = view.findViewById(R.id.tvPreviewRefCompany);
                TextView phoneView = view.findViewById(R.id.tvPreviewRefPhone);

                nameView.setText(employee.getName());
                occupationView.setText(employee.getPosition());
                companyView.setText(employee.getDepartment());
                phoneView.setText(employee.getPhone());
            });
        }
    }

    /**
     * Example 5: Dynamic List Operations
     */
    public void exampleListOperations(GenericRecyclerAdapter<String> adapter) {
        // Add item
        adapter.addItem("New Item");

        // Add item at specific position
        adapter.addItem(0, "First Item");

        // Update item
        adapter.updateItem(1, "Updated Item");

        // Remove item by position
        adapter.removeItem(2);

        // Remove item by object
        adapter.removeItem("Item to Remove");

        // Clear all items
        adapter.clearItems();

        // Update entire list
        List<String> newList = new ArrayList<>();
        newList.add("Item A");
        newList.add("Item B");
        adapter.updateList(newList);

        // Get current list
        List<String> currentList = adapter.getItemList();

        // Get specific item
        String item = adapter.getItem(0);
    }

    /**
     * Example 6: ViewHolder Pattern with findViewById Caching
     * For better performance, you can cache view references
     */
    public static class OptimizedAdapter extends GenericRecyclerAdapter<String> {
        public OptimizedAdapter(List<String> items) {
            super(items, android.R.layout.simple_list_item_1, (view, item, position) -> {
                // Get or create view holder
                ViewCache cache = (ViewCache) view.getTag();
                if (cache == null) {
                    cache = new ViewCache(view);
                    view.setTag(cache);
                }
                cache.textView.setText(item);
            });
        }

        private static class ViewCache {
            TextView textView;

            ViewCache(android.view.View view) {
                textView = view.findViewById(android.R.id.text1);
            }
        }
    }

    // Sample Employee class for demonstration
    private static class Employee {
        private String name;
        private String position;
        private String department;
        private String phone;

        public String getName() { return name; }
        public String getPosition() { return position; }
        public String getDepartment() { return department; }
        public String getPhone() { return phone; }
    }
}
