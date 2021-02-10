import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { OfferTableViewComponent } from './offer-table-view.component';

describe('OfferTableViewComponent', () => {
  let component: OfferTableViewComponent;
  let fixture: ComponentFixture<OfferTableViewComponent>;

  beforeEach(async(() => {
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
