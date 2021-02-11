import {User} from '../auth/user';
import {Pharmacy} from '../business/pharmacy';

export class PharmacyAdmin extends User {
  pharmacy: Pharmacy;
}
