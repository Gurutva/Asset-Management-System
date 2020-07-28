package hackathon.service;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import hackathon.entities.Asset;
import hackathon.entities.AssetAllocated;
import hackathon.entities.Report;
import hackathon.entities.User;
import hackathon.repositories.AssetAllocatedRepository;
import hackathon.repositories.AssetRepository;
import hackathon.repositories.ReportRepository;
import hackathon.repositories.UserRepository;


@Service
public class AssetServiceImpl implements AssetServices{
	@Autowired
    private AssetRepository assetRepository;
	
	@Autowired
	private AssetAllocatedRepository assetAllocatedRepository;
	
	@Autowired
	private ReportRepository reportRepository;



	@Override
	public List < Asset > getAllAssets() {
		
		return assetRepository.findAll();
	}



	@Override
	public void saveAsset(Asset asset) {
		  this.assetRepository.save(asset);
		
	}
	
	
	@Override
	public Asset getAssetById(long id) {
	    Optional < Asset > optional = assetRepository.findById(id);
	    Asset asset = null;
	    if (optional.isPresent()) {
	        asset = optional.get();
	    } else {
	        throw new RuntimeException(" Asset not found for id :: " + id);
	    }
	    return asset;
	}

	 @Override
	 public Page<Asset> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
	  Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
	   Sort.by(sortField).descending();
	  
	  Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
	  return this.assetRepository.findAll(pageable);
	 }

	 @Autowired
	    private UserRepository userRepository;

	@Override
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}



	@Override
	public void saveUser(User user) {
		 this.userRepository.save(user);
		
	}



	@Override
	public User getUserById(long id) {
		Optional < User > optional = userRepository.findById((int) id);
	    User user = null;
	    if (optional.isPresent()) {
	        user = optional.get();
	    } else {
	        throw new RuntimeException(" Employee not found for id :: " + id);
	    }
	    return user;
	}



	@Override
	public Page<User> findPaginatedUser(int pageNo, int pageSize, String sortField, String sortDirection) {
	 Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
	  Sort.by(sortField).descending();
	 
	 Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
	 return this.userRepository.findAll(pageable);
	}



	@Override
	public void saveAssetAllocated(AssetAllocated assetAllocated) {
		this.assetAllocatedRepository.save(assetAllocated);
		
	}



	@Override
	public AssetAllocated getAssetAllocatedById(long id) {
		Optional < AssetAllocated > optional = assetAllocatedRepository.findById(id);
		AssetAllocated assetAllocated = null; 
		if (optional.isPresent()) {
			assetAllocated = optional.get();
	    } else {
	        throw new RuntimeException(" Asset not found for id :: " + id);
	    }
	    return assetAllocated;
	}

	@Autowired
    private AssetAllocatedRepository repository;
	@Autowired
    private UserRepository repository1;

	@Override
	public List<AssetAllocated> findAssetAlloctedDetails(long id) {
		 List<AssetAllocated> assetsdetails = (List<AssetAllocated>) repository.findAssetAlloctedDetails(id);
	        return assetsdetails;
	}



	@Override
	public List<User> findEmployeeDetails(String email) {
		 List<User> userdetails = (List<User>) repository1.findEmployeeDetails(email);
		return userdetails;
	}



	@Override
	public List<AssetAllocated> findAssetAlloctedDetailsUsingEmpId(int id) {
		List<AssetAllocated> assetsdetailsempid = (List<AssetAllocated>) repository.findAssetAlloctedDetailsUsingEmpId(id);
        return assetsdetailsempid;
	}



	@Override
	public List<User> findEmailId(int id) {
		List<User> emailUser = (List<User>) repository1.findEmailId(id);
        return emailUser;
	}





	@Override
	public void saveReport(Report report) {
		  this.reportRepository.save(report);
		
	}



	@Override
	public List<Report> getAllReports() {
		return reportRepository.findAll();
	}



	@Override
	public List<Asset> findAllAssets() {
		return assetRepository.findAll();
	}



	@Override
	public List<Asset> findEmployeeSurvey(int id) {
		return assetRepository.findEmployeeSurvey(id);
	}
	

	
	
	
	

	
}
