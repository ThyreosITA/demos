package com.example.demo.service;

import com.example.demo.model.Giocatore;
import com.example.demo.repository.GiocatoreRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class QuotazioniService {

    @Autowired
    private GiocatoreRepository giocatoreRepository;

    public void importaQuotazioni(String file) throws Exception {

        System.out.println(">>> INIZIO IMPORTAZIONE COMBINATA (QUOTAZIONI + STATISTICHE)...");

        // 1. SVUOTA IL DATABASE
        giocatoreRepository.deleteAll();

        // PASSO 1: Importazione Prezzi e Ruoli da Quotazioni CSV
        String pathQuotazioni = "csv/Quotazioni_Fantacalcio_Stagione_2025_26.csv";
        importaPrezziERuoli(pathQuotazioni);

        // PASSO 2: Arricchimento con Statistiche da Giocatori CSV
        String pathStatistiche = "csv/Giocatori.csv";
        importaStatisticheRendimento(pathStatistiche);

        System.out.println(">>> IMPORTAZIONE COMPLETATA CON SUCCESSO!");
    }

    private void importaPrezziERuoli(String path) throws Exception {
        ClassPathResource resource = new ClassPathResource(path);
        if (!resource.exists()) {
            throw new Exception("File non trovato: " + path);
        }

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {

            String line;
            boolean primaRiga = true;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("\uFEFF")) line = line.substring(1);
                if (line.trim().isEmpty()) continue;

                String[] rawCols = line.split(";", -1);
                if (primaRiga) { primaRiga = false; continue; }
                if (rawCols.length < 13) continue;

                int len = rawCols.length;
                String ruoloClassic = clean(get(rawCols, 1));

                // Unione ruoli Mantra multipli
                int endIndexMantra = len - 11;
                StringBuilder sbMantra = new StringBuilder();
                for (int i = 2; i <= endIndexMantra; i++) {
                    String rMantra = clean(get(rawCols, i));
                    if (!rMantra.isEmpty()) {
                        if (sbMantra.length() > 0) sbMantra.append(",");
                        sbMantra.append(rMantra);
                    }
                }

                String nome        = clean(get(rawCols, len - 10));
                String squadra     = clean(get(rawCols, len - 9));
                Integer qtClassic  = parseInt(get(rawCols, len - 8));
                Integer qtMantra   = parseInt(get(rawCols, len - 5));
                Integer fvmClassic = parseInt(get(rawCols, len - 2));
                Integer fvmMantra  = parseInt(get(rawCols, len - 1));

                if (nome.isEmpty()) continue;

                Giocatore g = new Giocatore();
                g.setNome(nome);
                g.setSquadra(squadra);
                g.setRuolo(ruoloClassic);
                g.setRuoloMantra(sbMantra.toString());
                g.setQuotazioneClassic(qtClassic);
                g.setQuotazioneMantra(qtMantra);
                g.setFvmClassic(fvmClassic);
                g.setFvmMantra(fvmMantra);

                giocatoreRepository.save(g);
            }
        }
    }

    private void importaStatisticheRendimento(String path) throws Exception {
        ClassPathResource resource = new ClassPathResource(path);
        if (!resource.exists()) {
            System.out.println("Attenzione: File " + path + " non trovato. Le statistiche rimarranno a 0.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {

            String line;
            boolean primaRiga = true;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("\uFEFF")) line = line.substring(1);
                if (line.trim().isEmpty()) continue;

                String[] rawCols = line.split(";", -1);
                if (primaRiga) { primaRiga = false; continue; }
                if (rawCols.length < 15) continue;

                int len = rawCols.length;

                // Ancoraggio da destra per tollerare formattazioni Mantra
                String nome      = clean(get(rawCols, len - 15));
                String squadra   = clean(get(rawCols, len - 14));
                Integer presenze = parseInt(get(rawCols, len - 13));
                Double mv        = parseDouble(get(rawCols, len - 12));
                Double fm        = parseDouble(get(rawCols, len - 11));
                Integer gf       = parseInt(get(rawCols, len - 10));
                Integer gs       = parseInt(get(rawCols, len - 9));
                Integer ass      = parseInt(get(rawCols, len - 4));
                Integer amm      = parseInt(get(rawCols, len - 3));
                Integer esp      = parseInt(get(rawCols, len - 2));

                List<Giocatore> trovati = giocatoreRepository
                        .findAllByNomeIgnoreCaseAndSquadraIgnoreCase(nome, squadra);

                if (!trovati.isEmpty()) {
                    Giocatore g = trovati.get(0);
                    g.setPresenze(presenze);
                    g.setMediaVoto(mv);
                    g.setFantaMedia(fm);
                    g.setGolFatti(gf);
                    g.setGolSubiti(gs);
                    g.setAssist(ass);
                    g.setAmmonizioni(amm);
                    g.setEspulsioni(esp);

                    giocatoreRepository.save(g);
                }
            }
        }
    }

    private String get(String[] array, int index) {
        if (index < 0 || index >= array.length) return "";
        return array[index];
    }

    private String clean(String s) {
        if (s == null) return "";
        return s.replace("\"", "").replace("\uFEFF", "").trim();
    }

    private Integer parseInt(String s) {
        try {
            return Integer.parseInt(clean(s));
        } catch (Exception e) {
            return 0;
        }
    }

    private Double parseDouble(String s) {
        try {
            return Double.parseDouble(clean(s).replace(",", "."));
        } catch (Exception e) {
            return 0.0;
        }
    }
}