import { User } from "../auth/user";
import { Examination } from "../business/examination";
import { Pharmacy } from "../business/pharmacy";

export class Dermatologist extends User {
  pharmacies: Pharmacy[];
  appointments: Examination[];
}
