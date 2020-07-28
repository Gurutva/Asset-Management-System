package hackathon.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hackathon.entities.Asset;
import hackathon.entities.Report;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
	
}
