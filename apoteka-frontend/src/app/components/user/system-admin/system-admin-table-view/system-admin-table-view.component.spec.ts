/* tslint:disable:no-unused-variable */
import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import {SystemAdminTableViewComponent} from './system-admin-table-view.component';

describe('SystemAdminTableViewComponent', () => {
  let component: SystemAdminTableViewComponent;
  let fixture: ComponentFixture<SystemAdminTableViewComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [SystemAdminTableViewComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SystemAdminTableViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
