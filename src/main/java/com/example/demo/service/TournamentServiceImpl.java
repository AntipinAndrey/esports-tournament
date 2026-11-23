package com.example.demo.service;

import com.example.demo.dto.TournamentRequest;
import com.example.demo.dto.TournamentResponse;
import com.example.demo.exception.DuplicateResourceException;
import com.example.demo.exception.InvalidRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Organizer;
import com.example.demo.model.Tournament;
import com.example.demo.repository.OrganizerRepository;
import com.example.demo.repository.TournamentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TournamentServiceImpl implements TournamentService {
    private final TournamentRepository tournamentRepository;
    private final OrganizerRepository organizerRepository;

    @Override
    public List<TournamentResponse> findAll(String status, String discipline) {
        List<Tournament> tournaments;
        boolean hasStatus = status != null && !status.isBlank();
        boolean hasDiscipline = discipline != null && !discipline.isBlank();
        if (hasStatus && hasDiscipline) {
            tournaments = tournamentRepository
                    .findByStatusIgnoreCaseAndDisciplineIgnoreCase(
                            status,
                            discipline
                    );
        } else if (hasStatus) {
            tournaments = tournamentRepository.findByStatusIgnoreCase(status);
        } else if (hasDiscipline) {
            tournaments = tournamentRepository
                    .findByDisciplineIgnoreCase(discipline);
        } else {
            tournaments = tournamentRepository.findAll();
        }
        return tournaments.stream().map(this::toResponse).toList();
    }

    @Override
    public TournamentResponse findByName(String tournamentName) {
        return toResponse(getTournament(tournamentName));
    }

    @Override
    @Transactional
    public TournamentResponse create(TournamentRequest request) {
        if (tournamentRepository.existsById(request.tournamentName())) {
            throw new DuplicateResourceException(
                    "Tournament already exists: " + request.tournamentName(),
                    "Use another tournamentName or update the existing tournament with PUT."
            );
        }
        Organizer organizer = getOrganizer(request.organizerName());
        Tournament tournament = new Tournament(
                request.tournamentName(),
                request.discipline(),
                request.startDate(),
                request.endDate(),
                request.prizePool(),
                request.status(),
                organizer
        );
        return toResponse(tournamentRepository.save(tournament));
    }

    @Override
    @Transactional
    public TournamentResponse update(
            String tournamentName,
            TournamentRequest request
    ) {
        Tournament tournament = getTournament(tournamentName);
        if (!tournamentName.equals(request.tournamentName())) {
            throw new InvalidRequestException(
                    "Path tournamentName must match request tournamentName",
                    "Use the same tournamentName in the URL and in the JSON body."
            );
        }
        tournament.setDiscipline(request.discipline());
        tournament.setStartDate(request.startDate());
        tournament.setEndDate(request.endDate());
        tournament.setPrizePool(request.prizePool());
        tournament.setStatus(request.status());
        tournament.setOrganizer(getOrganizer(request.organizerName()));
        return toResponse(tournamentRepository.save(tournament));
    }

    @Override
    @Transactional
    public void delete(String tournamentName) {
        tournamentRepository.delete(getTournament(tournamentName));
    }

    private Tournament getTournament(String tournamentName) {
        return tournamentRepository.findById(tournamentName)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Tournament not found: " + tournamentName,
                        "Check tournamentName or first send GET /api/tournaments to see available tournaments."
                ));
    }

    private Organizer getOrganizer(String organizerName) {
        return organizerRepository.findById(organizerName)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Organizer not found: " + organizerName,
                        "Create the organizer first or check organizerName with GET /api/organizers."
                ));
    }

    private TournamentResponse toResponse(Tournament tournament) {
        String organizerName = tournament.getOrganizer() == null
                ? null
                : tournament.getOrganizer().getOrganizerName();
        return new TournamentResponse(
                tournament.getTournamentName(),
                tournament.getDiscipline(),
                tournament.getStartDate(),
                tournament.getEndDate(),
                tournament.getPrizePool(),
                tournament.getStatus(),
                organizerName,
                tournament.getMatches().size()
        );
    }
}
