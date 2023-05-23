package com.example.proiect;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class pagina_date extends AppCompatActivity {
    private boolean isUpdating = false;

    private Map<String, Double> toUnitConversion;
    private Map<String, Double> fromUnitConversion;

    private static final String PREFERENCES_NAME = "com.example.proiect";
    private static final String FAVORITE_KEY = "pagina_date_favorite";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagina_date);

        initializeConversionMaps();


        Switch favoriteSwitch = findViewById(R.id.favorite_switch);

        SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE);
        boolean isFavorite = sharedPreferences.getBoolean(FAVORITE_KEY, false);

        favoriteSwitch.setChecked(isFavorite);

        favoriteSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            SharedPreferences sharedPreferences1 = getSharedPreferences("com.example.proiect", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences1.edit();
            editor.putBoolean(FAVORITE_KEY, isChecked);
            editor.apply();
        });


        AutoCompleteTextView unitSpinner1 = findViewById(R.id.autoComplet1);
        AutoCompleteTextView unitSpinner2 = findViewById(R.id.autoComplet2);
        EditText valueInput1 = findViewById(R.id.value_input_1);
        EditText valueInput2 = findViewById(R.id.value_input_2);

        List<String> distanceUnits = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.data_units)));
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, distanceUnits) {
            @Override
            public Filter getFilter() {
                return new Filter() {
                    @Override
                    protected FilterResults performFiltering(CharSequence constraint) {
                        if (constraint != null) {
                            ArrayList<String> suggestions = new ArrayList<String>();
                            for (String item : getResources().getStringArray(R.array.data_units)) {
                                if (item.toLowerCase().contains(constraint.toString().toLowerCase())) {
                                    suggestions.add(item);
                                }
                            }

                            FilterResults filterResults = new FilterResults();
                            filterResults.values = suggestions;
                            filterResults.count = suggestions.size();
                            return filterResults;
                        } else {
                            return new FilterResults();
                        }
                    }

                    @Override
                    protected void publishResults(CharSequence constraint, FilterResults results) {
                        ArrayList<String> filteredList = (ArrayList<String>) results.values;
                        if (results != null && results.count > 0) {
                            clear();
                            for (String c : filteredList) {
                                add(c);
                            }
                            notifyDataSetChanged();
                        }
                    }
                };
            }
        };

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
                    hasShownError = false;
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
                    hasShownError = false;
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


        unitSpinner1.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                unitSpinner1.setText("");
            }
        });
        unitSpinner2.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                unitSpinner2.setText("");
            }
        });

        unitSpinner1.setOnClickListener(v -> unitSpinner1.setText(""));
        unitSpinner2.setOnClickListener(v -> unitSpinner2.setText(""));

    }

    private void initializeConversionMaps() {
        toUnitConversion = new HashMap<>();
        toUnitConversion.put("Bit (b)", 1.0);
        toUnitConversion.put("Byte (B)", 8.0);
        toUnitConversion.put("Kilobit (Kb)", 1e3);
        toUnitConversion.put("Kilobyte (KB)", 8e3);
        toUnitConversion.put("Megabit (Mb)", 1e6);
        toUnitConversion.put("Megabyte (MB)", 8e6);
        toUnitConversion.put("Gigabit (Gb)", 1e9);
        toUnitConversion.put("Gigabyte (GB)", 8e9);
        toUnitConversion.put("Terabit (Tb)", 1e12);
        toUnitConversion.put("Terabyte (TB)", 8e12);
        toUnitConversion.put("Petabit (Pb)", 1e15);
        toUnitConversion.put("Petabyte (PB)", 8e15);
        toUnitConversion.put("Exabit (Eb)", 1e18);
        toUnitConversion.put("Exabyte (EB)", 8e18);
        toUnitConversion.put("Zettabit (Zb)", 1e21);
        toUnitConversion.put("Zettabyte (ZB)", 8e21);
        toUnitConversion.put("Yottabit (Yb)", 1e24);
        toUnitConversion.put("Yottabyte (YB)", 8e24);

        fromUnitConversion = new HashMap<>();
        fromUnitConversion.put("Bit (b)", 1.0);
        fromUnitConversion.put("Byte (B)", 0.125);
        fromUnitConversion.put("Kilobit (Kb)", 1e-3);
        fromUnitConversion.put("Kilobyte (KB)", 0.125e-3);
        fromUnitConversion.put("Megabit (Mb)", 1e-6);
        fromUnitConversion.put("Megabyte (MB)", 0.125e-6);
        fromUnitConversion.put("Gigabit (Gb)", 1e-9);
        fromUnitConversion.put("Gigabyte (GB)", 0.125e-9);
        fromUnitConversion.put("Terabit (Tb)", 1e-12);
        fromUnitConversion.put("Terabyte (TB)", 0.125e-12);
        fromUnitConversion.put("Petabit (Pb)", 1e-15);
        fromUnitConversion.put("Petabyte (PB)", 0.125e-15);
        fromUnitConversion.put("Exabit (Eb)", 1e-18);
        fromUnitConversion.put("Exabyte (EB)", 0.125e-18);
        fromUnitConversion.put("Zettabit (Zb)", 1e-21);
        fromUnitConversion.put("Zettabyte (ZB)", 0.125e-21);
        fromUnitConversion.put("Yottabit (Yb)", 1e-24);
        fromUnitConversion.put("Yottabyte (YB)", 0.125e-24);
    }


    private void convertAndSetValues(AutoCompleteTextView fromUnitSpinner, AutoCompleteTextView toUnitSpinner, EditText fromValueInput, EditText toValueInput, boolean reverse) {
        String fromUnit = reverse ? toUnitSpinner.getText().toString() : fromUnitSpinner.getText().toString();
        String toUnit = reverse ? fromUnitSpinner.getText().toString() : toUnitSpinner.getText().toString();
        EditText input = reverse ? toValueInput : fromValueInput;
        EditText output = reverse ? fromValueInput : toValueInput;
        String inputValue = input.getText().toString();

        if (!inputValue.isEmpty() && isNumeric(inputValue)) {
            double value = Double.parseDouble(inputValue);
            double result = convertUnit(value, fromUnit, toUnit);
            output.setText(String.valueOf(result));
            isUpdating = false;
        } else {
            output.setText("");
        }
    }

    private boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }

    private double convertUnit(double value, String fromUnit, String toUnit) {
        double meters = toMeters(value, fromUnit);
        return fromMeters(meters, toUnit);
    }

    private long lastToastTime = 0;


    private boolean hasShownError = false;

    private double toMeters(double value, String unit) {
        if (!toUnitConversion.containsKey(unit)) {
            if (!hasShownError) {
                long currentTime = System.currentTimeMillis();
                if (currentTime - lastToastTime > 4000) { // Verificăm dacă au trecut cel puțin 2 secunde de la ultimul toast
                    Toast.makeText(this, "Unitatea de măsură nu este recunoscută.", Toast.LENGTH_SHORT).show();
                    lastToastTime = currentTime; // Actualizăm timpul ultimului toast
                }
                hasShownError = true;
            }
            return 0;
        }
        return value * toUnitConversion.get(unit);
    }

    private double fromMeters(double meters, String unit) {
        if (!fromUnitConversion.containsKey(unit)) {
            if (!hasShownError) {
                long currentTime = System.currentTimeMillis();
                if (currentTime - lastToastTime > 4000) { // Verificăm dacă au trecut cel puțin 2 secunde de la ultimul toast
                    Toast.makeText(this, "Unitatea de măsură nu este recunoscută.", Toast.LENGTH_SHORT).show();
                    lastToastTime = currentTime; // Actualizăm timpul ultimului toast
                }
                hasShownError = true;
            }
            return 0;
        }
        return meters * fromUnitConversion.get(unit);
    }

}
