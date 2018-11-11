package com.adach.scrumote.service.external;

import com.adach.scrumote.dto.simple.PlanningSimpleDto;
import com.adach.scrumote.entity.Planning;
import com.adach.scrumote.exception.planning.PlanningAlreadyFinishedException;
import com.adach.scrumote.exception.planning.PlanningNotFoundException;
import com.adach.scrumote.mapper.PlanningMapper;
import com.adach.scrumote.repository.PlanningRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PlanningPublicService {

  private final PlanningRepository planningRepository;
  private final PlanningMapper planningMapper;

  public void finish(Long id) {
    Planning planning = findIfExists(id);
    validateNotFinished(planning);
    planning.setFinished(true);
    planningRepository.save(planning);
  }

  private Planning findIfExists(Long id) {
    Optional<Planning> planningOpt = planningRepository.findById(id);
    if (planningOpt.isPresent()) {
      return planningOpt.get();
    } else {
      throw new PlanningNotFoundException(String.format("Planning with id %d does not exist.", id));
    }
  }

  private void validateNotFinished(Planning planning) {
    if (planning.isFinished()) {
      throw new PlanningAlreadyFinishedException(
          String.format("Planning with id %d is already finished.", planning.getId()));
    }
  }

  public List<PlanningSimpleDto> findAll() {
    return planningRepository.findAll().stream().map(planningMapper::mapToSimpleDto)
        .collect(Collectors.toList());
  }
}