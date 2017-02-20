package com.codepath.simpletodo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by tonya on 2/15/17.
 */

public class AddItemDialogFragment extends DialogFragment {

    Button addItemButton;
    EditText addText;

    public interface AddItemDialogListener {
        void onAddItemDialog(String newText);
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

        View rootView = inflater.inflate(R.layout.activity_add_item, container);
        getDialog().setTitle(R.string.add_item);
        return rootView;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        addText = (EditText) view.findViewById(R.id.etNewItem);
        addItemButton = (Button) view.findViewById(R.id.btnAddItem);


        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);


        addItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddItemDialogListener listener = (AddItemDialogListener) getActivity();
                listener.onAddItemDialog(addText.getText().toString());
                dismiss();

            }
        });
    }


}
