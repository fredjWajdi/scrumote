package com.adach.scrumote.api;

import com.adach.scrumote.configuration.api.PrefixedRestController;
import com.adach.scrumote.dto.simple.VoteSimpleDto;
import com.adach.scrumote.service.external.IssueExternalService;
import com.adach.scrumote.service.external.VoteExternalService;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@PrefixedRestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class VoteController extends AbstractController {

  private final VoteExternalService voteExternalService;
  private final IssueExternalService issueExternalService;

  @PreAuthorize("hasAnyAuthority('createVote')")
  @PostMapping("/plannings/{planningId}/issues/{issueId}/votes")
  public ResponseEntity<?> createVote(@PathVariable Long planningId, @PathVariable Long issueId,
      @RequestBody @Valid VoteSimpleDto dto) {
    Long newVoteId = voteExternalService.createVote(planningId, issueId, dto);
    issueExternalService.deactivateIssueIfAllUsersVoted(issueId, newVoteId);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @PreAuthorize("hasAnyAuthority('getVotesForIssue')")
  @GetMapping("/plannings/{planningId}/issues/{issueId}/votes")
  public List<VoteSimpleDto> getVotesForIssue(@PathVariable Long planningId,
      @PathVariable Long issueId, @RequestParam Integer iteration) {
    return voteExternalService.getVotesForIssue(planningId, issueId, iteration);
  }
}