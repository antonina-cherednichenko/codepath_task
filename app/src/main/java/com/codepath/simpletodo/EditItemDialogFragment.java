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

public class EditItemDialogFragment extends DialogFragment {

    Button saveButton;
    EditText editText;

    public interface EditNameDialogListener {
        void onFinishEditDialog(String newText, int pos);
    }


    public EditItemDialogFragment() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    public static EditItemDialogFragment newInstance(String text, int pos) {
        EditItemDialogFragment frag = new EditItemDialogFragment();
        frag.setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog);
        Bundle args = new Bundle();
        args.putString("text", text);
        args.putInt("pos", pos);
        frag.setArguments(args);
        return frag;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_edit_item, container);
        getDialog().setTitle(R.string.edit_item);
        return rootView;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editText = (EditText) view.findViewById(R.id.etItem);
        saveButton = (Button) view.findViewById(R.id.btnEditItem);

        String text = getArguments().getString("text");
        final int pos = getArguments().getInt("pos");


        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);


        editText.setText(text);
        editText.setSelection(editText.getText().length());

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditNameDialogListener listener = (EditNameDialogListener) getActivity();
                listener.onFinishEditDialog(editText.getText().toString(), pos);
                dismiss();

            }
        });
    }


}
