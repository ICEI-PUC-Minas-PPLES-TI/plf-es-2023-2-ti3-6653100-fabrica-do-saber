import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReligionSelectComponent } from './religion-select.component';

describe('ReligionSelectComponent', () => {
  let component: ReligionSelectComponent;
  let fixture: ComponentFixture<ReligionSelectComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ReligionSelectComponent]
    });
    fixture = TestBed.createComponent(ReligionSelectComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
