package no.naks.biovigilans.dao;

import java.util.List;
import java.util.Map;

import no.naks.biovigilans.model.Diskusjon;
import no.naks.biovigilans.model.Melder;
import no.naks.biovigilans.model.Sak;
import no.naks.rammeverk.kildelag.dao.TablesUpdateImpl;

public interface SakDAO {
	public void saveDiskusjon(Map<String,Diskusjon> diskusjonsMappe);
	public void saveDiskusjon(Diskusjon diskusjon);
	public void saveSak(Map<String,Sak> saksMappe);
	public String getInsertDiskusjonSQL();

	public void setInsertDiskusjonSQL(String insertDiskusjonSQL);

	public String getUpdateDiskusjonSQL();

	public void setUpdateDiskusjonSQL(String updateDiskusjonSQL);

	public String getDiskusjonPrimaryKey();

	public void setDiskusjonPrimaryKey(String diskusjonPrimaryKey);

	public String[] getDiskusjonprimarykeyTableDefs();

	public void setDiskusjonprimarykeyTableDefs(
			String[] diskusjonprimarykeyTableDefs);
	public String getSelectenDiskusjonSQL();
	public void setSelectenDiskusjonSQL(String selectenDiskusjonSQL);
	public String getInsertSakSQL();
	public void setInsertSakSQL(String insertSakSQL);
	public String getUpdateSakSQL();
	public void setUpdateSakSQL(String updateSakSQL);
	public String getSakPrimaryKey();
	public void setSakPrimaryKey(String sakPrimaryKey);
	public String[] getSakprimarykeyTableDefs();
	public void setSakprimarykeyTableDefs(String[] sakprimarykeyTableDefs);

	public String[] getFlaggNames();

	public void setFlaggNames(String[] flaggNames);
	public Map<String,List> collectDiskusjoner(Long meldeId);
	public Diskusjon collectDiskusjon(Long diskId);
	public String getSelectDiskusjonSQL();
	public void setSelectDiskusjonSQL(String selectDiskusjonSQL);
	public String getSelectSakSQL();
	public void setSelectSakSQL(String selectSakSQL);
	public String[] getDiskusjonTableDefs();
	public void setDiskusjonTableDefs(String[] diskusjonTableDefs);
	public String[] getSakTableDefs();
	public void setSakTableDefs(String[] sakTableDefs);
	public List collectSaksbehandlere();
	public String getSelectmelderSQL();
	public void setSelectmelderSQL(String selectmelderSQL);
	public String[] getMelderTableDefs();
	public void setMelderTableDefs(String[] melderTableDefs);
	public Melder collectMelder(Long melderId);
}
