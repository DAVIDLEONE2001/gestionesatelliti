package it.prova.gestionesatelliti.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import it.prova.gestionesatelliti.model.Satellite;
import it.prova.gestionesatelliti.model.StatoSatellite;

public interface SatelliteRepository extends CrudRepository<Satellite, Long>, JpaSpecificationExecutor<Satellite> {

	List<Satellite> findAllByStatoInAndDataLancioLessThan(Iterable<StatoSatellite> stati, LocalDate dataLancio);

	List<Satellite> findAllByStatoInAndDataLancioIsNotNull(Iterable<StatoSatellite> stati);

	List<Satellite> findAllByStatoAndDataRientroIsNull(StatoSatellite statoSatellite);

	List<Satellite> findAllByStatoAndDataLancioLessThan(StatoSatellite statoSatellite, LocalDate dataLancio);

	@Modifying
	@Query("update Satellite s set s.stato = :newStato, s.dataRientro = :newDataRientro where s.stato in :stati and s.dataLancio is not null")
	int updateStatoAndDataRientroByStatiAndDataLancioIsNotNull(@Param("newStato") StatoSatellite newStato,
			@Param("newDataRientro") LocalDate newDataRientro, @Param("stati") Iterable<StatoSatellite> stati);
}
