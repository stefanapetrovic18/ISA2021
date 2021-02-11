import {Pharmacy} from './pharmacy';

export class Promotion {
  id: number;
  startDate: Date;
  endDate: Date;
  title: string;
  description: string;
  pharmacy: Pharmacy;
}
