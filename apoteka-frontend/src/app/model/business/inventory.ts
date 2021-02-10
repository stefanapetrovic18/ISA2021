import {InventoryItem} from './inventory-item';
import {Pharmacy} from './pharmacy';
import {Supplier} from '../user/supplier';

export class Inventory {
  id: number;
  items: InventoryItem[];
  expiryDate: Date;
  supplier: Supplier;
}
