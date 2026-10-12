package Ex.modele;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

@Entity
public class Composante {
    @Id
    @Column(length = 8)
    private String acronyme;

    @Column(length = 50)
    private String nom;

    @Column(length = 30)
    private String responsable;

    @ManyToMany
    @JoinTable(name = "exploite", joinColumns = @JoinColumn(name = "team"), // references acronyme in Composante
            inverseJoinColumns = @JoinColumn(name = "building")) // references codeB in Batiment
    private List<Batiment> batiments = new ArrayList<>();

    public Composante() {
    }

    public Composante(String acronyme, String nom, String responsable) {
        this.acronyme = acronyme;
        this.nom = nom;
        this.responsable = responsable;
    }

    public String getAcronyme() {
        return acronyme;
    }

    public void setAcronyme(String acronyme) {
        this.acronyme = acronyme;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public List<Batiment> getBatiments() {
        return batiments;
    }

    public void setBatiments(List<Batiment> batiments) {
        this.batiments = batiments;
    }

    @Override
    public String toString() {
        return "Composante [acronyme=" + acronyme + ", nom=" + nom + ", responsable=" + responsable + "]";
    }

}
