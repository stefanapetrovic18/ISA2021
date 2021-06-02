import {DayOfWeek} from './day-of-week';
import {Pharmacy} from './pharmacy';

export class VacationRequest {
  id: number;
  employeeID: number;
  pharmacy: Pharmacy;
  vacationStart: Date;
  vacationEnd: Date;
  accepted: boolean;
  rejected: boolean;
  rejectionReason: string;
}
