package org.example.demmo1.service.impl;

import org.example.demmo1.dto.EventDto;
import org.example.demmo1.entity.Club;
import org.example.demmo1.entity.Event;
import org.example.demmo1.repository.ClubRepository;
import org.example.demmo1.repository.EventRepository;
import org.example.demmo1.service.EventService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final ClubRepository clubRepository;

    public EventServiceImpl(EventRepository eventRepository, ClubRepository clubRepository) {
        this.eventRepository = eventRepository;
        this.clubRepository = clubRepository;
    }

    @Override
    public List<EventDto> findEventsByClubId(Long clubId) {
        return eventRepository.findByClub_IdOrderByStartTimeDesc(clubId).stream()
                .map(this::mapToEventDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void saveEvent(Long clubId, Event event) {
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy CLB id=" + clubId));
        event.setClub(club);
        eventRepository.save(event);
    }

    @Override
    public void deleteEvent(Long eventId) {
        eventRepository.deleteById(eventId);
    }

    @Override
    public EventDto findEventById(Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy sự kiện id=" + eventId));
        return mapToEventDto(event);
    }

    private EventDto mapToEventDto(Event event) {
        EventDto.EventDtoBuilder builder = EventDto.builder()
                .id(event.getId())
                .name(event.getName())
                .startTime(event.getStartTime())
                .endTime(event.getEndTime())
                .createdOn(event.getCreatedOn());

        if (event.getClub() != null) {
            builder.clubId(event.getClub().getId())
                    .clubTitle(event.getClub().getTitle());
        }
        return builder.build();
    }

}
