package WebProject.withpet.pets.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HealthCareRepository extends JpaRepository<HealthCare, Long>, HealthCareRepositoryCustom {
}
