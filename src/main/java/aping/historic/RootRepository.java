package aping.historic;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RootRepository extends JpaRepository<Root, Long> {
}
