import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { OrderItemDeleteComponent } from './order-item-delete.component';

describe('OrderItemDeleteComponent', () => {
  let component: OrderItemDeleteComponent;
  let fixture: ComponentFixture<OrderItemDeleteComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ OrderItemDeleteComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(OrderItemDeleteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
