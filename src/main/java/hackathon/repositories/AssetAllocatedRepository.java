package hackathon.repositories;

import java.util.List;
import java.util.Optional;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import hackathon.entities.AssetAllocated;


@Repository
public interface AssetAllocatedRepository extends JpaRepository<AssetAllocated, Long> {
	@Query(nativeQuery = true, value="SELECT * FROM asset_allocated WHERE asset_allocated.asset_id= ?1")
	List <AssetAllocated> findAssetAlloctedDetails(long id);
	
	@Query(nativeQuery = true, value="SELECT * FROM asset_allocated WHERE asset_allocated.employee_id= ?1")
	List <AssetAllocated> findAssetAlloctedDetailsUsingEmpId(int id);
	
}
