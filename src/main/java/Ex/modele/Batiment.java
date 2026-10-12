package Ex.modele;


import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

@Entity
public class Batiment {
	
	@Id
	@Column(length = 16)
	private String codeB;
	
	private int anneeC;
	
	// relationship is controlled from here, from the Batiment side
	// while Campus has a list of Batiments mappedBy "campus"
	@ManyToOne
	@JoinColumn(name="campus") //foreign key column in table batiment
	private Campus campus;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy="batiment", cascade = CascadeType.REMOVE)
	private List<Salle> salles = new ArrayList<Salle>();
	
	//creates a relationship table
	@ManyToMany(mappedBy = "batiments")
    private List<Composante> composantes = new ArrayList<>();
            

	public Batiment() {} 
	
	public Batiment(String codeB, int anneeC, Campus campus) {
		super();
		this.codeB = codeB;
		this.anneeC = anneeC;
		this.campus = campus;
	}

	public String getCodeB() {
		return codeB;
	}

	public void setCodeB(String codeB) {
		this.codeB = codeB;
	}

	public int getAnneeC() {
		return anneeC;
	}

	public void setAnneeC(int anneeC) {
		this.anneeC = anneeC;
	}

	public Campus getCampus() {
		return campus;
	}

	public void setCampus(Campus campus) {
		this.campus = campus;
	}

	public List<Salle> getSalles() {
		return salles;
	}

	public void setSalles(List<Salle> salles) {
		this.salles = salles;
	}

	public List<Composante> getComposantes() {
		return composantes;
	}

	public void setComposantes(List<Composante> composantes) {
		this.composantes = composantes;
	}
	

	@Override
	public String toString() {
		return "Batiment [codeB=" + codeB  + ", anneeC=" + anneeC + "]";
	}
	
}
