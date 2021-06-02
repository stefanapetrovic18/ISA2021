import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { PharmacyAdminDashboardComponent } from './pharmacy-admin-dashboard.component';

describe('PharmacyAdminDashboardComponent', () => {
  let component: PharmacyAdminDashboardComponent;
  let fixture: ComponentFixture<PharmacyAdminDashboardComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ PharmacyAdminDashboardComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PharmacyAdminDashboardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
