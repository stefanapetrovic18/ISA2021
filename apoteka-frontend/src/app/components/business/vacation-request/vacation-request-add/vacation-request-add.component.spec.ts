import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { VacationRequestAddComponent } from './vacation-request-add.component';

describe('VacationRequestAddComponent', () => {
  let component: VacationRequestAddComponent;
  let fixture: ComponentFixture<VacationRequestAddComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ VacationRequestAddComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(VacationRequestAddComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
