package org.example.demmo1.service;

import org.example.demmo1.dto.ClubDto;
import org.example.demmo1.entity.Club;

import java.util.List;

public interface ClubService {

    List<ClubDto> findAllClub();

    Club saveClub(Club club);

    ClubDto findClubById(long clubId);

    void updateClub(ClubDto club);

    void deleteClub(Long clubId);

    List<ClubDto> findAllDeletedClubs();

    void restoreClub(Long clubId);

    List<ClubDto> searchClubs(String query);
}
