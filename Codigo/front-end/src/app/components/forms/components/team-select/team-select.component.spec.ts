import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TeamSelectComponent } from './team-select.component';

describe('TeamSelectComponent', () => {
  let component: TeamSelectComponent;
  let fixture: ComponentFixture<TeamSelectComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TeamSelectComponent]
    });
    fixture = TestBed.createComponent(TeamSelectComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
