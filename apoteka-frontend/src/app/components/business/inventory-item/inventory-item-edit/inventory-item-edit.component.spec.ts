import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { InventoryItemEditComponent } from './inventory-item-edit.component';

describe('InventoryItemEditComponent', () => {
  let component: InventoryItemEditComponent;
  let fixture: ComponentFixture<InventoryItemEditComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ InventoryItemEditComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(InventoryItemEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
