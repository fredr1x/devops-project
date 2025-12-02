package pp.devopsfinal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pp.devopsfinal.entity.LogAction;

@Repository
public interface LogActionRepository extends JpaRepository<LogAction, Long> {

}
