package com.example.demo.repository;


import com.example.demo.model.Giocatore;

import org.springframework.data.jpa.domain.Specification;


public class GiocatoreSpecification {



    public static Specification<Giocatore> filtra(


            String nome,

            String ruolo,

            String ruoloMantra,

            String squadra,


            Integer quotMin,

            Integer quotMax,


            Integer fvmMin,

            Integer fvmMax,


            Double fmMin,

            Double mvMin


    ){


        return Specification
                .where(nomeLike(nome))
                .and(ruoloUguale(ruolo))
                .and(ruoloMantraLike(ruoloMantra))
                .and(squadraLike(squadra))
                .and(quotazioneMin(quotMin))
                .and(quotazioneMax(quotMax))
                .and(fvmMinimo(fvmMin))
                .and(fvmMassimo(fvmMax))
                .and(fantaMediaMin(fmMin))
                .and(mediaVotoMin(mvMin));


    }









    private static Specification<Giocatore> nomeLike(
            String nome
    ){


        return (root, query, cb) -> {


            if(nome==null || nome.isBlank())

                return null;



            return cb.like(

                    cb.lower(
                    root.get("nome")
                    ),

                    "%" +
                    nome.toLowerCase()
                    +
                    "%"

            );


        };

    }









    private static Specification<Giocatore> squadraLike(
            String squadra
    ){


        return (root, query, cb) -> {


            if(squadra==null || squadra.isBlank())

                return null;



            return cb.like(

                    cb.lower(
                    root.get("squadra")
                    ),

                    "%" +
                    squadra.toLowerCase()
                    +
                    "%"

            );


        };


    }









    private static Specification<Giocatore> ruoloUguale(
            String ruolo
    ){


        return (root, query, cb) -> {


            if(ruolo==null || ruolo.isBlank())

                return null;



            return cb.equal(

                    root.get("ruolo"),

                    ruolo.toUpperCase()

            );


        };


    }









    private static Specification<Giocatore> ruoloMantraLike(
            String ruoloMantra
    ){


        return (root, query, cb) -> {


            if(ruoloMantra==null || ruoloMantra.isBlank())

                return null;



            return cb.like(

                    cb.lower(
                    root.get("ruoloMantra")
                    ),

                    "%" +
                    ruoloMantra.toLowerCase()
                    +
                    "%"

            );


        };


    }









    private static Specification<Giocatore> quotazioneMin(
            Integer valore
    ){


        return (root, query, cb) -> {


            if(valore==null)

                return null;



            return cb.greaterThanOrEqualTo(

                    root.get("quotazioneClassic"),

                    valore

            );


        };


    }









    private static Specification<Giocatore> quotazioneMax(
            Integer valore
    ){


        return (root, query, cb) -> {


            if(valore==null)

                return null;



            return cb.lessThanOrEqualTo(

                    root.get("quotazioneClassic"),

                    valore

            );


        };


    }









    private static Specification<Giocatore> fvmMinimo(
            Integer valore
    ){


        return (root, query, cb) -> {


            if(valore==null)

                return null;



            return cb.greaterThanOrEqualTo(

                    root.get("fvmClassic"),

                    valore

            );


        };


    }









    private static Specification<Giocatore> fvmMassimo(
            Integer valore
    ){


        return (root, query, cb) -> {


            if(valore==null)

                return null;



            return cb.lessThanOrEqualTo(

                    root.get("fvmClassic"),

                    valore

            );


        };


    }









    private static Specification<Giocatore> fantaMediaMin(
            Double valore
    ){


        return (root, query, cb) -> {


            if(valore==null)

                return null;



            return cb.greaterThanOrEqualTo(

                    root.get("fantaMedia"),

                    valore

            );


        };


    }









    private static Specification<Giocatore> mediaVotoMin(
            Double valore
    ){


        return (root, query, cb) -> {


            if(valore==null)

                return null;



            return cb.greaterThanOrEqualTo(

                    root.get("mediaVoto"),

                    valore

            );


        };


    }



}