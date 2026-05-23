package org.example.demmo1.controller;

import jakarta.validation.Valid;
import org.example.demmo1.dto.ClubDto;
import org.example.demmo1.dto.EventDto;
import org.example.demmo1.entity.Event;
import org.example.demmo1.service.ClubService;
import org.example.demmo1.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/clubs/{clubId}/events")
public class EventController {

    private final EventService eventService;
    private final ClubService clubService;

    @Autowired
    public EventController(EventService eventService, ClubService clubService) {
        this.eventService = eventService;
        this.clubService = clubService;
    }

    @GetMapping
    public String listEvents(@PathVariable Long clubId, Model model) {
        ClubDto club = clubService.findClubById(clubId);
        List<EventDto> events = eventService.findEventsByClubId(clubId);
        model.addAttribute("club", club);
        model.addAttribute("events", events);
        model.addAttribute("pageTitle", "Sự kiện - " + club.getTitle());
        return "pages/event-list";
    }

    @GetMapping("/new")
    public String createEventForm(@PathVariable Long clubId, Model model) {
        ClubDto club = clubService.findClubById(clubId);
        model.addAttribute("club", club);
        model.addAttribute("event", new Event());
        model.addAttribute("pageTitle", "Thêm sự kiện - " + club.getTitle());
        return "pages/event-create";
    }

    @PostMapping
    public String saveEvent(@PathVariable Long clubId,
                            @Valid @ModelAttribute("event") Event event,
                            BindingResult result,
                            Model model) {
        if (result.hasErrors()) {
            ClubDto club = clubService.findClubById(clubId);
            model.addAttribute("club", club);
            model.addAttribute("pageTitle", "Thêm sự kiện - " + club.getTitle());
            return "pages/event-create";
        }
        eventService.saveEvent(clubId, event);
        return "redirect:/clubs/" + clubId + "/events?success";
    }

    @PostMapping("/{eventId}/delete")
    public String deleteEvent(@PathVariable Long clubId,
                              @PathVariable Long eventId) {
        eventService.deleteEvent(eventId);
        return "redirect:/clubs/" + clubId + "/events?deleted";
    }

    @GetMapping("/{eventId}")
    public String viewEventDetail(@PathVariable Long clubId,
                                  @PathVariable Long eventId,
                                  Model model) {
        EventDto event = eventService.findEventById(eventId);
        ClubDto club = clubService.findClubById(clubId);
        model.addAttribute("club", club);
        model.addAttribute("event", event);
        model.addAttribute("pageTitle", "Chi tiết sự kiện - " + event.getName());
        return "pages/event-detail";
    }
}
