package it.polito.tdp.poweroutages.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import it.polito.tdp.poweroutages.DAO.PowerOutageDAO;

public class Model {
	
	PowerOutageDAO podao;
	List<PowerOutages> outages; 
	List<PowerOutages> best;
	int sommaPersone;
	
	public Model() {
		podao = new PowerOutageDAO();
		sommaPersone = 0;
	}
	
	public List<Nerc> getNercList() {
		return podao.getNercList();
	}
	
	public List<PowerOutages> cercaSequenza(int idNerc, int maxYears, int maxHours){
		
		outages = podao.getNercOutages(idNerc);
		List<PowerOutages> parziale = new ArrayList<>();
		cercaSoluzione(parziale , maxYears, maxHours);
		
		return best;
	}

	private void cercaSoluzione(List<PowerOutages> parziale, int maxYears, int maxHours) {
		
		// condizioneFinale
		if(this.sumPeople(parziale) > sommaPersone) {
			sommaPersone = this.sumPeople(parziale);
			best = new ArrayList<PowerOutages>(parziale);
		}
		
		for(PowerOutages po : outages) {
			if(isValita(parziale, po, maxYears, maxHours) && !parziale.contains(po)) {
				parziale.add(po);
				cercaSoluzione(parziale, maxYears, maxHours);
				//backtraking
				parziale.remove(po);
			}
		}	
	}
	
	private boolean isValita(List<PowerOutages> parziale, PowerOutages po, int maxYears, int maxHours) {
		
		boolean result = false;
		
		int sommaOreDisservizio = 0;
		
		for(PowerOutages poi : parziale) {
			sommaOreDisservizio += poi.getHoursOfOutage();
		}
		sommaOreDisservizio += po.getHoursOfOutage();
		
		if(sommaOreDisservizio <= maxHours && (parziale.size() == 0 || parziale.get(0).getYear() - po.getYear() <= maxYears)) {
			result = true;
		}
		
		return result;
	}

	private int sumPeople(List<PowerOutages> parziale) {
		int somma = 0;
		for(PowerOutages po : parziale) {
			somma += po.getPeopleAffected();
		}
		return somma;
	}

}
