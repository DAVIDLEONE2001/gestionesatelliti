package it.prova.gestionesatelliti.utility;

import java.time.LocalDate;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import it.prova.gestionesatelliti.model.Satellite;
import it.prova.gestionesatelliti.model.StatoSatellite;

public class BindingResultUtils {

	
	public static BindingResult bindErrorsSatellite(Satellite satellite, BindingResult result) {
		if (satellite.getDataLancio() == null && satellite.getDataRientro() != null) {
			result.addError(new FieldError("insert_satellite_attr", "dataRientro", "Attenzione data di lancio vuota"));
		}
		if (satellite.getDataLancio() != null && satellite.getDataRientro() != null) {
			if (satellite.getDataLancio().isAfter(satellite.getDataRientro())) {
				result.addError(new FieldError("insert_satellite_attr", "dataRientro",
						"Attenzione rientro antecedente a lancio"));
			}
		}
		if (satellite.getStato() != StatoSatellite.DISATTIVATO && satellite.getStato() != null
				&& satellite.getDataLancio() == null) {
			result.addError(new FieldError("insert_satellite_attr", "stato", "Non puo essere attivo se non lanciato"));
		}
		if (satellite.getDataLancio() != null) {
			if (satellite.getStato() != null && satellite.getDataLancio().isAfter(LocalDate.now())) {
				result.addError(
						new FieldError("insert_satellite_attr", "stato", "Non puo avere stato se non lanciato"));
			}
		}
		return result;
	}
	
	public BindingResultUtils() {
		// TODO Auto-generated constructor stub
	}

}
