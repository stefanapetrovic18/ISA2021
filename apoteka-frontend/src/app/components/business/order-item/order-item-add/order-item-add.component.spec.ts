import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { OrderItemAddComponent } from './order-item-add.component';

describe('OrderItemAddComponent', () => {
  let component: OrderItemAddComponent;
  let fixture: ComponentFixture<OrderItemAddComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ OrderItemAddComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(OrderItemAddComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
