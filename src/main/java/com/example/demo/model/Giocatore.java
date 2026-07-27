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
    private String ruolo;
    private String ruoloMantra;

    private Integer quotazioneClassic;
    private Integer fvmClassic;
    private Integer quotazioneMantra;
    private Integer fvmMantra;

    private Integer presenze;
    private Double mediaVoto;
    private Double fantaMedia;
    private Integer golFatti;
    private Integer golSubiti;
    private Integer assist;
    private Integer ammonizioni;
    private Integer espulsioni;

    public Giocatore() {}

    // Calcolo dell'Indice Efficienza Giocatore (IEG)
    public double getIndiceEfficienzaGiocatore() {
        double gol = golFatti != null ? golFatti : 0.0;
        double ast = assist != null ? assist : 0.0;
        double mv = mediaVoto != null ? mediaVoto : 0.0;
        double amm = ammonizioni != null ? ammonizioni : 0.0;
        double esp = espulsioni != null ? espulsioni : 0.0;

        return (gol * 3) + (ast * 2) + (mv * 1.5) - (amm * 1) - (esp * 2);
    }

    // Calcolo dell'Indice Modificatore Difesa (IMD) - Media Voto peso 4.5
    public double getIndiceModificatoreDifesa() {
        double gol = golFatti != null ? golFatti : 0.0;
        double ast = assist != null ? assist : 0.0;
        double mv = mediaVoto != null ? mediaVoto : 0.0;
        double amm = ammonizioni != null ? ammonizioni : 0.0;
        double esp = espulsioni != null ? espulsioni : 0.0;

        return (mv * 9.5) + (gol * 0.5) + (ast * 0.25) - (amm * 0.25) - (esp * 0.5);
    }

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

    public Integer getFvmClassic() { return fvmClassic; }
    public void setFvmClassic(Integer fvmClassic) { this.fvmClassic = fvmClassic; }

    public Integer getQuotazioneMantra() { return quotazioneMantra; }
    public void setQuotazioneMantra(Integer quotazioneMantra) { this.quotazioneMantra = quotazioneMantra; }

    public Integer getFvmMantra() { return fvmMantra; }
    public void setFvmMantra(Integer fvmMantra) { this.fvmMantra = fvmMantra; }

    public Integer getPresenze() { return presenze; }
    public void setPresenze(Integer presenze) { this.presenze = presenze; }

    public Double getMediaVoto() { return mediaVoto; }
    public void setMediaVoto(Double mediaVoto) { this.mediaVoto = mediaVoto; }

    public Double getFantaMedia() { return fantaMedia; }
    public void setFantaMedia(Double fantaMedia) { this.fantaMedia = fantaMedia; }

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