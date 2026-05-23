package org.example.demmo1.controller;

import jakarta.validation.Valid;
import org.example.demmo1.dto.ClubDto;
import org.example.demmo1.entity.Club;
import org.example.demmo1.service.ClubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ClubController {
    private final ClubService clubService;

    @Autowired
    public ClubController(ClubService clubService) {
        this.clubService = clubService;
    }

    @GetMapping("/clubs")
    public String listClubs(Model model) {
        List<ClubDto> clubs = clubService.findAllClub();
        model.addAttribute("clubs", clubs);
        model.addAttribute("pageTitle", "Danh sách Câu lạc bộ");
        return "pages/club-list";
    }

    @GetMapping("/clubs/new")
    public String createClubForm(Model model) {
        model.addAttribute("club", new Club());
        model.addAttribute("pageTitle", "Thêm Câu lạc bộ mới");
        return "pages/club-create";
    }



    @GetMapping("/clubs/{clubId}")
    public String viewClub(@PathVariable("clubId") Long clubId, Model model) {
        ClubDto club = clubService.findClubById(clubId);
        model.addAttribute("club", club);
        model.addAttribute("pageTitle", club.getTitle());
        return "pages/club-view";
    }

    @GetMapping("/clubs/{clubId}/edit")
    public String editClubForm(@PathVariable("clubId") long clubId, Model model) {
        ClubDto club = clubService.findClubById(clubId);
        model.addAttribute("club", club);
        model.addAttribute("pageTitle", "Chỉnh sửa: " + club.getTitle());
        return "pages/club-edit";
    }

    @PostMapping("/clubs/{clubId}/edit")
    public String updateClub(@PathVariable("clubId") Long clubId,
                             @Valid @ModelAttribute("club") ClubDto club,
                             BindingResult result,
                             Model model) {
        if (result.hasErrors()) {
            model.addAttribute("pageTitle", "Chỉnh sửa Câu lạc bộ");
            return "pages/club-edit";
        }
        club.setId(clubId);
        clubService.updateClub(club);
        return "redirect:/clubs?success";
    }

    @PostMapping("/clubs/new")
    public String saveClub(@Valid @ModelAttribute("club") Club club,
                           BindingResult result,
                           Model model) {
        if (result.hasErrors()) {
            model.addAttribute("pageTitle", "Thêm Câu lạc bộ mới");
            return "pages/club-create";
        }
        clubService.saveClub(club);
        return "redirect:/clubs?success";
    }

    @PostMapping("/clubs/delete/{clubId}")
    public String deleteClub(@PathVariable("clubId") Long clubId) {
        ClubDto club = clubService.findClubById(clubId);
        boolean wasDeleted = Boolean.TRUE.equals(club.getIsDeleted());
        clubService.deleteClub(clubId);
        if (wasDeleted) {
            return "redirect:/clubs/trash?success";
        }
        return "redirect:/clubs?success";
    }

    @GetMapping("/clubs/trash")
    public String listDeletedClubs(Model model) {
        List<ClubDto> clubs = clubService.findAllDeletedClubs();
        model.addAttribute("clubs", clubs);
        model.addAttribute("pageTitle", "Thùng rác Câu lạc bộ");
        return "pages/club-trash";
    }

    @PostMapping("/clubs/restore/{clubId}")
    public String restoreClub(@PathVariable("clubId") Long clubId) {
        clubService.restoreClub(clubId);
        return "redirect:/clubs/trash?success";
    }

    @GetMapping("/clubs/search")
    public String searchClubs(@RequestParam(value = "query") String query, Model model) {
        List<ClubDto> clubs = clubService.searchClubs(query);
        model.addAttribute("clubs", clubs);
        model.addAttribute("pageTitle", "Tìm kiếm Câu lạc bộ");
        return "pages/club-list";
    }
}
