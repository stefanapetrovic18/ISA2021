import {User} from '../auth/user';
import {Consultation} from '../business/consultation';
import {Pharmacy} from '../business/pharmacy';
import {WorkingHours} from '../business/working-hours';

export class Pharmacist extends User {
  vacationStart: Date;
  vacationEnd: Date;
  pharmacy: Pharmacy;
  consultations: Consultation[];
  workingHours: WorkingHours[];
  rating: number;
}
