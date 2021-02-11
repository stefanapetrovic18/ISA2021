/* tslint:disable:no-unused-variable */
import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {PharmacistTableViewComponent} from './pharmacist-table-view.component';

describe('PharmacistTableViewComponent', () => {
  let component: PharmacistTableViewComponent;
  let fixture: ComponentFixture<PharmacistTableViewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [PharmacistTableViewComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PharmacistTableViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
