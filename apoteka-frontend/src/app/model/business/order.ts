import {OrderItem} from './order-item';
import {Pharmacy} from './pharmacy';

export class Order {
  id: number;
  items: OrderItem[];
  expiryDate: Date;
  pharmacy: Pharmacy;
}
