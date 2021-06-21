package com.example.lab_1_2021;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class OcenkiActivity extends AppCompatActivity
{
    private int liczbaOcen;
    Button przycisk;
    RecyclerView mListaOcen;
    String[] przedmiotyTab;
    ArrayList<ModelOceny> mDane;

    protected void onCreate(Bundle savedInstanceState)
    {
        przedmiotyTab = getResources().getStringArray(R.array.tablica_ocen);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ocenki); //widok layoutu ocenki
        //pobranie listy przedmiotów z tablicy stringów:
        przycisk = (Button)findViewById(R.id.button_oblicz); //przycisk "oblicz oceny"
        przycisk.setText(getString(R.string.przycisk_oblicz)); //ustawienie napisu na przycisku "oblicz oceny"
        //pobranie danych przekazanych z głównej aktywności
        Bundle paczuszka = getIntent().getExtras();
        liczbaOcen = paczuszka.getInt("LICZBA_OCEN");
        //wypełnienie tab. danymi
        mDane = new ArrayList<ModelOceny>();
        //wypełnienie obiektów listy:
        for(int i = 0; i < liczbaOcen; i++){
            mDane.add(new ModelOceny(przedmiotyTab[i],2));
        }

        //utworzenie adaptera tablicy mDane:
        InteraktywnyAdapterTablicy adapterekTab = new InteraktywnyAdapterTablicy(mDane, this);

        //ustawienie adaptera pod recyclerVBiew:
        mListaOcen = (RecyclerView)findViewById(R.id.recycler_przedmioty);
        mListaOcen.setAdapter(adapterekTab);
        mListaOcen.setLayoutManager(new LinearLayoutManager(this));
    }

    public void obliczSrednia(View view)
    {
        double wynikSredniej;
        int sumaOcen = 0;
        for(ModelOceny all :mDane) //obliczenie sumy ocen do średniej
        {
            sumaOcen += all.getOcena();
        }
        //obliczenie średniej
        wynikSredniej = 1.0 * sumaOcen / (1.0 * liczbaOcen);
        //utworzenie intencji
        Intent intencja = new Intent();
        //przekazanie średniej do utworzonej intencji
        intencja.putExtra("SREDNIA_WYNIK", wynikSredniej);
        setResult(RESULT_OK, intencja);
        finish();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState)
    {
        super.onSaveInstanceState(outState);
        //zapisanie wartości przycisków "radio button"
        //tab. do przechowania danych wartości ocen z tab. mDane
        int[] ocenki = new int[mDane.size()];
        //wypełnianie ocenami tablicy
        int i = 0;
        for(ModelOceny all :mDane)
        {
            ocenki[i] = all.getOcena();
            i++;
        }
        outState.putIntArray("OCENY", ocenki);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState)
    {
        super.onRestoreInstanceState(savedInstanceState);
        //odczytywanie wartości przycisków "radio button"
        //pobieranie tablicy z ocenami
        int[] ocenki  = savedInstanceState.getIntArray("OCENY");
        //wyczyszczenie listy mDane
        mDane = new ArrayList<ModelOceny>();
        //wypełnienie listy mDane na nowo:
        for(int i = 0; i < ocenki.length; i++)
        {
            mDane.add(new ModelOceny(przedmiotyTab[i], ocenki[i]));
        }
        //utowrzenie adaptera z odczytanych danych:
        InteraktywnyAdapterTablicy adapter = new InteraktywnyAdapterTablicy(mDane, this);
        //ustawienia utworzonego adaptera:
        mListaOcen = (RecyclerView) findViewById(R.id.recycler_przedmioty);
        mListaOcen.setAdapter(adapter);
        mListaOcen.setLayoutManager(new LinearLayoutManager(this));
    }
}