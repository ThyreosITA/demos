package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "giocatori")
public class Giocatore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String squadra;
    private String ruolo;        // Ruolo Classic (P, D, C, A)
    private String ruoloMantra;  // Ruolo Mantra (Por, Dc, E, W, T, Pc, ecc.)

    private Integer quotazioneClassic;
    private Integer quotazioneMantra;
    private Integer fvmClassic;
    private Integer fvmMantra;

    // Statistiche di Rendimento
    private Double mediaVoto = 0.0;
    private Double fantaMedia = 0.0;
    private Integer presenze = 0;
    private Integer golFatti = 0;
    private Integer golSubiti = 0;
    private Integer assist = 0;
    private Integer ammonizioni = 0;
    private Integer espulsioni = 0;

    public Giocatore() {}

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getSquadra() { return squadra; }
    public void setSquadra(String squadra) { this.squadra = squadra; }

    public String getRuolo() { return ruolo; }
    public void setRuolo(String ruolo) { this.ruolo = ruolo; }

    public String getRuoloMantra() { return ruoloMantra; }
    public void setRuoloMantra(String ruoloMantra) { this.ruoloMantra = ruoloMantra; }

    public Integer getQuotazioneClassic() { return quotazioneClassic; }
    public void setQuotazioneClassic(Integer quotazioneClassic) { this.quotazioneClassic = quotazioneClassic; }

    public Integer getQuotazioneMantra() { return quotazioneMantra; }
    public void setQuotazioneMantra(Integer quotazioneMantra) { this.quotazioneMantra = quotazioneMantra; }

    public Integer getFvmClassic() { return fvmClassic; }
    public void setFvmClassic(Integer fvmClassic) { this.fvmClassic = fvmClassic; }

    public Integer getFvmMantra() { return fvmMantra; }
    public void setFvmMantra(Integer fvmMantra) { this.fvmMantra = fvmMantra; }

    public Double getMediaVoto() { return mediaVoto; }
    public void setMediaVoto(Double mediaVoto) { this.mediaVoto = mediaVoto; }

    public Double getFantaMedia() { return fantaMedia; }
    public void setFantaMedia(Double fantaMedia) { this.fantaMedia = fantaMedia; }

    public Integer getPresenze() { return presenze; }
    public void setPresenze(Integer presenze) { this.presenze = presenze; }

    public Integer getGolFatti() { return golFatti; }
    public void setGolFatti(Integer golFatti) { this.golFatti = golFatti; }

    public Integer getGolSubiti() { return golSubiti; }
    public void setGolSubiti(Integer golSubiti) { this.golSubiti = golSubiti; }

    public Integer getAssist() { return assist; }
    public void setAssist(Integer assist) { this.assist = assist; }

    public Integer getAmmonizioni() { return ammonizioni; }
    public void setAmmonizioni(Integer ammonizioni) { this.ammonizioni = ammonizioni; }

    public Integer getEspulsioni() { return espulsioni; }
    public void setEspulsioni(Integer espulsioni) { this.espulsioni = espulsioni; }
}