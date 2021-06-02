import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { OrderItemTableViewComponent } from './order-item-table-view.component';

describe('OrderItemTableViewComponent', () => {
  let component: OrderItemTableViewComponent;
  let fixture: ComponentFixture<OrderItemTableViewComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ OrderItemTableViewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(OrderItemTableViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
