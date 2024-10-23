package elxrojo.account_service.repository;

import elxrojo.account_service.model.Account;
import elxrojo.account_service.model.DTO.AccountDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface IAccountMapper {
    Account updateAccount(AccountDTO accountDTO, @MappingTarget Account account);
}
