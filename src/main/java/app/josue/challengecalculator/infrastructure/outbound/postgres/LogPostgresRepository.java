package app.josue.challengecalculator.infrastructure.outbound.postgres;

import app.josue.challengecalculator.infrastructure.outbound.postgres.entity.LogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface LogPostgresRepository extends JpaRepository<LogEntity, Long> {

}
