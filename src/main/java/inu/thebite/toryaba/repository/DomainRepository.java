package inu.thebite.toryaba.repository;

import inu.thebite.toryaba.entity.Domain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DomainRepository extends JpaRepository<Domain, Long> {

}