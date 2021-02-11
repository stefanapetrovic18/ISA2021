import {User} from '../auth/user';
import {Examination} from '../business/examination';
import {Pharmacy} from '../business/pharmacy';
import {WorkingHours} from '../business/working-hours';

export class Dermatologist extends User {
  vacationStart: Date;
  vacationEnd: Date;
  pharmacies: Pharmacy[];
  appointments: Examination[];
  workingHours: WorkingHours[];
  rating: number;
}
