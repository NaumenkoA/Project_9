package com.teamtreehouse.ribbit.ui;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.teamtreehouse.ribbit.R;

public class MessageActivity extends Activity {

    public static final String MESSAGE = "message" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        Button submitButton = (Button) findViewById(R.id.submitButton);
        final EditText messageEditText = (EditText) findViewById(R.id.messageEditText);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = messageEditText.getText().toString();
                if (message.equals("")) {
                    Toast.makeText(MessageActivity.this, "Enter some message!", Toast.LENGTH_SHORT).show();
                } else {
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra(MESSAGE, message);
                    setResult(Activity.RESULT_OK,returnIntent);
                    finish();
                }
            }
        });
    }
}
