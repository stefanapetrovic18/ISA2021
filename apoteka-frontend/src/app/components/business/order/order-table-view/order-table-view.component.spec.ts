import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { OrderTableViewComponent } from './order-table-view.component';

describe('OrderTableViewComponent', () => {
  let component: OrderTableViewComponent;
  let fixture: ComponentFixture<OrderTableViewComponent>;

  beforeEach(async(() => {
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
