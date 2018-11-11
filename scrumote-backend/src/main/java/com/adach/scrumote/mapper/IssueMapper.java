package com.adach.scrumote.mapper;

import com.adach.scrumote.configuration.dto.SimpleDtoTypeMap;
import com.adach.scrumote.dto.simple.IssueSimpleDto;
import com.adach.scrumote.entity.Issue;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class IssueMapper extends AbstractSimpleDtoMapper<Issue, IssueSimpleDto> {

  public IssueMapper(ModelMapper modelMapper, SimpleDtoTypeMap simpleDtoTypeMap) {
    super(modelMapper, simpleDtoTypeMap);
  }
}