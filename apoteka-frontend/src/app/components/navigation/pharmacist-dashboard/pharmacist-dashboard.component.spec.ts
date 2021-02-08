import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PharmacistDashboardComponent } from './pharmacist-dashboard.component';

describe('PharmacistDashboardComponent', () => {
  let component: PharmacistDashboardComponent;
  let fixture: ComponentFixture<PharmacistDashboardComponent>;

  beforeEach(async(() => {
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
