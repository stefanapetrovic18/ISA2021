import { DermatologistRating } from "./dermatologist-rating";
import { PharmacistRating } from "./pharmacist-rating";

export class RatingReport {
  pharmacyRating: number;
  pharmacistRatings: PharmacistRating[];
  dermatologistRatings: DermatologistRating[];
}
