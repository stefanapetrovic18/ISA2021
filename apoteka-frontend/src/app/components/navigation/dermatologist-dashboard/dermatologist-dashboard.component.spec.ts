import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { DermatologistDashboardComponent } from './dermatologist-dashboard.component';

describe('DermatologistDashboardComponent', () => {
  let component: DermatologistDashboardComponent;
  let fixture: ComponentFixture<DermatologistDashboardComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ DermatologistDashboardComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DermatologistDashboardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
