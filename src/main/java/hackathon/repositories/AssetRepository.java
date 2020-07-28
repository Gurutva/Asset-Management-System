package hackathon.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import hackathon.entities.Asset;
import hackathon.entities.AssetAllocated;

@Repository
public interface AssetRepository extends JpaRepository<Asset, Long> {
	
	@Query(nativeQuery = true, value="SELECT * FROM asset")
	List <Asset> findAllAssets();
	
	@Query(nativeQuery = true, value="SELECT * FROM assets WHERE assets.allocated= ?1")
	List <Asset> findEmployeeSurvey(int id);
}
