import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { OrderTableViewComponent } from './order-table-view.component';

describe('OrderTableViewComponent', () => {
  let component: OrderTableViewComponent;
  let fixture: ComponentFixture<OrderTableViewComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ OrderTableViewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(OrderTableViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
