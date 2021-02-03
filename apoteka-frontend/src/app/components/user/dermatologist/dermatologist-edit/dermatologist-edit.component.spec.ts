/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

import { DermatologistEditComponent } from './dermatologist-edit.component';

describe('DermatologistEditComponent', () => {
  let component: DermatologistEditComponent;
  let fixture: ComponentFixture<DermatologistEditComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DermatologistEditComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DermatologistEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
