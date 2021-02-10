import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { OrderItemDeleteComponent } from './order-item-delete.component';

describe('OrderItemDeleteComponent', () => {
  let component: OrderItemDeleteComponent;
  let fixture: ComponentFixture<OrderItemDeleteComponent>;

  beforeEach(async(() => {
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
