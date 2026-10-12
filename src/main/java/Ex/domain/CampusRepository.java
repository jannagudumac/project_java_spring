package Ex.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import Ex.modele.Campus;


@Repository
public interface CampusRepository extends JpaRepository<Ex.modele.Campus, String> {
	
	List<Campus> findByVille(String ville);
	
	// -------------- TD2 ------------------------
	@Query("SELECT c.nomC, bts.codeB FROM Campus c "
			+ "JOIN c.batiments bts ")	
	List<String[]> campusEtCodeB();

	// count batiments by campus: format result[0] (campus name) - result[1] (quantity)
	@Query("SELECT c.nomC, COUNT(b) FROM Campus c LEFT JOIN c.batiments b GROUP BY c.nomC")
	List<Object[]> countBatimentsByCampus();
	
}
