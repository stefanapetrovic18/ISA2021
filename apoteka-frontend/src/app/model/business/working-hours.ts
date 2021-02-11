import {DayOfWeek} from './day-of-week';
import {Pharmacy} from './pharmacy';

export class WorkingHours {
  id: number;
  pharmacy: Pharmacy;
  dayOfWeek: DayOfWeek;
  shiftStart: Date;
  shiftEnd: Date;
  employeeID: number;
}
