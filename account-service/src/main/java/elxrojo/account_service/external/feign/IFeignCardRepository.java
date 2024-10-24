package elxrojo.account_service.external.feign;

import elxrojo.account_service.model.DTO.CardDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@FeignClient(name = "card-service", url = "http://localhost:8087")
public interface IFeignCardRepository {

    @PostMapping("/cards/")
    void createCard(@RequestBody CardDTO card);

    @GetMapping("/cards/{cardNumber}/number")
    Optional<CardDTO> getCardByNumber(@PathVariable String cardNumber);

}
