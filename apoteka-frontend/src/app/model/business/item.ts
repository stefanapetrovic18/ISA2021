import {Medicine} from './medicine';
import {Pricelist} from './pricelist';

export class Item {
  id: number;
  medicine: Medicine;
  price: number;
  quantity: number;
  pricelist: Pricelist;
}
