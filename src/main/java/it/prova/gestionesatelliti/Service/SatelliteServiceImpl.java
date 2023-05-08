package it.prova.gestionesatelliti.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.prova.gestionesatelliti.model.Satellite;
import it.prova.gestionesatelliti.model.StatoSatellite;
import it.prova.gestionesatelliti.repository.SatelliteRepository;

@Service
public class SatelliteServiceImpl implements SatelliteService {

	@Autowired
	private SatelliteRepository repository;

	public SatelliteServiceImpl() {
	}

	@Override
	@Transactional(readOnly = true)
	public List<Satellite> listAllElements() {
		return (List<Satellite>) repository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Satellite caricaSingoloElemento(Long id) {
		return repository.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void aggiorna(Satellite satelliteInstance) {

		repository.save(satelliteInstance);

	}

	@Override
	@Transactional
	public void inserisciNuovo(Satellite satelliteInstance) {

		repository.save(satelliteInstance);

	}

	@Override
	@Transactional
	public void rimuovi(Long idSatellite) {

		repository.deleteById(idSatellite);

	}

	@Override
	@Transactional(readOnly = true)
	public List<Satellite> findByExample(Satellite example) {
		Specification<Satellite> specificationCriteria = (root, query, cb) -> {

			List<Predicate> predicates = new ArrayList<Predicate>();

			if (StringUtils.isNotEmpty(example.getDenominazione()))
				predicates.add(cb.like(cb.upper(root.get("denominazione")),
						"%" + example.getDenominazione().toUpperCase() + "%"));

			if (StringUtils.isNotEmpty(example.getCodice()))
				predicates.add(cb.like(cb.upper(root.get("codice")), "%" + example.getCodice().toUpperCase() + "%"));

			if (example.getStato() != null)
				predicates.add(cb.equal(root.get("stato"), example.getStato()));

			if (example.getDataLancio() != null)
				predicates.add(cb.greaterThanOrEqualTo(root.get("dataLancio"), example.getDataLancio()));

			if (example.getDataRientro() != null)
				predicates.add(cb.greaterThanOrEqualTo(root.get("dataRientro"), example.getDataRientro()));

			return cb.and(predicates.toArray(new Predicate[predicates.size()]));
		};

		return repository.findAll(specificationCriteria);

	}

	@Override
	@Transactional
	public void lanciaSatellite(Long id) {

		Satellite satellite = repository.findById(id).orElse(null);
		satellite.setDataLancio(LocalDate.now());
		satellite.setStato(StatoSatellite.IN_MOVIMENTO);
		repository.save(satellite);

	}

	@Override
	@Transactional
	public void ritornaSatellite(Long id) {

		Satellite satellite = repository.findById(id).orElse(null);
		satellite.setDataRientro(LocalDate.now());
		satellite.setStato(StatoSatellite.DISATTIVATO);
		repository.save(satellite);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Satellite> lanciatiDa2AnniOPiuAttivi() {

		List<StatoSatellite> attivi = Arrays.asList(StatoSatellite.FISSO, StatoSatellite.IN_MOVIMENTO);

		return repository.findAllByStatoInAndDataLancioLessThan(attivi, LocalDate.now().minusYears(2));
	}

	@Override
	public List<Satellite> disattivatiInOrbita() {

		return repository.findAllByStatoAndDataRientroIsNull(StatoSatellite.DISATTIVATO);

	}

	@Override
	public List<Satellite> lanciatiDa10AnniFissi() {

		return repository.findAllByStatoAndDataLancioLessThan(StatoSatellite.FISSO, LocalDate.now().minusYears(10));
	}

	@Override
	public List<Satellite> lanciatiAttivi() {

		List<StatoSatellite> attivi = Arrays.asList(StatoSatellite.FISSO, StatoSatellite.IN_MOVIMENTO);

		return repository.findAllByStatoInAndDataLancioIsNotNull(attivi);

	}

	@Override
	@Transactional
	public void proceduraEmergenza() {
		
		List<StatoSatellite> attivi = Arrays.asList(StatoSatellite.FISSO, StatoSatellite.IN_MOVIMENTO);

		repository.updateStatoAndDataRientroByStatiAndDataLancioIsNotNull(StatoSatellite.DISATTIVATO,LocalDate.now(),attivi);

	}

	/**/

}
