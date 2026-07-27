package com.example.demo.controller;

import com.example.demo.model.Giocatore;
import com.example.demo.model.GiocatoreRosa;
import com.example.demo.repository.GiocatoreRepository;
import com.example.demo.repository.GiocatoreSpecification;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
public class GiocatoreController {

    @Autowired
    private GiocatoreRepository giocatoreRepository;

    // ================================
    // API REST PER IL TEAM BUILDER (JSON)
    // ================================

    @GetMapping("/api/giocatori")
    @ResponseBody
    public List<Giocatore> getApiGiocatori() {
        return giocatoreRepository.findAll();
    }

    // ================================
    // ROSA SESSIONE
    // ================================

    @SuppressWarnings("unchecked")
    private List<GiocatoreRosa> getRosa(
            HttpSession session,
            String nome){

        List<GiocatoreRosa> rosa =
                (List<GiocatoreRosa>)
                session.getAttribute(nome);

        if(rosa == null){
            rosa = new ArrayList<>();
            session.setAttribute(nome, rosa);
        }

        return rosa;
    }

    // ================================
    // LISTONE
    // ================================

    @GetMapping("/")
    public String listone(
            @RequestParam(required=false) String nome,
            @RequestParam(required=false) String ruolo,
            @RequestParam(required=false) String ruoloMantra,
            @RequestParam(required=false) String squadra,
            @RequestParam(required=false) Integer quotMin,
            @RequestParam(required=false) Integer quotMax,
            @RequestParam(required=false) Integer fvmMin,
            @RequestParam(required=false) Integer fvmMax,
            @RequestParam(required=false) Double fmMin,
            @RequestParam(required=false) Double mvMin,
            @RequestParam(defaultValue="fantaMedia") String sortBy,
            @RequestParam(defaultValue="desc") String sortDir,
            Model model
    ){

        List<String> validSort =
                Arrays.asList(
                        "nome",
                        "squadra",
                        "quotazioneClassic",
                        "fvmClassic",
                        "quotazioneMantra",
                        "fvmMantra",
                        "fantaMedia",
                        "mediaVoto",
                        "golFatti",
                        "assist",
                        "ieg",
                        "imd"
                );

        if(!validSort.contains(sortBy))
            sortBy="fantaMedia";

        Sort sort = Sort.unsorted();
        if (!sortBy.equals("ieg") && !sortBy.equals("imd")) {
            if(sortDir.equalsIgnoreCase("asc"))
                sort=Sort.by(sortBy).ascending();
            else
                sort=Sort.by(sortBy).descending();
        }

        List<Giocatore> giocatori =
                giocatoreRepository.findAll(
                        GiocatoreSpecification.filtra(
                                nome,
                                ruolo,
                                ruoloMantra,
                                squadra,
                                quotMin,
                                quotMax,
                                fvmMin,
                                fvmMax,
                                fmMin,
                                mvMin
                        ),
                        sort
                );

        if (sortBy.equals("ieg")) {
            giocatori.sort((g1, g2) -> {
                int cmp = Double.compare(g1.getIndiceEfficienzaGiocatore(), g2.getIndiceEfficienzaGiocatore());
                return sortDir.equalsIgnoreCase("asc") ? cmp : -cmp;
            });
        } else if (sortBy.equals("imd")) {
            giocatori.sort((g1, g2) -> {
                int cmp = Double.compare(g1.getIndiceModificatoreDifesa(), g2.getIndiceModificatoreDifesa());
                return sortDir.equalsIgnoreCase("asc") ? cmp : -cmp;
            });
        }

        model.addAttribute("giocatori", giocatori);
        model.addAttribute("nome", nome);
        model.addAttribute("ruolo", ruolo);
        model.addAttribute("ruoloMantra", ruoloMantra);
        model.addAttribute("squadra", squadra);
        model.addAttribute("quotMin", quotMin);
        model.addAttribute("quotMax", quotMax);
        model.addAttribute("fvmMin", fvmMin);
        model.addAttribute("fvmMax", fvmMax);
        model.addAttribute("fmMin", fmMin);
        model.addAttribute("mvMin", mvMin);

        model.addAttribute(
                "reverseSortDir",
                sortDir.equals("asc") ? "desc" : "asc"
        );

        model.addAttribute("sortBy", sortBy);
        model.addAttribute("sortDir", sortDir);

        return "listone";
    }

    // ================================
    // AGGIUNGI CLASSIC
    // ================================

    @GetMapping("/aggiungi-classic")
    public String aggiungiClassic(
            @RequestParam Long id,
            HttpSession session
    ){

        List<GiocatoreRosa> rosa =
                getRosa(
                        session,
                        "rosaClassic"
                );

        giocatoreRepository.findById(id)
        .ifPresent(g -> {

            boolean presente =
                    rosa.stream()
                    .anyMatch(
                    x -> x.getGiocatore()
                    .getId()
                    .equals(id)
                    );

            if(!presente){
                rosa.add(
                        new GiocatoreRosa(
                                g,
                                g.getQuotazioneClassic()!=null
                                ? g.getQuotazioneClassic()
                                : 1,
                                "CLASSIC"
                        )
                );
            }
        });

        return "redirect:/";
    }

    // ================================
    // AGGIUNGI MANTRA
    // ================================

    @GetMapping("/aggiungi-mantra")
    public String aggiungiMantra(
            @RequestParam Long id,
            HttpSession session
    ){

        List<GiocatoreRosa> rosa =
                getRosa(
                        session,
                        "rosaMantra"
                );

        giocatoreRepository.findById(id)
        .ifPresent(g -> {

            boolean presente =
                    rosa.stream()
                    .anyMatch(
                    x -> x.getGiocatore()
                    .getId()
                    .equals(id)
                    );

            if(!presente){
                rosa.add(
                        new GiocatoreRosa(
                                g,
                                g.getQuotazioneMantra()!=null
                                ? g.getQuotazioneMantra()
                                : 1,
                                "MANTRA"
                        )
                );
            }
        });

        return "redirect:/";
    }

    // ================================
    // RIMOZIONE
    // ================================

    @GetMapping("/rimuovi-classic")
    public String rimuoviClassic(
            @RequestParam Long id,
            HttpSession session
    ){

        getRosa(
                session,
                "rosaClassic"
        )
        .removeIf(
        x -> x.getGiocatore()
        .getId()
        .equals(id)
        );

        return "redirect:/team-builder";
    }

    @GetMapping("/rimuovi-mantra")
    public String rimuoviMantra(
            @RequestParam Long id,
            HttpSession session
    ){

        getRosa(
                session,
                "rosaMantra"
        )
        .removeIf(
        x -> x.getGiocatore()
        .getId()
        .equals(id)
        );

        return "redirect:/team-builder";
    }

    // ================================
    // TEAM BUILDER
    // ================================

    @GetMapping("/team-builder")
    public String teamBuilder(
            @RequestParam(defaultValue="4-3-3") String module,
            HttpSession session,
            Model model
    ){

        List<GiocatoreRosa> classic =
                getRosa(
                        session,
                        "rosaClassic"
                );

        List<GiocatoreRosa> mantra =
                getRosa(
                        session,
                        "rosaMantra"
                );

        model.addAttribute("rosaClusterClassic", classic);
        model.addAttribute("rosaClassic", classic);
        model.addAttribute("rosaMantra", mantra);
        model.addAttribute("selectedModule", module);

        return "team-builder";
    }
}