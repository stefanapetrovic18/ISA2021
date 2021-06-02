import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { InventoryEditComponent } from './inventory-edit.component';

describe('InventoryEditComponent', () => {
  let component: InventoryEditComponent;
  let fixture: ComponentFixture<InventoryEditComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ InventoryEditComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(InventoryEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
