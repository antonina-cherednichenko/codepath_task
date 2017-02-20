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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by tonya on 2/15/17.
 */

public class EditItemDialogFragment extends DialogFragment {

    Button saveButton;
    EditText editText;
    DatePicker dp;
    Spinner spinner;


    public interface EditNameDialogListener {
        void onFinishEditDialog(Note note, int pos);
    }


    public EditItemDialogFragment() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    public static EditItemDialogFragment newInstance(Note note, int pos) {
        EditItemDialogFragment frag = new EditItemDialogFragment();
        frag.setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog);
        Bundle args = new Bundle();
        args.putSerializable("note", note);
        args.putInt("pos", pos);
        frag.setArguments(args);
        return frag;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_add_edit_item, container);
        getDialog().setTitle(R.string.edit_item);
        return rootView;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editText = (EditText) view.findViewById(R.id.etNewItem);
        saveButton = (Button) view.findViewById(R.id.btnAddItem);
        dp = (DatePicker) view.findViewById(R.id.dpResult);
        spinner = (Spinner) view.findViewById(R.id.priority_spinner);

        final Note note = (Note) getArguments().getSerializable("note");
        final int pos = getArguments().getInt("pos");


        editText.setText(note.getText());
        editText.setSelection(editText.getText().length());

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.priority_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        int n = adapter.getCount();
        //set selected item in spinner
        for (int i = 0; i < n; i++) {
            String item = (String) adapter.getItem(i);
            if (item.equals(note.getPriority())) {
                spinner.setSelection(i);
                break;
            }
        }

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy");
            Date date = sdf.parse(note.getDate());
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            dp.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), null);

        } catch (ParseException ex) {
            ex.printStackTrace();
        }

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditNameDialogListener listener = (EditNameDialogListener) getActivity();
                String res = editText.getText().toString();

                if (res.length() > 0) {
                    String priority = (String) spinner.getSelectedItem();
                    Calendar cal = Calendar.getInstance();
                    cal.set(dp.getYear(), dp.getMonth(), dp.getDayOfMonth());
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy");
                    String date = sdf.format(cal.getTime());
                    note.setText(res);
                    note.setPriority(priority);
                    note.setDate(date);
                    listener.onFinishEditDialog(note, pos);
                    dismiss();
                }

            }
        });
    }

}
