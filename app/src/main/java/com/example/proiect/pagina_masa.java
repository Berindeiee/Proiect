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

public class pagina_masa extends AppCompatActivity {
    private boolean isUpdating = false;

    private Map<String, Double> toUnitConversion;
    private Map<String, Double> fromUnitConversion;

    private static final String PREFERENCES_NAME = "com.example.proiect";
    private static final String FAVORITE_KEY = "pagina_masa_favorite";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagina_masa);

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

        List<String> distanceUnits = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.mass_units)));
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, distanceUnits) {
            @Override
            public Filter getFilter() {
                return new Filter() {
                    @Override
                    protected FilterResults performFiltering(CharSequence constraint) {
                        if (constraint != null) {
                            ArrayList<String> suggestions = new ArrayList<String>();
                            for (String item : getResources().getStringArray(R.array.mass_units)) {
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
        toUnitConversion.put("Miligram (mg)", 1e-6);
        toUnitConversion.put("Gram (g)", 1e-3);
        toUnitConversion.put("Kilogram (kg)", 1.0);
        toUnitConversion.put("Tona (t)", 1e3);
        toUnitConversion.put("Uncie (oz)", 0.0283495);
        toUnitConversion.put("Lira (lb)", 0.453592);
        toUnitConversion.put("Piatra (st)", 6.35029);
        toUnitConversion.put("Tona scurta (us t)", 907.185);
        toUnitConversion.put("Tona lunga (uk t)", 1016.05);

        fromUnitConversion = new HashMap<>();
        fromUnitConversion.put("Miligram (mg)", 1e6);
        fromUnitConversion.put("Gram (g)", 1e3);
        fromUnitConversion.put("Kilogram (kg)", 1.0);
        fromUnitConversion.put("Tona (t)", 1e-3);
        fromUnitConversion.put("Uncie (oz)", 35.274);
        fromUnitConversion.put("Lira (lb)", 2.20462);
        fromUnitConversion.put("Piatra (st)", 0.157473);
        fromUnitConversion.put("Tona scurta (us t)", 0.00110231);
        fromUnitConversion.put("Tona lunga (uk t)", 0.000984207);
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
