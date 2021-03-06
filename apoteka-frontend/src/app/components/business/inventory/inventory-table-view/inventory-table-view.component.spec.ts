import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { InventoryTableViewComponent } from './inventory-table-view.component';

describe('InventoryTableViewComponent', () => {
  let component: InventoryTableViewComponent;
  let fixture: ComponentFixture<InventoryTableViewComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ InventoryTableViewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(InventoryTableViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
