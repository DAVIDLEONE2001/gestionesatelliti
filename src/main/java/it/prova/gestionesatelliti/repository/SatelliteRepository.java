package it.prova.gestionesatelliti.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import it.prova.gestionesatelliti.model.Satellite;
import it.prova.gestionesatelliti.model.StatoSatellite;

public interface SatelliteRepository extends CrudRepository<Satellite, Long>, JpaSpecificationExecutor<Satellite> {

	List<Satellite> findAllByStatoInAndDataLancioLessThan(Iterable<StatoSatellite> stati, LocalDate dataLancio);

	List<Satellite> findAllByStatoAndDataRientroIsNull(StatoSatellite statoSatellite);

	List<Satellite> findAllByStatoAndDataLancioLessThan(StatoSatellite statoSatellite, LocalDate dataLancio);

}