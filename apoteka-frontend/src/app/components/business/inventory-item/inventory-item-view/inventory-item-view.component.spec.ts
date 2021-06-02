import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { InventoryItemViewComponent } from './inventory-item-view.component';

describe('InventoryItemViewComponent', () => {
  let component: InventoryItemViewComponent;
  let fixture: ComponentFixture<InventoryItemViewComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ InventoryItemViewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(InventoryItemViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
