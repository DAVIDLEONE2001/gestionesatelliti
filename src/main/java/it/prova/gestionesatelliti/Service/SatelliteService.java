package it.prova.gestionesatelliti.Service;

import java.util.List;

import it.prova.gestionesatelliti.model.Satellite;

public interface SatelliteService {
	public List<Satellite> listAllElements();

	public Satellite caricaSingoloElemento(Long id);

	public void aggiorna(Satellite satelliteInstance);

	public void inserisciNuovo(Satellite satelliteInstance);

	public void rimuovi(Long idSatellite);

	public List<Satellite> findByExample(Satellite example);

	public void lanciaSatellite(Long id);

	public void ritornaSatellite(Long id);

	public List<Satellite> lanciatiDa2AnniOPiuAttivi();
	
	public List<Satellite> disattivatiInOrbita();
	
	public List<Satellite> lanciatiDa10AnniFissi();

	List<Satellite> lanciatiAttivi();
	
	void proceduraEmergenza();

}
