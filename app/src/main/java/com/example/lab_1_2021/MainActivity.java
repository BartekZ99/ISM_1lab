package com.example.lab_1_2021;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity{

    public static boolean boolImie = false, boolNazwisko = false, boolLiczbaOcen = false;
    Button przyciskOceny;
    private static String kluczWidocznoscPrzycisku;
    final String KLUCZ_1 = "imieTextKey", KLUCZ2 = "nazwiskoTextKey", KLUCZ_3 = "ocenyTextKey";
    boolean czyZdal = false, wylaczenie = false;

    void bool_istnienia_przycisku() //funkcja do znikania i pojawiania przycisku OCENY
    {
        if(boolImie && boolNazwisko && boolLiczbaOcen)
            przyciskOceny.setVisibility(View.VISIBLE);
        else
            przyciskOceny.setVisibility(View.INVISIBLE);
    }

    @Override //aktywność jest tworzona
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        przyciskOceny = (Button)findViewById(R.id.button_oceny); //przycisk OCENY
        //przyciskOceny.setBackgroundColor(Color.GRAY);


        EditText poleImie = (EditText)findViewById(R.id.gui_enterName); //pole do wpisywania imienia
        poleImie.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Toast grzankaImie = Toast.makeText(MainActivity.this, getString(R.string.error_imie), Toast.LENGTH_SHORT);
                if(poleImie.hasFocus() == false && poleImie.getText().toString().trim().length() == 0)
                {
                    poleImie.setError(getString(R.string.error_imie));
                    grzankaImie.show();
                    boolImie = false;
                }
                else if(poleImie.hasFocus() == true && poleImie.getText().toString().trim().length() == 0)
                    boolImie = false;
                    else
                        boolImie = true;

                bool_istnienia_przycisku();
            }
        });

        EditText poleNazwisko = (EditText)findViewById(R.id.gui_enterSurname); //pole do wpisywania nazwiska
        poleNazwisko.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Toast grzankaNazwisko = Toast.makeText(MainActivity.this, getString(R.string.error_nazwisko), Toast.LENGTH_SHORT);
                if(poleNazwisko.hasFocus() == false && poleNazwisko.getText().toString().trim().length() == 0)
                {
                    poleNazwisko.setError(getString(R.string.error_nazwisko));
                    grzankaNazwisko.show();
                    boolNazwisko = false;
                }
                else if(poleNazwisko.hasFocus() == true && poleNazwisko.getText().toString().trim().length() == 0)
                    boolNazwisko = false;
                    else
                        boolNazwisko = true;

                bool_istnienia_przycisku();
            }
        });

        EditText poleLiczbaOcen = (EditText) findViewById(R.id.gui_enterGradeCount); //pole do wpisywania liczby ocen
        poleLiczbaOcen.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Toast grzankaLiczbaOcen = Toast.makeText(MainActivity.this, getString(R.string.error_liczbaOcen), Toast.LENGTH_LONG);
                if(poleLiczbaOcen.getText().toString().isEmpty())
                {
                    poleLiczbaOcen.setError(getString(R.string.error_liczbaOcen));
                    grzankaLiczbaOcen.show();
                    boolLiczbaOcen = false;
                }
                else if(Integer.parseInt(poleLiczbaOcen.getText().toString()) < 5 || Integer.parseInt(poleLiczbaOcen.getText().toString()) > 15)
                    {
                        poleLiczbaOcen.setError(getString(R.string.error_liczbaOcen));
                        grzankaLiczbaOcen.show();
                        boolLiczbaOcen = false;
                    }
                else
                    boolLiczbaOcen = true;

                bool_istnienia_przycisku();
            }
        });

        //naciśnięcie przycisku OCENY
        przyciskOceny.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intencja = new Intent(MainActivity.this, OcenkiActivity.class);
               // EditText poleLiczbaOcen = (EditText)findViewById(R.id.gui_enterGradeCount); //pole do wpisywania liczby ocen
                intencja.putExtra("LICZBA_OCEN", Integer.parseInt(poleLiczbaOcen.getText().toString()));
                startActivityForResult(intencja, 16);
                System.out.println("PRZEKAZANA LICZBA OCEN" + poleLiczbaOcen); //print liczby odebranej
            }
        });

        //Tworzenie elementów, odczytanie referencjji do elementów GUI
        //(findViewById()) ustawienie obsługi zdarzeń, odczytanie zapisanego stanu
        //aktywności z obiektu savedInstanceState


    }

   @Override //Aktywność była niewidoczna
    protected void onRestart()
    {
        super.onRestart();
        //czynności wykonywane, gdy aktywność wcześniej istaniała ale była niewidoczna
    }

    @Override //Aktywność za chwilę stanie się widoczna
    protected void onStart()
    {
        super.onStart();
        //Tworzenie elementów niezbędnych do uaktualnienia interfejsu użytkownika

    }

    @Override //Aktywność jest na pierwszym planie
    protected void onResume()
    {
        super.onResume();
        EditText poleImie = (EditText)findViewById(R.id.gui_enterName); //pole do wpisywania imienia
        EditText poleNazwisko = (EditText)findViewById(R.id.gui_enterSurname); //pole do wpisywania nazwiska
        EditText poleLiczbaOcen = (EditText)findViewById(R.id.gui_enterGradeCount); //pole do wpisywania liczby ocen
        //odczyt. z sharedPref.:
        SharedPreferences preferencje = getPreferences(MODE_PRIVATE);
        //odczyt. wart. pól tekst.:
        poleImie.setText(preferencje.getString("KLUCZ_imie_txt", ""));
        poleNazwisko.setText(preferencje.getString("KLUCZ_nazwisko_txt", ""));
        poleLiczbaOcen.setText(preferencje.getString("KLUCZ_liczba_txt", ""));

    }

    @Override //Aktywność traci "focus". Zostanie zapauzowana (gdy system ma wznowićinną aktywność).
    protected void onPause()
    {
        super.onPause();
        EditText poleImie = (EditText)findViewById(R.id.gui_enterName); //pole do wpisywania imienia
        EditText poleNazwisko = (EditText)findViewById(R.id.gui_enterSurname); //pole do wpisywania nazwiska
        EditText poleLiczbaOcen = (EditText)findViewById(R.id.gui_enterGradeCount); //pole do wpisywania liczby ocen
        //odczyt. sharedPref. i stworzenie edytora:
        SharedPreferences preferencje = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor edytor = preferencje.edit();
        //zapisanie tekstu w sharedPref.:
        edytor.putString("KLUCZ_imie_txt", poleImie.getText().toString());
        edytor.putString("KLUCZ_nazwisko_txt", poleNazwisko.getText().toString());
        edytor.putString("KLUCZ_liczba_txt", poleLiczbaOcen.getText().toString());
        edytor.commit();
    }

    @Override //Aktywność jest niewidoczna. Została zatrzymana.
    protected void onStop()
    {
        super.onStop();
        //Tutaj należy zwolnić zasoby i ewentualnie zapisać istotne elementy stanu.
        //Po wykonaniu tej metody proces z tą aktuywnością możę zostać "zabity" przez
        //system (bez wykonania kolejnych metod cyklu życia).
    }

    @Override //Za chwilę aktywność zostanie zniszczona (tymczasowo lub w celu zwolnienia pamięci).
    protected void onDestroy()
    {
        super.onDestroy();
        if(wylaczenie == true)
        {
            SharedPreferences preferencje = getPreferences(MODE_PRIVATE);
            SharedPreferences.Editor edytor = preferencje.edit();
            //czyszczenie pam. SHaredPref. gdy apka jest "niszczona":
            edytor.clear();
            edytor.commit();
        }
        //Po wykonaniu tej metody proces z tą aktywnością może zostać "zabity" przez system
        //(bez wykonania kolejnych metod cyklu życia).
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState)
    {
        super.onSaveInstanceState(outState);
        Button przyciskOceny = (Button)findViewById(R.id.button_oceny); //przycisk OCENY
        Button przyciskExit = (Button)findViewById(R.id.button_exit); //przycisk wyłączający program
        TextView napisSrednia = (TextView)findViewById(R.id.textViewSrednia); //napis wyświetlający średnią
        //zapisanie stanu przcisków oraz etykiety
        outState.putInt("buttonOcenyVisibility", przyciskOceny.getVisibility());  //stan przycisku oceny
        outState.putInt("buttonExitVisibility", przyciskExit.getVisibility()); //stan przycisku kończącego
        outState.putString("buttonKoniecText", przyciskExit.getText().toString());
        outState.putInt("sredniaTextVisibility", napisSrednia.getVisibility()); //stan napisu wyśw. średnią
        outState.putString("sredniaText", napisSrednia.getText().toString());
        //dodaniezachowanie zmiennej bool używanej w "toście" zamykającym apkę:
        outState.putBoolean("czyZdal", czyZdal);
        //pola tekstowe:
        EditText poleImie = (EditText)findViewById(R.id.gui_enterName); //pole do wpisywania imienia
        EditText poleNazwisko = (EditText)findViewById(R.id.gui_enterSurname); //pole do wpisywania nazwiska
        EditText poleLiczbaOcen = (EditText)findViewById(R.id.gui_enterGradeCount); //pole do wpisywania liczby ocen
        outState.putBoolean("klucz_imie_enabled", poleImie.isEnabled());
        outState.putBoolean("klucz_nazwisko_enabled", poleNazwisko.isEnabled());
        outState.putBoolean("klucz_liczba_enabled", poleLiczbaOcen.isEnabled());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) //TODO TUTTAJJJJJ!
    {
        super.onRestoreInstanceState(savedInstanceState);
        Button przyciskOceny = (Button)findViewById(R.id.button_oceny); //przycisk OCENY
        Button przyciskExit = (Button)findViewById(R.id.button_exit); //przycisk wyjścia
        TextView napisSrednia = (TextView)findViewById(R.id.textViewSrednia); //napis wyświetlający średnią
        //odczytywanie stanów etykiety i przycisków:
        przyciskOceny.setVisibility(savedInstanceState.getInt("buttonOcenyVisibility")); //odczytanie widoczności przycisku oceny
        przyciskExit.setVisibility(savedInstanceState.getInt("buttonExitVisibility")); //odczyt. widoczn. przycisku kończącego
        przyciskExit.setText(savedInstanceState.getString("buttonKoniecText")); //odczytanie tekstu przycisku kończącego
        napisSrednia.setVisibility(savedInstanceState.getInt("sredniaTextVisibility")); //odczyt. napisu średniej
        napisSrednia.setText(savedInstanceState.getString("sredniaText")); //odczyt. tekstu średniej
        //odczyt zmiennej typu boolean użytego w toście
        czyZdal = savedInstanceState.getBoolean("czyZdal");
        //pola tekstowe:
        EditText poleImie = (EditText)findViewById(R.id.gui_enterName); //pole do wpisywania imienia
        EditText poleNazwisko = (EditText)findViewById(R.id.gui_enterSurname); //pole do wpisywania nazwiska
        EditText poleLiczbaOcen = (EditText)findViewById(R.id.gui_enterGradeCount); //pole do wpisywania liczby ocen
        poleImie.setEnabled(savedInstanceState.getBoolean("klucz_imie_enabled"));
        poleNazwisko.setEnabled(savedInstanceState.getBoolean("klucz_nazwisko_enabled"));
        poleLiczbaOcen.setEnabled(savedInstanceState.getBoolean("klucz_liczba_enabled"));

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(data != null)
        {
            Bundle paczuszka = data.getExtras();
            //pobieranie wyniku z aktywności drugiej:
            double wynik_pobrany = paczuszka.getDouble("SREDNIA_WYNIK");
            //łączenie łańcucha znaków do wyświeltenia średniej:
            String doWyswietlenia = getString(R.string.twojaSrednia) + " " + wynik_pobrany;
            TextView napisSrednia = (TextView)findViewById(R.id.textViewSrednia); //napis wyświetlający średnią
            //włączenie widoku etykiety oraz wyśw. wyniku na niej:
            napisSrednia.setText(doWyswietlenia);
            napisSrednia.setVisibility(View.VISIBLE);
            //wyłączenie przycisku OCENY a włączenie kończącego
            przyciskOceny = (Button)findViewById(R.id.button_oceny); //przycisk OCENY
            Button przyciskExit = (Button)findViewById(R.id.button_exit); //przycisk wyjścia
            przyciskOceny.setVisibility(View.GONE);
            przyciskExit.setVisibility(View.VISIBLE);
            //wyłączenie opcji edycji pół EditText:
            EditText poleImie = (EditText)findViewById(R.id.gui_enterName); //pole do wpisywania imienia
            EditText poleNazwisko = (EditText)findViewById(R.id.gui_enterSurname); //pole do wpisywania nazwiska
            EditText poleLiczbaOcen = (EditText)findViewById(R.id.gui_enterGradeCount); //pole do wpisywania liczby ocen
            poleImie.setEnabled(false);
            poleNazwisko.setEnabled(false);
            poleLiczbaOcen.setEnabled(false);
            //sprawdzenie czy student zdał, ustawienie boola oraz tekstu do wyświetlenia:
            if(wynik_pobrany >= 3.0)
            {
                przyciskExit.setText(R.string.przycisk_koniec_zdal);
                czyZdal = true;
            }
            else
            {
                przyciskExit.setText(R.string.przycisk_koniec_nieZdal);
                czyZdal = false;
            }
        }
    }

    public void exitOnClick(View view)
    {
        if(czyZdal == true)
        {
            Toast.makeText(MainActivity.this, R.string.tost_exit_poszlo, Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(MainActivity.this, R.string.tost_exit_nie_poszlo, Toast.LENGTH_SHORT).show();
        }
        wylaczenie = true;
        finish();
    }
}
