import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { InventoryItemDeleteComponent } from './inventory-item-delete.component';

describe('InventoryItemDeleteComponent', () => {
  let component: InventoryItemDeleteComponent;
  let fixture: ComponentFixture<InventoryItemDeleteComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ InventoryItemDeleteComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(InventoryItemDeleteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
