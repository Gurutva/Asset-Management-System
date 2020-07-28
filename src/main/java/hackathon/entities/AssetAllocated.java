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
@Table(name = "asset_allocated")
public class AssetAllocated {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "asset_id")
    private long asset_id;
    
    @Column(name = "employee_id")
    private int employee_id;

  
    @Column(name = "status")
    private int status=1;
    
    @Basic
    Date expected_return_date;

    @Basic
    Date return_date=Date.valueOf("9999-12-31");
    
    @Basic
    Date allocated_date;
    
    @Column(name = "remark")
    private String remark;
    
    @Column(name = "is_confirm")
    private int is_confirm=0;
    

	public int getIs_confirm() {
		return is_confirm;
	}

	public void setIs_confirm(int is_confirm) {
		this.is_confirm = is_confirm;
	}

	public Date getReturn_date() {
		return return_date;
	}

	public void setReturn_date(Date return_date) {
		this.return_date = return_date;
	}

	

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getAsset_id() {
		return asset_id;
	}

	public void setAsset_id(long asset_id) {
		this.asset_id = asset_id;
	}

	public int getEmployee_id() {
		return employee_id;
	}

	public void setEmployee_id(int employee_id) {
		this.employee_id = employee_id;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	

	public Date getExpected_return_date() {
		return expected_return_date;
	}

	public void setExpected_return_date(Date expected_return_date) {
		this.expected_return_date = expected_return_date;
	}

	public Date getAllocated_date() {
		return allocated_date;
	}

	public void setAllocated_date(Date allocated_date) {
		this.allocated_date = allocated_date;
	}
    

    
}
