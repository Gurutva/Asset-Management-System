package hackathon.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import hackathon.entities.AssetAllocated;
import hackathon.entities.User;


public interface UserRepository extends JpaRepository<User, Integer>
{
	
	Optional<User> findByEmail(String email);
	
	@Query(nativeQuery = true, value="SELECT * FROM users WHERE users.email= ?1")
	List <User> findEmployeeDetails(String email);
	
	
	@Query(nativeQuery = true, value="SELECT * FROM users WHERE users.id= ?1")
	List <User> findEmailId(int id);
	

}
