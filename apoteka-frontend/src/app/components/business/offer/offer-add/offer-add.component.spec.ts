import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { OfferAddComponent } from './offer-add.component';

describe('OfferAddComponent', () => {
  let component: OfferAddComponent;
  let fixture: ComponentFixture<OfferAddComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ OfferAddComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(OfferAddComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
