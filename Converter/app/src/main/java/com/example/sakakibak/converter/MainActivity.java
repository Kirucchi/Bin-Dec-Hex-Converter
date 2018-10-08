package com.example.sakakibak.converter;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private String inputType;
    private String outputType;
    private boolean error = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //creates spinners
        Spinner spinner1 = (Spinner) findViewById(R.id.input_select);
        Spinner spinner2 = (Spinner) findViewById(R.id.output_select);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.options, R.layout.spinner_font);
        adapter.setDropDownViewResource(R.layout.spinner_font);
        spinner1.setAdapter(adapter);
        spinner1.setOnItemSelectedListener(this);
        spinner2.setAdapter(adapter);
        spinner2.setOnItemSelectedListener(this);

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                convert();
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String text = adapterView.getItemAtPosition(i).toString();
        //determines input type
        if (adapterView.getId() == R.id.input_select){
            inputType = text;
        } //determines output type
        else if (adapterView.getId() == R.id.output_select){
            outputType = text;
        }
        //Toast.makeText(adapterView.getContext(), text, Toast.LENGTH_SHORT).show();

    }


    public void convert(){
        EditText edit = (EditText) findViewById(R.id.input);
        long basetennum = 0;
        boolean negative = false;
        String num = edit.getText().toString();
        TextView output = (TextView) findViewById((R.id.output));
        if (num.equals("")) {
            output.setText("");
            return;
        }
        if (num.substring(0, 1).equals("-")){
            negative = true;
            num = num.substring(1, num.length());
        }
        for (int a=0; a<num.length(); a++){
            String let = num.substring(num.length()-a-1, num.length()-a);

            //Binary check

            if (inputType.equals("Binary")){
                if (!"01".contains(let)){
                    Toast.makeText(this, "Invalid input", Toast.LENGTH_SHORT).show();
                    return;
                }
                basetennum += (Math.pow(2, a) * Long.parseLong(let));
            }

            //Decimal Check

            else if (inputType.equals("Decimal")){
                if (!"0123456789".contains(let)){
                    Toast.makeText(this, "Invalid input", Toast.LENGTH_SHORT).show();
                    return;
                }
                basetennum += (Math.pow(10, a) * Long.parseLong(let));
            }

            //Hexadecimal check

            else if (inputType.equals("Hexadecimal")){
                if (!"0123456789ABCDEFabcdef".contains(let)){
                    Toast.makeText(this, "Invalid input", Toast.LENGTH_SHORT).show();
                    return;
                }
                basetennum += (Math.pow(16, a) * Long.parseLong(let, 16));
            }
        }

        //convert to output

        if (basetennum==Long.MAX_VALUE || basetennum==Long.MIN_VALUE){
            Toast.makeText(this, "Number is too large", Toast.LENGTH_SHORT).show();
            return;
        }

        String finalval = "";
        if (negative)
            finalval = "-";
        if (outputType.equals("Binary"))
            finalval += Long.toBinaryString(basetennum);
        else if (outputType.equals("Decimal"))
            finalval += Long.toString(basetennum);
        else
            finalval += Long.toHexString(basetennum).toUpperCase();

        output.setText(finalval);
    }


    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

}
