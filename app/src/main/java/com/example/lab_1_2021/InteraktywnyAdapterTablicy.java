package com.example.lab_1_2021;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class InteraktywnyAdapterTablicy extends RecyclerView.Adapter<InteraktywnyAdapterTablicy.OcenyViewHolder>
{
    private List<ModelOceny> mListaOcen;
    private LayoutInflater mPompka;

    //konstruktor Adaptera
    public InteraktywnyAdapterTablicy(List<ModelOceny> listaOcen, Activity kontekst)
    {
        this.mPompka = kontekst.getLayoutInflater();
        this.mListaOcen = listaOcen;
    }

    //tworzy główny element layout i tworzy pojemnik (holder) dla danego wiersza
    @NonNull
    @Override
    public OcenyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        //utworzenie layoutu wiersza na podstawie XMLa
        View wiersz = mPompka.inflate(R.layout.wiersz_listy, null);
        //zwrócenie nowego obiektu holdera
        return new OcenyViewHolder(wiersz);
    }

    //wypełnia wiersz przechowywany w pojemniku danymi dla określonego wiersza
    @Override
    public void onBindViewHolder(@NonNull OcenyViewHolder holder, int position)
    {
        //powiązanie grupy przycisków radiowych z wierszem listy
        holder.grupaRadioPrzyciskow.setTag(position);
        holder.nazwaPrzedmiotuText.setTag(position);
        //ustawienie nazwy przedmiotu
        ModelOceny modelOceny = mListaOcen.get(position);
        holder.nazwaPrzedmiotuText.setText(modelOceny.getNazwa());
        //zaznaczenie odpowiedniego przycisku radiowego
        switch(modelOceny.getOcena())
        {
            case 2:
                holder.grupaRadioPrzyciskow.check(R.id.radioButton);
                break;
            case 3:
                holder.grupaRadioPrzyciskow.check(R.id.radioButton2);
                break;
            case 4:
                holder.grupaRadioPrzyciskow.check(R.id.radioButton3);
                break;
            case 5:
                holder.grupaRadioPrzyciskow.check(R.id.radioButton4);
                break;
            default: break;
        }
    }

    //zwraca liczbę elementów
    @Override
    public int getItemCount()
    {
        return mListaOcen.size();
    }

    //widok wiersza z referencjami do poszczególnych elementów wiersza
    public class OcenyViewHolder extends RecyclerView.ViewHolder implements RadioGroup.OnCheckedChangeListener
    {
        //pola przechowujące referencje do elementów wiersza
        RadioGroup grupaRadioPrzyciskow;
        TextView nazwaPrzedmiotuText;

        public OcenyViewHolder(@NonNull View glownyElementWiersza)
        {
            super(glownyElementWiersza);
            grupaRadioPrzyciskow = (RadioGroup)glownyElementWiersza.findViewById(R.id.grupa_buttonow);
            nazwaPrzedmiotuText = (TextView)glownyElementWiersza.findViewById(R.id.nazwaPrzedmiotu);
            //ustawienia listenera zaznaczenia radio buttona
            grupaRadioPrzyciskow.setOnCheckedChangeListener(this);
        }

        //implementacje interfejsów obsługujących zdarzenia
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId)
        {
            RadioButton radioSnickers = (RadioButton)group.findViewById(checkedId);
            int wartoscBatona;
            if(radioSnickers.isChecked())
            {
                wartoscBatona = Integer.parseInt(radioSnickers.getText().toString());
                int indeks = (Integer)grupaRadioPrzyciskow.getTag();
                ModelOceny modelOceny = mListaOcen.get((Integer) grupaRadioPrzyciskow.getTag());
                modelOceny.setOcena(Integer.parseInt((String) radioSnickers.getText()));
            }
        }
    }
}
