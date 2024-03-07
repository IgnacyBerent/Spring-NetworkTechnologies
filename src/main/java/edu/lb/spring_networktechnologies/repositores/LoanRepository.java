package edu.lb.spring_networktechnologies.repositores;

import edu.lb.spring_networktechnologies.entities.Loan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanRepository extends CrudRepository<Loan, Integer> {
}
