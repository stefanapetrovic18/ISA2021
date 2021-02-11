import {Item} from './item';

export class Pricelist {
  items: Item[];
  id: number;
  validFrom: Date;
  validUntil: Date;
}
