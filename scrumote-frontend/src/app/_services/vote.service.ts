﻿import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Vote} from '../_models';

@Injectable()
export class VoteService {

  private planningBaseUrl = '/plannings/';
  private issueBaseUrl = '/issues/';
  private baseUrl = '/votes';

  constructor(private http: HttpClient) {
  }

  createVote(planningId: number, issueId: number, vote: Vote) {
    return this.http.post<Vote>(
        this.planningBaseUrl + planningId + this.issueBaseUrl + issueId + this.baseUrl, vote);
  }

  getVotesForIssue(planningId: number, issueId: number) {
    return this.http.get<Vote[]>(
        this.planningBaseUrl + planningId + this.issueBaseUrl + issueId + this.baseUrl, {
          params: {
            iteration: '0'
          }
        });
  }

  checkIfMyVoteExists(planningId: number, issueId: number, iteration: number) {
    return this.http.get<boolean>(
        this.planningBaseUrl + planningId + this.issueBaseUrl + issueId + this.baseUrl + '/my', {
          params: {
            iteration: iteration.toString()
          }
        });
  }
}
