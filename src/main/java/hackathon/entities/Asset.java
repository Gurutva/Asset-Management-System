package hackathon.entities;

import java.sql.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "assets")
public class Asset {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "allocated")
    private int allocated;
    
    @Column(name = "brand")
    private String brand;

    @Column(name = "model")
    private String model;

    @Column(name = "ram")
    private String ram;
    
    @Column(name = "rom")
    private String rom;
  
    @Column(name = "graphics")
    private String graphics;
    
    @Column(name = "status")
    private int status=1;
    
    @Column(name = "allocated_id")
    private long allocated_id;
    
    
    @Column(name = "confirm_survey")
    private int confirm_survey=0;
    
    public int getConfirm_survey() {
		return confirm_survey;
	}

	public void setConfirm_survey(int confirm_survey) {
		this.confirm_survey = confirm_survey;
	}

	public long getAllocated_id() {
		return allocated_id;
	}

	public void setAllocated_id(long allocated_id) {
		this.allocated_id = allocated_id;
	}

	@Basic
    Date allocated_date;
    

	public Date getAllocated_date() {
		return allocated_date;
	}


	public void setAllocated_date(Date allocated_date) {
		this.allocated_date = allocated_date;
	}


	public Date getExpected_return_date() {
		return expected_return_date;
	}

	public void setExpected_return_date(Date expected_return_date) {
		this.expected_return_date = expected_return_date;
	}

	@Basic
    Date expected_return_date;
    
    
    public int getStatus() {
		return status;
	}
    
    
	public void setStatus(int status) {
		this.status = status;
	}
	
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	

	public int getAllocated() {
		return allocated;
	}

	public void setAllocated(int allocated) {
		this.allocated = allocated;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getRam() {
		return ram;
	}

	public void setRam(String ram) {
		this.ram = ram;
	}

	public String getRom() {
		return rom;
	}

	public void setRom(String rom) {
		this.rom = rom;
	}

	public String getGraphics() {
		return graphics;
	}

	public void setGraphics(String graphics) {
		this.graphics = graphics;
	} 
    
}
