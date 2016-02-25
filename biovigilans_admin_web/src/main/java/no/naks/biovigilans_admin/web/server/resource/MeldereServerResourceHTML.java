package no.naks.biovigilans_admin.web.server.resource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import no.naks.biovigilans.felles.model.LoginModel;
import no.naks.biovigilans.model.Melder;
import no.naks.biovigilans.model.Vigilansmelding;

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

public class MeldereServerResourceHTML extends SaksbehandlingSessionServer {

	private String utvalg = "";
	private String merknadValg = "";
	private String utvalgKey = "valgt";
	private String merknadKey = "merknader";
	private String merknadlisteKey = "merknadvalgt";
	private String meldereKey = "meldere";

	@Get
	public Representation getHemovigilans() {


	    Reference reference = new Reference(getReference(),"..").getTargetRef();
	
	    Request request = getRequest();

	    List<Melder>  meldere = saksbehandlingWebservice.collectMeldere();
	    
	    List<Vigilansmelding> meldinger = (List)sessionAdmin.getSessionObject(request, meldingsId); // For å vise tidligere valgt liste
	    if (meldinger == null)
	    	meldinger = hentMeldingene(statusflag[0]);
	     Map<String, Object> dataModel = new HashMap<String, Object>();
		 sessionAdmin.setSessionObject(request, meldinger, meldingsId);
	 	 sessionAdmin.setSessionObject(request,dobleMeldingene,dobleMeldingKey);
	 	 SimpleScalar simpleDisplay = new SimpleScalar(displayPart);
	 	 SimpleScalar simple = new SimpleScalar(utvalg);
	 	 SimpleScalar merk = new SimpleScalar(merknadValg);
	 	 dataModel.put(merknadlisteKey, merk);
	 	 dataModel.put(utvalgKey, simple);

		 dataModel.put(displayKey, simpleDisplay);
	     dataModel.put(meldeKey,meldinger);
 		 dataModel.put(statusflagKey,statusflag);
 		 dataModel.put(merknadKey, merknader);
 		 dataModel.put(meldereKey, meldere);
	     LocalReference pakke = LocalReference.createClapReference(LocalReference.CLAP_CLASS,
                 "/hemovigilans");
	    
	     LocalReference localUri = new LocalReference(reference);
	
// Denne client resource forholder seg til src/main/resource katalogen !!!	
	     ClientResource clres2 = new ClientResource(LocalReference.createClapReference(LocalReference.CLAP_CLASS,"/hemovigilans/meldere.html"));
	     
	        // Load the FreeMarker template
	        Representation pasientkomplikasjonFtl = clres2.get();

	        TemplateRepresentation  templatemapRep = new TemplateRepresentation(pasientkomplikasjonFtl,dataModel,
	                MediaType.TEXT_HTML);
		 return templatemapRep;
	 }
	  /**
     * storeHemovigilans
     * Denne rutinen tar imot meldingsnøkkel fra bruker og henter frem meidngsinformasjon basert på 
     * oppgitt meldingsnøkkel
     * Bruker kan også velge å endre listeutvalg
     * @param form
     * @return
     */
    /**
     * @param form
     * @return
     */
    @Post
    public Representation storeHemovigilans(Form form) {
        Map<String, Object> dataModel = new HashMap<String, Object>();
        Reference reference = new Reference(getReference(),"..").getTargetRef();
        Request request = getRequest();
  	    List<Vigilansmelding> meldinger = (List)sessionAdmin.getSessionObject(getRequest(), meldingsId);
  	    dobleMeldingene = (List)sessionAdmin.getSessionObject(request,dobleMeldingKey);
  	    login = (LoginModel) sessionAdmin.getSessionObject(request, loginKey);
  	    dataModel.put(meldeKey,meldinger);
  	    dataModel.put(statusflagKey,statusflag);
		 dataModel.put(merknadKey, merknader);
  		 SimpleScalar simpleDisplay = new SimpleScalar(displayPart);
  		 dataModel.put(displayKey, simpleDisplay);
  	    meldingsNokkel = null;
  	    String meldingsID = null;
  	    String utvalget = null;
  		Map<String,List> meldingDetaljene = null;
		Parameter sokMelding = form.getFirst("meldingsnokkelsok"); // Søk etter meldinger tilet gitt utvalg meldere
			if (meldingsID != null){
	    		 meldingDetaljene = (Map<String,List>)saksbehandlingWebservice.selectMeldingetternokkel(meldingsID);
	    	}
			if (meldingDetaljene != null){
	    		meldinger = (List) meldingDetaljene.get(meldingsID);
	    		meldinger = hentMeldingstyper(meldinger);
			}
			sessionAdmin.setSessionObject(request, meldinger, meldingsId);
 		 	sessionAdmin.setSessionObject(request,dobleMeldingene,dobleMeldingKey);
		 	SimpleScalar simple = new SimpleScalar(utvalg);
 		 	dataModel.put(utvalgKey, simple);
 		 	 SimpleScalar merk = new SimpleScalar(merknadValg);
 		 	 dataModel.put(merknadlisteKey, merk);
 		    dataModel.put(meldeKey,meldinger);
 	  		ClientResource clres2 = new ClientResource(LocalReference.createClapReference(LocalReference.CLAP_CLASS,"/hemovigilans/saksbehandling.html"));

   		// Load the FreeMarker template
   		Representation pasientkomplikasjonFtl = clres2.get();

   		TemplateRepresentation  templatemapRep = new TemplateRepresentation(pasientkomplikasjonFtl,dataModel,
   				MediaType.TEXT_HTML);
   		return templatemapRep;  			
 		}
    }
    
