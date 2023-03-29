package WebProject.withpet.pets.domain;

import WebProject.withpet.users.domain.User;
import org.springframework.stereotype.Repository;

@Repository
public interface PetRepositoryCustom {
    boolean isDuplicateNamePet(User user, String petName);
}
