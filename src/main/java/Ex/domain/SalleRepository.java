package Ex.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import Ex.modele.Salle;
import Ex.modele.TypeSalle;

public interface SalleRepository extends JpaRepository<Salle, String> {
    // findAll inherited

    // all td salles in building 36 (for enum in JPQL the full class path needed)
    @Query("SELECT s FROM Salle s WHERE s.batiment.codeB='triolet_b36' AND s.typeS=Ex.modele.TypeSalle.td")
    List<Salle> findSallesTdBat36();
    // Derived query:
    // List<Salle> findByBatiment_CodeBAndTypeS(String code, TypeSalle type);

    // ---------------------------------------
    // all salles of the building by its code
    // derived: List<Salle> findByBatiment_CodeB(String codeB);
    // translates to:
    // SELECT *
    // FROM salle
    // WHERE batiment = 'X';
    @Query("SELECT s FROM Salle s WHERE s.batiment.codeB = :codeB")
    List<Salle> findByBatiment(@Param("codeB") String codeB);

    // -----------------------------------------
    // all salles of the campus by name of campus
    // derived: List<Salle> findByBatiment_Campus_nomC(String nomC);
    // translates to:
    // SELECT s.*
    // FROM salle s
    // JOIN batiment b ON s.batiment = b.codeB
    // JOIN campus c ON b.campus = c.nomC
    // WHERE c.nomC = 'X';
    @Query("SELECT s from Salle s WHERE s.batiment.campus.nomC = :nomC")
    List<Salle> findByCampus(@Param("nomC") String nomC);

    // find salles quantity for each building
    @Query("SELECT s.batiment.codeB, COUNT(s) FROM Salle s GROUP BY s.batiment.codeB")
    List<Object[]> countByBatiment(); // object is needed bc each line will have two different Object types: codeB →
                                      // String, COUNT(s) → Long, so Object takes into account both

    // find salles quantity for each salle type
    @Query("SELECT s.typeS, COUNT(s) FROM Salle s GROUP BY s.typeS")
    List<Object[]> countByType();

    // ---------------- TD 2 ----------------------------
    // find salles quantity for given campus
    @Query("SELECT COUNT(s) FROM Salle s WHERE s.batiment.campus.nomC = :nomC")
    Long countSalles(@Param("nomC") String nomC);

    // find how many salles TD <40 places and accessible are in MTP
    // @Query("""
    // SELECT COUNT(s)
    // FROM Salle s
    // WHERE s.typeS = Ex.modele.TypeSalle.td
    // AND s.capacite < 40
    // AND s.acces = 'oui'
    // AND s.batiment.campus.ville = 'Montpellier'
    // """)
    // Long countAccessibleTdSallesUnder40InMtp();
    // ^^^^^^^
    // generic verion:
    @Query("""
            SELECT COUNT(s)
            FROM Salle s
            WHERE s.typeS = :type
            AND s.capacite < :capacite
            AND s.acces = :acces
            AND s.batiment.campus.ville = :ville
            """)
    Long countSallesWithParams(
            @Param("type") TypeSalle type,
            @Param("capacite") int capacite,
            @Param("acces") String acces,
            @Param("ville") String ville);

    // list amphitheaters

    @Query("""
            SELECT s FROM Salle s
            WHERE s.typeS = :type
            AND s.capacite >= :capacite
            AND s.batiment.campus.nomC = :nomC
            """)
    List<Salle> findSallesByTypeCapacityAndCampus(
            @Param("type") TypeSalle type,
            @Param("capacite") int capacite,
            @Param("nomC") String nomC);

}
