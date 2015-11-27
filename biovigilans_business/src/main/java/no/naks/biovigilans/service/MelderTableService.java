package no.naks.biovigilans.service;

import java.util.List;
import java.util.Map;

import no.naks.biovigilans.model.Melder;
import no.naks.biovigilans.model.Vigilansmelding;

public interface MelderTableService {
	
	public void saveMelder(Melder melder);
	public List selectMelder(String epost);
	public Map<String,List> selectMeldinger(String meldingsNokkel);
	public Map collectAnnenMeldinger(List<Vigilansmelding>meldinger);
}
