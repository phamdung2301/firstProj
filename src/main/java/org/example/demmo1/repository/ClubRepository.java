package org.example.demmo1.repository;

import org.example.demmo1.dto.ClubDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.example.demmo1.entity.Club;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ClubRepository extends JpaRepository<Club, Long> {
    Optional<Club> findByTitle(String url);
    java.util.List<Club> findByIsDeletedFalseOrIsDeletedIsNull();
    java.util.List<Club> findByIsDeletedTrue();
    @Query("SELECT c from Club c WHERE c.title LIKE CONCAT('%', :query, '%')")
    List<Club> searchClubs(String query);
}
