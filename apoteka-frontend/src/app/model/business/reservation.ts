import {Patient} from '../user/patient';
import {Medicine} from './medicine';
import {Pharmacy} from './pharmacy';

export class Reservation {
  id: number;
  reservationNumber: string;
  reservationDate: Date;
  pharmacy: Pharmacy;
  patient: Patient;
  medicine: Medicine;
  collected: boolean;
}
