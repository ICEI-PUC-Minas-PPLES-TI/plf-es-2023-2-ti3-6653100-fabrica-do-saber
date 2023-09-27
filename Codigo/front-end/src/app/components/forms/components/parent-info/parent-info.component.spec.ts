import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ParentInfoComponent } from './parent-info.component';

describe('GuardianInfoComponent', () => {
  let component: ParentInfoComponent;
  let fixture: ComponentFixture<ParentInfoComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ParentInfoComponent]
    });
    fixture = TestBed.createComponent(ParentInfoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
