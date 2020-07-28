package hackathon.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "report_details")
public class Report {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "asset_allocated_id")
    private int asset_allocated_id;
    
    @Column(name = "report")
    private String report;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getAsset_allocated_id() {
		return asset_allocated_id;
	}

	public void setAsset_allocated_id(int asset_allocated_id) {
		this.asset_allocated_id = asset_allocated_id;
	}

	public String getReport() {
		return report;
	}

	public void setReport(String report) {
		this.report = report;
	}

}
