package elxrojo.account_service.external.feign;

import elxrojo.account_service.model.DTO.TransactionDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "transaction-service", url = "http://localhost:8086")
public interface IFeignTransactionRepository {

    @GetMapping("/transactions/{accountId}")
    List<TransactionDTO> getTransactionsByAccount(@PathVariable Long accountId, @RequestParam(required = false) Integer limit);


    @GetMapping("/transactions/{transactionId}/account/{accountId}")
    TransactionDTO getTransactionByAccount(@PathVariable Long accountId, @PathVariable Long transactionId);

    @PostMapping("/transactions/create")
    ResponseEntity<?> create(@RequestParam Float amount,
                             @RequestParam(required = false) Integer activityType,
                             @RequestParam int transactionType,
                             @RequestParam(required = false)  String origin,
                             @RequestParam(required = false)  String name,
                             @RequestParam(required = false)  String destination,
                             @RequestParam Long accountId);
}
