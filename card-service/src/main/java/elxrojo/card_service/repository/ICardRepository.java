package elxrojo.card_service.repository;

import elxrojo.card_service.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICardRepository extends JpaRepository<Card,Long> {
}
