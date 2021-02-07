import { User } from "../auth/user";
import { Consultation } from "../business/consultation";
import { Pharmacy } from "../business/pharmacy";
import { WorkingHours } from "../business/working-hours";

export class Pharmacist extends User {
  vacationStart: Date;
  vacationEnd: Date;
  pharmacy: Pharmacy;
  consultations: Consultation[];
  workingHours: WorkingHours[];
  rating: number;
  convert(pharmacist: Pharmacist, user: User): Pharmacist {
    pharmacist.username = user.username;
    pharmacist.password = user.password;
    pharmacist.forename = user.forename;
    pharmacist.surname = user.surname;
    pharmacist.address = user.address;
    pharmacist.city = user.city;
    pharmacist.country = user.country;
    pharmacist.phone = user.phone;
    pharmacist.enabled = user.enabled;
    pharmacist.validated = user.validated;
    pharmacist.passwordChanged = user.passwordChanged;
    return pharmacist;
  }
}
