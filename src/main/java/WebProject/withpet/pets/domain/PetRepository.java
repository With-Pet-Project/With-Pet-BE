package WebProject.withpet.pets.domain;

import WebProject.withpet.users.domain.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {
    List<Pet> findAllByUser(User user);

    boolean existsByName(String name);
}
