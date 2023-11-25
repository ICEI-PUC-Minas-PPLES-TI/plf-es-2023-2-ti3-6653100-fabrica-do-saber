import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FormationSelectComponent } from './formation-select.component';

describe('FormationSelectComponent', () => {
  let component: FormationSelectComponent;
  let fixture: ComponentFixture<FormationSelectComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [FormationSelectComponent]
    });
    fixture = TestBed.createComponent(FormationSelectComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
