import { Medicine } from "./medicine";
import { Pharmacy } from "./pharmacy";

export class Stockpile {
    id: number;
    medicine: Medicine;
    quantity: number;
    pharmacy: Pharmacy;
}
