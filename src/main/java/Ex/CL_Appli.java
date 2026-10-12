package Ex;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import jakarta.transaction.Transactional;
import Ex.domain.* ;
import Ex.modele.* ;
import Ex.service.ICampusService;

// COMMAND LINE RUNNER is a startup hook that runs custom code right after the app launch
// NOT used in big apps, but useful for demos

// SpringBootApplication =  Spring Boot startup config
// it tells Spring Boot to : scan the package for beans,
// enable auto-config, cretae the app context, start the app

// Transactional - in Spring a group of DB operations treated as one unit:
// MEANS : all or nothing, either all transactions are successful -> COMMIT
// error -> ROLLBACK

@Transactional 
@SpringBootApplication 
public class CL_Appli implements CommandLineRunner {

		//------------------ TD1 ---------------------
		 // building repo (instance created as Bean during startup)
		@Autowired
		 private BatimentRepository br;
		 // campus repo (instance created as Bean during startup)
		 @Autowired
		 private CampusRepository cr;
		 @Autowired
		 private SalleRepository sr;
		@Autowired
		 private ComposanteRepository compr;

		// ------------------ TD1 --------------------
		@Autowired
		private ICampusService cs;		 

		// --------------------------
		// the app starts here 
		// we get the APPLICATION CONTEXT -  container that manages all Spring beans
		// Spring reads the app setup -> creates objects (repos, services, controllers)
		// -> wires them together automatically (@Autowired) -> keeps them ready for use
		public static void main(String[] args) {
			SpringApplication.run(CL_Appli.class, args); // get the application context and run
		} 
		
	
	// run method from command line runner is invoked
	// it automatically executes the logic when the app is launched :
	@Override
	public void run(String... args) throws Exception {
		runTD1();
		runTD2();
	}

	private void runTD1() {
		// ------------------------ TD1 -------------------------------
		tsLesBatiments(); //shows all buildings and their infos
		campusParVille("Montpellier"); // show a list of all MTP campuses
		List<String> codes = new ArrayList<String>(); // create a list of codes
		codes.add("triolet_b36"); // add building to codes
		codes.add("triolet_b16"); // add building to codes
		certainsBatiments(codes); // call method that prints info about buildings on the codes list

		System.out.println("Salles TD du batiment 36 Triolet");
		for (Salle salle : sr.findSallesTdBat36()) {
			System.out.println(salle);
		}

		System.out.println("Salles du batiment 5 Triolet");
		for (Salle salle : sr.findByBatiment("triolet_b05")) {
			System.out.println(salle);
		}

		System.out.println("Salles du campus Triolet");
		for (Salle salle : sr.findByCampus("Triolet")) {
			System.out.println(salle);
		}

		System.out.println("Nombre de salles par batiment");
		for (Object[] result : sr.countByBatiment()) {
			System.out.println(result[0] + ": " + result[1]);
		}

		System.out.println("Nombre de salles par type");
		for (Object[] result : sr.countByType()) {
			System.out.println(result[0] + ": " + result[1]);
		}

		System.out.println("Liste des composantes");
		for (Composante c : compr.findAll()) {
    		System.out.println(c);
		}

		// createNimesCampusEtc(); -- ran only once to avoid duplication
	}

	private void runTD2() {
		// ------------------------- TD2 --------------------------------
		Campus	cpus = new Campus("STAPS1","Montpellier");
		Batiment bat = new Batiment();
		bat.setAnneeC(1978);
		bat.setCodeB("st_Bat_1");
		bat.setCampus(cpus);
		cs.createCampus(cpus); 
	}
	   
	// -------------------- TD1 -----------------------------------
	// SHOW ALL BUILDINGS + INFO
	public void tsLesBatiments()
	{  Iterable<Batiment> e = br.findAll();
	  System.out.println("La liste des batiments");
      if (e != null) { 
   	   for (Batiment b : e)
          { System.out.println(b.getCodeB()+"\t "+b.getAnneeC()+"\t "+b.getCampus().getNomC());}
      } else System.out.println("nada");
	} 
	// SHOW ALL CAMPUSES BY CITY
	public void campusParVille(String ville)
	{  Iterable<Campus> e = cr.findByVille(ville);
	  System.out.println("La liste des campus de "+ville);
      if (e != null) { 
   	   for (Campus c : e)
          { System.out.println(c.toString());}
      } else System.out.println("nada");
	} 
	// SHOW INFOS ABOUT BUILDINGS MENTIONED IN THE LIST codes
	public void certainsBatiments(List<String> codes)
	{  Iterable<Batiment> e = br.findByIds(codes);
	  System.out.println("La liste de certains batiments");
      if (e != null) { 
   	   for (Batiment b : e)
          { System.out.println(b.getCodeB()+"\t "+b.getAnneeC()+"\t "+b.getCampus().getNomC());}
      } else System.out.println("nada");
	}

	// use default methods such as (saveAndFlush,getReferenceById etc) to
	// create a new campus and FDE Nimes in city Nimes  and a new batiment ’bt1 nimes’ 2024 in this campus
	// add 2 new salles for this batiment

	private void createNimesCampusEtc() {
		Campus campus = new Campus("FDE Nimes", "Nimes");
		cr.saveAndFlush(campus);

		Batiment batiment = new Batiment("bt1_nimes", 2024, campus);
		br.saveAndFlush(batiment);

		sr.saveAndFlush(new Salle("N1", 30, TypeSalle.td, "oui", "1", batiment));
		sr.saveAndFlush(new Salle("N2", 20, TypeSalle.tp, "oui", "1", batiment));
	}

	//-------------------------- TD2 ---------------------------------
	

}
