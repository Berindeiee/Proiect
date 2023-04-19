package com.example.proiect;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

public class pagina_lungime extends AppCompatActivity {
    private boolean isUpdating = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagina_lungime);

        AutoCompleteTextView unitSpinner1 = findViewById(R.id.autoComplet1);
        AutoCompleteTextView unitSpinner2 = findViewById(R.id.autoComplet2);
        EditText valueInput1 = findViewById(R.id.value_input_1);
        EditText valueInput2 = findViewById(R.id.value_input_2);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.distance_units,
                android.R.layout.simple_dropdown_item_1line
        );

        unitSpinner1.setAdapter(adapter);
        unitSpinner2.setAdapter(adapter);

        unitSpinner1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!isUpdating) {
                    isUpdating = true;
                    convertAndSetValues(unitSpinner1, unitSpinner2, valueInput1, valueInput2, false);

                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                isUpdating = false;
            }
        });

        unitSpinner2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!isUpdating) {
                    isUpdating = true;
                    convertAndSetValues(unitSpinner1, unitSpinner2, valueInput1, valueInput2, true);

                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                isUpdating = false;
            }
        });

        valueInput1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!isUpdating) {
                    isUpdating = true;
                    convertAndSetValues(unitSpinner1, unitSpinner2, valueInput1, valueInput2, false);

                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        valueInput2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!isUpdating) {
                    isUpdating = true;
                    convertAndSetValues(unitSpinner1, unitSpinner2, valueInput1, valueInput2, true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                isUpdating = false;
            }
        });

    }

    private void convertAndSetValues(AutoCompleteTextView fromUnitSpinner, AutoCompleteTextView toUnitSpinner, EditText fromValueInput, EditText toValueInput, boolean reverse) {
        String fromUnit = reverse ? toUnitSpinner.getText().toString() : fromUnitSpinner.getText().toString();
        String toUnit = reverse ? fromUnitSpinner.getText().toString() : toUnitSpinner.getText().toString();
        EditText input = reverse ? toValueInput : fromValueInput;
        EditText output = reverse ? fromValueInput : toValueInput;
        String inputValue = input.getText().toString();

        if (!inputValue.isEmpty()) {
            double value = Double.parseDouble(inputValue);
            double result = convertDistance(value, fromUnit, toUnit);
            output.setText(String.valueOf(result));
            isUpdating = false;
        } else {
            output.setText("");
        }
    }


    private double convertDistance(double value, String fromUnit, String toUnit) {
        double meters = toMeters(value, fromUnit);
        double result = fromMeters(meters, toUnit);
        return result;
    }

    private double toMeters(double value, String unit) {
        switch (unit) {
            case "Milimetru (mm)":
                return value / 1000;
            case "Centimetru (cm)":
                return value / 100;
            case "Decimetru (dm)":
                return value / 10;
            case "Metru (m)":
                return value;
            case "Decametru (dam)":
                return value * 10;
            case "Hectometru (hm)":
                return value * 100;
            case "Kilometru (km)":
                return value * 1000;
            case "Inch (in)":
                return value * 0.0254;
            case "Picior (ft)":
                return value * 0.3048;
            case "Yard (yd)":
                return value * 0.9144;
            case "Milă (mi)":
                return value * 1609.344;
            case "Milă marină (nmi)":
                return value * 1852;
            case "Unitate astronomică (AU)":
                return value * 149597870700L;
            case "An-lumină (ly)":
                return value * 9.461e+15;
            case "Parsec (pc)":
                return value * 3.086e+16;
            default:
                return 0;
        }
    }

    private double fromMeters(double meters, String unit) {
        switch (unit) {
            case "Milimetru (mm)":
                return meters * 1000;
            case "Centimetru (cm)":
                return meters * 100;
            case "Decimetru (dm)":
                return meters * 10;
            case "Metru (m)":
                return meters;
            case "Decametru (dam)":
                return meters / 10;
            case "Hectometru (hm)":
                return meters / 100;
            case "Kilometru (km)":
                return meters / 1000;
            case "Inch (in)":
                return meters / 0.0254;
            case "Picior (ft)":
                return meters / 0.3048;
            case "Yard (yd)":
                return meters / 0.9144;
            case "Milă (mi)":
                return meters / 1609.344;
            case "Milă marină (nmi)":
                return meters / 1852;
            case "Unitate astronomică (AU)":
                return meters / 149597870700L;
            case "An-lumină (ly)":
                return meters / 9.461e+15;
            case "Parsec (pc)":
                return meters / 3.086e+16;
            default:
                return 0;
        }
    }
}