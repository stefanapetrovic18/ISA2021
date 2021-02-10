import {User} from '../auth/user';
import {Inventory} from '../business/inventory';

export class Supplier extends User {
  inventory: Inventory;
}
