import {Order} from './order';
import {Supplier} from '../user/supplier';

export class Offer {
  id: number;
  order: Order;
  supplier: Supplier;
  price: number;
  shippingDate: Date;
}
