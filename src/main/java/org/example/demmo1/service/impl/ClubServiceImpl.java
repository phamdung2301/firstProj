package org.example.demmo1.service.impl;

import org.example.demmo1.dto.ClubDto;
import org.example.demmo1.entity.Club;
import org.example.demmo1.repository.ClubRepository;
import org.example.demmo1.service.ClubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClubServiceImpl implements ClubService {

    private final ClubRepository clubRepository;

    @Autowired
    public ClubServiceImpl(ClubRepository clubRepository) {
        this.clubRepository = clubRepository;
    }

    @Override
    public List<ClubDto> findAllClub() {
        return clubRepository.findByIsDeletedFalseOrIsDeletedIsNull().stream()
                .map(this::maptoClubDto)
                .collect(Collectors.toList());
    }

    @Override
    public Club saveClub(Club club) {
        if (club.getIsDeleted() == null) {
            club.setIsDeleted(false);
        }
        return clubRepository.save(club);
    }

    @Override
    public ClubDto findClubById(long clubId) {
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy CLB id=" + clubId));
        return maptoClubDto(club);
    }

    @Override
    public void updateClub(ClubDto clubDto) {
        Club existing = clubRepository.findById(clubDto.getId())
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy CLB id=" + clubDto.getId()));
        existing.setTitle(clubDto.getTitle());
        existing.setPhotoUrl(clubDto.getPhotoUrl());
        existing.setContent(clubDto.getContent());
        clubRepository.save(existing);
    }

    @Override
    public void deleteClub(Long clubId) {
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy CLB id=" + clubId));
        club.setIsDeleted(true);
        clubRepository.save(club);
    }

    @Override
    public List<ClubDto> findAllDeletedClubs() {
        return clubRepository.findByIsDeletedTrue().stream()
                .map(this::maptoClubDto)
                .collect(Collectors.toList());
    }

    @Override
    public void restoreClub(Long clubId) {
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy CLB id=" + clubId));
        club.setIsDeleted(false);
        clubRepository.save(club);
    }

    @Override
    public List<ClubDto> searchClubs(String query) {
        return clubRepository.searchClubs(query).stream()
                .filter(c -> !Boolean.TRUE.equals(c.getIsDeleted()))
                .map(this::maptoClubDto)
                .collect(Collectors.toList());
    }

    private ClubDto maptoClubDto(Club club) {
        return ClubDto.builder()
                .id(club.getId())
                .title(club.getTitle())
                .photoUrl(club.getPhotoUrl())
                .content(club.getContent())
                .createdOn(club.getCreatedOn())
                .updatedOn(club.getUpdatedOn())
                .isDeleted(club.getIsDeleted())
                .build();
    }
}
