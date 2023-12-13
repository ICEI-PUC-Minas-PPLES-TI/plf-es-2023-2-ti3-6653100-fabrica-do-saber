import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VacationTeamCreateComponent } from './vacation-team-create.component';

describe('VacationTeamCreateComponent', () => {
  let component: VacationTeamCreateComponent;
  let fixture: ComponentFixture<VacationTeamCreateComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [VacationTeamCreateComponent]
    });
    fixture = TestBed.createComponent(VacationTeamCreateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
