import { Dermatologist } from "../user/dermatologist";
import { Pharmacist } from "../user/pharmacist";
import { PharmacyAdmin } from "../user/pharmacy-admin";
import { Consultation } from "./consultation";
import { Examination } from "./examination";
import { Pricelist } from "./pricelist";

export class Pharmacy {
  id: number;
  name: string;
  admins: PharmacyAdmin[];
  dermatologists: Dermatologist[];
  pharmacists: Pharmacist[];
  examinations: Examination[];
  consultations: Consultation[];
  pricelist: Pricelist;
}
