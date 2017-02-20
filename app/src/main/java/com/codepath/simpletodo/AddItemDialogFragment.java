package com.codepath.simpletodo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by tonya on 2/15/17.
 */

public class AddItemDialogFragment extends DialogFragment {

    Button addItemButton;
    EditText addText;
    Spinner spinner;
    DatePicker dp;

    public interface AddItemDialogListener {
        void onAddItemDialog(String newText, String priority, String date);
    }


    public AddItemDialogFragment() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    public static AddItemDialogFragment newInstance() {
        AddItemDialogFragment frag = new AddItemDialogFragment();
        frag.setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog);
        return frag;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_add_edit_item, container);
        getDialog().setTitle(R.string.add_item);
        return rootView;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        addText = (EditText) view.findViewById(R.id.etNewItem);
        addItemButton = (Button) view.findViewById(R.id.btnAddItem);
        spinner = (Spinner) view.findViewById(R.id.priority_spinner);
        dp = (DatePicker) view.findViewById(R.id.dpResult);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.priority_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        addItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddItemDialogListener listener = (AddItemDialogListener) getActivity();
                String res = addText.getText().toString();
                String priority = (String) spinner.getSelectedItem();
                Calendar cal = Calendar.getInstance();
                cal.set(dp.getYear(), dp.getMonth(), dp.getDayOfMonth());
                SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy");
                String date = sdf.format(cal.getTime());

                if (res.length() > 0) {
                    listener.onAddItemDialog(res, priority, date);
                    dismiss();
                }


            }
        });
    }


}
