package Ex.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import Ex.modele.Campus;



public interface ICampusService {
	
	Campus createCampus(Campus campus) ;

	void deleteCampus(String id);

	Optional<Campus> campusParId(String nomC);
	
	void campusParVille(String ville) ;

	void campusEtBatiments();
	
	public List<Campus> findAll() ;

	// ------------- TD2 ----------------
	void showNumberOfBatimentsAndSalles();

}