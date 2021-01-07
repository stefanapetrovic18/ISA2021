import { User } from "../auth/user";
import { Consultation } from "../business/consultation";
import { Pharmacy } from "../business/pharmacy";

export class Pharmacist extends User {
  pharmacy: Pharmacy;
  consultations: Consultation[];
}
