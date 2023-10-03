import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TeacherSelectComponent } from './teacher-select.component';

describe('TeacherSelectComponent', () => {
  let component: TeacherSelectComponent;
  let fixture: ComponentFixture<TeacherSelectComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TeacherSelectComponent]
    });
    fixture = TestBed.createComponent(TeacherSelectComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
