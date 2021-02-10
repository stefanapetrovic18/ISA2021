import {User} from '../auth/user';
import {Inventory} from '../business/inventory';
import {Offer} from '../business/offer';

export class Supplier extends User {
  inventory: Inventory;
  offers: Offer[];
}
