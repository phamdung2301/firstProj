package org.example.demmo1.service;

import org.example.demmo1.dto.EventDto;
import org.example.demmo1.entity.Event;

import java.util.List;

public interface EventService {

    List<EventDto> findEventsByClubId(Long clubId);

    void saveEvent(Long clubId, Event event);

    void deleteEvent(Long eventId);

    EventDto findEventById(Long eventId);
}
