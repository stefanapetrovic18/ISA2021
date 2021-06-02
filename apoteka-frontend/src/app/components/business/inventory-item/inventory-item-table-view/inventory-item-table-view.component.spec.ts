import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { InventoryItemTableViewComponent } from './inventory-item-table-view.component';

describe('InventoryItemTableViewComponent', () => {
  let component: InventoryItemTableViewComponent;
  let fixture: ComponentFixture<InventoryItemTableViewComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ InventoryItemTableViewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(InventoryItemTableViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
