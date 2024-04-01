package edu.lb.spring_networktechnologies.mappings;

import edu.lb.spring_networktechnologies.infrastructure.dtos.loan.GetLoanDto;
import edu.lb.spring_networktechnologies.infrastructure.entities.LoanEntity;

public class MapLoan {
    public static GetLoanDto toGetLoanDto(LoanEntity loanEntity) {
        return new GetLoanDto(
                loanEntity.getId(),
                loanEntity.getLoanDate(),
                loanEntity.getDueDate(),
                MapUser.toGetUserDto(loanEntity.getUser()),
                MapBook.toGetBookDto(loanEntity.getBook())
        );
    }
}
