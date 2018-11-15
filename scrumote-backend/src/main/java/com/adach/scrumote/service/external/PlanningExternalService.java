package com.adach.scrumote.service.external;

import com.adach.scrumote.configuration.transaction.RequiresNewTransactions;
import com.adach.scrumote.dto.complex.PlanningWithUsersDto;
import com.adach.scrumote.dto.simple.PlanningSimpleDto;
import com.adach.scrumote.entity.Deck;
import com.adach.scrumote.entity.Planning;
import com.adach.scrumote.entity.User;
import com.adach.scrumote.exception.planning.PlanningAlreadyFinishedException;
import com.adach.scrumote.mapper.PlanningMapper;
import com.adach.scrumote.service.internal.DeckInternalService;
import com.adach.scrumote.service.internal.PlanningInternalService;
import com.adach.scrumote.service.internal.UserHistoryInternalService;
import com.adach.scrumote.service.internal.UserInternalService;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiresNewTransactions
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PlanningExternalService {

  private final PlanningInternalService internalService;
  private final PlanningMapper mapper;

  private final UserHistoryInternalService userHistoryInternalService;
  private final DeckInternalService deckInternalService;
  private final UserInternalService userInternalService;

  public PlanningWithUsersDto findById(Long id) {
    Planning planning = internalService.findById(id);
    return mapper.mapToDtoWithUsers(planning);
  }

  public List<PlanningSimpleDto> findAll() {
    return internalService.findAll().stream().map(mapper::mapToSimpleDto)
        .collect(Collectors.toList());
  }

  public Long create(PlanningWithUsersDto dto) {
    Deck deck = deckInternalService.findById(dto.getDeckId());
    Set<User> users = new HashSet<>(userInternalService.findByIds(dto.getUserIds()));
    Planning planning = mapper.mapToEntity(dto);
    planning.setFinished(false);
    planning.setDeck(deck);
    planning.setUsers(users);
    return internalService.save(planning).getId();
  }

  public void update(Long id, PlanningWithUsersDto dto) {
    Planning planning = internalService.findById(id);
    Deck deck = deckInternalService.findById(dto.getDeckId());
    Set<User> users = new HashSet<>(userInternalService.findByIds(dto.getUserIds()));
    planning.setCode(dto.getCode());
    planning.setName(dto.getName());
    planning.setDescription(dto.getDescription());
    planning.setDeck(deck);
    planning.setUsers(users);
  }

  public void finish(Long id) {
    Planning planning = internalService.findById(id);
    validateNotFinished(planning);
    planning.setFinished(true);
    userHistoryInternalService.updateUsersHistoryForPlanning(planning);
  }

  public void delete(Long id) {
    internalService.deleteById(id);
  }

  private void validateNotFinished(Planning planning) {
    if (planning.isFinished()) {
      throw new PlanningAlreadyFinishedException(
          String.format("Planning with id %d is already finished.", planning.getId()));
    }
  }
}
