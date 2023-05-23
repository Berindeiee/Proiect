package com.example.proiect;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private LinearLayout conversionLayout_distanta;
    private LinearLayout conversionLayout_masa;

    private LinearLayout conversionLayout_date;
    private LinearLayout panouFavorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        conversionLayout_distanta = findViewById(R.id.distanta_item);
        conversionLayout_masa = findViewById(R.id.masa_item);
        conversionLayout_date = findViewById(R.id.date_item);
        panouFavorite = findViewById(R.id.panouFavorite);


        setMenuItem(R.id.date_item, "Date", "Convertor de date", R.drawable.date);


        conversionLayout_distanta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, pagina_lungime.class);
                startActivity(intent);
            }
        });

        conversionLayout_masa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, pagina_masa.class);
                startActivity(intent);
            }
        });

        conversionLayout_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, pagina_date.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Șterge toate butoanele existente
        panouFavorite.removeAllViews();

        SharedPreferences sharedPreferences = getSharedPreferences("com.example.proiect", MODE_PRIVATE);

        boolean isPaginaLungimeFavorite = sharedPreferences.getBoolean("pagina_lungime_favorite", false);
        if (isPaginaLungimeFavorite) {
            // Adaugă pagina_lungime la lista de favorite
            Button button = new Button(this);
            button.setText("Distanță");
            button.setBackground(ContextCompat.getDrawable(this, R.drawable.round_button)); // setează fundalul rotund
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, 0, 10, 0); // setează marginile (dreapta este 10dp)
            button.setLayoutParams(params);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, pagina_lungime.class);
                    startActivity(intent);
                }
            });
            panouFavorite.addView(button);
        }

        boolean isPaginaGreutateFavorite = sharedPreferences.getBoolean("pagina_masa_favorite", false);
        if (isPaginaGreutateFavorite) {
            // Adaugă pagina_greutate la lista de favorite
            Button button = new Button(this);
            button.setText("Masă");
            button.setBackground(ContextCompat.getDrawable(this, R.drawable.round_button)); // setează fundalul rotund
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, 0, 10, 0); // setează marginile (dreapta este 10dp)
            button.setLayoutParams(params);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, pagina_masa.class);
                    startActivity(intent);
                }
            });
            panouFavorite.addView(button);
        }

        boolean isPaginaDateFavorite = sharedPreferences.getBoolean("pagina_date_favorite", false);
        if (isPaginaDateFavorite) {
            // Adaugă pagina_greutate la lista de favorite
            Button button = new Button(this);
            button.setText("Date");
            button.setBackground(ContextCompat.getDrawable(this, R.drawable.round_button)); // setează fundalul rotund
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, 0, 10, 0); // setează marginile (dreapta este 10dp)
            button.setLayoutParams(params);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, pagina_date.class);
                    startActivity(intent);
                }
            });
            panouFavorite.addView(button);
        }

        // Repetă pentru fiecare activitate care poate fi adăugată la favorite
    }

    public void setMenuItem(int layoutId, String itemName, String itemCategory, int imageResourceId) {
        // Referiți componenta folosind ID-ul pe care l-ați setat în XML
        LinearLayout menuItemLayout = findViewById(layoutId);

        // Referiți TextView-urile și ImageView din interiorul componentei
        TextView menuItemName = menuItemLayout.findViewById(R.id.menuItemName);
        TextView menuItemCategory = menuItemLayout.findViewById(R.id.menuItemCategory);
        ImageView menuItemImage = menuItemLayout.findViewById(R.id.menuItemImage);

        // Setează textul pentru TextView-uri și imaginea pentru ImageView
        menuItemName.setText(itemName);
        menuItemCategory.setText(itemCategory);
        menuItemImage.setImageResource(imageResourceId);
    }



}
