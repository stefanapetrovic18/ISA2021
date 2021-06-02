import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { PharmacistDashboardComponent } from './pharmacist-dashboard.component';

describe('PharmacistDashboardComponent', () => {
  let component: PharmacistDashboardComponent;
  let fixture: ComponentFixture<PharmacistDashboardComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ PharmacistDashboardComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PharmacistDashboardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
