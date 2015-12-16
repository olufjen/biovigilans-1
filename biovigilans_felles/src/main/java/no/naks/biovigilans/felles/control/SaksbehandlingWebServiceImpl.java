package no.naks.biovigilans.felles.control;

import java.util.List;
import java.util.Map;

import no.naks.biovigilans.model.Diskusjon;
import no.naks.biovigilans.model.Melder;
import no.naks.biovigilans.model.Regionstatistikk;
import no.naks.biovigilans.model.Sak;
import no.naks.biovigilans.model.Vigilansmelding;
import no.naks.biovigilans.service.MelderTableService;
import no.naks.biovigilans.service.SaksbehandlingService;
import no.naks.framework.web.control.MasterWebServiceImpl;

/**
 * Denne klassen er WebService for saksbehandling
 * @author olj
 *
 */
public class SaksbehandlingWebServiceImpl extends MasterWebServiceImpl
		implements SaksbehandlingWebService {
	private SaksbehandlingService saksbehandlingService;
	private MelderTableService melderTableService;
	
	public SaksbehandlingWebServiceImpl() {
		super();
		 System.out.println("Saksbehandlingwebservice admin created");
	}


	public MelderTableService getMelderTableService() {
		return melderTableService;
	}


	public void setMelderTableService(MelderTableService melderTableService) {
		this.melderTableService = melderTableService;
	}


	public SaksbehandlingService getSaksbehandlingService() {
		return saksbehandlingService;
	}


	public void setSaksbehandlingService(SaksbehandlingService saksbehandlingService) {
		this.saksbehandlingService = saksbehandlingService;
	}


	@Override
	public List collectMessages() {
		
		return saksbehandlingService.collectMessages();
	}
	public List collectMessages(String start,String end){
		return saksbehandlingService.collectMessages(start, end);
	}
	public void setTimeperiodType(boolean timeperiodType) {
		 saksbehandlingService.setTimeperiodType(timeperiodType);
	}
	public List collectMessages(String types){
		return saksbehandlingService.collectMessages(types);
	}
	public List collectMessagesMarks(String merknader){
		return saksbehandlingService.collectMessagesMarks(merknader);
	}
	public Map<String,List> selectMeldinger(String meldingsNokkel){
		Map<String,List> meldinger = saksbehandlingService.selectMeldinger(meldingsNokkel);
		return meldinger;
	}

	public Map collectAnnenMeldinger(List<Vigilansmelding>meldinger){
		return saksbehandlingService.collectAnnenMeldinger(meldinger);
	}
	public void saveDiskusjon(Map<String,Diskusjon> diskusjonsMappe){
		saksbehandlingService.saveDiskusjon( diskusjonsMappe);
	}
	public void saveDiskusjon(Diskusjon diskusjon){
		saksbehandlingService.saveDiskusjon(diskusjon);
	}
	public void saveSak(Map<String,Sak> saksMappe){
		saksbehandlingService.saveSak(saksMappe);
	}
	public Map<String,List> collectDiskusjoner(Long meldeId){
		return saksbehandlingService.collectDiskusjoner(meldeId);
	}
	public List collectSaksbehandlere(){
		return saksbehandlingService.collectSaksbehandlere();
		
	}
	public Melder collectmelder(Long melderId){
		return saksbehandlingService.collectmelder(melderId);
	}
	public List<Regionstatistikk> collectRegionstatistikk(){
		return saksbehandlingService.collectRegionstatistikk();
	}
}
