package com.example.proiect;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
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

public class pagina_lungime extends AppCompatActivity {
    private boolean isUpdating = false;

    private Map<String, Double> toMetersConversion;
    private Map<String, Double> fromMetersConversion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagina_lungime);

        initializeConversionMaps();

        Switch favoriteSwitch = findViewById(R.id.favorite_switch);

        SharedPreferences sharedPreferences = getSharedPreferences("com.example.proiect", MODE_PRIVATE);
        boolean isFavorite = sharedPreferences.getBoolean("pagina_lungime_favorite", false);
        favoriteSwitch.setChecked(isFavorite);

        favoriteSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            SharedPreferences sharedPreferences1 = getSharedPreferences("com.example.proiect", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences1.edit();
            editor.putBoolean("pagina_lungime_favorite", isChecked);
            editor.apply();
        });


        AutoCompleteTextView unitSpinner1 = findViewById(R.id.autoComplet1);
        AutoCompleteTextView unitSpinner2 = findViewById(R.id.autoComplet2);
        EditText valueInput1 = findViewById(R.id.value_input_1);
        EditText valueInput2 = findViewById(R.id.value_input_2);

        List<String> distanceUnits = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.distance_units)));
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, distanceUnits) {
            @Override
            public Filter getFilter() {
                return new Filter() {
                    @Override
                    protected FilterResults performFiltering(CharSequence constraint) {
                        if (constraint != null) {
                            ArrayList<String> suggestions = new ArrayList<String>();
                            for (String item : getResources().getStringArray(R.array.distance_units)) {
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
        toMetersConversion = new HashMap<>();
        toMetersConversion.put("Milimetru (mm)", 1/1000.0);
        toMetersConversion.put("Centimetru (cm)", 1/100.0);
        toMetersConversion.put("Decimetru (dm)", 1/10.0);
        toMetersConversion.put("Metru (m)", 1.0);
        toMetersConversion.put("Decametru (dam)", 10.0);
        toMetersConversion.put("Hectometru (hm)", 100.0);
        toMetersConversion.put("Kilometru (km)", 1000.0);
        toMetersConversion.put("Inch (in)", 0.0254);
        toMetersConversion.put("Picior (ft)", 0.3048);
        toMetersConversion.put("Yard (yd)", 0.9144);
        toMetersConversion.put("Milă (mi)", 1609.344);
        toMetersConversion.put("Milă marină (nmi)", 1852.0);
        toMetersConversion.put("Unitate astronomică (AU)", 149597870700.0);
        toMetersConversion.put("An-lumină (ly)", 9.461e+15);
        toMetersConversion.put("Parsec (pc)", 3.086e+16);

        fromMetersConversion = new HashMap<>();
        fromMetersConversion.put("Milimetru (mm)", 1000.0);
        fromMetersConversion.put("Centimetru (cm)", 100.0);
        fromMetersConversion.put("Decimetru (dm)", 10.0);
        fromMetersConversion.put("Metru (m)", 1.0);
        fromMetersConversion.put("Decametru (dam)", 1/10.0);
        fromMetersConversion.put("Hectometru (hm)", 1/100.0);
        fromMetersConversion.put("Kilometru (km)", 1/1000.0);
        fromMetersConversion.put("Inch (in)", 1/0.0254);
        fromMetersConversion.put("Picior (ft)", 1/0.3048);
        fromMetersConversion.put("Yard (yd)", 1/0.9144);
        fromMetersConversion.put("Milă (mi)", 1/1609.344);
        fromMetersConversion.put("Milă marină (nmi)", 1/1852.0);
        fromMetersConversion.put("Unitate astronomică (AU)", 1/149597870700.0);
        fromMetersConversion.put("An-lumină (ly)", 1/9.461e+15);
        fromMetersConversion.put("Parsec (pc)", 1/3.086e+16);
    }

    private void convertAndSetValues(AutoCompleteTextView fromUnitSpinner, AutoCompleteTextView toUnitSpinner, EditText fromValueInput, EditText toValueInput, boolean reverse) {
        String fromUnit = reverse ? toUnitSpinner.getText().toString() : fromUnitSpinner.getText().toString();
        String toUnit = reverse ? fromUnitSpinner.getText().toString() : toUnitSpinner.getText().toString();
        EditText input = reverse ? toValueInput : fromValueInput;
        EditText output = reverse ? fromValueInput : toValueInput;
        String inputValue = input.getText().toString();

        if (!inputValue.isEmpty() && isNumeric(inputValue)) {
            double value = Double.parseDouble(inputValue);
            double result = convertDistance(value, fromUnit, toUnit);
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

    private double convertDistance(double value, String fromUnit, String toUnit) {
        double meters = toMeters(value, fromUnit);
        return fromMeters(meters, toUnit);
    }

    private long lastToastTime = 0;


    private boolean hasShownError = false;

    private double toMeters(double value, String unit) {
        if (!toMetersConversion.containsKey(unit)) {
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
        return value * toMetersConversion.get(unit);
    }

    private double fromMeters(double meters, String unit) {
        if (!fromMetersConversion.containsKey(unit)) {
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
        return meters * fromMetersConversion.get(unit);
    }

}