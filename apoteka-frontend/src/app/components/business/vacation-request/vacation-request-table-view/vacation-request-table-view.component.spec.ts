import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { VacationRequestTableViewComponent } from './vacation-request-table-view.component';

describe('VacationRequestTableViewComponent', () => {
  let component: VacationRequestTableViewComponent;
  let fixture: ComponentFixture<VacationRequestTableViewComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ VacationRequestTableViewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(VacationRequestTableViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
