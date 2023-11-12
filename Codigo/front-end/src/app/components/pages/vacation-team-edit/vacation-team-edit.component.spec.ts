import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VacationTeamEditComponent } from './vacation-team-edit.component';

describe('VacationTeamEditComponent', () => {
  let component: VacationTeamEditComponent;
  let fixture: ComponentFixture<VacationTeamEditComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [VacationTeamEditComponent]
    });
    fixture = TestBed.createComponent(VacationTeamEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
