package org.example.demmo1.repository;

import org.example.demmo1.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findByClub_IdOrderByStartTimeDesc(Long clubId);
}
