import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StudentSelectComponent } from './student-select.component';

describe('StudentsSelectComponent', () => {
  let component: StudentSelectComponent;
  let fixture: ComponentFixture<StudentSelectComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [StudentSelectComponent]
    });
    fixture = TestBed.createComponent(StudentSelectComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
