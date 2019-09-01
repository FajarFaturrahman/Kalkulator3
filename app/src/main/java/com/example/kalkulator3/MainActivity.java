package com.example.kalkulator3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class MainActivity extends AppCompatActivity {

    private int[] btnAngka= {R.id.btn_0,R.id.btn_1,R.id.btn_2,R.id.btn_3,R.id.btn_4,R.id.btn_5,R.id.btn_6,R.id.btn_7,R.id.btn_8,R.id.btn_9};

    private int[] btnOperasi = {R.id.btn_tambah,R.id.btn_kurang,R.id.btn_kali,R.id.btn_bagi};

    private TextView txtHasil;

    private boolean angkaTerakhir;

    private boolean stateError;

    private boolean lastDot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.txtHasil = findViewById(R.id.txtHasil);

        setNumberOnClickListener();
        setOperatorOnClickListener();
    }

    private void setNumberOnClickListener() {

        View.OnClickListener Listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Button btn = (Button) view;

                if (stateError) {
                    txtHasil.setText(btn.getText());
                    stateError = false;
                } else {
                    txtHasil.append(btn.getText());
                }

                angkaTerakhir = true;
            }
        };

        for (int id: btnAngka){
            findViewById(id).setOnClickListener(Listener);
        }
    }

    private void setOperatorOnClickListener(){

        View.OnClickListener Listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (angkaTerakhir && !stateError){
                    Button btn = (Button) view;
                    txtHasil.append(btn.getText());
                    angkaTerakhir = false;
                    lastDot = false;
                }
            }
        };

        for (int id: btnOperasi){
            findViewById(id).setOnClickListener(Listener);
        }

        findViewById(R.id.btn_koma).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (angkaTerakhir && !stateError && !lastDot){
                    txtHasil.append(".");
                    angkaTerakhir = false;
                    lastDot = true;
                }
            }
        });

        findViewById(R.id.btnAc).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtHasil.setText("");
                angkaTerakhir = false;
                lastDot = false;
                stateError = false;
            }
        });

        findViewById(R.id.btn_samaDengan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                samaDengan();
            }
        });
    }

    private void samaDengan(){
        if (angkaTerakhir && !stateError){

            String txt = txtHasil.getText().toString();

            Expression expression = new ExpressionBuilder(txt).build();

            try{
                double hasil = expression.evaluate();
                txtHasil.setText(Double.toString(hasil));
                lastDot = true;
            }catch (ArithmeticException ex){
                txtHasil.setText("error");
                stateError = true;
                angkaTerakhir = true;
            }
        }
    }
}
