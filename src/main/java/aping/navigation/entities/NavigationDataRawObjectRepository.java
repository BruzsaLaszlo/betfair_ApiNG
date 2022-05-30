package aping.navigation.entities;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NavigationDataRawObjectRepository extends JpaRepository<RawObject, Long> {
}
