package hungphan.shorten.AuthenService.Repository;

import hungphan.shorten.AuthenService.Entity.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<TokenEntity, Integer> {
}