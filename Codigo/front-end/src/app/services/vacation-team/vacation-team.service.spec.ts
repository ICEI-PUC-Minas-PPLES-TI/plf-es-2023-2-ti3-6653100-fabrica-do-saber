import { TestBed } from '@angular/core/testing';

import { VacationTeamService } from './vacation-team.service';

describe('VacationTeamService', () => {
  let service: VacationTeamService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(VacationTeamService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
