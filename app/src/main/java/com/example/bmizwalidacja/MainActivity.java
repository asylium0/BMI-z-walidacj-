package com.example.bmizwalidacja;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private RadioGroup rgGender;
    private EditText etWeight, etHeight;
    private Button btnCalc;
    private TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        rgGender = findViewById(R.id.rgGender);
        etWeight = findViewById(R.id.etWeight);
        etHeight = findViewById(R.id.etHeight);
        btnCalc = findViewById(R.id.btnCalc);
        tvResult = findViewById(R.id.tvResult);

        btnCalc.setOnClickListener(v -> validate());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void validate(){
        if(rgGender.getCheckedRadioButtonId() == -1){
            Toast.makeText(this, getString(R.string.error_gender), Toast.LENGTH_SHORT).show();
            rgGender.requestFocus();
            return;
        }
        RadioButton checkedGender = findViewById(rgGender.getCheckedRadioButtonId());

        String height = etHeight.getText().toString().trim();
        if(height.isEmpty() || height.equals("0") || Integer.parseInt(height) >= 300){
            etHeight.setError(getString(R.string.error_height));
            return;
        }

        String weight = etWeight.getText().toString().trim();
        if(weight.isEmpty() || weight.equals("0")){
            etWeight.setError(getString(R.string.error_weight));
            return;
        }

        calculate(checkedGender, height, weight);
    }

    private void calculate(RadioButton checkedGender, String height, String weight){
        double BMI = Double.parseDouble(weight) / ( Double.parseDouble(height) * Double.parseDouble(height) );
        String gender = checkedGender.getText().toString();

        String weightCategory;
        if(BMI < 18.5){
            weightCategory = getString(R.string.underweight);
        } else if (BMI < 25) {
            weightCategory = getString(R.string.correct_weight);
        }else{
            weightCategory = getString(R.string.overweight);
        }


        tvResult.setText(getString(R.string.tv_result, BMI, weightCategory, gender));

    }

}