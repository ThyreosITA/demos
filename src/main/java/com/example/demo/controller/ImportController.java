package com.example.demo.controller;


import com.example.demo.service.QuotazioniService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.ui.Model;



@Controller
public class ImportController {



    @Autowired
    private QuotazioniService quotazioniService;







    // =====================================
    // IMPORT MANUALE QUOTAZIONI
    // =====================================


    @GetMapping("/importa-quotazioni")
    public String importaQuotazioni(
            Model model
    ){



        try {



            quotazioniService.importaQuotazioni(null);



            model.addAttribute(
                    "messaggio",
                    "Quotazioni importate correttamente"
            );



        } catch(Exception e){



            model.addAttribute(
                    "errore",
                    "Errore importazione quotazioni: "
                    + e.getMessage()
            );



            e.printStackTrace();


        }





        return "redirect:/";

    }





}