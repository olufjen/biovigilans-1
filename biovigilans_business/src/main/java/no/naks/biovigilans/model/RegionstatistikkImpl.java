package no.naks.biovigilans.model;

/**
 * Implementasjonen av AbstractRegionstatistikk
 * @author olj
 *
 */
public class RegionstatistikkImpl extends AbstractRegionstatistikk implements
		Regionstatistikk {

	public RegionstatistikkImpl() {
		super();
		setAntall(new Double(0));
		setRegion("");
	}

}
