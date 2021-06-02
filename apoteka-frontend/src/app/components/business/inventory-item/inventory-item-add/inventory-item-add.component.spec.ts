import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { InventoryItemAddComponent } from './inventory-item-add.component';

describe('InventoryItemAddComponent', () => {
  let component: InventoryItemAddComponent;
  let fixture: ComponentFixture<InventoryItemAddComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ InventoryItemAddComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(InventoryItemAddComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
