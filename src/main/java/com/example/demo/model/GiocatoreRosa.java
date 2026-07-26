package com.example.demo.model;


public class GiocatoreRosa {


    // =========================
    // DATI
    // =========================


    private Giocatore giocatore;


    private int prezzo;


    private String modalita;
    // CLASSIC oppure MANTRA






    // =========================
    // COSTRUTTORI
    // =========================


    public GiocatoreRosa(){

    }




    public GiocatoreRosa(
            Giocatore giocatore,
            int prezzo,
            String modalita
    ){

        this.giocatore = giocatore;

        this.prezzo = prezzo;

        this.modalita = modalita;

    }






    public GiocatoreRosa(
            Giocatore giocatore,
            int prezzo
    ){

        this.giocatore = giocatore;

        this.prezzo = prezzo;

        this.modalita = "CLASSIC";

    }








    // =========================
    // GETTER SETTER
    // =========================



    public Giocatore getGiocatore(){

        return giocatore;

    }



    public void setGiocatore(
            Giocatore giocatore
    ){

        this.giocatore = giocatore;

    }





    public int getPrezzo(){

        return prezzo;

    }



    public void setPrezzo(
            int prezzo
    ){

        this.prezzo = prezzo;

    }







    public String getModalita(){

        return modalita;

    }



    public void setModalita(
            String modalita
    ){

        this.modalita = modalita;

    }





    // =========================
    // UTILI
    // =========================



    public String getNome(){

        if(giocatore==null)
            return "";

        return giocatore.getNome();

    }




    public String getRuolo(){

        if(giocatore==null)
            return "";

        return giocatore.getRuolo();

    }





    public String getRuoloMantra(){

        if(giocatore==null)
            return "";

        return giocatore.getRuoloMantra();

    }





    @Override
    public String toString(){

        return giocatore.getNome()
                +" - "
                +prezzo
                +" - "
                +modalita;

    }


}