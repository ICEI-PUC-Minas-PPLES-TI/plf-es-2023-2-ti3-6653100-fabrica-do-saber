import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VacationTeamFormComponent } from './vacation-team-form.component';

describe('VacationTeamFormComponent', () => {
  let component: VacationTeamFormComponent;
  let fixture: ComponentFixture<VacationTeamFormComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [VacationTeamFormComponent]
    });
    fixture = TestBed.createComponent(VacationTeamFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
