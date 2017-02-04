package com.codepath.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends AppCompatActivity {

    private EditText textField;
    private Intent startIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        startIntent = getIntent();
        String text = startIntent.getStringExtra("text");
        textField = (EditText) findViewById(R.id.etItem);
        textField.setText(text);
    }

    public void onSaveEditItem(View v) {
        String text = textField.getText().toString();

        Intent data = new Intent();
        data.putExtra("text", text);
        data.putExtra("pos", startIntent.getIntExtra("pos", 0));

        setResult(RESULT_OK, data);
        finish();

    }
}
