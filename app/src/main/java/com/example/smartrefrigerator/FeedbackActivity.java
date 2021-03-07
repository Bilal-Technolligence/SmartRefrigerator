package com.example.smartrefrigerator;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class FeedbackActivity extends BaseClass {
    Button Submit;
    String name,emailInput,feedback;
    EditText nameField,emailField,feedbackField;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  setContentView(R.layout.activity_feedback);

         nameField = (EditText) findViewById(R.id.EditText_Enter_your_name);

       emailField = (EditText) findViewById(R.id.EditText_Enter_your_Email);

         feedbackField = (EditText) findViewById(R.id.textFeedBack);

        final CheckBox responseCheckbox = (CheckBox) findViewById(R.id.CheckBoxResponse);
        boolean bRequiresResponse = responseCheckbox.isChecked();

        Submit=(Button)findViewById(R.id.ButtonSendFeedback);
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmInput();
            }

        });



    }
    public void confirmInput() {
        if (!validateEmail() | !validateFullName() | !validateFeedback()) {
            return;
        }
        sendEmail();
        Toast.makeText(FeedbackActivity.this,"Thanks! Your Feedback is submitted.",Toast.LENGTH_LONG).show();


    }

    @SuppressLint("LongLogTag")
    protected void sendEmail() {
        Log.i("Send email", "");
       // String[] TO = {""};
       // String[] CC = {""};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.setData(Uri.parse("nicemehar24@gmail.com"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, emailInput);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, name);
        emailIntent.putExtra(Intent.EXTRA_COMPONENT_NAME,name);
        emailIntent.putExtra(Intent.EXTRA_TEXT, feedback);

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
            startActivity(new Intent(getApplicationContext(),MainActivity.class));

            Log.i("Finished sending email...", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(FeedbackActivity.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validateEmail() {
        String val = emailField.getText().toString().trim();
        String checkEmail = "[a-zA-Z0-9._-]+@[a-z]+.+[a-z]+";

        if (val.isEmpty()) {
            emailField.setError("Field can not be empty");
            return false;
        } else if (!val.matches(checkEmail)) {
            emailField.setError("Invalid Email!");
            return false;
        } else {
            emailField.setError(null);
           // emailField.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateFullName() {
        String val = nameField.getText().toString().trim();
        if (val.isEmpty()) {
            nameField.setError("Field can not be empty");
            return false;
        } else {
            nameField.setError(null);
            //nameField.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateFeedback() {
        String val = feedbackField.getText().toString().trim();
        if (val.isEmpty()) {
            feedbackField.setError("Field can not be empty");
            return false;
        } else {
            feedbackField.setError(null);
            // nameField.setErrorEnabled(false);
            return true;
        }
    }

    @Override
    int getContentViewId() {
        return R.layout.activity_feedback;
    }

    @Override
    int getNavigationMenuItemId() {
        return R.id.nav_setting;
    }

}