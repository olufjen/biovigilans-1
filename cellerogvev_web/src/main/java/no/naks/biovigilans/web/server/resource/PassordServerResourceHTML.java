package no.naks.biovigilans.web.server.resource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.restlet.Request;
import org.restlet.data.Form;
import org.restlet.data.LocalReference;
import org.restlet.data.MediaType;
import org.restlet.data.Parameter;
import org.restlet.data.Reference;
import org.restlet.ext.freemarker.TemplateRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;
import org.restlet.resource.Get;
import org.restlet.resource.Post;

import freemarker.template.SimpleScalar;
import no.naks.biovigilans.felles.model.MelderwebModel;
import no.naks.biovigilans.felles.server.resource.SessionServerResource;
import no.naks.biovigilans.model.Melder;
import no.naks.biovigilans.model.MelderImpl;

public class PassordServerResourceHTML extends SessionServerResource {
	private String meldeTxtId = "melding";

	/**
	 * getHemovigilans
	 * Denne rutinen henter inn nødvendige session objekter og  setter opp nettsiden for å ta i mot
	 * en epost adresse fra en bruker som ikke husker epost/passord
	 * @return
	 */
	@Get
	public Representation getHemovigilans() {


	     Reference reference = new Reference(getReference(),"..").getTargetRef();
	     Request request = getRequest();
	     Map<String, Object> dataModel = new HashMap<String, Object>();
	 	 String meldingsText = "";
	 	 SimpleScalar simple = new SimpleScalar(meldingsText);
		 dataModel.put( meldeTxtId,simple);
	     LocalReference pakke = LocalReference.createClapReference(LocalReference.CLAP_CLASS,
                 "/cellerogvev");
	    
	     LocalReference localUri = new LocalReference(reference);
	
	     setMelderObject();
//	     setTransfusjonsObjects();
    	 melderwebModel.setFormNames(sessionParams);
 //   	 setMelderparams();
    	 melderwebModel.distributeTerms();
    	 
	     dataModel.put(melderId, melderwebModel);
	     ClientResource clres2 = new ClientResource(LocalReference.createClapReference(LocalReference.CLAP_CLASS,"/cellerogvev/passord.html"));
	     melderwebModel =(MelderwebModel) sessionAdmin.getSessionObject(request,melderId);

	     sessionAdmin.setSessionObject(getRequest(), melderwebModel,melderId);
	        Representation pasientkomplikasjonFtl = clres2.get();
	        TemplateRepresentation  templatemapRep = new TemplateRepresentation(pasientkomplikasjonFtl,dataModel,
	                MediaType.TEXT_HTML);
		 return templatemapRep;
	}
    /**
     * storeHemovigilans
     * 
     * @param form
     * @return
     */
    @Post
    public Representation storeHemovigilans(Form form) {
    	TemplateRepresentation  templateRep = null;
 	    Map<String, Object> dataModel = new HashMap<String, Object>();
 	    String meldingsText = "Melders epost finnes ikke, prøv igjen";

	    Request request = getRequest();
	    
/*	    Map<String,List> alleMeldinger = new HashMap<String,List>();
 	    List<Vigilansmelding> meldinger = null;
 //	    List delMeldinger = null;
 	    List<Vigilansmelding> andreMeldinger = null;
 	    List<Vigilansmelding> pasientMeldinger = null;
 	    List<Vigilansmelding> giverMeldinger = null;*/
 	    
    	if(form == null){
    		invalidateSessionobjects();
    	}
/*
 * Verdier angitt av bruker    	
 */
    	String melderEpost = null;
    	String melderPassord = null;
    	String meldingsNokkel = null;
/*
 * Verdier fra database
 */
		String name ="";
		String passord = "";
		String epost = "";
    	Long melderid = null; 
    	
    	for (Parameter entry : form) {
			if (entry.getValue() != null && !(entry.getValue().equals(""))){
					System.out.println(entry.getName() + "=" + entry.getValue());
					if (entry.getName().equals("k-epost")){
						melderEpost = entry.getValue();
					}
			
			}
			
    	}
		Parameter formValue = form.getFirst("passord"); // Bruker oppgir epostadresse
//	    String page = "../hemovigilans/melder_rapport.html"; 
		if (formValue != null && melderEpost != null){
			List<Map<String, Object>> rows = melderWebService.selectMelder(melderEpost);
			Melder melder = null;
			if(rows.size() > 0){
				for(Map row:rows){
					melderid = Long.parseLong(row.get("melderid").toString());
	
					if (row.get("meldernavn") != null)
						name = row.get("meldernavn").toString();
					if (row.get("melderpassord") != null)
						passord = row.get("melderpassord").toString();
					if (row.get("melderepost") != null)
						epost = row.get("melderepost").toString();
						melder = new MelderImpl();
						melder.setMelderId(melderid);
						melder.setMeldernavn(name);
						melder.setMelderPassord(passord);
						melder.setMelderepost(epost);
						sessionAdmin.setSessionObject(request, melder, melderNokkel); 
						break;
					
				}
			}
			if (melderid != null && melder != null ){
				emailWebService.setSubject("Passord");
				passord = adminWebService.decryptMelderPassword(melder); // Tilpasset encrypted passord OLJ 25.01.18
     	    	emailWebService.setEmailText("Ditt passord er: "+passord); // Tilpasset encrypted passord OLJ 25.01.18
    	    	 emailWebService.setMailTo(melder.getMelderepost());
    	    	 emailWebService.sendEmail("");
				meldingsText = "Melding med passord er sendt til oppgitt adresse";
			}
	
	    
		}
	 SimpleScalar simple = new SimpleScalar(meldingsText);
	 dataModel.put( meldeTxtId,simple);
	
		//Feil passord går til startside.
 		ClientResource clres2 = new ClientResource(LocalReference.createClapReference(LocalReference.CLAP_CLASS,"/cellerogvev/passord.html"));
		Representation pasientkomplikasjonFtl = clres2.get();
		templateRep = new TemplateRepresentation(pasientkomplikasjonFtl, dataModel,
				MediaType.TEXT_HTML);
		return templateRep;
    }
    
}
