package Ex.modele;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

@Entity
public class Campus {
	
	@Id
	@Column(length = 16)
	private String nomC;
	
	@Column(length = 20)
	private String ville;
	
	// campus -> many buildings
	// FetchType.LAZY = don't load all buildings immeadiately, only when needed (when campus.getBatiments() is called)
	// mappedBy = "campus" ---> the owher of the relationship is NOT Campus, but Batiment (the field "campus" inside it)
	// mappedBy = "campus" ---> “The campus field in Batiment is the foreign key side, so do not create a separate join table or extra mapping here.”
	// the Campus table itself does not hold the foreign key directly. The relationship is represented by the batiment.campus reference.
	@OneToMany(fetch = FetchType.LAZY, mappedBy="campus", cascade = CascadeType.REMOVE) // if you delete a Campus you also delete related Batiments
	private List<Batiment> batiments = new ArrayList<Batiment>();
	
	public Campus() {
	}
	
	public Campus(String nomC, String ville) {
		super();
		this.nomC = nomC;
		this.ville = ville;
		this.batiments = new ArrayList<Batiment>();
	}
	
	public String getNomC() {
		return nomC;
	}
	public void setNomC(String nomC) {
		this.nomC = nomC;
	}
	public String getVille() {
		return ville;
	}
	public void setVille(String ville) {
		this.ville = ville;
	}

	public List<Batiment> getBatiments() {
		return batiments;
	}

	public void setBatiments(List<Batiment> batiments) {
		this.batiments = batiments;
	}

	@Override
	public String toString() {
		return "Campus [nomC=" + nomC + ", ville=" + ville + "]";
	}
	
}
