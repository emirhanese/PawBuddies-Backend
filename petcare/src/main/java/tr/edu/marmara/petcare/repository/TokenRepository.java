package tr.edu.marmara.petcare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tr.edu.marmara.petcare.model.Token;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Integer> {

    Optional<Token> findByToken(String token);
}
