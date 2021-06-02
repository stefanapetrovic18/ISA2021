import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { OfferTableViewComponent } from './offer-table-view.component';

describe('OfferTableViewComponent', () => {
  let component: OfferTableViewComponent;
  let fixture: ComponentFixture<OfferTableViewComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ OfferTableViewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(OfferTableViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
