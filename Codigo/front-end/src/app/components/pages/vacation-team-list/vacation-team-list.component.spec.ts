import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VacationTeamListComponent } from './vacation-team-list.component';

describe('VacationTeamListComponent', () => {
  let component: VacationTeamListComponent;
  let fixture: ComponentFixture<VacationTeamListComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [VacationTeamListComponent]
    });
    fixture = TestBed.createComponent(VacationTeamListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
