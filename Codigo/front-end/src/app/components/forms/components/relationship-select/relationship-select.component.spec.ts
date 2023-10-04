import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RelationshipSelectComponent } from './relationship-select.component';

describe('RelationshipSelectComponent', () => {
  let component: RelationshipSelectComponent;
  let fixture: ComponentFixture<RelationshipSelectComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [RelationshipSelectComponent]
    });
    fixture = TestBed.createComponent(RelationshipSelectComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
