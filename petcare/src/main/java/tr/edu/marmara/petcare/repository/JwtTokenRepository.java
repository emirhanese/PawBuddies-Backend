package tr.edu.marmara.petcare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tr.edu.marmara.petcare.model.JwtToken;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface JwtTokenRepository extends JpaRepository<JwtToken, Integer> {
    @Query("""
select t from JwtToken t inner join User u on t.user.id = u.id
where t.user.id = :userId and t.loggedOut = false
""")
    List<JwtToken> findAllTokensByUser(UUID userId);

    Optional<JwtToken> findByToken(String token);
}
