
package za.ac.cput.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.ac.cput.entity.Statement;

@Repository
public interface StatementRepository extends JpaRepository<Statement, String> {
}

