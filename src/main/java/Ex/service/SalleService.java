package Ex.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Ex.domain.SalleRepository;
import Ex.modele.TypeSalle;

//---------------------TD2----------------------
@Service
public class SalleService implements ISalleService {

    private final SalleRepository sr;

    @Autowired
    public SalleService(SalleRepository sr) {
        this.sr = sr;
    }

    @Override
    public Long countAccessibleTdSallesUnder40InMtp() {
        return sr.countSallesWithParams(
                TypeSalle.td,
                40,
                "oui",
                "Montpellier");
    }
}
