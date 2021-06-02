import {User} from '../auth/user';
import {Consultation} from '../business/consultation';
import {Examination} from '../business/examination';
import {Medicine} from '../business/medicine';
import { Pharmacy } from '../business/pharmacy';

export class Patient extends User {
  points: number;
  examinations: Examination[];
  consultations: Consultation[];
  allergies: Medicine[];
  subscriptions: Pharmacy[];
}
