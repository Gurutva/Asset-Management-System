package hackathon.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Query;

import hackathon.entities.Asset;
import hackathon.entities.AssetAllocated;
import hackathon.entities.Report;
import hackathon.entities.User;


public interface AssetServices {
	List <Asset> getAllAssets();
	
	
	
	 void saveAsset(Asset asset);
	 Asset getAssetById(long id);
	 Page < Asset > findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);
	 
//	@Query("SELECT * FROM users INNER JOIN user_role ON users.id=user_role.user_id WHERE user_role.role_id='2';")
	 List<User> getAllUsers();
	 
	 void saveUser(User user);
	 User getUserById(long id);
	 
	 Page<User> findPaginatedUser(int pageNo, int pageSize, String sortField, String sortDirection);
	 
	 void saveAssetAllocated(AssetAllocated assetAllocated);
	 AssetAllocated getAssetAllocatedById(long id);
	 
	 List <AssetAllocated> findAssetAlloctedDetails(long id);
	 List <User> findEmployeeDetails(String email);
	 
	 List <AssetAllocated> findAssetAlloctedDetailsUsingEmpId(int id);
	 
	 List <User> findEmailId(int id);
	 
	 void saveReport(Report report);
	 
	 List<Report> getAllReports();
	 
		List <Asset> findAllAssets();
		
		List <Asset> findEmployeeSurvey(int id);
	
}
