/* tslint:disable:no-unused-variable */
import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {PatientTableViewComponent} from './patient-table-view.component';

describe('PatientTableViewComponent', () => {
  let component: PatientTableViewComponent;
  let fixture: ComponentFixture<PatientTableViewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [PatientTableViewComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PatientTableViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
