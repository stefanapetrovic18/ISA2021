import {Patient} from '../user/patient';
import {Pharmacist} from '../user/pharmacist';
import {Pharmacy} from './pharmacy';

export class Consultation {
  id: number;
  consultationDate: Date;
  pharmacist: Pharmacist;
  pharmacy: Pharmacy;
  patient: Patient;
  duration: number;
  price: number;
}
