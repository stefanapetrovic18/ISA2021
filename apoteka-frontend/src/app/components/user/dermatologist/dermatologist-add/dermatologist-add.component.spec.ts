/* tslint:disable:no-unused-variable */
import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {DermatologistAddComponent} from './dermatologist-add.component';

describe('DermatologistAddComponent', () => {
  let component: DermatologistAddComponent;
  let fixture: ComponentFixture<DermatologistAddComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [DermatologistAddComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DermatologistAddComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
